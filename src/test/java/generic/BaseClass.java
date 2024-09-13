package generic;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static WebDriver driver;
	public FileLib f;
	
	@BeforeClass
	public WebDriver openBrowser() throws IOException {
		WebDriverManager.chromedriver().setup();
		// Initializing the driver of specific browser type object
		driver = new ChromeDriver();
		Reporter.log("Browser opened", true);
		// Maximize the browser window
		driver.manage().window().maximize();
		// Adding Implicit wait for all the WebElement
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		f=new FileLib();
		String url = f.getPropertyData("url");
		driver.get(url);;
		return driver;
	}
	
	@AfterClass
	public void closeBrowser() {
		Reporter.log("closeBrowser",true);	
		driver.quit();
	}

}

