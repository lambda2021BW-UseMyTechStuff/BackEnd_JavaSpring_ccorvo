package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Product;

import java.util.List;

public interface ProductService
{

    // ------------- These connect to our various GET endpoints ---------------------------------

    List<Product> findAll();

    Product findProductById(long id);

    List<Product> findProductByNameLike(String productNameSubString);

    List<Product> findProductByCategory(String category);

    // Stretch Endpoints
    // + list by zipcode
    // + list by amount per day
    // + list by amount per week

    // ------------ These connect to our POST, PUT, PATCH, DELETE endpoints ------------------

    // Given a complete Product obj, save the product obj in the db
    // If a primary key is provided, the record is completely replaced
    // If no primary key is provided, one is auto generated and the record is added to the database
    Product save(Product product);

    // Updates the provided fields in the product record referenced by a productid
    Product update(Product product, long id);

    // delete a product record with a given productid
    void delete(long id);

    void deleteAllProducts();
}
