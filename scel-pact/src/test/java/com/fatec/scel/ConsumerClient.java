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

}
