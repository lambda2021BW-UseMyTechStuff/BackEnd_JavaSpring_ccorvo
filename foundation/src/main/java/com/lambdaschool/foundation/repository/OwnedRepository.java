package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.OwnedProduct;
import com.lambdaschool.foundation.models.OwnedProductId;
import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.models.User;
import org.springframework.data.repository.CrudRepository;

public interface OwnedRepository extends CrudRepository<OwnedProduct, OwnedProductId>
{
    OwnedProduct findByUser_UseridAndProduct_Productid(long user, long product);
}
