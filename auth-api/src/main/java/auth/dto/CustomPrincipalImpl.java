package auth.dto;

import java.security.Principal;

public class CustomPrincipalImpl implements CustomPrincipal {

    private String name;
    private String firstName;
    private String lastName;

    private Long userId;

    public CustomPrincipalImpl(String name, String firstName, String lastname, Long userId) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.name = name;
        this.userId = userId;
    }


    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "CustomPrincipalImpl{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
