package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ChannelPage extends BasePage {
    public ChannelPage(WebDriver driver) {
        super(driver);
    }

    private static final String TWITCH_MESSAGE_LINK = "Привіт, бачу, що у тебе не так багато глядачів, якщо вирішиш купити глядачів та розвивати стрім, то у нас найдешевший сервіс на ринку (тест від 6грн) https://twitch-viewers.minisite.ai/ Гарного тобі стріму! ❤";
    private static final String TWITCH_MESSAGE = "!уміртс\u00A0ібот\u00A0огонраГ\u00A0.нрг\u00A0sreweiv\u00A06\u00A0дів\u00A0тсет(укнир\u00A0ан\u00A0сіврес\u00A0йишвешедйан\u00A0сан\u00A0у\u00A0от,міртс\u00A0итавивзор\u00A0ат\u00A0вічадялг\u00A0итипук\u00A0шишірив\u00A0ощкя,вічадялг\u00A0отагаб\u00A0кат\u00A0ен\u00A0ебету\u00A0ощ,учаб,тівирП";
    private static final String TWITCH_CHAT_WINDOW = "//div[@data-a-target='chat-input']";
    ////div[@data-a-target='chat-input'] //div[@data-slate-node='element'] "//div[@class='chat-wysiwyg-input__editor']"
    private static final String SEND_MESSAGE_BUTTON = "//button[@aria-label='Send Chat']";  //div[text()='Chat']
    private static final String VERIFIED_ACCOUNT_MESSAGE = "//p[text()='Verified Accounts Only Chat']";
    private static final String BANNED_FROM_CHAT = "//p[text()='You are banned from Chat']";
    private static final String EMOTE_CHAT_ONLY = "//p[text()='Emote Only Chat']";
    private static final String FOLLOW_BUTTON = "//button[@aria-label='Follow']";
    private static final String FOLLOWERS_ONLY_CHAT = "//p[text()='Followers-Only Chat']";
    private static final String OFFLINE_TAG = "//p[text()='Offline']";
    private static final String CHAT_RULES_BUTTON = "//button[@class='ScCoreButton-sc-ocjdkq-0 ScCoreButtonPrimary-sc-ocjdkq-1 kGYojv gmCwLG']";

    public void sendMessageToChat() {
        driver.findElement(By.xpath(TWITCH_CHAT_WINDOW)).click();
        for (int i = 0; i < TWITCH_MESSAGE_LINK.length(); i++) {
            char currentChar = TWITCH_MESSAGE_LINK.charAt(i);
            driver.findElement(By.xpath(TWITCH_CHAT_WINDOW)).sendKeys(Character.toString(currentChar)); // Отправка текущего символа
            try {
                Thread.sleep(20); // Пауза между отправкой символов (можно настроить по вашему усмотрению)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickSendMessageButton() {
        driver.findElement(By.xpath(SEND_MESSAGE_BUTTON)).click();
    }

    public void clickFollowButton() {
        driver.findElement(By.xpath(FOLLOW_BUTTON)).click();
    }

    public void chatRulesButtonShown() {
        try {
            driver.findElement(By.xpath(TWITCH_CHAT_WINDOW)).click();
            if (driver.findElement(By.xpath(CHAT_RULES_BUTTON)).isDisplayed()) {
                driver.findElement(By.xpath(CHAT_RULES_BUTTON)).click();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void verifyIfStreamIsOn() {
        try {

            if (driver.findElement(By.xpath(OFFLINE_TAG)).isDisplayed()) {
                driver.close();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void checkIfBanned() {
        try {

            if (driver.findElement(By.xpath(BANNED_FROM_CHAT)).isDisplayed()) {
                driver.close();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void followMessageIsShown() {
        try {
            if (driver.findElement(By.xpath(FOLLOWERS_ONLY_CHAT)).isDisplayed()) {
                driver.close();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void emoteChatOnlyMessageIsShown() {
        try {
            if (driver.findElement(By.xpath(EMOTE_CHAT_ONLY)).isDisplayed()) {
                driver.close();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void verifiedAccountMessageIsShown() {
        if (isElementDisplayed(VERIFIED_ACCOUNT_MESSAGE)
                || isElementDisplayed(OFFLINE_TAG)
                || isElementDisplayed(EMOTE_CHAT_ONLY)
                || isElementDisplayed(FOLLOWERS_ONLY_CHAT)
                || isElementDisplayed(BANNED_FROM_CHAT)) {
            driver.close();
        }
    }

    private boolean isElementDisplayed(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}