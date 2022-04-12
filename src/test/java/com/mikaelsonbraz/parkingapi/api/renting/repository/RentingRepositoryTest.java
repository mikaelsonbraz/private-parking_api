package com.mikaelsonbraz.parkingapi.api.renting.repository;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.model.repository.RentingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RentingRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RentingRepository repository;

    public ParkingSpace createParkingSpace(){
        return ParkingSpace.builder().idSpace(2).spaceType(1).build();
    }

    @Test
    @DisplayName("Must return true when the departure date is not null")
    public void existsDepartureDateTest(){
        //cenario
        Renting renting = Renting.builder().entryDate(LocalDateTime.now()).departureDate(LocalDateTime.of(2023, 8, 12, 5, 15)).parkingSpace(createParkingSpace()).build();
        entityManager.persist(renting);

        //execução
        boolean exists = repository.existsDepartureDateByIdRenting(renting.getIdRenting());

        //verificação
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Must return false when not exists departure date on renting")
    public void notExistsdepartureDate(){
        //cenário
        Renting renting = Renting.builder().entryDate(LocalDateTime.now()).parkingSpace(createParkingSpace()).build();
        entityManager.persist(renting);

        //execuçção
        boolean exists = repository.existsDepartureDateByIdRenting(renting.getIdRenting());

        //verificação
        Assertions.assertThat(exists).isFalse();
    }
}
