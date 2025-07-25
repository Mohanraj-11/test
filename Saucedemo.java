package sauceDemo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Saucedemo {

    public static void main(String[] args) throws IOException {
        
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Capture product name
        WebElement firstProduct = driver.findElement(By.className("inventory_item_name"));
        String productName = firstProduct.getText();
        System.out.println("The first product name is: " + productName);

        // Full page screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("./screenshot.png");
        FileUtils.copyFile(src, dest);

        // Capture and print price
        WebElement firstProductPrice = driver.findElement(By.className("inventory_item_price"));
        String priceText = firstProductPrice.getText();
        System.out.println("The first product price is: " + priceText);

        // Price screenshot
        File priceScreenshot = firstProductPrice.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(priceScreenshot, new File("./price_screenshot.png"));

        // Add to cart
        WebElement addToCartBtn = driver.findElement(By.xpath("(//button[contains(@id,'add-to-cart')])[1]"));
        addToCartBtn.click();

        // Go to cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Checkout
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Mohan");
        driver.findElement(By.id("last-name")).sendKeys("Kumar");
        driver.findElement(By.id("postal-code")).sendKeys("600001");

        // Continue to overview
        driver.findElement(By.id("continue")).click();

        // ✅ Collect all product prices on overview page
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> prices = new ArrayList<>();

        for (WebElement price : priceElements) {
            String text = price.getText().replace("$", "");
            prices.add(Double.parseDouble(text));
        }

        // ✅ Sort and print second largest price
        Collections.sort(prices);  // ascending order
        if (prices.size() >= 2) {
            double secondHighest = prices.get(prices.size() - 2);
            System.out.println("Second highest price: $" + secondHighest);
        } else {
            System.out.println("Not enough products to find second highest price.");
        }

        // ✅ Click Finish
        driver.findElement(By.id("finish")).click();

        // ✅ Take screenshot of confirmation page
        File confirmationScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(confirmationScreenshot, new File("./confirmation.png"));
        System.out.println("Order confirmation screenshot saved.");

        // ✅ Close browser
        driver.quit();
    }
}
