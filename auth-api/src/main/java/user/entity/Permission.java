package user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permission", schema = "public")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name="code")
    public String code;
    @Column(name="description")
    public String description;

    @OneToMany(mappedBy = "permission",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    public List<UserPermission> userPermissions = new ArrayList<>();

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
        return "Permission{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
