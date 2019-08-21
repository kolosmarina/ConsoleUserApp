package userStorage.util;

import userStorage.User;

import java.util.Scanner;
import java.util.Set;

import static userStorage.util.AppConfig.LIST_SEPARATOR;

public final class UserConsoleUtil {

    private static final int MAXIMUM_NUMBER_OF_ROLES = 3;
    private static final int MAXIMUM_OF_PHONES = 3;

    private UserConsoleUtil() {
    }

    public static User createNewUser(Scanner scanner) {
        System.out.print("ENTER NAME: ");
        String name = getStringFromConsole(scanner, "name");
        System.out.print("ENTER SURNAME: ");
        String surname = getStringFromConsole(scanner, "surname");
        System.out.print("ENTER EMAIL: ");
        String email = getLineFromConsole(scanner);
        while (email.isEmpty() || !(ValidationUtil.isValidEmail(email))) {
            System.out.print("You entered an invalid email address. Try again: ");
            email = getLineFromConsole(scanner);
        }
        System.out.print("ENTER ROLES SEPARATED BY COMMAS (NUMBER OF ROLES FROM 1 TO 3): ");
        String rolesFromConsole = getLineFromConsole(scanner);
        while (isNotValidLineForRole(rolesFromConsole)) {
            System.out.println("You entered an incorrect number of roles. Try again.");
            System.out.print("ENTER ROLES SEPARATED BY COMMAS (NUMBER OF ROLES FROM 1 TO 3): ");
            rolesFromConsole = getLineFromConsole(scanner);
        }
        System.out.print("ENTER PHONE NUMBERS SEPARATED BY COMMAS(NUMBER OF PHONES FROM 1 TO 3 AND FORMAT 375** *******): ");
        String phonesFromConsole = getLineFromConsole(scanner);
        while (isNotValidLineForPhone(phonesFromConsole)) {
            System.out.println("You entered an incorrect number of phone numbers or an incorrect format. Try again.");
            System.out.print("ENTER PHONES SEPARATED BY COMMAS(NUMBER OF PHONES FROM 1 TO 3 AND FORMAT 375** *******): ");
            phonesFromConsole = getLineFromConsole(scanner);
        }
        Set<String> rolesSet = DataConverter.convertToSet(rolesFromConsole.split(LIST_SEPARATOR));
        Set<String> phonesSet = DataConverter.convertToSet(phonesFromConsole.split(LIST_SEPARATOR));
        return new User.Builder()
                .setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setRole(rolesSet)
                .setPhoneNumber(phonesSet)
                .build();
    }

    private static String getStringFromConsole(Scanner scanner, String fieldName) {
        String consoleInput = getLineFromConsole(scanner);
        while (consoleInput.isEmpty()) {
            System.out.printf("You have not entered a %s. Try again: ", fieldName);
            System.out.printf("ENTER %s: ", fieldName.toUpperCase());
            consoleInput = getLineFromConsole(scanner);
        }
        return consoleInput;
    }

    public static boolean isNotValidLineForRole(String string) {
        String[] array = string.split(LIST_SEPARATOR);
        return string.isEmpty() || array.length > MAXIMUM_NUMBER_OF_ROLES;
    }

    public static boolean isNotValidLineForPhone(String string) {
        String[] array = string.split(LIST_SEPARATOR);
        return string.isEmpty() || array.length > MAXIMUM_OF_PHONES || !ValidationUtil.isValidPhoneFormat(array);
    }

    public static String getLineFromConsole(Scanner scanner) {
        return scanner.nextLine().trim();
    }
}
