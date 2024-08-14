package com.ninza.api.genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;


public class DatabaseUtilities {
	static Connection con = null;
	static ResultSet result = null;
	static FileUtility fLib = new FileUtility();

	/**
	 * Connect to database
	 * @throws Throwable 
	 */
	public static void connectToDB() throws Throwable {
		Driver driverRef;
		try {
			driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			con = DriverManager.getConnection(fLib.getDataFromPropertiesFile("DBUrl")
					,fLib.getDataFromPropertiesFile("DB_Username"),
					fLib.getDataFromPropertiesFile("DB_Password"));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
	}
	
	/**
	 * close the Db connection
	 * @throws SQLException
	 */
	public static void closeDb() throws SQLException {
		con.close();
	}
	

	/**
	 * getDataFromDB method to retrieve data from database as key value pairs
	 * 
	 * @param query
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static ResultSet execyteQuery(String query) throws Throwable {
		try {
			// executing the query
			 result = con.createStatement().executeQuery(query);
			 return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return result;
		
	}
	

	/**
	 * 
	 * @param query
	 * @param columnName
	 * @param expectedData
	 * @return
	 * @throws Throwable
	 */
	public boolean  executeQueryVerifyAndGetData(String query ,int columnIndex , String expectedData) throws Throwable{
         boolean flag = false;
			result = con.createStatement().executeQuery(query);
			
		while (result.next()) {
			  		if(result.getString(columnIndex).equals(expectedData)) {
			  			flag= true;
			  			break;
			  		}
		}	
		if(flag) {
			System.out.println(expectedData + "===> data verified in data base table");
			return true;
		}else {
			System.out.println(expectedData + "===> data not verified in data base table");
			return false;
		}
		
		
	}

}
