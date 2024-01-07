package domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "location", schema = "dbo")
@NamedQuery(name = "Location.findAllByCompany",
        query = "SELECT l FROM Location l WHERE l.company.id = :companyId ORDER BY l.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Location {
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

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
