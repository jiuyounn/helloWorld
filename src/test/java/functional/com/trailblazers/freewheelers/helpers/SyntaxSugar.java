package functional.com.trailblazers.freewheelers.helpers;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class SyntaxSugar {

    public static final String SOME_PHONE_NUMBER = "555-123456";
    public static final String SOME_PASSWORD = "V3ry Secure!";
    public static final String SOME_CONFIRMED_PASSWORD = "V3ry Secure!";
    public static final String SOME_INVALID_CONFIRMEDPASSWORD = "WRONG V3ry Secure!";
    public static final String SOME_EMAIL = "somebody@something.de";
    public static final String SOME_INVALID_EMAIL = "someb__ody@something.de";
    public static final String VERIFICATION_ID = "736f6d65626f647940736f6d657468696e672e6465";
    public static final BigDecimal SOME_PRICE = valueOf(49.99);
    public static final String EMPTY_COUNTRY = "Select";
    public static final String VALID_COUNTRY = "United Kingdom";
    public static final Integer VALID_COUNTRY_ID = 1;
    public static final String SOME_STREET = "Greenwood Avenue";
    public static final String SOME_CITY = "London";
    public static final String SOME_POSTCODE = "12453";
    public static final String SOME_STATE = "Somewhere";

    public static final String SOME_CARD_NUMBER = "100100100100";
    public static final String SOME_MONTH= "12";

    public static final String VALID_CARD_NUMBER = "4111111111111111";
    public static final String VALID_EXPIRAY_MONTH = "11";
    public static final String VALID_EXPIRAY_YEAR  = "2020";
    public static final String VALID_CCV  = "534";

    public static final String EMPTY_PASSWORD = "";
    public static final String NO_QUANTITY = "";
    public static final String ZERO_VALUE = "0";
    public static final long ONLY_ONE_LEFT = 1L;
    public static final String REALLY_EXPENSIVE = "2899.00";
    public static final String SOME_DESCRIPTION = "4 x red, curved Arrow shape, screw fastening";
    public static final String OVER255_DESCRIPTION = "4 x red, curved Arrow shape, screw fastening 4 x red, " +
            "curved Arrow shape, screw fastening 4 x red, curved Arrow shape, screw fastening 4 x red, curved " +
            "Arrow shape, screw fastening 4 x red, curved Arrow shape, screw fastening 4 x red, curved Arrow " +
            "shape, screw fastening 4 x red, curved Arrow shape, screw fastening 4 x red, curved Arrow shape, " +
            "screw fastening 4 x red, curved Arrow shape, screw fastening";
    public static final String A_LOT = "1000";
    public static final String PHONE_NUMBER_WITH_CHARACTERS = "a";
    public static final String PAYMENT_THANKYOU = "Thank you for your purchase!";


    public static String emailFor(String userName) {
        return userName.replace(' ', '-') + "@random-email.com";
    }

    public static String from(String s) {
        return s;
    }

    public static String to(String s) {
        return s;
    }


}