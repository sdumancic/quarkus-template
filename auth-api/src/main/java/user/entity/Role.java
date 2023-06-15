package user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role", schema = "public")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToMany(mappedBy = "roles")
    public List<User> users = new ArrayList<>();

    @RolesValue
    public String roleName;

    @CreationTimestamp
    @Column(name = "created_on")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dateCreated;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_on")
    public LocalDateTime dateModified;

    @Override
    public String toString() {
        return "Role{" +
                "role='" + roleName + '\'' +
                '}';
    }

}
