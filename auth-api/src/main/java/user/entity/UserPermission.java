package user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_permission", schema = "public")
@org.hibernate.annotations.Immutable
@Getter
@Setter
@NamedQuery(name="UserPermission.findByUserIdAndPermissionCode",
            query = "select up from UserPermission up where up.user.id=:userId and  up.permission.code = :permissionCode",
            hints={@QueryHint(name="org.hibernate.cacheable",value="true")})
public class UserPermission{
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "USER_ID")
        protected Long userId;
        @Column(name = "PERMISSION_ID")
        protected Long permissionId;
        public Id() {
        }
        public Id(Long userId, Long permissionId) {
            this.userId = userId;
            this.permissionId = permissionId;
        }
        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.userId.equals(that.userId)
                        && this.permissionId.equals(that.permissionId);
            }
            return false;
        }
        public int hashCode() {
            return userId.hashCode() + permissionId.hashCode();
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "USER_ID",
            insertable = false, updatable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "PERMISSION_ID",
            insertable = false, updatable = false)
    public Permission permission;

    @CreationTimestamp
    @Column(name = "created_on")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime dateCreated;

    @Column(name = "created_by")
    private String createdBy;

    public UserPermission(){}
    public UserPermission(
            String createdByUsername,
            User user,
            Permission permission) {
        this.createdBy = createdByUsername;
        this.user = user;
        this.permission = permission;
        this.id.userId = user.id;
        this.id.permissionId = permission.id;
        user.getUserPermissions().add(this);
        permission.getUserPermissions().add(this);
    }
}
