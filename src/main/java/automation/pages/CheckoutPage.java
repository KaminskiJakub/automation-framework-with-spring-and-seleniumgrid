package automation.pages;

import automation.drivers.DriverSingleton;
import automation.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    public WebElement waitUntilElementIsClickable(WebElement webElement) {
        wait = new WebDriverWait(driver, Constants.TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    @FindBy(xpath = "//a[@class='button btn btn-default standard-checkout button-medium']")
    private WebElement checkoutButtonSummary;

    @FindBy(css = "button[name='processAddress']")
    private WebElement checkoutButtonConfirmAddress;

    @FindBy(id = "cgv")
    private WebElement confirmShippingCheckBox;

    @FindBy(css = "button[name='processCarrier']")
    private WebElement checkoutButtonConfirmShipping;

    @FindBy(css = "a.bankwire")
    private WebElement payByBankWireOption;

    @FindBy(css = "button[class='button btn btn-default button-medium']")
    private WebElement confirmOrder;

    @FindBy(css = "p.cheque-indent")
    private WebElement orderConfirmationMessage;

    @FindBy(id = "summary_products_quantity")
    private WebElement summaryProducts;

    public void goToCheckout() {
        waitUntilElementIsClickable(checkoutButtonSummary);
        checkoutButtonSummary.click();
    }

    public void confirmAddress() {
        waitUntilElementIsClickable(checkoutButtonConfirmAddress);
        checkoutButtonConfirmAddress.click();
    }

    public void confirmShipping() {
        waitUntilElementIsClickable(checkoutButtonConfirmShipping);
        confirmShippingCheckBox.click();
        checkoutButtonConfirmShipping.click();
    }

    public void payByBankWire() {
        waitUntilElementIsClickable(payByBankWireOption);
        payByBankWireOption.click();
    }

    public void confirmFinalOrder() {
        waitUntilElementIsClickable(confirmOrder);
        confirmOrder.click();
    }

    public Boolean checkFinalStatus() {
        waitUntilElementIsClickable(orderConfirmationMessage);
        return orderConfirmationMessage.getText().contains(Constants.COMPLETE_ORDER);
    }

    public String getSummaryProductsString() {
        return summaryProducts.getText();
    }
}
