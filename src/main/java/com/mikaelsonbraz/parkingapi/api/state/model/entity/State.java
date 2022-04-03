package com.mikaelsonbraz.parkingapi.api.state.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.city.model.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idState;

    @Column
    private String name;

    @OneToMany(mappedBy = "state")
    private List<City> cities = new ArrayList<City>();
}
