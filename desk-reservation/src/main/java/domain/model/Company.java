package domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company", schema = "dbo")
@NamedQueries({
        @NamedQuery(name = "Company.findAll",
                query = "SELECT c FROM Company c ORDER BY c.name",
                hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")
        ),
        @NamedQuery(name = "Company.findByName",
                query = "SELECT c FROM Company c WHERE c.name LIKE :name ORDER BY c.name",
                hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")
        )
})

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Column(name = "date_created")
    public LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "date_modified")
    public LocalDateTime dateModified;

    @Column(name = "name")
    private String name;
    @Column(name = "street")
    private String street;
    @Column(name = "building_no")
    private String buildingNo;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "company", orphanRemoval = true)
    private List<Location> locations;
}
