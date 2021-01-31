package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Product;

public interface ProductService
{
    // Given a complete Product obj, save the product obj in the db
    // If a primary key is provided, the record is completely replaced
    // If no primary key is provided, one is auto generated and the record is added to the database
    Product save(Product product);
}
