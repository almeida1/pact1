package com.fatec.scel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.HttpResponse;
import au.com.dius.pact.core.model.Request;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ArticlesProvider")
class ExampleJavaConsumerPactTest {

   

	@Pact(provider="ArticlesProvider", consumer="test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
            .given("test state")
            .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/articles.json")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body("{\"responsetest\": true}")
            .toPact();
    }
	@Test
	  void test(MockServer mockServer) throws IOException {
		//HttpResponse httpResponse = Request.get(mockServer.getUrl() + "/articles.json").execute().returnResponse();
	  }
}
