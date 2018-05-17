package com.gft.dbbenchmark.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table
@Builder
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Long population;
}
