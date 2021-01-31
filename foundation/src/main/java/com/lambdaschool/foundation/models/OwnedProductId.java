package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OwnedProductId implements Serializable
{
    // ---------- Class fields ---------------
    /**
     * The id of the user
     */
    private long user;

    /**
     * The id of the product
     */
    private long product;

    // ----------- Constructors -------------------
    public OwnedProductId()
    {
        // default constructor to be used with JPA
    }

    // -------------- Getters and Setters -------------
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

    // ------------------ Additional Methods -------------------
    // Need to override equals() and hashCode() because we are implementing Serializable
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
        OwnedProductId that = (OwnedProductId) o;
        return user == that.user &&
            product == that.product;
    }

    @Override
    public int hashCode()
    {
        return 37; // so equals() is triggered
    }
}
