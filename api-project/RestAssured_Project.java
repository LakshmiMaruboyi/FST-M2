package GitHub_RestAssured_Project;

import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class RestAssured_Project {
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	String gitHubAccessToken="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";
	String BASE_URI="https://api.github.com";
	int sshId;
	@BeforeClass
	public void setUp() { // Create request specification
	requestSpec = new RequestSpecBuilder()
	// Set content type
	.setContentType(ContentType.JSON)
	// set header as githubaccesstoken
	.addHeader("Authorization", "token " + gitHubAccessToken)
	// Set base URL
	.setBaseUri("https://api.github.com")
	// Build request specification
	.build(); responseSpec = new ResponseSpecBuilder()
	// Check response content type
	.expectContentType("application/json")
	// Check if response contains name property
	// Build response specification
	.build();
	
	} // post request
	@Test(priority = 1)
	public void addSSHKey() {
	String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDa0LeUZp4QS5q83ywoh8gSQEJDo3zU41H2KIKdXp+mQSH1ZyBn1/88Rh7hrkQXinsLNHyugwbkmVkOCDYi5fliftSU8iejRwPW+j0kEx4SGQ0bg6UsegGgPhw2BofLS1p6F3IJUmZLsQQlw2tZAX1JeRNjOm9Wg58lOEDnQ26KWpg7Gz8N2lVCJe1LD2EjDUJNEj7rPHxdQnLARiBCTokQTIpdhdI7yvmxBc9du5vxckiZxArWFM6vZuW50ZYGryMdB0BHimgeSx9RxKj9BJNfbmP0h9ZrspmEzPUrRxBkh/oZeEIXah9fyP/UwtOR28SmSUdnFbBAhBnZbzmcD2cdyLqI9EI8IOHd2eUOm4peoGKky5TSv+DIci0bn2YobToskNFD7eLaZcEy1cZWPPfC4sN0ACSKar8oJnU4TLlhtP68pBRgy75cKsclAMedhIUOk3SuvI7vTPMuObY9i+sRFL9Y47ks8QBUFculaFYVNHCyXQSGf2M5IrbjTaH2Fkc="
	+ "\"}";
	Response response = given().spec(requestSpec) // Use requestSpec
	.body(reqBody) // Send request body
	.when().post( "/user/keys"); // Send POST request
	System.out.println(response.asPrettyString());
	// Additional Assertion Use responseSpec
	response.then().spec(responseSpec).statusCode(201); // save sshId
	
	sshId = response.then().extract().path("id"); 
	System.out.println(sshId);
	}
	// get request
	@Test(priority = 2)
	public void getSSHKeys() {
	Response response = given().spec(requestSpec) // Use requestSpec
	.when().get(BASE_URI + "/user/keys"); // Send GET request // Print response
	Reporter.log(response.asString()); // Assertions
	response.then().spec(responseSpec) // Use responseSpec
	.statusCode(200); // Additional Assertion
	}
	  //delete request  
	  @Test(priority=3)
	  public void deleteSSHKeys(){
	  Response response = given().spec(requestSpec) // Use requestSpec
	                .pathParam("keyId", sshId) // Add path parameter
	                .when().get(BASE_URI +"/user/keys/{keyId}"); // Send GET request
	 
	Reporter.log(response.asString());

	  // Assertions
	  response.then().spec(responseSpec) // Use responseSpec
	.statusCode(200);
	 
	  }

}
