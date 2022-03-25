package com.mikaelsonbraz.parkingapi.api.renting.service;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.model.repository.RentingRepository;
import com.mikaelsonbraz.parkingapi.api.renting.service.impl.RentingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RentingServiceTest {

    RentingService service;

    @MockBean
    RentingRepository repository;

    @MockBean
    @Autowired
    TestEntityManager entityManager;

    public Renting createNewRenting(){
        return Renting.builder().idRenting(1).entryDate(LocalDateTime.of(2020, 8, 19, 14, 30)).hourPrice(5).dayPrice(100).build();
    }

    @BeforeEach
    public void setUp(){
        this.service = new RentingServiceImpl(repository);
    }

    @Test
    @DisplayName("Must save a renting")
    public void saveRentingTest(){
        //cenario
        Renting renting = createNewRenting();
        Mockito.when(repository.save(renting)).thenReturn(renting);

        //execução
        Renting savedRenting = service.save(renting);

        //verificação
        Assertions.assertThat(savedRenting.getIdRenting()).isNotNull();
        Assertions.assertThat(savedRenting.getEntryDate()).isEqualTo(renting.getEntryDate());
        Assertions.assertThat(savedRenting.getHourPrice()).isEqualTo(renting.getHourPrice());
        Assertions.assertThat(savedRenting.getDayPrice()).isEqualTo(renting.getDayPrice());
    }

    @Test
    @DisplayName("Must throw an error when entryDate receives an invalid value")
    public void shoulThrowErrorWhenEntryDateIsInvalidTest(){
        //cenario
        Renting renting = createNewRenting();
        renting.setEntryDate(LocalDateTime.of(2023, 4, 5, 22, 40));

        //execução
        Throwable exception = Assertions.catchThrowable(() -> service.save(renting));

        //verificações
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Data inválida");
    }
}
