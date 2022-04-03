package com.mikaelsonbraz.parkingapi.api.city;

import com.mikaelsonbraz.parkingapi.api.city.model.entity.City;
import com.mikaelsonbraz.parkingapi.api.city.model.repository.CityRepository;
import com.mikaelsonbraz.parkingapi.api.state.model.entity.State;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CityRepository repository;

    private State createNewState(){
        return State.builder().name("Rio Grande do Norte").build();
    }

    private City createNewCity(){
        return City.builder().name("Santana do Seridó").state(createNewState()).build();
    }

    @Test
    @DisplayName("Must find a city by id")
    public void findByIdTest(){
        //cenário
        City city = createNewCity();
        entityManager.persist(city);

        //execução
        Optional<City> foundCity = repository.findById(city.getIdCity());

        //verificação
        Assertions.assertThat(foundCity.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Must verify if state is correctly saved on city info")
    public void mustReturnStateOfCity(){
        //cenário
        City city = createNewCity();


        //execução
        City savedCity = repository.save(city);

        //verificação
        Assertions.assertThat(savedCity.getState().getName()).isEqualTo("Rio Grande do Norte");
    }
}
