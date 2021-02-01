package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.FoundationApplication;
import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.services.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FoundationApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
public class ProductControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    private MockMvc mockMvc;

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

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

    }

    @After
    public void tearDown() throws Exception
    {
        // Leave this blank
    }

    @Test
    public void listAllProducts() throws Exception
    {
        String apiUrl = "/products/products";

        Mockito.when(productService.findAll())
            .thenReturn(productList);

        System.out.println(SecurityContextHolder.getContext()
            .getAuthentication().getName());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // following performs actual controller call
        MvcResult r = mockMvc.perform(requestBuilder)
            .andReturn(); // this could throw an exception

        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(productList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API returns list",
            er,
            tr);
    }

    @Test
    public void getProductById() throws Exception
    {
        String apiUrl = "/products/product/10";

        Mockito.when(productService.findProductById(10))
            .thenReturn(productList.get(1));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(requestBuilder)
            .andReturn();

        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(productList.get(1));

        System.out.println("Expected: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API returns list",
            er,
            tr);

    }

    @Test
    public void listProductByNameLike()
    {
    }

    @Test
    public void findProductByCategoryName()
    {
    }

    @Test
    public void addNewProduct() throws Exception
    {
        String apiUrl = "/products/product";

        Mockito.when(productService.save(any(Product.class)))
            .thenReturn(productList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"productName\": \"TestTest\", \"brandName\": \"Sony\", \"description\" : \"Test\", \"productImageUrl\": \"testurl\",\"pricePerDay\": 100, \"pricePerWeek\": 100, \"category\": \"camera\"}");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullProduct() throws Exception
    {
        String apiUrl = "/products/product/{productid}";

        Mockito.when(productService.update(any(Product.class),
            any(Long.class)))
            .thenReturn(productList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,10L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"productName\": \"TestTest\", \"brandName\": \"Sony\", \"description\" : \"Test\", \"productImageUrl\": \"testurl\",\"pricePerDay\": 100, \"pricePerWeek\": 100, \"category\": \"camera\"}");

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProduct()
    {
    }

    @Test
    public void deleteProductById() throws Exception
    {
        String apiUrl = "/products/product/{productid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }
}