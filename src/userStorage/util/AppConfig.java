package userStorage.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {

    private AppConfig() {
    }

    public static final Path STORAGE_FILE_PATH = Paths.get("src", "filesStorage", "User.txt");

    public static final String CHOOSE_ACTION = "CHOOSE ACTION:\n" +
            "1 - CREATE A NEW USER\n" +
            "2 - FIND USER BY E-MAIL\n" +
            "3 - CHANGE USER NAME\n" +
            "4 - CHANGE USER SURNAME\n" +
            "5 - CHANGE USER ROLES\n" +
            "6 - CHANGE USER PHONES\n" +
            "7 - DELETE A USER BY E-MAIL\n" +
            "8 - EXIT PROGRAM\n";
    public static final String FIELD_SEPARATOR = ";";

    public static final String LIST_SEPARATOR = ",";
}
