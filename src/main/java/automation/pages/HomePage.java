package automation.pages;

import automation.utils.Constants;
import automation.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
    private final WebDriver driver;
    private WebDriverWait wait;
    private Actions hover;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement waitUntilElementIsClickable(WebElement webElement) {
        wait = new WebDriverWait(driver, Constants.TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    @FindBy(xpath = "//a[@title='Add to cart' and @data-id-product='1']")
    private WebElement addToCartFirst;

    @FindBy(xpath = "//a[@title='Add to cart' and @data-id-product='2']")
    private WebElement addToCartSecond;

    @FindBy(xpath = "//a[@title='View my shopping cart']")
    private WebElement cart;

    @FindBy(xpath = "//a[@title='Proceed to checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//span[@title='Continue shopping']")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//li[contains(@class,'first-in-line first-item-of-tablet-line')]")
    private WebElement firstElement;

    @FindBy(xpath = "//li[contains(@class,'col-xs-12 col-sm-4 col-md-3 last-item-of-mobile-line')]")
    private WebElement secondElement;

    @FindBy(linkText = "Sign in")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@class='account']//span")
    private WebElement username;

    @FindBy(id = "search_query_top")
    private WebElement searchBar;

    @FindBy(name = "submit_search")
    private WebElement searchButton;

    @FindBy(css = "#center_column > ul > li > div > div.left-block > div > a.product_img_link > img")
    private WebElement searchResults;

    public void goTo(String url) {
        driver.get(url);
    }

    public Boolean searchElement(String searchStr) {
        searchBar.sendKeys(searchStr);
        searchButton.click();
        try {
            if (searchResults.isEnabled())
                return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public void clickSignIn() {
        waitUntilElementIsClickable(signInButton);
        signInButton.click();
    }

    public String getUserName() {
        return username.getText();
    }

    public void addFirstElementToCart() {
        hover = new Actions(driver);
        hover.moveToElement(firstElement).build().perform();
        addToCartFirst.click();
        waitUntilElementIsClickable(continueShoppingButton);
        continueShoppingButton.click();
        if (cart.getText().contains(Constants.CART_QUANTITY))
            System.out.println("Cart has been updated");
        else {
            System.out.println("Cart has not been updated");
            Utils.takeScreenshot();
        }
    }

    public void addSecondElementToCart() {
        hover = new Actions(driver);
        hover.moveToElement(secondElement).build().perform();
        addToCartSecond.click();
        waitUntilElementIsClickable(proceedToCheckoutButton);
        proceedToCheckoutButton.click();
    }
}


