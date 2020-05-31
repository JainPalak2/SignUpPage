package nancy;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Except {
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") 
				+ "/extent-reports/" + new SimpleDateFormat("hh-mm-ss-ms-dd-MM-yyyy")
				.format(new Date(0)) + ".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "GFT NexGen Testing Stream");
		extent.setSystemInfo("Environment", "Automation Testing Selenium");
		extent.setSystemInfo("User Name", "Pratiksha Daptare");
		htmlReporter.config().setDocumentTitle("AGP-BatchReport");
		htmlReporter.config().setReportName("AGP-Batch-extentReport");
		htmlReporter.config().setTheme(Theme.STANDARD);

		System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://lkmdemoaut.accenture.com/TestMeApp/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

		
	}
	@Test(priority=1)
	public void reg() {
		logger = extent.createTest("passTest");
		driver.findElement(By.linkText("SignUp")).click();
		driver.findElement(By.id("userName")).sendKeys("jannanniii");
		driver.findElement(By.id("firstName")).sendKeys("naveen");
		driver.findElement(By.id("lastName")).sendKeys("teju");
		driver.findElement(By.id("password")).sendKeys("naveen123");
		driver.findElement(By.name("confirmPassword")).sendKeys("naveen123");
		driver.findElement(By.xpath("//input[@value= 'Male' and @type= 'radio']")).click();
		driver.findElement(By.id("emailAddress")).sendKeys("xyz@gmail.com");
		driver.findElement(By.id("mobileNumber")).sendKeys("7338337030");
		driver.findElement(By.name("dob")).sendKeys("11-29-1997");
		driver.findElement(By.id("address")).sendKeys("bengaluru");
		Select sel=new Select(driver.findElement(By.name("securityQuestion")));
		sel.selectByIndex(1);
		driver.findElement(By.id("answer")).sendKeys("Blue");
		driver.findElement(By.name("Submit")).click();
		logger.log(Status.PASS, MarkupHelper.createLabel("test case passed is Pass test", ExtentColor.GREEN));
	}
	@Test(priority=2)
	public void testLogin() {
		logger = extent.createTest("passTest");
		driver.findElement(By.id("userName")).sendKeys("lalitha");
		driver.findElement(By.id("password")).sendKeys("password123");
		driver.findElement(By.xpath("//input[@name='Login']")).click();
		Assert.assertEquals("Home",driver.getTitle());
		logger.log(Status.PASS, MarkupHelper.createLabel("test case passed is Pass test", ExtentColor.GREEN));
	}
	@Test(priority=3)
	public void testCart() {
		logger = extent.createTest("passTest");
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.linkText("All Categories"))).build().perform();
	
		act.moveToElement(driver.findElement(By.linkText("Electronics"))).click().build().perform();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		act.moveToElement(driver.findElement(By.linkText("Travel Kit"))).click().build().perform();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Assert.assertEquals("Search",driver.getTitle());
		driver.findElement(By.linkText("Add to cart")).click();
		driver.findElement(By.xpath("//a[@href='displayCart.htm']")).click();
		Assert.assertEquals("View Cart",driver.getTitle());
		driver.findElement(By.linkText("Checkout")).click();
		driver.findElement(By.xpath("//input[@value='Proceed to Pay']")).click();
		logger.log(Status.PASS, MarkupHelper.createLabel("test case passed is Pass test", ExtentColor.GREEN));
	}
	@Test(priority=4)
	public void testPayment() {
		logger = extent.createTest("passTest");
		driver.findElement(By.xpath("//label[text()='Andhra Bank']")).click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='#' and @id='btn']")).click();
		Assert.assertEquals("Payment Gateway", driver.getTitle());
		driver.findElement(By.name("username")).sendKeys("123456");
		driver.findElement(By.name("password")).sendKeys("Pass@456");
		driver.findElement(By.xpath("//input[@type='submit' and @value='LOGIN']")).click();
		Assert.assertEquals("Payment Gateway", driver.getTitle());
		driver.findElement(By.name("transpwd")).sendKeys("Trans@456");
		driver.findElement(By.xpath("//input[@type='submit' and @value='PayNow']")).click();
		Assert.assertEquals("Order Details",driver.getTitle());
		logger.log(Status.PASS, MarkupHelper.createLabel("test case passed is Pass test", ExtentColor.GREEN));
	}
	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}else if (result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
	}
	@AfterTest
	public void afterTest() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
		System.out.println("closed all tabs");
		extent.flush();
		
	}

}
