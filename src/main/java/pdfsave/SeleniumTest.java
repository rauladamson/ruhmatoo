package pdfsave;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {
    /**
     * Retired. Seleniumiga õisist cssrequestide tegemine kursuse andmete saamiseks
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/java/pdfsave/chromedriver32.exe");

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-web-security", "--disable-site-isolation-trials", "--remote-allow-origins=*"); // Giga hack?
        WebDriver driver = new ChromeDriver(options);



        try {
            driver.get("https://ois2.ut.ee/#/courses/LTAT.03.007/version/fde0bca8-705f-74c9-456e-e68104c23b53/details");
            Thread.sleep(5000); //hack for now :///



            WebElement hindamine_inf = driver.findElement(By.cssSelector("ois2-courses-version-grading-info-panel"));
            WebElement maht_inf = driver.findElement(By.cssSelector("ois2-courses-version-study-hours-info-panel"));

            String content = hindamine_inf.getText() + maht_inf.getText();


            System.out.println("Content: " + content);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}