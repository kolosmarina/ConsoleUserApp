package userStorage.util;

import userStorage.User;

import java.util.*;
import java.util.stream.Collectors;

import static userStorage.util.AppConfig.FIELD_SEPARATOR;
import static userStorage.util.AppConfig.LIST_SEPARATOR;

public final class DataConverter {

    private DataConverter() {
    }

    public static void fillStorage(List<String> data, Map<String, User> storage) {
        String[] split;
        for (String s : data) {
            if (!s.isEmpty()) {
            split = s.split(FIELD_SEPARATOR);
            User user = new User.Builder()
                    .setName(split[0])
                    .setSurname(split[1])
                    .setEmail(split[2])
                    .setRole(convertToSet(split[3].split(LIST_SEPARATOR)))
                    .setPhoneNumber(convertToSet(split[4].split(LIST_SEPARATOR)))
                    .build();
            storage.put(user.getEmail(), user);
            }
        }
    }

    public static List<User> getListUsers(Map<String, User> storage) {
        return storage.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public static Set<String> convertToSet(String... strings) {
        Set<String> set = new HashSet<>();
        Arrays.stream(strings).map(String::trim).forEach(set::add);
        return set;
    }

    public static String convert(User user) {
        return user.getName() + FIELD_SEPARATOR + user.getSurname() + FIELD_SEPARATOR + user.getEmail() +
                FIELD_SEPARATOR + String.join("LIST_SEPARATOR", user.getRole()) + FIELD_SEPARATOR +
                String.join("LIST_SEPARATOR", user.getPhone());
    }
}
