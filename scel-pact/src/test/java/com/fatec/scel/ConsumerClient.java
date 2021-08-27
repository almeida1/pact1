package com.fatec.scel;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ConsumerClient {
	


	public ConsumerClient(String url) {
		// TODO Auto-generated constructor stub
	}

	
	public Map<String, String> get(String string) {
		Map expectedResponse = new HashMap();
        expectedResponse.put("responsetest", true);
	    return expectedResponse;
	}
	//@GetMapping
	public Map<String, String> sayHello() {
	    HashMap<String, String> map = new HashMap<>();
	    map.put("key", "value");
	    map.put("foo", "bar");
	    map.put("aa", "bb");
	    return map;
	}
	//This will lead to the following JSON response:
	//{ "key": "value", "foo": "bar", "aa": "bb" }
}
