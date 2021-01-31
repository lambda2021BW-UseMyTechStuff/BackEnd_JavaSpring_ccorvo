package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>
{
    List<Product> findByProductNameContainingIgnoreCase(String productname);

    List<Product> findByCategory(String category);

}
