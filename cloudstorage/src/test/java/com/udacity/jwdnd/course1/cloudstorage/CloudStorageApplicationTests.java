package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

/*
Resources used:
1) Udacity Cognizant Full Stack Developer Train to Hire Course videos and execise projects
2) Udacity Connect session notes and examples demo code
3)Tony Session Lead Doc -https://docs.google.com/spreadsheets/d/1RdQnR5scVZUVORQbcf1YsoZltlJxaP5tKubf0ceECs4/edit#gid=0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {

		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login?registration=success", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));

	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	private void doLogOut()
	{
		// Log out of dummy account.

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Login"));

	}

	//Test that verifies that the home page is not accessible without logging in.
	@Test
	public void testHomePageWithoutLogging() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertFalse(driver.getTitle().contains("Home"));
	}

	//Write a Selenium test that signs up a new user, logs that user in,
	// verifies that they can access the home page, then logs out and
	// verifies that the home page is no longer accessible.

	@Test
	public void testHomePageWithLogging() {
		doMockSignUp("Home","Test","HP","123");
		doLogIn("HP", "123");
		Assertions.assertTrue(driver.getTitle().contains("Home"));
		doLogOut();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertFalse(driver.getTitle().contains("Home"));

	}
 //Create Note test
 private void createNote(String ntitle, String ndesc) {
	 WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
	 WebElement buttonNoteTab = driver.findElement(By.id("nav-notes-tab"));
	 buttonNoteTab.click();

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNote")));
	 WebElement buttonAddNote = driver.findElement(By.id("btnAddNote"));
	 buttonAddNote.click();

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
	 WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
	 inputNoteTitle.click();
	 inputNoteTitle.sendKeys(ntitle);

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
	 WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
	 inputNoteDescription.click();
	 inputNoteDescription.sendKeys(ndesc);

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveNote")));
	 WebElement buttonSaveNote = driver.findElement(By.id("btnSaveNote"));
	 buttonSaveNote.click();



 }
 private void signLoginUserWithNotes(String firstname, String lastname, String username, String password, String noteStatus){
	 doMockSignUp(firstname,lastname,username,password);
	 doLogIn(username, password);
	 createNote("ToDoList", "This is Note1");
	 if(noteStatus.equals("edit")){
		 editNote("Note Title Updated");
	 }
	 else if(noteStatus.equals("delete")){
		 deleteNote();
	 }

 }
 //Test to check create note functionality
 @Test
 public void testCreateNote(){

	 signLoginUserWithNotes("Note","Add","NA", "PNA","create");
	 WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

	 driver.get("http://localhost:" + this.port + "/home");
	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
	 WebElement buttonViewNoteAfterSaveTab = driver.findElement(By.id("nav-notes-tab"));
	 buttonViewNoteAfterSaveTab.click();

	 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
	 WebElement webElement = driver.findElement(By.id("nav-notes"));
	 Assertions.assertTrue(webElement.getText().contains("ToDoList"));
	 doLogOut();


 }
	//Test to check edit note functionality
	@Test
	public void testEditNote(){

		signLoginUserWithNotes("Note","Edit","NE", "PNE","edit");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonViewNoteAfterSaveTab = driver.findElement(By.id("nav-notes-tab"));
		buttonViewNoteAfterSaveTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		WebElement webElement = driver.findElement(By.id("nav-notes"));
		Assertions.assertTrue(webElement.getText().contains("Note Title Updated"));
		doLogOut();


	}
    private void editNote(String updatedTitle){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonViewNoteAfterSaveTab = driver.findElement(By.id("nav-notes-tab"));
		buttonViewNoteAfterSaveTab.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonNoteTab = driver.findElement(By.id("nav-notes-tab"));
		buttonNoteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditNote")));
		WebElement buttonEditNote = driver.findElement(By.id("btnEditNote"));
		buttonEditNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
		inputNoteTitle.clear();
		inputNoteTitle.click();
		inputNoteTitle.sendKeys(updatedTitle);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveNote")));
		WebElement buttonSaveNote = driver.findElement(By.id("btnSaveNote"));
		buttonSaveNote.click();
	}

	private void deleteNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonViewNoteAfterSaveTab = driver.findElement(By.id("nav-notes-tab"));
		buttonViewNoteAfterSaveTab.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonNoteTab = driver.findElement(By.id("nav-notes-tab"));
		buttonNoteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnDelNote")));
		WebElement buttonDeleteNote = driver.findElement(By.id("btnDelNote"));
		buttonDeleteNote.click();


	}
	//Test to check delete note functionality
	@Test
	public void testDeleteNote(){

		signLoginUserWithNotes("Note","Delete","ND", "PND","delete");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement buttonViewNoteAfterSaveTab = driver.findElement(By.id("nav-notes-tab"));
		buttonViewNoteAfterSaveTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		WebElement webElement = driver.findElement(By.id("nav-notes"));
		Assertions.assertFalse(webElement.getText().contains("ToDoList"));
		doLogOut();


	}

	//Create Credential test
	private void createCredential(String url, String username, String password) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonNoteTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonNoteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddCrdential")));
		WebElement buttonAddCredential = driver.findElement(By.id("btnAddCrdential"));
		buttonAddCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialURL = driver.findElement(By.id("credential-url"));
		inputCredentialURL.click();
		inputCredentialURL.sendKeys(url);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputCredentialUserName = driver.findElement(By.id("credential-username"));
		inputCredentialUserName.click();
		inputCredentialUserName.sendKeys(username);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputCredentialPwd = driver.findElement(By.id("credential-password"));
		inputCredentialPwd.click();
		inputCredentialPwd.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveCredential")));
		WebElement buttonSaveNote = driver.findElement(By.id("btnSaveCredential"));
		buttonSaveNote.click();



	}
	private void signLoginUserWithCredential(String cfname,String clname, String cusername, String cpassword, String credentialStatus){
		doMockSignUp(cfname, clname, cusername, cpassword );
		doLogIn(cusername, cpassword);
		createCredential("Credential One", "username1", "password1");
		if(credentialStatus.equals("edit")){
			editCredential("Credential Title Updated");
		}
		else if(credentialStatus.equals("delete")){
			deleteCredential();
		}

	}

	//Test to check create credential functionality
	@Test
	public void testCreateCredential(){

		signLoginUserWithCredential("Credential","Add","CA", "PCA","create");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonViewCredentialAfterSaveTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonViewCredentialAfterSaveTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		WebElement webElement = driver.findElement(By.id("nav-credentials"));
		Assertions.assertTrue(webElement.getText().contains("Credential One"));
		doLogOut();


	}

	//Test to check edit credential functionality
	@Test
	public void testEditCredential(){

		signLoginUserWithCredential("Credential ","Edit","CE", "PCE","edit");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonViewCredentialAfterSaveTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonViewCredentialAfterSaveTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		WebElement webElement = driver.findElement(By.id("nav-credentials"));
		Assertions.assertTrue(webElement.getText().contains("Credential Title Updated"));
		doLogOut();


	}
	private void editCredential(String updatedTitle){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonViewCredentialAfterSaveTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonViewCredentialAfterSaveTab.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		WebElement webElement = driver.findElement(By.id("nav-credentials"));
		webElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditCredential")));
		WebElement buttonEditCredential= driver.findElement(By.id("btnEditCredential"));
		buttonEditCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialUrl = driver.findElement(By.id("credential-url"));
		inputCredentialUrl.clear();
		inputCredentialUrl.click();
		inputCredentialUrl.sendKeys(updatedTitle);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveCredential")));
		WebElement buttonSaveNote = driver.findElement(By.id("btnSaveCredential"));
		buttonSaveNote.click();
	}

	private void deleteCredential(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonViewCredentialAfterSaveTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonViewCredentialAfterSaveTab.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		WebElement webElement = driver.findElement(By.id("nav-credentials"));
		webElement.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnDelCredential")));
		WebElement buttonDeleteNote = driver.findElement(By.id("btnDelCredential"));
		buttonDeleteNote.click();


	}
	//Test to check delete  credential functionality
	@Test
	public void testDeleteCredential(){

		signLoginUserWithCredential("Credential","Delete","CD", "PCD","delete");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement buttonfterDelTab = driver.findElement(By.id("nav-credentials-tab"));
		buttonfterDelTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		WebElement webElement = driver.findElement(By.id("nav-credentials"));
		Assertions.assertFalse(webElement.getText().contains("Credential One"));
		doLogOut();


	}


}
