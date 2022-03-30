package com.mikaelsonbraz.parkingapi.api.parkingSpace.model.spaceTypeENUM;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpaceType {

    COVERED(0, "Covered space"),
    UNCOVERED(1, "Uncovered space");

    private final Integer code;
    private final String description;

    private SpaceType toEnum(Integer code){
        if (code == null){
            return null;
        }

        for (SpaceType x : SpaceType.values()){
            if (code.equals(x.getCode())){
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
