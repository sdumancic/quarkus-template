package auth.event;

public class UserLoggedIn {

    private final Long userId;
    private final String username;
    private final String firstName;
    private final String lastName;

    public UserLoggedIn(Long userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "UserLoggedIn{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
