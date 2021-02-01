package com.lambdaschool.foundation;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.services.ProductService;
import com.lambdaschool.foundation.services.RoleService;
import com.lambdaschool.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        // ------------ Product mock data -------------
        productService.deleteAllProducts();
        Product p1 = new Product("Nikon Z6",
            "Nikon",
            "Mirrorless Digital Camera with 24-70mm Lens and Accessories Kit",
            "https://www.bhphotovideo.com/images/images2500x2500/nikon_z_6_mirrorless_digital_1461752.jpg",
            100.00,
            21900.00,
            "camera");

        Product p2 = new Product("Hero7",
            "GoPro",
            "Say hello to HERO7, the perfect partner on any adventure. It's tough, tiny and totally waterproof so it can go wherever you do. An intuitive touch screen makes it simple to get great shots. Just swipe and tap. Use the photo timer to grab a sweet selfie.",
            "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQzuHhlcilhqv9cXBjZ9Tf2obUyeQwq10EIV09Marl1Yz1KT5Z3PkWoPVb4bv2IRjBLb4i7O_zLPzQhHsgWN-u2n7RE0v46&usqp=CAY",
            25.00,
            200.00,
            "camera");

        Product p3 = new Product("SM7B",
            "Shure",
            "While Shure’s SM7B is a first-call mic for many sources, it’s a total rockstar dialog microphone. You’ll find this dynamic beauty dangling from broadcast booms in voiceover, ADR, radio, and podcast studios around the world. There are two settings that let you tailor the SM7B to a wide range of voices and applications. Whether you want to bring out the resonance of a deep baritone, tame the brittleness of a high soprano, capture a whisper, or record a gunshot, the Shure SM7B is a must-have part of your rig.",
            "https://media.sweetwater.com/api/i/f-webp__q-82__ha-25d5deb5fd838426__hmac-a032c45ed3629d2f1fb99788acae043c6b53c489/images/items/750/SM7B-large.jpg.auto.webp",
            50,
            400.00,
            "audio");

        Product p4 = new Product("13-inch MackBook Pro",
            "Apple",
            "Brand New Mackbook Pro with Apple M1 Chip and 16Gb of memory",
            "https://www.apple.com/v/macbook-pro-13/g/images/specs/keyboard_13_inch__thzyim8yzpm6_large.jpg",
            75.00,
            1300.00,
            "computer");

        p1 = productService.save(p1);
        p2 = productService.save(p2);
        p3 = productService.save(p3);
        p4 = productService.save(p4);

        // -------- creating mock user accounts ----------
        userService.deleteAll();
        roleService.deleteAll();
                Role r1 = new Role("admin");
                Role r2 = new Role("user");
                Role r3 = new Role("data");

                r1 = roleService.save(r1);
                r2 = roleService.save(r2);
                r3 = roleService.save(r3);

                // admin, data, user
                User u1 = new User("admin",
                    "password",
                    "admin@lambdaschool.local",
                    "John",
                    "Smith",
                    "08048",
                    "858-456-6586",
                    "https://uploads-ssl.webflow.com/5dc1432400ebeb08f8cb067e/5dc1435564067e81c49ac8da_Nav%20Jyoti.jpg");
                u1.getRoles()
                    .add(new UserRoles(u1,
                        r1));
                u1.getRoles()
                    .add(new UserRoles(u1,
                        r2));
                u1.getRoles()
                    .add(new UserRoles(u1,
                        r3));
                // adding to OwnedProduct []
                u1.getOwnedProducts()
                    .add(new OwnedProduct(u1,p1));
                // adding to RentedProduct []
                u1.getRentedProducts()
                    .add(new RentedProduct(u1, p2));
                userService.save(u1);

//                // data, user
                User u2 = new User("jackSmith2021",
                    "1234567",
                    "jackSmith2021@seeddata.com",
                    "Jack",
                    "Smith",
                    "08048",
                    "896-458-6666",
                    "http://metrotaps.com/wp-content/uploads/2016/08/amelia.png");
                u2.getRoles()
                    .add(new UserRoles(u2,
                        r2));
                u2.getRoles()
                    .add(new UserRoles(u2,
                        r3));
                u2.getUseremails()
                    .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
                // adding to OwnedProduct []
                u2.getOwnedProducts()
                    .add(new OwnedProduct(u2,p2));
                // adding to RentedProduct []
                u2.getRentedProducts()
                    .add(new RentedProduct(u2, p1));
                userService.save(u2);

                // user
                User u3 = new User("user",
                    "password",
                    "mariaAngela2021@seeddata.com",
                    "Maria",
                    "Angela",
                    "11385",
                    "856-359-4444",
                    "http://metrotaps.com/wp-content/uploads/2016/08/ghg.png");
                u3.getRoles()
                    .add(new UserRoles(u3,
                        r2));
                u3.getUseremails()
                    .add(new Useremail(u3,
                        "barnbarn@email.local"));
                // adding to OwnedProduct []
                u3.getOwnedProducts()
                    .add(new OwnedProduct(u3,p3));
                // adding to RentedProduct []
                u3.getRentedProducts()
                    .add(new RentedProduct(u3, p4));
                userService.save(u3);

                User u4 = new User("davidSin2021",
                    "password",
                    "davidsin2021@seeddata.com",
                    "David",
                    "Sin",
                    "10014",
                    "456-999-8888",
                    "http://metrotaps.com/wp-content/uploads/2016/08/dsd.png");
                u4.getRoles()
                    .add(new UserRoles(u4,
                        r2));
                // adding to OwnedProduct []
                u4.getOwnedProducts()
                    .add(new OwnedProduct(u4,p4));
                // adding to RentedProduct []
                u4.getRentedProducts()
                    .add(new RentedProduct(u4, p3));
                userService.save(u4);

                User u5 = new User("susanSmith2021",
                    "password",
                    "susansmith@seeddata.com",
                    "Susan",
                    "Smith",
                    "11245",
                    "789-658-6666",
                    "http://www.microstar.ng/images/user.jpg");
                u5.getRoles()
                    .add(new UserRoles(u5,
                        r2));
                // adding to OwnedProduct []
                u5.getOwnedProducts()
                    .add(new OwnedProduct(u5,p2));
                // adding to RentedProduct []
                u5.getRentedProducts()
                    .add(new RentedProduct(u5, p3));
                userService.save(u5);
        }

    }