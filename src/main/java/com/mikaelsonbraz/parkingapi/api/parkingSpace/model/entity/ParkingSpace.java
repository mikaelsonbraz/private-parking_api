package com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.spaceTypeENUM.SpaceType;
import com.mikaelsonbraz.parkingapi.api.resource.renting.model.entity.Renting;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ParkingSpace that = (ParkingSpace) o;
        return idSpace != null && Objects.equals(idSpace, that.idSpace);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
