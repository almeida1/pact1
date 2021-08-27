package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import com.fatec.scel.service.ProductService;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

class ExampleJavaConsumerPactTest2 {
	@Rule
	public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 8080, this);

	@Pact(provider = "test_provider", consumer = "test_consumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder.given("test state").
				uponReceiving("ExampleJavaConsumerPactRuleTest test interaction").
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

	@Test
	@PactVerification("test_provider")
	public void runTest() {
		Map expectedResponse = new HashMap();
		expectedResponse.put("responsetest", true);
		assertEquals(new ConsumerClient(mockProvider.getUrl()).get("/"), expectedResponse);
	}

}
