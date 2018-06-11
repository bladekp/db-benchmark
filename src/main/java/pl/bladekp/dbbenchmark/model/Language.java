package pl.bladekp.dbbenchmark.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Builder
@Getter
@ToString
public class Language {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "COUNTRY", foreignKey = @ForeignKey(name = "FK_COUNTRY"))
    @ManyToOne
    private Country country;

    @Column
    private String name;

    @Column
    private Boolean isOfficial;

    @Column
    private Double percentage;
}
