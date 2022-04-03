package com.mikaelsonbraz.parkingapi.api.state.model.repository;

import com.mikaelsonbraz.parkingapi.api.state.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
}
