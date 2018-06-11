package pl.bladekp.dbbenchmark.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
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
    private Continent continent;

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
    private double gnp;

    @Column
    private double gnpOld;

    @Column
    private String localName;

    @Column
    private String govermentForm;

    @Column
    private String headOfState;

    @OneToOne
    @JoinColumn(name="CAPITAL", foreignKey = @ForeignKey(name = "FK_CAPITAL"))
    private Town capital;

    @OneToMany(mappedBy = "country")
    private Set<Language> languages;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Town> towns;

    enum Continent{
        ASIA, EUROPE, NORTH_AMERICA, SOUTH_AMERICA, AFRICA, OCEANIA, ANTARCTICA
    }
}
