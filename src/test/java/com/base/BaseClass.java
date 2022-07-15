package com.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	RequestSpecification reqSpec;
	Response response;
	
	
	
	public String getPropFile(String Key) throws IOException {
		FileInputStream stream=new FileInputStream(System.getProperty("user.dir")+"/config.properties");
		Properties properties=new Properties();
		properties.load(stream);
		String value = (String)properties.get(Key);
		return value;
		
		
	}
	public void basicAuth(String userName, String passwword) {
	 reqSpec = reqSpec.auth().basic(userName, passwword);
		
		
	}
	public void resAssured() {
	 reqSpec = RestAssured.given();
	}
	public void addHeader(String Key, String value) {
		reqSpec = RestAssured.given().headers(Key, value);

	}
	public void addHeaders(List<Header> header) {
		Headers headers=new Headers(header);
		reqSpec = RestAssured.given().headers(headers);

	}
	public void pathParam(String Key, String value) {
		reqSpec = reqSpec.pathParam(Key, value);

	}

	public void querryParam(String Key, String value) {
		reqSpec = reqSpec.queryParam(Key, value);

	}

	public void addBody(String payLoad) {
		reqSpec = reqSpec.body(payLoad);
	}
	public void addBody(Object payLoad) {
		reqSpec = reqSpec.body(payLoad);
	}
	public Response reqType(String reqType, String endPoint) {

		switch (reqType) {
		case "GET":
			response = reqSpec.log().all().get(endPoint);
			break;
		case "PUT":
			response = reqSpec.log().all().put(endPoint);
			break;
		case "POST":
			response = reqSpec.log().all().post(endPoint);
			break;
		case "DELETE":
			response = reqSpec.log().all().delete(endPoint);
			break;
		default:
			break;
		}
		return response;

	}

	public int getStatusCode(Response response) { 
		int statusCode = response.statusCode();
		return statusCode;
	}

//	public ResponseBody  resBody(Response response) {
//		ResponseBody body = response.getBody();
//		return body;
//		
//	}
	public String asString(Response response) {
			String asString = response.getBody().asString();
			return asString;
	}
	public String asPrettyString(Response response) {
		String asPrettyString = response.getBody().asPrettyString();
		return asPrettyString;
}
}
