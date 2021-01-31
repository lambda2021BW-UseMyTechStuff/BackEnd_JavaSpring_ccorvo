package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RentedProductId implements Serializable
{
    /**
     *  The id of the user
     */
    private long user;

    /**
     * The id of the Product
     */
    private long product;

    // ---------------- Constructor ------------------
    public RentedProductId()
    {
        // constructor to be used with JPA
    }

    // ---------------- Getters and Setters -----------
    public long getUser()
    {
        return user;
    }

    public void setUser(long user)
    {
        this.user = user;
    }

    public long getProduct()
    {
        return product;
    }

    public void setProduct(long product)
    {
        this.product = product;
    }

    // ------------- Additional Methods -------------
    // Since we are using Serializable we need to add the equals() and hashCode() methods
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        RentedProductId that = (RentedProductId) o;
        return user == that.user &&
            product == that.product;
    }

    @Override
    public int hashCode()
    {
        return 37; // use 37 so that it always triggers equals()
    }
}
