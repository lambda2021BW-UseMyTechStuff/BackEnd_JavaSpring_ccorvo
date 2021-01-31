package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User
    extends Auditable
{
    // ------------ Table Fields ---------------------------------
    // Standard User Fields

    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @NotNull
    @Column(unique = true)
    private String username;

    /**
     * The password (String) for this user. Cannot be null. Never get displayed
     */
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
     */
    @NotNull
    @Column(unique = true)
    @Email
    private String primaryemail;

    // Specific user fields added 1:02pm 1/30/21
    private String fName;


    private String lName;


    private String zipcode;

    private String phoneNumber;

    private String profilePicUrl;


    // ------------- Association Fields ------------------------

    /**
     * A list of emails for this user
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    // Association fields for specific project
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<RentedProduct> rentedProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<OwnedProduct> ownedProducts = new ArrayList<>();

    // --------------- Constructors --------------------------

    /**
     * Default constructor used primarily by the JPA.
     */
    public User()
    {
        // default constructor to be used with JPA
    }

    // Constructor with parameters
    public User(
        String username,
        String password,
        String primaryemail,
        String fName,
        String lName,
        String zipcode,
        String phoneNumber,
        String profilePicUrl)
    {
        setUsername(username);
        setPassword(password);
        this.primaryemail = primaryemail;
        this.fName = fName;
        this.lName = lName;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.profilePicUrl = profilePicUrl;
    }

    // ----------------- Getters and Setters --------------------
    public long getUserid()
    {
        return userid;
    }


    public void setUserid(long userid)
    {
        this.userid = userid;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    public String getPrimaryemail()
    {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail.toLowerCase();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    /**
     * @param password the new password (String) for this user. Comes in plain text and goes out encrypted
     */
    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getfName()
    {
        return fName;
    }

    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getlName()
    {
        return lName;
    }

    public void setlName(String lName)
    {
        this.lName = lName;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicUrl()
    {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl)
    {
        this.profilePicUrl = profilePicUrl;
    }

    // ----------- Getters and Setters for Association Fields -----------
    public List<Useremail> getUseremails()
    {
        return useremails;
    }

    public void setUseremails(List<Useremail> useremails)
    {
        this.useremails = useremails;
    }


    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    // These Association Getters and Setters were made by me 1:19pm 1/30/21
    public List<RentedProduct> getRentedProducts()
    {
        return rentedProducts;
    }

    public void setRentedProducts(List<RentedProduct> rentedProducts)
    {
        this.rentedProducts = rentedProducts;
    }

    public List<OwnedProduct> getOwnedProducts()
    {
        return ownedProducts;
    }

    public void setOwnedProducts(List<OwnedProduct> ownedProducts)
    {
        this.ownedProducts = ownedProducts;
    }

    // ----------- Additional Methods ----------------------

    /**
     * Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
     * Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
     *
     * @return The list of authorities, roles, this user object has
     */
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles)
        {
            String myRole = "ROLE_" + r.getRole()
                .getName()
                .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
