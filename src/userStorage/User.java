package userStorage;

import java.util.Set;

public class User {

    private String name;
    private String surname;
    private String email;
    private Set<String> role;
    private Set<String> phone;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public void setPhone(Set<String> phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRole() {
        return role;
    }

    public Set<String> getPhone() {
        return phone;
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setName(String name) {
            user.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            user.surname = surname;
            return this;
        }

        public Builder setEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder setRole(Set<String> role) {
            user.role = role;
            return this;
        }

        public Builder setPhoneNumber(Set<String> phoneNumber) {
            user.phone = phoneNumber;
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public String toString() {
        return "User information: " +
                "\nname='" + name + '\'' +
                ",\nsurname='" + surname + '\'' +
                ",\nemail='" + email + '\'' +
                ",\nrole=" + role +
                ",\nphone=" + phone;
    }
}
