import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

public class WordPressTest {
	
	public static WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:\\Chrome Profile");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@After
	public void closeDown() {
		driver.quit();
	}
	
	@Test
	public void testPageIsNavigable() {
		driver.get("https://wordpress.com/me");
		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		assert(driver.findElements(By.cssSelector("input[id='first_name']")).size()>0);
		assert(driver.findElements(By.cssSelector("input[id='last_name']")).size()>0);
		assert(driver.findElements(By.cssSelector("input[id='display_name']")).size()>0);
		assert(driver.findElements(By.cssSelector("textarea[id='description']")).size()>0);
	}
	
	@Test
	public void testEditProfile() {
		driver.get("https://wordpress.com/me");
		
		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		assert(driver.findElement(By.xpath("//button[text()='Save profile details']")).isEnabled() == false);
		driver.findElement(By.cssSelector("input[id='first_name']")).click();
		driver.findElement(By.cssSelector("input[id='first_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='last_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='display_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("textarea[id='description']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals(""));
		
		// Fill in Fields
		
		driver.findElement(By.cssSelector("input[id='first_name']")).sendKeys("Evan");
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals("Evan"));
		driver.findElement(By.cssSelector("input[id='last_name']")).sendKeys("Teng");
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals("Teng"));
		driver.findElement(By.cssSelector("input[id='display_name']")).sendKeys("evanteng");
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals("evanteng"));
		driver.findElement(By.cssSelector("textarea[id='description']")).sendKeys("A QA programmer living in Washington, DC.");
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals("A QA programmer living in Washington, DC."));
		assert(driver.findElement(By.xpath("//button[text()='Save profile details']")).isEnabled() == true);
		driver.findElement(By.xpath("//button[text()='Save profile details']")).click();
		
		new WebDriverWait(driver, 3).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return driver.findElement(By.cssSelector("span[class='notice__text']")).getText().equals("Settings saved successfully!") 
						&& driver.findElement(By.cssSelector("h2[class='profile-gravatar__user-display-name']")).getText().equals("evanteng");
			}
		});
		
		// Verify
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals("Evan"));
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals("Teng"));
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals("evanteng"));
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals("A QA programmer living in Washington, DC."));
		assert(driver.findElement(By.cssSelector("h2[class='profile-gravatar__user-display-name']")).getText().equals("evanteng"));
		assert(driver.findElement(By.xpath("//button[text()='Save profile details']")).isEnabled() == false);
		
		// Empty Fields
		
		driver.get("https://wordpress.com/me");
		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		driver.findElement(By.cssSelector("input[id='first_name']")).click();
		driver.findElement(By.cssSelector("input[id='first_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='last_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='display_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("textarea[id='description']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals(""));
		assert(driver.findElement(By.xpath("//button[text()='Save profile details']")).isEnabled() == true);
		driver.findElement(By.xpath("//button[text()='Save profile details']")).click();
		
		new WebDriverWait(driver, 3).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return driver.findElement(By.cssSelector("span[class='notice__text']")).getText().equals("Settings saved successfully!");
			}
		});
		
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals(""));
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals(""));
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals(""));
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals(""));
	}
	
	@Test
	public void testWarningPopUp() {
		driver.get("https://wordpress.com/me");

		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		assert(driver.findElement(By.xpath("//button[text()='Save profile details']")).isEnabled() == false);
		driver.findElement(By.cssSelector("input[id='first_name']")).click();
		driver.findElement(By.cssSelector("input[id='first_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='first_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='last_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("input[id='last_name']")).sendKeys("Retest");
		assert(driver.findElement(By.cssSelector("input[id='last_name']")).getAttribute("value").equals("Retest"));
		driver.findElement(By.cssSelector("input[id='display_name']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("input[id='display_name']")).getAttribute("value").equals(""));
		driver.findElement(By.cssSelector("textarea[id='description']")).sendKeys(Keys.chord(Keys.CONTROL ,"a") + Keys.DELETE);
		assert(driver.findElement(By.cssSelector("textarea[id='description']")).getText().equals(""));
		
		driver.findElement(By.cssSelector("a[href='/me/account']")).click();
		assert(driver.switchTo().alert().getText().equals("You have unsaved changes. Are you sure you want to leave this page?"));
		driver.switchTo().alert().dismiss();

		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		driver.findElement(By.cssSelector("a[href='/me/account']")).click();
		assert(driver.switchTo().alert().getText().equals("You have unsaved changes. Are you sure you want to leave this page?"));
		driver.switchTo().alert().accept();
	}
	
	@Test
	public void testManageLinks() {
		driver.get("https://wordpress.com/me");
		assert(driver.findElement(By.className("formatted-header__title")).getText().equals("My Profile"));
		driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[3]/div[2]/button")).click();
		assert(driver.findElements(By.cssSelector("div[class='popover__menu']")).size() > 0);
		driver.findElement(By.xpath("//button[normalize-space()='Add WordPress Site']")).click();
		assert(driver.findElement(By.cssSelector("input[type='checkbox']")) != null);
		assert(driver.findElement(By.xpath("//button[text()='Add Sites']")).isEnabled() == false);
		driver.findElement(By.cssSelector("input[type='checkbox']")).click();
		assert(driver.findElement(By.xpath("//button[text()='Add Site']")).isEnabled());
		driver.findElement(By.xpath("//button[text()='Add Site']")).click();
		assert(driver.findElement(By.cssSelector("button[class='button profile-link__remove is-borderless']")) != null);
		driver.findElement(By.cssSelector("button[class='button profile-link__remove is-borderless']")).click();
		assert(driver.findElement(By.cssSelector("p[class='profile-links__no-links']"))
				.getText().equals("You have no sites in your profile links. You may add sites if you'd like."));
		
		driver.findElement(By.xpath("//*[@id=\"primary\"]/main/div[3]/div[2]/button")).click();
		assert(driver.findElements(By.cssSelector("div[class='popover__menu']")).size() > 0);
		driver.findElement(By.xpath("//button[normalize-space()='Add URL']")).click();
		assert(driver.findElements(By.cssSelector("input[placeholder='Enter a URL']")).size() > 0);
		assert(driver.findElements(By.cssSelector("input[placeholder='Enter a description']")).size() > 0);
		assert(driver.findElement(By.xpath("//button[text()='Add Site']")).isEnabled() == false);
		
		driver.findElement(By.cssSelector("input[placeholder='Enter a URL']")).sendKeys("https://www.aweber.com/");
		assert(driver.findElement(By.cssSelector("input[placeholder='Enter a URL']")).getAttribute("value").equals("https://www.aweber.com/"));
		driver.findElement(By.cssSelector("input[placeholder='Enter a description']")).sendKeys("AWeber");
		assert(driver.findElement(By.cssSelector("input[placeholder='Enter a description']")).getAttribute("value").equals("AWeber"));
		assert(driver.findElement(By.xpath("//button[text()='Add Site']")).isEnabled());
		driver.findElement(By.xpath("//button[text()='Add Site']")).click();
		
		assert(driver.findElement(By.cssSelector("span[class='profile-link__title']")).getText().equals("AWeber"));
		assert(driver.findElement(By.cssSelector("span[class='profile-link__url']")).getText().equals("www.aweber.com/"));
		assert(driver.findElement(By.cssSelector("button[class='button profile-link__remove is-borderless']")) != null);
		driver.findElement(By.cssSelector("button[class='button profile-link__remove is-borderless']")).click();
		assert(driver.findElement(By.cssSelector("p[class='profile-links__no-links']"))
				.getText().equals("You have no sites in your profile links. You may add sites if you'd like."));
	}
}
