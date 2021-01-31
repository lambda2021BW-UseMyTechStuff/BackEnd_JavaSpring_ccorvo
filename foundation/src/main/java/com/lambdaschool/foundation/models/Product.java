package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product
{
    //--------------- Table Fields ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productid; // primary key

    @NotNull
    private String productName;

    @NotNull
    private String brandName;



    @NotNull
    @Column(length = 1000)
    private String description;

    private String productImageUrl;

    // ---------------------------
    @Transient
    @JsonIgnore
    public boolean hasValueForPricePerDay = false;

    private double pricePerDay;

    // ----------------------------

    //----------------------------
    @Transient
    @JsonIgnore
    public boolean hasValueForPricePerWeek = false;

    private double pricePerWeek;
    //---------------------------------

    @NotNull
    private String category;

    // -------------- Association Fields -----------
//    @OneToMany(mappedBy = "product",
//    cascade = CascadeType.ALL,
//    orphanRemoval = true)
//    @JsonIgnoreProperties(value = "product",
//    allowSetters = true)
//    private List<RentedProduct> rentedProducts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "product",
//    cascade = CascadeType.ALL,
//    orphanRemoval = true)
//    @JsonIgnoreProperties(value = "product",
//    allowSetters = true)
//    private List<OwnedProduct> ownedProducts = new ArrayList<>();

    // -------------- Constructors ---------------
    public Product()
    {
        // default constructor to be used with JPA
    }

    // Constructor with parameters
    public Product(
        @NotNull String productName,
        @NotNull String brandName,
        @NotNull String description,
        String productImageUrl,
        double pricePerDay,
        double pricePerWeek,
        @NotNull String category)
    {
        this.productName = productName;
        this.brandName = brandName;
        this.description = description;
        this.productImageUrl = productImageUrl;
        this.pricePerDay = pricePerDay;
        this.pricePerWeek = pricePerWeek;
        this.category = category;
    }


    // -------------- Getters and Setters ---------------
    public long getProductid()
    {
        return productid;
    }

    public void setProductid(long productid)
    {
        this.productid = productid;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }

    public double getPricePerDay()
    {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay)
    {
        hasValueForPricePerDay = true;
        this.pricePerDay = pricePerDay;
    }

    public double getPricePerWeek()
    {
        return pricePerWeek;
    }

    public void setPricePerWeek(double pricePerWeek)
    {
        hasValueForPricePerWeek = true;
        this.pricePerWeek = pricePerWeek;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    // --------------- Association Getters and Setters ---------------

//    public List<RentedProduct> getRentedProducts()
//    {
//        return rentedProducts;
//    }
//
//    public void setRentedProducts(List<RentedProduct> rentedProducts)
//    {
//        this.rentedProducts = rentedProducts;
//    }
//
//    public List<OwnedProduct> getOwnedProducts()
//    {
//        return ownedProducts;
//    }
//
//    public void setOwnedProducts(List<OwnedProduct> ownedProducts)
//    {
//        this.ownedProducts = ownedProducts;
//    }
}
