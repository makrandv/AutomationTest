/*
Base Page class holds methods common to all product/page model for the given application
 */

package modelPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver)
    {
        this.driver=driver;
    }

    /*Verification of Session Expiry message appear after waiting for 500ms when no activity is performed
        on the page*/
    public boolean VerifySessionExpiryMessage()
    {
        return driver.findElement(By.xpath(".//div[contains(text(),'Your session has expired')]")).isDisplayed();
    }

    /*
    Click on the refresh results button on the session expiry message box
     */
    public void ClickRefreshResultonSessionExpiry()
    {
        driver.findElement(By.xpath(".//span[contains(text(),'Refresh Results')]")).click();
    }
}
