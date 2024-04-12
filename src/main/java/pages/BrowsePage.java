package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class BrowsePage extends BasePage{
    public BrowsePage(WebDriver driver) {
        super(driver);
    }
    private static int count = 130;
    private static final String LANBUAGE_BUTTON="//div[@class='ScCoreButtonLabel-sc-s7h2b7-0 ijUtjZ']//div[text()='Language']";
    private static final String LANBUAGE_BUTTON_UKR="//div[text()='Українська']";
    private static final String BROWSE_HEADLINE="//h1[text()='Browse']";
    private static final String PREVIEW_CHANNEL_lINK="//p[@data-a-target='preview-card-channel-link']";
public void clickLanguageButton(){
    driver.findElement(By.xpath(LANBUAGE_BUTTON)).click();
}
public void clickLanguageButtonUKR(){
    driver.findElement(By.xpath(LANBUAGE_BUTTON_UKR)).click();
}
public void clickBrowseHeadline(){
    driver.findElement(By.xpath(BROWSE_HEADLINE)).click();
}
public String getPreviewLink(){
    String result=driver.findElements(By.xpath(PREVIEW_CHANNEL_lINK)).get(count).getText();
         count=count+1;
         return result;
}
public void openNewTab(){
    driver.findElement(By.xpath(BROWSE_HEADLINE)).sendKeys(Keys.CONTROL +"t");
}

}
