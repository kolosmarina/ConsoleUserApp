package userStorage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\..+");
    private static final Pattern PHONE_PATTERN = Pattern.compile("375\\d{2}\\s\\d{7}");

    private ValidationUtil() {
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber.trim());
        return matcher.matches();
    }

    public static boolean isValidPhoneFormat(String[] array) {
        boolean result = true;
        for (String string : array) {
            if (!isValidPhoneNumber(string)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
