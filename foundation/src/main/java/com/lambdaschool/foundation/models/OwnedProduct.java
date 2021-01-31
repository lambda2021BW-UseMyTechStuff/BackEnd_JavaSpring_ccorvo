package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ownedproduct")
@IdClass(OwnedProductId.class)
public class OwnedProduct extends Auditable implements Serializable
{
    // ------------------ Association Fields -------------------
    /**
     * 1/2 of the primary key (long) for OwnedProduct.
     * Also is a foreign key in the User tables.
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "product",
        allowSetters = true)
    private User user;

    /**
     * 1/2 of the primary key (long) for OwnedProduct.
     * Also is a foreign key in the Product tables.
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "productid")
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Product product;

    // ------------------ Constructor ------------------
    public OwnedProduct()
    {
        // constructor to be used with JPA
    }

    // constructor with parameters
    public OwnedProduct(
        @NotNull User user,
        @NotNull Product product)
    {
        this.user = user;
        this.product = product;
    }

    // ------------------ Association Getters and Setters --------------
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    // ---------------- Additional Methods -------------
    // Since we are implementing Serializable we need to add hashCode() and equals()

    @Override
    public boolean equals(Object o)
    {
       if(this == o)
       {
           return true;
       }
       if(!(o instanceof OwnedProduct))
       {
           return false;
       }
       OwnedProduct that = (OwnedProduct) o;
       return ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
           ((product == null) ? 0 : product.getProductid()) == ((that.product == null) ? 0 : that.product.getProductid());
    }

    @Override
    public int hashCode()
    {
        return 37; // so equals() gets triggered
    }
}
