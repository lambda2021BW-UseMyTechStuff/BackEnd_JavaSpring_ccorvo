package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    // ------------------- GET Requests ----------------------
    // GET a list of all Products
    @GetMapping(value = "/products",
    produces = {"application/json"})
    public ResponseEntity<?> listAllProducts()
    {
        List<Product> myProducts = productService.findAll();
        return new ResponseEntity<>(myProducts, HttpStatus.OK);
    }

    // Return a product object with a given id
    @GetMapping(value = "/product/{productId}",
    produces = {"application/json"})
    public ResponseEntity<?> getProductById(
        @PathVariable
        long productId)
    {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // GET a list of all products with a given substring in their name
    @GetMapping(value = "/products/namelike/{productNameSubString}",
    produces = {"application/json"})
    public ResponseEntity<?> listProductByNameLike(
        @PathVariable
        String productNameSubString)
    {
        List<Product> myProducts = productService.findProductByNameLike(productNameSubString);
        return new ResponseEntity<>(myProducts, HttpStatus.OK);
    }

    // GET a list of all products with a certain category type
    @GetMapping(value = "/products/category/{category}",
    produces = {"application/json"})
    public ResponseEntity<?> findProductByCategoryName(
        @PathVariable
        String category)
    {
        List<Product> myProducts = productService.findProductByCategory(category);
        return new ResponseEntity<>(myProducts, HttpStatus.OK);
    }

    // ------------------- POST Request ----------------------
    // Create a new Product obj
    @PostMapping(value = "/product",
    consumes = {"application/json"},
    produces = {"application/json"})
    public ResponseEntity<?> addNewProduct(
        @Valid
        @RequestBody
        Product newProduct)
    {
        newProduct = productService.save(newProduct);

        // set the location header for the newly created truck
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProductURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{productid")
            .buildAndExpand(newProduct.getProductid())
            .toUri();
        responseHeaders.setLocation(newProductURI);

        return new ResponseEntity<>(null,responseHeaders, HttpStatus.CREATED);
    }
    // ------------------- PUT Request -----------------------
    @PutMapping(value = "/product/{productid}")
    public ResponseEntity<?> updateFullProduct(
        @RequestBody
        Product updateProduct,
        @PathVariable
        long productid)
    {
        updateProduct.setProductid(productid);
        productService.save(updateProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // ------------------- PATCH Request ---------------------
    @PatchMapping(value = "/product/{productid}",
    consumes = {"application/json"})
    public ResponseEntity<?> updateProduct(
        @RequestBody
            Product updateProduct,
        @PathVariable
        long productid)
    {
        productService.update(updateProduct, productid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // ------------------- DELETE Request --------------------
    @DeleteMapping(value = "/product/{productid}")
    public ResponseEntity<?> deleteProductById(
        @PathVariable
        long productid)
    {
        productService.delete(productid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
