package com.mikaelsonbraz.parkingapi.api.state;

import com.mikaelsonbraz.parkingapi.api.city.model.entity.City;
import com.mikaelsonbraz.parkingapi.api.state.model.entity.State;
import com.mikaelsonbraz.parkingapi.api.state.model.repository.StateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class StateRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StateRepository repository;

    private State createNewState(){
        return State.builder().name("Rio Grande do Norte").build();
    }

    private City createNewCity(){
        return City.builder().name("Santana do Seridó").state(createNewState()).build();
    }

    @Test
    @DisplayName("Must return a Optiona.of(State) by id")
    public void findByIdTest(){
        //cenário
        State state = createNewState();
        entityManager.persist(state);

        //execução
        Optional<State> foundState = repository.findById(state.getIdState());

        //verificação
        Assertions.assertThat(foundState.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Must save a State with a list of cities")
    public void saveCitiesOnStateTest(){
        //cenário
        State state = createNewState();
        City city1 = createNewCity();
        City city2 = City.builder().name("Jardim do Seridó").build();
        state.setCities(Arrays.asList(city1, city2));

        //execução
        State savedState = repository.save(state);

        //verificação
        Assertions.assertThat(savedState.getIdState()).isNotNull();
        Assertions.assertThat(savedState.getCities()).hasSize(2);
        Assertions.assertThat(savedState.getCities().get(0).getName()).isEqualTo("Santana do Seridó");
    }
}
