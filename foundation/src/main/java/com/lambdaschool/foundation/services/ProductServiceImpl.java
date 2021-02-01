package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "productService")
public class ProductServiceImpl implements ProductService
{
    // connects this service to the product model
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HelperFunctions helperFunctions;


    // --------------- Business Logic for GET -------------------------------------
    @Override
    public List<Product> findAll()
    {
       List<Product> productList = new ArrayList<>();

        // findAll products and return an iterator set
        // iterate over the iterator set and add each element to an array list.
       productRepository.findAll()
       .iterator()
       .forEachRemaining(productList::add);

       return productList;
    }

    // Find a Product obj by Id
    @Override
    public Product findProductById(long productid)
    {
        return productRepository.findById(productid)
            .orElseThrow(() -> new ResourceNotFoundException("Product id " + productid + " not found."));
    }

    @Override
    public List<Product> findProductByNameLike(String productNameSubString)
    {
        List<Product> productList = productRepository.findByProductNameContainingIgnoreCase(productNameSubString);

        return productList;
    }

    @Override
    public List<Product> findProductByCategory(String category)
    {
        List<Product> productList = productRepository.findByCategory(category);
        return productList;
    }

    // --------------- Business Logic for POST, PUT, PATCH, DELETE ----------------
    @Transactional
    @Override
    public Product update(
        Product product,
        long productid)
    {
        Product currentProduct = productRepository.findById(productid)
            .orElseThrow(()-> new ResourceNotFoundException("Product " + productid + " not found."));

        if(helperFunctions.isAuthorizedToUpdateProduct(productid))
        {
            if (product.getProductName() != null)
            {
                currentProduct.setProductName(product.getProductName());
            }

            if (product.getBrandName() != null)
            {
                currentProduct.setBrandName(product.getBrandName());
            }

            if (product.getDescription() != null)
            {
                currentProduct.setDescription(product.getDescription());
            }

            if (product.getProductImageUrl() != null)
            {
                currentProduct.setProductImageUrl(product.getProductImageUrl());
            }

            if (product.hasValueForPricePerDay)
            {
                currentProduct.setPricePerDay(product.getPricePerDay());
            }

            if (product.hasValueForPricePerWeek)
            {
                currentProduct.setPricePerWeek(product.getPricePerWeek());
            }

            if (product.getCategory() != null)
            {
                currentProduct.setCategory(product.getCategory());
            }

            return productRepository.save(currentProduct);
        }
        else
        {
            // We should never get to this line but it is needed for the compiler
            // inorder to recognize that this exception can be thrown
            throw new ResourceNotFoundException("This user is not authorized to make change.");
        }
    }

    @Transactional
    @Override
    public Product save(Product product)
    {
        Product newProduct = new Product();

        if(product.getProductid() !=0)
        {
            productRepository.findById(product.getProductid())
                .orElseThrow(()-> new ResourceNotFoundException("Product Id " + product.getProductid() + " not found."));
            newProduct.setProductid(product.getProductid());
        }

        // adding values from the passed product object into our newly created newProduct obj
        newProduct.setProductName(product.getProductName());
        newProduct.setBrandName(product.getBrandName());
        newProduct.setDescription(product.getDescription());
        newProduct.setProductImageUrl(product.getProductImageUrl());
        newProduct.setPricePerDay(product.getPricePerDay());
        newProduct.setPricePerWeek(product.getPricePerWeek());
        newProduct.setCategory(product.getCategory());



        return productRepository.save(newProduct);
    }

    @Transactional
    @Override
    public void delete(long productid)
    {
        if(productRepository.findById(productid).isPresent())
        {
            productRepository.deleteById(productid);
        }
        else
        {
            throw new ResourceNotFoundException("Product id " + productid + " not found.");
        }
    }


    @Override
    public void deleteAllProducts()
    {
        productRepository.deleteAll();
    }
}
