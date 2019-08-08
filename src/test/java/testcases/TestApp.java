package testcases;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class TestApp {
	public static URL url;
	public static DesiredCapabilities capabilities;
	public static AndroidDriver<MobileElement> driver; // 1

	AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
			.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
			.withAppiumJS(new File(
					"C:\\Users\\EdwardKalu\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"))
			.withLogFile(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\logs\\drag&dropLog.txt"))
			.withArgument(GeneralServerFlag.LOCAL_TIMEZONE));

	@BeforeSuite
	public void setupAppium() throws MalformedURLException {

		service.start();

		File app = new File(".\\app\\\\Drag Sort Demos_v0.5.0_apkpure.com.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	} // 5

	@AfterSuite
	public void close() throws InterruptedException {
		driver.quit();

		service.stop();
	}

	@Test(enabled = true)
	public void myFirstTest() throws InterruptedException {
		driver.findElements(By.id("com.mobeta.android.demodslv:id/activity_title")).get(0).click();

		MobileElement firstElement = driver.findElements(By.id("com.mobeta.android.demodslv:id/drag_handle")).get(0);
		MobileElement secondElement = driver.findElements(By.id("com.mobeta.android.demodslv:id/drag_handle")).get(3);

		TouchAction action = new TouchAction(driver);

		action.press(ElementOption.element(firstElement)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
				.moveTo(ElementOption.element(secondElement)).release().perform();
	}
}
