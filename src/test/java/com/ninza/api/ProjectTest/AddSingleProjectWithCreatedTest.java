package com.ninza.api.ProjectTest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.api.baseClass.BaseApiClass;
import com.ninza.api.pojoclass.Project_POJO;
import com.ninza.api.constants.endpoints.IEndPoint;

import io.restassured.response.Response;

public class AddSingleProjectWithCreatedTest extends BaseApiClass{
	WebDriver driver;
	WebDriverWait wait;
	@Test
	public void addSingleProjectWithCreatedTest() throws Throwable {
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		String expSucMsg = "Successfully Added";
	    String projectName = "FactorUno_"+jLib.getRandomNumber();		
		 Project_POJO pObj = new Project_POJO(projectName, "Created", "Rekha", 0);
		//verify the projectName in API layer
			Response resp= given()
					         .spec(specReqObj)
					          .body(pObj)
					        
					        .when()
					          .post(IEndPoint.ADDProj);
			
					        resp.then()
					        
					         .assertThat().statusCode(201)
					         .assertThat().time(Matchers.lessThan(3000L))
					         .spec(specRespObj)
					         .log().all();
		            
					   String actMsg = resp.jsonPath().get("msg");
					  String projectId= resp.jsonPath().get("projectId");
					  String prjtName= resp.jsonPath().get("projectName");
					  String status = resp.jsonPath().get("status");
					   Assert.assertEquals(expSucMsg, actMsg);
					   
			 //verify the projectName in DB layer
					   dbLib.connectToDB();
				boolean flag  = dbLib.executeQueryVerifyAndGetData("select * from project", 4, projectName);
			    Assert.assertTrue(flag,"Project in DB is not verified");
			    
		//Verify the status of project in GUI
			    driver= new ChromeDriver();
			    driver.manage().window().maximize();
			    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			    driver.get(BASEURI);
			    driver.findElement(By.id("username")).sendKeys("rmgyantra");
			    driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
			    driver.findElement(By.xpath("//button[text()='Sign in']")).click();
			    driver.findElement(By.xpath("//a[text()='Projects']")).click();
			   WebElement SearchPrjId = driver.findElement(By.xpath("//input[@placeholder='Search by Project Id']"));
			   SearchPrjId.sendKeys(projectId, Keys.ENTER);
			   WebElement sts = driver.findElement(By.xpath("//table/tbody/tr/td[5]"));
			   String statusmsg = sts.getText();
			   assertTrue(status.equals(statusmsg));
	}

}
