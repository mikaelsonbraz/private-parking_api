package com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.spaceTypeENUM.SpaceType;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ParkingSpace {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSpace;

    @Column
    private Integer spaceType;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_renting")
    private Renting renting;

    @Column
    private boolean occupied;

    public SpaceType getSpaceType(){
        return SpaceType.toEnum(spaceType);
    }

    public void setSpaceType(SpaceType code){
        this.spaceType = code.getCode();
    }

    public String getSpaceTypeDescription(){
        return this.getSpaceType().getDescription();
    }
}
