package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatec.scel.model.Product;
import com.fatec.scel.service.ProductService;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import net.minidev.json.JSONArray;

class ExampleJavaConsumerPactTest2 {
	@Rule //cria a api mockada
	public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 8080, this);
	//*****************************************************************************
    //https://github.com/pact-foundation/pact-workshop-jvm-spring
	//*****************************************************************************
	@Pact(provider = "test_provider", consumer = "test_consumer") 
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder.given("pre condicoes de teste - existem produtos no provedor").
				uponReceiving("quando o cliente solicitar todos").
				path("/").method("GET").
				willRespondWith().
				status(200).
				body("{\"responsetest\": true}").
				toPact();
	}

	@Pact(consumer = "test_consumer") // will default to the provider name from mockProvider
	public RequestResponsePact createFragment(PactDslWithProvider builder) {
		return builder.given("test state").
				uponReceiving("ExampleJavaConsumerPactRuleTest test interaction").
				path("/")
				.method("GET").
				willRespondWith().status(200).
				body("{\"responsetest\": true}").
				toPact();
	}
	
	@Pact(consumer = "test_consumer", provider = "test_provider")
	public RequestResponsePact getAllProducts(PactDslWithProvider builder) {
		return builder.given("existem produtos cadastrados")
				.uponReceiving("e o cliente solicitou consulta todos os produtos")
				.method("GET")
				.path("/products")
			.willRespondWith()
				.status(200)
				.headers(Map.of("Content-Type", "application/json; charset=utf-8"))
				.body("{\"id\": \"1\", \"type\" : \"CREDIT_CARD\", \"name\" : \"Visa\", \"version\" : \"1.0\" }")
                .toPact();
	}
	
	public String  getProduto() {
		Map<String, String> produto = new HashMap<String, String>();
       produto.put("id", "10");
       produto.put("type","CREDIT_CARD");
       produto.put("name", "Visa");
       produto.put("version", "1.0");
       ObjectMapper objectMapper = new ObjectMapper();
       String json = null;
       try {
	           json = objectMapper.writeValueAsString(produto);
	           System.out.println(json);
	       } catch (JsonProcessingException e) {
	           e.printStackTrace();
	       }
		return json ;
	} 

	@Test
	@PactVerification("test_provider")
	public void runTest() {
		Map expectedResponse = new HashMap();
		expectedResponse.put("responsetest", true);
		assertEquals(new ConsumerClient(mockProvider.getUrl()).get("/"), expectedResponse);
	}
	 @Test
	 @PactTestFor(pactMethod = "getAllProducts")
	// @PactVerification("test_consumer")
	    void getAllProducts_whenProductsExist(MockServer mockServer) {
	        Product product = new Product();
	        product.setId("09");
	        product.setType("CREDIT_CARD");
	        product.setName("Visa");
	        product.setVersion("1.0");
	        List<Product> expected = List.of(product, product);

	        RestTemplate restTemplate = new RestTemplateBuilder()
	                .rootUri(mockServer.getUrl())
	                .build();
	        List<Product> products = new ProductService(restTemplate).getAllProducts();

	        assertEquals(expected, products);
	    }

}


