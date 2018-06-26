package pl.bladekp.dbbenchmark.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(indexes = { @Index(name = "countryPopulationIndex", columnList = "population"), @Index(name = "countrySurfaceAreaIndex", columnList = "surfaceArea")})
@Builder
@Getter
@ToString
public class Country {
    @Id
    @Column
    private String code;

    @Column
    private String name;

    @Column
    //private Continent continent;
    private Integer continent;

    @Column
    private String region;

    @Column
    private Double surfaceArea;

    @Column
    private Integer independenceYear;

    @Column
    private Long population;

    @Column
    private Double lifeExpectancy;

    @Column
    private Double gnp;

    @Column
    private Double gnpOld;

    @Column
    private String localName;

    @Column
    private String govermentForm;

    @Column
    private String headOfState;

    @Column
    private String code2;

    //@OneToOne
    //@JoinColumn(name="CAPITAL", foreignKey = @ForeignKey(name = "FK_CAPITAL"))
    @Column
    private int capital;

    @OneToMany(mappedBy = "country")
    private Set<Language> languages;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Town> towns;

    enum Continent{
        ASIA, EUROPE, NORTH_AMERICA, SOUTH_AMERICA, AFRICA, OCEANIA, ANTARCTICA
    }
}
