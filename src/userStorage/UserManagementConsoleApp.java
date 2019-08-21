package userStorage;

import userStorage.util.*;

import java.util.*;

import static userStorage.util.AppConfig.LIST_SEPARATOR;

public class UserManagementConsoleApp {

    private static final Map<String, User> IN_MEMORY_STORAGE = new HashMap<>();
    private static final String SUCCESS_RESULT = "RESULT: change saved)";
    private static final String NOT_EXIST_USER = "RESULT: change is impossible (user does not exist with such email)";
    private static final String SAME_PARAMETERS = "RESULT: change is impossible (old and new parameters are the same)";
    private static boolean execution_flag = true;

    static {
        List<String> dataFromFile = FileUtil.readFromFile(AppConfig.STORAGE_FILE_PATH);
        DataConverter.fillStorage(dataFromFile, IN_MEMORY_STORAGE);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (execution_flag) {
            runDialog(scanner);
        }
        System.out.println("Program finished");
    }

    private static void runDialog(Scanner scanner) {
        System.out.println(AppConfig.CHOOSE_ACTION);
        int selection;
        try {
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("The wrong format (not a number) was entered " +
                    "or the number went beyond the bounds of the valid int format");
            System.out.println(AppConfig.CHOOSE_ACTION);
            try {
                scanner.nextLine();
                selection = scanner.nextInt();
            } catch (InputMismatchException e2) {
                System.out.println("You should use numbers next time");
                selection = 8;
            }
        }

        switch (selection) {
            case 1:
                userCreation(scanner);
                break;
            case 2:
                findUserByEmail(scanner);
                break;
            case 3:
                changeUserName(scanner);
                break;
            case 4:
                changeUserSurname(scanner);
                break;
            case 5:
                changeUserRoles(scanner);
                break;
            case 6:
                changeUserPhones(scanner);
                break;
            case 7:
                deleteUserByEmail(scanner);
                break;
            case 8:
                execution_flag = false;
                break;
            default:
                System.out.println("Option not supported. Try again.");
        }
    }

    private static void userCreation(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: CREATING A NEW USER");
        User user = UserConsoleUtil.createNewUser(scanner);
        String email = user.getEmail();
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            System.out.println("RESULT: creation is impossible (user exists with such email)");
        } else {
            List<User> userList = new ArrayList<>();
            userList.add(user);
            FileUtil.writeToFileNewUsers(userList);
            IN_MEMORY_STORAGE.put(email, user);
            System.out.println(SUCCESS_RESULT);
        }
    }

    private static void findUserByEmail(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: GETTING USER INFORMATION BY E-MAIL");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            User user = IN_MEMORY_STORAGE.get(email);
            System.out.println(user);
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static void changeUserName(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: CHANGE USER NAME");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            User user = IN_MEMORY_STORAGE.get(email);
            String oldName = user.getName();
            System.out.println("Old name - " + oldName + ". Enter new name.");
            String newName = UserConsoleUtil.getLineFromConsole(scanner);
            while (newName.isEmpty()) {
                System.out.println("You did not enter a name. Try again");
                newName = UserConsoleUtil.getLineFromConsole(scanner);
            }
            if (isEqualsParameters(oldName, newName)) {
                System.out.println(SAME_PARAMETERS);
            } else {
                user.setName(newName);
                writeChangesToFile(email, user);
            }
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static void changeUserSurname(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: CHANGE USER SURNAME");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            User user = IN_MEMORY_STORAGE.get(email);
            String oldSurname = user.getSurname();
            System.out.println("Old surname - " + oldSurname + ". Enter new surname.");
            String newSurname = UserConsoleUtil.getLineFromConsole(scanner);
            while (newSurname.isEmpty()) {
                System.out.println("You did not enter a surname. Try again");
                newSurname = UserConsoleUtil.getLineFromConsole(scanner);
            }
            if (isEqualsParameters(oldSurname, newSurname)) {
                System.out.println(SAME_PARAMETERS);
            } else {
                user.setSurname(newSurname);
                writeChangesToFile(email, user);
            }
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static void deleteUserByEmail(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: DELETING A USER BY E-MAIL");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            IN_MEMORY_STORAGE.remove(email);
            FileUtil.rewriteFile(IN_MEMORY_STORAGE);
            System.out.println(SUCCESS_RESULT);
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static void changeUserPhones(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: CHANGE USER PHONE NUMBER");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            Set<String> phones = IN_MEMORY_STORAGE.get(email).getPhone();
            System.out.println("Old phones: " + phones + ". Old phones will be deleted");
            System.out.println("ENTER PHONES SEPARATED BY COMMAS(NUMBER OF PHONES FROM 1 TO 3 AND FORMAT 375** *******): ");
            String newPhones = UserConsoleUtil.getLineFromConsole(scanner);
            while (UserConsoleUtil.isNotValidLineForPhone(newPhones)) {
                System.out.println("The phone entered is in the wrong format. Try again");
                newPhones = UserConsoleUtil.getLineFromConsole(scanner);
            }
            User user = IN_MEMORY_STORAGE.get(email);
            user.setPhone(DataConverter.convertToSet(newPhones.split(LIST_SEPARATOR)));
            writeChangesToFile(email, user);
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static void changeUserRoles(Scanner scanner) {
        System.out.println("OPERATION IN PROGRESS: CHANGE USER ROLE");
        String email = emailEnterDialog(scanner);
        if (isUserExists(IN_MEMORY_STORAGE, email)) {
            Set<String> oldRoles = IN_MEMORY_STORAGE.get(email).getRole();
            System.out.println("Old roles: " + oldRoles + " . Old roles will be deleted");
            System.out.println("ENTER ROLES SEPARATED BY COMMAS(NUMBER OF ROLES FROM 1 TO 3): ");
            String newRoles = UserConsoleUtil.getLineFromConsole(scanner);
            while (UserConsoleUtil.isNotValidLineForRole(newRoles)) {
                System.out.println("You entered an incorrect number of roles. Try again.");
                newRoles = UserConsoleUtil.getLineFromConsole(scanner);
            }
            User user = IN_MEMORY_STORAGE.get(email);
            user.setRole(DataConverter.convertToSet(newRoles.split(LIST_SEPARATOR)));
            writeChangesToFile(email, user);
        } else {
            System.out.println(NOT_EXIST_USER);
        }
    }

    private static String emailEnterDialog(Scanner scanner) {
        System.out.println("ENTER E-MAIL ADDRESS");
        String emailForChange = UserConsoleUtil.getLineFromConsole(scanner);
        while (!ValidationUtil.isValidEmail(emailForChange)) {
            System.out.print("You entered an invalid email address. Try again: ");
            emailForChange = UserConsoleUtil.getLineFromConsole(scanner);
        }
        return emailForChange;
    }

    private static boolean isUserExists(Map<String, User> userStorage, String userKey) {
        return userStorage.containsKey(userKey);
    }

    private static void writeChangesToFile(String userKey, User user) {
        IN_MEMORY_STORAGE.put(userKey, user);
        FileUtil.rewriteFile(IN_MEMORY_STORAGE);
        System.out.println(SUCCESS_RESULT);
    }

    private static boolean isEqualsParameters(String oldParameter, String newParameter) {
        return oldParameter.equals(newParameter);
    }
}
