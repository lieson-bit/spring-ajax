package com.shopme.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shopme.entity.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public State(Country country) {
        this.country = country;
    }

    public State(Country country, String name) {
        this.country = country;
        this.name = name;
    }

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public State(String name) {
        this.name = name;
    }

}
