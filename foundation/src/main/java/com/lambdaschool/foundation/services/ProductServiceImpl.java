package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "productService")
public class ProductServiceImpl implements ProductService
{
    // connects this service to the product model
    @Autowired
    private ProductRepository productRepository;



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
}
