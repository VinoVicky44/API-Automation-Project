package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.endpoints.EndPoints;
import com.pojo.AddAddress_Input_pojo;
import com.pojo.AddAddress_Output_pojo;
import com.pojo.Login_Output_pojo;

import io.restassured.http.Header;
import io.restassured.response.Response;

public class OMRBranchClub extends BaseClass {
	String logtoken;
	String addressId;
	public void common() {
		List<Header> list=new ArrayList<Header>();
		Header h1=new Header("accept", "application/json");
		Header h2=new Header("Authorization", "Bearer "+ logtoken);
		Header h3=new Header("Content-Type", "application/json");
		list.add(h1);
		list.add(h2);
		list.add(h3);
		addHeaders(list);
		
	}
	@Test()
	public void basicAuthLogin() throws IOException {
		addHeader("accept", "application/json");
		basicAuth(getPropFile("username"), getPropFile("password"));
		Response reqType = reqType("POST", EndPoints.POSTMANBASICAUTH);
		Login_Output_pojo output_pojo = reqType.as(Login_Output_pojo.class);	
		Assert.assertEquals(output_pojo.getData().getApi_key(), "MNOPQ4008xyz18477","Verify the APIKey of user");
		Assert.assertEquals(getStatusCode(reqType), 200,"Verify the Statuscodes of user");
		 logtoken = output_pojo.getData().getLogtoken();
		 System.out.println(logtoken);
		
	}
	@Test(dependsOnMethods="basicAuthLogin")
	public void addAddress() {

//		List<Header> list=new ArrayList<Header>();
//		Header h1=new Header("accept", "application/json");
//		Header h2=new Header("Authorization", "Bearer "+ logtoken);
//		Header h3=new Header("Content-Type", "application/json");
//		list.add(h1);
//		list.add(h2);
//		list.add(h3);
//		addHeaders(list);
		common();
		AddAddress_Input_pojo address_Input_pojo=new AddAddress_Input_pojo("Vinoth", "Kumar", "7896888245", "HSV", 33, 3378, 101, "789654", "Chennai", "Work");
		addBody(address_Input_pojo);
		Response reqType = reqType("POST", EndPoints.ADDADDRESS);
		System.out.println(getStatusCode(reqType));
		System.out.println(asPrettyString(reqType));
		AddAddress_Output_pojo output_pojo = reqType.as(AddAddress_Output_pojo.class);
		int id = output_pojo.getAddress_id();
		addressId = String.valueOf(id);
		Assert.assertEquals(output_pojo.getMessage(), "Address added successfully","Verify the Address added successfully");
		Assert.assertEquals(getStatusCode(reqType), 200,"Verify the status code");
		
	}
	@Test(dependsOnMethods="addAddress")
	public void updateAddress() {
//		List<Header> list=new ArrayList<>();
//		Header header1=new Header("accept", "application/json");
//		Header header2=new Header("Authorization", "Bearer "+ logtoken);
//		Header header3=new Header("Content-Type", "application/json");
//		list.add(header1);
//		list.add(header2);
//		list.add(header3);
//		addHeaders(list);
		common();
		AddAddress_Input_pojo addAddress_Input_pojo=new AddAddress_Input_pojo(addressId, "Vinoth", "Kumar", "7896888245", "HSV", 33, 3378, 101, "789654", "Chennai", "Work");
		addBody(addAddress_Input_pojo);
		Response reqType = reqType("PUT", EndPoints.UPDATEADDRESS);
		System.out.println(getStatusCode(reqType));
		System.out.println(asPrettyString(reqType));
		AddAddress_Output_pojo as = reqType.as(AddAddress_Output_pojo.class);
		Assert.assertEquals(getStatusCode(reqType), 200,"Verify the status code");
		Assert.assertEquals(as.getMessage(), "Address updated successfully","Address updated successfully");
		
	}
	@Test(dependsOnMethods="updateAddress")
	public void deleteAddress() {
//		List<Header> list=new ArrayList<>();
//		Header header1=new Header("accept", "application/json");
//		Header header2=new Header("Authorization", "Bearer "+ logtoken);
//		Header header3=new Header("Content-Type", "application/json");
//		list.add(header1);
//		list.add(header2);
//		list.add(header3);
//		addHeaders(list);
		common();
		AddAddress_Input_pojo addAddress_Input_pojo=new AddAddress_Input_pojo(addressId);
		addBody(addAddress_Input_pojo);
		Response reqType = reqType("DELETE", EndPoints.DELETEADDRESS);
		AddAddress_Output_pojo as = reqType.as(AddAddress_Output_pojo.class);
		Assert.assertEquals(as.getMessage(), "Address deleted successfully","Verify the Address deleted successfully");
		Assert.assertEquals(getStatusCode(reqType), 200,"Verify the status code");		
	
		
	}
	
	
}
