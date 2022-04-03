package com.mikaelsonbraz.parkingapi.api.city.model.entity;

import com.mikaelsonbraz.parkingapi.api.state.model.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCity;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state;
}
