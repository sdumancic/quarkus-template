package user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user", schema = "public")
@UserDefinition
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    public Long id;

    @Username
    public String username;
    @Password
    public String hashedPassword;
    @Column(name="first_name")
    public String firstName;
    @Column(name="last_name")
    public String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Roles
    public List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    public List<UserPermission> userPermissions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_on")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime dateCreated;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "modified_on")
    public LocalDateTime dateModified;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
