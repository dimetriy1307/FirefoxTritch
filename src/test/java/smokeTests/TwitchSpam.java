package smokeTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.testng.annotations.Test;

import java.util.Set;


public class TwitchSpam extends BaseTests {
    private static final String TWITCH_LINK = "https://www.twitch.tv/";
    private static final String MY_LINK = "https://www.twitch.tv/directory/all?sort=VIEWER_COUNT_ASC";
    private static final String PREVIEW_CHANNEL_lINK = "//p[@data-a-target='preview-card-channel-link']";

    @Test
    public void writeAMessageToChat() {
        getBrowsePage().implicitWait(2);
        getBrowsePage().clickLanguageButton();
        getBrowsePage().clickLanguageButtonUKR();
        getBrowsePage().clickBrowseHeadline();
        try {
            Thread.sleep(15000); // Приостановка выполнения потока на 10 секунд
        } catch (InterruptedException e) {
            // Обработка исключения, если поток был прерван
            System.out.println("ok");
        }
        for (int k = 0; k < 100; k++) {
            getBrowsePage().implicitWait(4);
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            getBrowsePage().implicitWait(4);
        }
        while (true) {
            getBrowsePage().implicitWait(4);
            int count = 0;
            String link = "";
            link = getBrowsePage().getPreviewLink();
            link = TWITCH_LINK + link;
            getBrowsePage().implicitWait(4);
            String currentTab = getDriver().getWindowHandle();
            ((JavascriptExecutor) getDriver()).executeScript("window.open('" + link + "','_blank');");
            getBrowsePage().implicitWait(2);

            Set<String> tabs = getDriver().getWindowHandles();
            for (String tab : tabs) {
                if (!tab.equals(currentTab)) {
                    getDriver().switchTo().window(tab);
                    break;
                }
            }
            getBrowsePage().implicitWait(2);
            getChannelPage().verifiedAccountMessageIsShown();
            try {
                if (!getDriver().getCurrentUrl().equals(link)) {
                    getDriver().switchTo().window(currentTab);
                } else {
                getChannelPage().handleChatWindow();
                    getChannelPage().sendMessageToChat();
                    writeToExcel("success", link);
                    getChannelPage().implicitWait(2);
                    getChannelPage().clickSendMessageButton();
                    getChannelPage().clickSendMessageButton(); //второй раз на всякий случай
                    getDriver().close();
                    getDriver().switchTo().window(currentTab);
                }
            } catch (NoSuchWindowException e) {
                writeToExcel("skipped", link);
                getDriver().switchTo().window(currentTab);
            }
        }
    }

    @Test
    public void profileCheck() {
        getDriver().get("https://www.twitch.tv/Folegzon");
        getBrowsePage().implicitWait(5);
        getChannelPage().verifiedAccountMessageIsShown();
        getChannelPage().handleChatWindow();
        getChannelPage().sendMessageToChat();

    }
}