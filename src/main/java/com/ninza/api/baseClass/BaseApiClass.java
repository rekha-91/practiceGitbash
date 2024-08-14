package com.ninza.api.baseClass;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.api.genericutility.DatabaseUtilities;
import com.ninza.api.genericutility.FileUtility;
import com.ninza.api.genericutility.JavaUtility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApiClass {
	public JavaUtility jLib = new JavaUtility();
	public FileUtility fLib = new FileUtility();
	public DatabaseUtilities dbLib = new DatabaseUtilities();
	public  static RequestSpecification specReqObj;
	public  static ResponseSpecification specRespObj;
	@BeforeSuite
	public void configBS() throws Throwable {
		 dbLib.connectToDB();
		 System.out.println("============Connect TO DB==========");
		 RequestSpecBuilder builder = new RequestSpecBuilder();
		 builder.setContentType(ContentType.JSON);
		// builder.setAuth(basic("username", "password"));
		// builder.addHeader("", "");
		 builder.setBaseUri(fLib.getDataFromPropertiesFile("BASEUri"));
		 specReqObj =  builder.build();
		 
		 ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		 resBuilder.expectContentType(ContentType.JSON);
		 specRespObj =  resBuilder.build();
	}
	
	@AfterSuite
	public void configAS() throws SQLException {
		dbLib.closeDb();
		System.out.println("==========DisConnectToDB===========");
	}



}
