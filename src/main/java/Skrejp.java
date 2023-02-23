import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Skrejp {

    private static ListStorage listStorage = new ListStorage();

    private WebDriver driver;
    private WebDriverWait wait;
    private FileWriter writer;

    public Skrejp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    public void scrape(String website, String book) {
        // Setting TXT File
        //writer = new FileWriter("C:\\Users\\EliteBook\\Desktop\\" + book + ".txt", true);

        int pageNum = 27;

        while (true) {
            driver.get(website + pageNum + "?page=" + pageNum);

            List<WebElement> bookListing = driver.findElements(By.className("AdItem_adHolder__NoNLJ"));

            for (WebElement listing : bookListing) {
                String title = listing.findElement(By.className("AdItem_name__80tI5")).getText();
                String price = listing.findElement(By.className("AdItem_price__jUgxi")).getText();
                if (title.toLowerCase().contains(book)) {
                    System.out.println("Knjiga --> " + title + " --> " + price + " --> str " + pageNum);
                    //writer.write("Knjiga --> " + title + " --> " + price + " --> str " + pageNum + "\n");

                    listStorage.addBook(title, price, pageNum);
                }
            }

            //List<WebElement> nextButton = driver.findElements(By.className("next_prev_link"));

            List<WebElement> arrows = driver.findElements(By.className("Button_trailing__CU1T2"));
            if (arrows.size() == 2) {
                pageNum++;
            } else {
                System.out.println(listStorage.getBookList()); //ISPISUJE LISTU KNJIGA
                //writer.close();
                driver.quit();
            }

        }
    }

}