package smokeTests;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.BrowsePage;
import pages.ChannelPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BaseTests {

    private WebDriver driver;
    private static final String TWITCH_BROWSE_PAGE = "https://www.twitch.tv/directory/all?sort=VIEWER_COUNT_ASC";
    private static final String FILE_NAME = "test.xlsx";

    @BeforeTest
    public void profileSetup() throws IOException {
        // Set Firefox driver path (if not in PATH)
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");

//        // Define profile path using environment variable
//        String profilePath = System.getenv("APPDATA") + "\\Mozilla\\Firefox\\Profiles\\" + "16jn7mwq.default-release";
//
//        // Create FirefoxOptions and set profile
//        FirefoxOptions options = new FirefoxOptions();
//        options.setProfile(new FirefoxProfile(new File(profilePath)));
//
//        // Create the driver
//        driver = new FirefoxDriver(options);
    }

    @BeforeMethod
    public void testsSetup() {
        String profilePath = System.getenv("APPDATA") + "\\Mozilla\\Firefox\\Profiles\\" + "4rtfcuiu.default-release";

        // Create FirefoxOptions and set profile
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile(new File(profilePath)));
//        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("user-data-dir=" + System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
      // options
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get(TWITCH_BROWSE_PAGE);
    }

    public static void writeToExcel(String status, String link) {
        try {
            Workbook workbook;
            File file = new File(FILE_NAME);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet("Results");
            if (sheet == null) {
                sheet = workbook.createSheet("Results");
            }

            int lastRowNum = sheet.getLastRowNum();
            Row row;
            if (status.equals("success")) {
                row = sheet.createRow(lastRowNum + 1);
                row.createCell(0).setCellValue(status);
                row.createCell(1).setCellValue(link);
            } else if (status.equals("skipped")) {
                row = sheet.getRow(lastRowNum);
                if (row == null) {
                    row = sheet.createRow(lastRowNum);
                }
                row.createCell(2).setCellValue(status);
                row.createCell(3).setCellValue(link);
            }

            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @AfterMethod
//    public void tearDown() {
//        driver.close();
//    }

    public WebDriver getDriver() {
        return driver;
    }

    public BrowsePage getBrowsePage() {
        return new BrowsePage(getDriver());
    }

    public ChannelPage getChannelPage() {
        return new ChannelPage(getDriver());
    }
}
