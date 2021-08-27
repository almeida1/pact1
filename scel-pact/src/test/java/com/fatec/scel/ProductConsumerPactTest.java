package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import com.fatec.scel.model.Product;
import com.fatec.scel.service.ProductService;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;



@ExtendWith(PactConsumerTestExt.class)
public class ProductConsumerPactTest{

    @Pact(consumer = "FrontendApplication", provider = "ProductService")
    RequestResponsePact getAllProducts(PactDslWithProvider builder) {
        return builder.given("products exist")
                .uponReceiving("get all products")
                .method("GET")
                .path("/products")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json; charset=utf-8"))
                .body("{\"resposetest\":true}")
                .toPact();
    }

   

    @Test
    @PactTestFor(pactMethod = "getAllProducts")
    void getAllProducts_whenProductsExist(MockServer mockServer) {
        Product product = new Product();
        product.setId("09");
        product.setType("CREDIT_CARD");
        product.setName("Gem Visa");
        List<Product> expected = List.of(product, product);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        List<Product> products = new ProductService(restTemplate).getAllProducts();

        assertEquals(expected, products);
    }

    @Test
    @PactTestFor(pactMethod = "getOneProduct")
    void getProductById_whenProductWithId10Exists(MockServer mockServer) {
        Product expected = new Product();
        expected.setId("10");
        expected.setType("CREDIT_CARD");
        expected.setName("28 Degrees");

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        Product product = new ProductService(restTemplate).getProduct("10");

        assertEquals(expected, product);
    }
}