package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.FoundationApplication;
import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.repository.ProductRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoundationApplication.class)
public class ProductServiceImplTest
{

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    HelperFunctions helperFunctions;

    private List<Product> productList;


    @Before
    public void setUp() throws Exception
    {
        productList = new ArrayList<>();

        Product p1 = new Product("Nikon Z6",
            "Nikon",
            "Mirrorless Digital Camera with 24-70mm Lens and Accessories Kit",
            "https://www.bhphotovideo.com/images/images2500x2500/nikon_z_6_mirrorless_digital_1461752.jpg",
            100.00,
            21900.00,
            "camera");
        p1.setProductid(10);
        productList.add(p1);

        Product p2 = new Product("Hero7",
            "GoPro",
            "Say hello to HERO7, the perfect partner on any adventure. It's tough, tiny and totally waterproof so it can go wherever you do. An intuitive touch screen makes it simple to get great shots. Just swipe and tap. Use the photo timer to grab a sweet selfie.",
            "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQzuHhlcilhqv9cXBjZ9Tf2obUyeQwq10EIV09Marl1Yz1KT5Z3PkWoPVb4bv2IRjBLb4i7O_zLPzQhHsgWN-u2n7RE0v46&usqp=CAY",
            25.00,
            200.00,
            "camera");
        p2.setProductid(20);
        productList.add(p2);

        Product p3 = new Product("SM7B",
            "Shure",
            "While Shure’s SM7B is a first-call mic for many sources, it’s a total rockstar dialog microphone. You’ll find this dynamic beauty dangling from broadcast booms in voiceover, ADR, radio, and podcast studios around the world. There are two settings that let you tailor the SM7B to a wide range of voices and applications. Whether you want to bring out the resonance of a deep baritone, tame the brittleness of a high soprano, capture a whisper, or record a gunshot, the Shure SM7B is a must-have part of your rig.",
            "https://media.sweetwater.com/api/i/f-webp__q-82__ha-25d5deb5fd838426__hmac-a032c45ed3629d2f1fb99788acae043c6b53c489/images/items/750/SM7B-large.jpg.auto.webp",
            50,
            400.00,
            "audio");
        p3.setProductid(30);
        productList.add(p3);

        Product p4 = new Product("13-inch MackBook Pro",
            "Apple",
            "Brand New Mackbook Pro with Apple M1 Chip and 16Gb of memory",
            "https://www.apple.com/v/macbook-pro-13/g/images/specs/keyboard_13_inch__thzyim8yzpm6_large.jpg",
            75.00,
            1300.00,
            "computer");
        p4.setProductid(40);
        productList.add(p4);

        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
        Mockito.when(productRepository.findAll())
            .thenReturn(productList);

        assertEquals(4,
            productService.findAll().size());
    }

    @Test
    public void findProductById()
    {
        Mockito.when(productRepository.findById(10L))
            .thenReturn(Optional.of(productList.get(0)));

        assertEquals("Nikon Z6",
            productService.findProductById(10L)
                .getProductName());
    }

    @Test
    public void findProductByNameLike()
    {
        Mockito.when(productRepository.findByProductNameContainingIgnoreCase("o"))
            .thenReturn(productList);

        assertEquals(3,
            productService.findProductByNameLike("o")
                .size());
    }

    @Test
    public void findProductByCategory()
    {
        Mockito.when(productRepository.findByCategory("audio"))
            .thenReturn(productList);

        assertEquals(2,
            productService.findProductByCategory("audio")
                .size());
    }

    @Test
    public void update()
    {
        Product p1 = new Product("Nikon Z6",
            "Nikon",
            "Mirrorless Digital Camera with 24-70mm Lens and Accessories Kit",
            "https://www.bhphotovideo.com/images/images2500x2500/nikon_z_6_mirrorless_digital_1461752.jpg",
            100.00,
            21900.00,
            "camera");

        Mockito.when(productRepository.findById(10L))
            .thenReturn(Optional.of(productList.get(2)));

        Mockito.when(helperFunctions.isAuthorizedToUpdateProduct(anyLong()))
            .thenReturn(true);

        Mockito.when(productRepository.save(any(Product.class)))
            .thenReturn(p1);

        assertEquals("Nikon Z6",
            productService.update(p1,
                10L).getProductName());

    }

    @Test
    public void save()
    {
        Product p1 = new Product("Nikon Z6",
            "Nikon",
            "Mirrorless Digital Camera with 24-70mm Lens and Accessories Kit",
            "https://www.bhphotovideo.com/images/images2500x2500/nikon_z_6_mirrorless_digital_1461752.jpg",
            100.00,
            21900.00,
            "camera");

        Mockito.when(productRepository.save(any(Product.class)))
            .thenReturn(p1);

        assertEquals("Nikon Z6",
            productService.save(p1)
                .getProductName());
    }

    @Test
    public void delete()
    {
        Mockito.when(productRepository.findById(10L))
            .thenReturn(Optional.of(productList.get(0)));

        Mockito.doNothing()
            .when(productRepository)
            .deleteById(10L);

        productService.delete(10L);
        assertEquals(4,
            productList.size());
    }

    @Test
    public void deleteAllProducts()
    {
        Mockito.doNothing()
            .when(productRepository)
            .deleteAll();

        productService.deleteAllProducts();
        assertEquals(4,
            productList.size());
    }
}