package userStorage.util;

import userStorage.User;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static userStorage.util.AppConfig.STORAGE_FILE_PATH;

public final class FileUtil {

    private FileUtil() {
    }

    public static List<String> readFromFile(Path path) {
        File file = path.toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Storage file creation failed", e);
            }
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Storage file not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Storage file read failed", e);
        }
    }

    public static void rewriteFile(Map<String, User> newData) {
        List<User> listUsers = DataConverter.getListUsers(newData);
        clearStorageFile();
        writeToFileNewUsers(listUsers);
    }

    public static void writeToFileNewUsers(List<User> listUsers) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(STORAGE_FILE_PATH.toFile(), true))) {
            for (User user : listUsers) {
                String stringUser = DataConverter.convert(user);
                bufferedWriter.append(stringUser);
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Storage file not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Storage file write failed");
        }
    }

    private static void clearStorageFile() {
        try {
            PrintWriter pw = new PrintWriter(STORAGE_FILE_PATH.toFile());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
