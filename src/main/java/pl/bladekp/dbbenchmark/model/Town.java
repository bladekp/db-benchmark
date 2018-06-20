package pl.bladekp.dbbenchmark.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(indexes = { @Index(name = "townPopulationIndex", columnList = "population")})
@Builder
@Getter
@ToString
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "COUNTRY", foreignKey = @ForeignKey(name = "FK_TOWN_COUNTRY"))
    private Country country;

    @Column
    private String district;

    @Column
    private Long population;
}
