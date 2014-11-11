package functional.com.trailblazers.freewheelers.apis;

import functional.com.trailblazers.freewheelers.helpers.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

import static functional.com.trailblazers.freewheelers.helpers.SyntaxSugar.*;
import static functional.com.trailblazers.freewheelers.helpers.SyntaxSugar.PAYMENT_THANKYOU;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScreenApi {
    private WebDriver driver;

    public ScreenApi(WebDriver driver) {
        this.driver = driver;
    }

    public void shows_error_alert(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "error");
    }


    public ScreenApi shows_error(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "text-error");
        return this;
    }

    public ScreenApi shows_error_in_red(String expectedMessage){
        String message = driver.findElement(By.id("paymentErrorMessage")).getText();
        assertThat(expectedMessage, is(message));
        return this;
    }

    public void shows_message(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "page-action");
    }

    public void shows_in_navbar(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "navbar-text");
    }


    public ScreenApi shows_profile_for(String name) {
        String userDetails = driver.findElement(By.id("user-details")).getText();

        assertThat(userDetails, containsString(name));
        return this;
    }

    public ScreenApi shows_login() {
        assertThat(driver.getCurrentUrl(), is(URLs.login()));
        return this;
    }

    public ScreenApi shows_admin_profile() {
        assertThat(driver.getCurrentUrl(), is(URLs.admin()));
        return this;
    }

    public ScreenApi shows_in_manage_item_list(String item) {
        assertNumberOfRows(1, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi shows_not_in_manage_item_list(String item) {
        assertNumberOfRows(0, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_list_item(String item) {
        assertNumberOfRows(1, HomeTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_not_list_item(String item) {
        assertNumberOfRows(0, HomeTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi there_should_be_an_order(String item, String state) {
        WebElement select = driver.findElement(OrderTable.selectFor(item));
        String selected = new Select(select).getFirstSelectedOption().getText();

        assertThat(selected, is(state));

        return this;
    }

    private void assertNumberOfRows(int expectedRows, By selector) {
        List<WebElement> elements = driver.findElements(selector);
        assertThat(elements.size(), is(expectedRows));
    }

    private void assertOrderOfRows(String[] itemNames, By itemListSelector, By firstColumnOfRow) {
        Arrays.sort(itemNames);
        List<WebElement> elements = driver.findElements(itemListSelector);
        int i = 1;
        for (WebElement itemRow : elements) {
            String actualName = itemRow.findElement(firstColumnOfRow).getText();
            assertEquals(itemNames[i], actualName);
            i++;
        }
    }

    private ScreenApi expectMessageWithClass(String expectedMessage, String messageClass) {
        List<WebElement> elements = driver.findElements(By.className(messageClass));
        boolean foundElement = false;
        for (WebElement element : elements) {
            if (element.getText().contains(expectedMessage)) foundElement = true;

        }
        assertTrue("Expected error message '" + expectedMessage + "' not found.", foundElement);
        return this;
    }

    public ScreenApi should_show_access_denied() {
        assertThat(driver.getPageSource(), containsString("403 Your access is denied"));
        return this;
    }

    public ScreenApi should_not_contain_nps_report_link_in_header() {
        assertThat(driver.findElements(By.linkText("NPS Report")).size(), is(0));
        return this;
    }

    public ScreenApi shouldShowNPSReportPage() {
        assertThat(driver.getCurrentUrl(), is(URLs.surveyReport()));
        assertThat(driver.findElement(By.className("net-promoter-score")).getText(), containsString("Net Promoter Score is"));
        return this;
    }

    public ScreenApi should_show_phone_number() {
        assertThat(driver.findElement(By.className("phone-number")).getText(), not("Phone Number :"));
        return this;
    }

    public ScreenApi shows_shopping_cart() {
        assertThat(driver.getCurrentUrl(), is(URLs.shoppingCart() + "/myShoppingCart"));
        return this;
    }

    public ScreenApi should_list_item_in_shopping_cart(String item) {
        assertNumberOfRows(1, ItemsTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_not_list_item_in_shopping_cart(String item) {
        assertNumberOfRows(0, ItemsTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_visit_confirmation_page() {
        assertThat(driver.getCurrentUrl(), containsString(URLs.shoppingCart() + "/confirmation"));
        return this;
    }

    public ScreenApi shouldGoToHomePage() {
        assertThat(driver.getCurrentUrl(), is(URLs.home()));
        return this;
    }

    public ScreenApi shouldShowAddressTitleInUserProfile() {
        assertThat(driver.findElement(By.className("user-address-title")).getText(), is("Address:"));
        return this;
    }

    public ScreenApi shouldShowAddressOfTheUser() {
        assertThat(driver.findElement(By.className("user-address")).getText(), containsString(SOME_STREET));
        assertThat(driver.findElement(By.className("user-address")).getText(), containsString(SOME_CITY));
        assertThat(driver.findElement(By.className("user-address")).getText(), containsString(VALID_COUNTRY));
        assertThat(driver.findElement(By.className("user-address")).getText(), containsString(SOME_POSTCODE));
        return this;
    }

    public ScreenApi shouldShowSuccessAtHomePageForAddingItemToCart() {
        assertThat(driver.getCurrentUrl(), is(URLs.home()));
        assertThat(driver.findElement(By.id("resultMessage")).getText(), containsString(" has been added to your shopping cart."));
        return this;
    }

    public ScreenApi showsQuantityForItem(String itemName, long newQuantity) {
        WebElement input = driver.findElement(ManageItemTable.quantityFieldFor(itemName));
        String value = input.getAttribute("value");

        assertThat(value, is(String.valueOf(newQuantity)));
        return this;
    }

    public ScreenApi should_list_items_with_order(String[] names) {
        assertNumberOfRows(2, ItemsTable.items());
        for (String name : names) {
            assertNumberOfRows(1, ItemsTable.nameFieldFor(name));
        }
        assertOrderOfRows(names, ItemsTable.items(), ItemsTable.firstColumnOfRow());
        return this;
    }

    public ScreenApi shouldListItemInUserOrders(String itemName) {
        assertNumberOfRows(1, ItemsTable.nameFieldFor(itemName));
        return this;
    }

    public ScreenApi shouldShowListOfOrderedItems(String... names) {
        assertThat(containsItem(names[0]), is(true));
        assertThat(containsItem(names[1]), is(true));
        return this;
    }

    private boolean containsItem(String name) {
        if (driver.findElement(By.id("order-table")).getText().contains(name)) {
            return true;
        }

        return false;
    }

    public ScreenApi should_confirm_payment() {
        assertThat(driver.findElement(By.id("confirmationMessage")).getText(), containsString(PAYMENT_THANKYOU));
        return this;
    }

    public ScreenApi should_show_card_fields(String cardNumber, String expiryMonth, String expiryYear, String CCV) {
        assertThat(driver.findElement(By.id("fld_cardNumber")).getAttribute("value"), is(cardNumber));
        assertThat(driver.findElement(By.id("sel_expiration_date_month")).getAttribute("value"), is(expiryMonth));
        assertThat(driver.findElement(By.id("sel_expiration_date_year")).getAttribute("value"), is(expiryYear));
        assertThat(driver.findElement(By.id("fld_csc")).getAttribute("value"), is(CCV));
        assertThat(driver.findElement(By.id("label_amount")).getText(), is("Amount:"));
        return this;
    }

    public ScreenApi should_show_address_in_confirmation_page() {
        assertThat(driver.findElement(By.id("shippingAddress")).getText(), containsString("Shipping Address"));
        return this;
    }
}
