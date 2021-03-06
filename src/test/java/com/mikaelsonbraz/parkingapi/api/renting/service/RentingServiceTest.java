package com.mikaelsonbraz.parkingapi.api.renting.service;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import com.mikaelsonbraz.parkingapi.api.resource.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.resource.renting.model.repository.RentingRepository;
import com.mikaelsonbraz.parkingapi.api.resource.renting.service.RentingService;
import com.mikaelsonbraz.parkingapi.api.resource.renting.service.impl.RentingServiceImpl;
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
    ParkingSpaceService parkingSpaceService;

    @MockBean
    RentingRepository repository;

    public Renting createNewRenting(){
        return Renting.builder().idRenting(1).entryDate(LocalDateTime.of(2020, 8, 19, 14, 30)).parkingSpace(createParkingSpace()).hourPrice(5).dayPrice(100).build();
    }

    public ParkingSpace createParkingSpace(){
        return ParkingSpace.builder().idSpace(1).spaceType(1).build();
    }

    @Autowired
    @MockBean
    TestEntityManager entityManager;

    @BeforeEach
    public void setUp(){
        this.service = new RentingServiceImpl(repository, parkingSpaceService);
    }

    @Test
    @DisplayName("Must save a renting")
    public void saveRentingTest(){
        //cenario
        Renting renting = createNewRenting();
        Mockito.when(repository.save(renting)).thenReturn(renting);

        //execu????o
        Renting savedRenting = service.save(renting);

        //verifica????o
        Assertions.assertThat(savedRenting.getIdRenting()).isNotNull();
        Assertions.assertThat(savedRenting.getEntryDate()).isEqualTo(renting.getEntryDate());
        Assertions.assertThat(savedRenting.getHourPrice()).isEqualTo(renting.getHourPrice());
        Assertions.assertThat(savedRenting.getDayPrice()).isEqualTo(renting.getDayPrice());
    }

    @Test
    @DisplayName("Must throw an error when entryDate receives an invalid value")
    public void shouldThrowErrorWhenEntryDateIsInvalidTest(){
        //cenario
        Renting renting = createNewRenting();
        renting.setEntryDate(LocalDateTime.of(2023, 4, 5, 22, 40));

        //execu????o
        Throwable exception = Assertions.catchThrowable(() -> service.save(renting));

        //verifica????es
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Data inv??lida");
    }

    @Test
    @DisplayName("Must update a renting with a departure date")
    public void shouldUpdateRenting(){
        //cen??rio
        Renting renting = createNewRenting();
        renting.setDepartureDate(LocalDateTime.of(2021, 8, 18, 14, 30));
        Mockito.when(repository.save(renting)).thenReturn(renting);

        //execu????o
        Renting updatedRenting = service.update(renting);

        //verifica????o
        Assertions.assertThat(updatedRenting.getIdRenting()).isNotNull();
        Assertions.assertThat(updatedRenting.getEntryDate()).isEqualTo(renting.getEntryDate());
        Assertions.assertThat(updatedRenting.getDepartureDate()).isEqualTo(renting.getDepartureDate());
    }

    @Test
    @DisplayName("Must throw an error when departure date is before the entry date on renting")
    public void shouldThrowAnErrorWhenDepartureIsBeforeEntryDateTest(){
        //cen??rio
        Renting renting = createNewRenting();
        renting.setDepartureDate(LocalDateTime.of(2019, 8, 19, 14, 30));

        //execu????o
        Throwable exception = Assertions.catchThrowable(() -> service.update(renting));

        //verifica????o
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Data de partida n??o pode ser anterior a data de entrada");
    }

    @Test
    @DisplayName("Must throw an error when dayPrice is less than hourPrice")
    public void shouldThrowAnErrorWhenDayPriceIsLessThaHourPriceTest(){
        //cen??rio
        Renting renting = createNewRenting();
        renting.setDayPrice(4);

        //execu????o
        Throwable exception = Assertions.catchThrowable(() -> service.save(renting));

        //verifica????o
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Valor do dia de estacionamento n??o pode ser menor que o valor da hora");
    }

    @Test
    @DisplayName("Must delete a renting")
    public void shouldDeleteRentingTest(){
        ///cen??rio
        Renting renting = createNewRenting();
        entityManager.persist(renting);

        //execu????o
        service.delete(renting);
        Renting deletedRenting = entityManager.find(Renting.class, renting);

        //verifica????o
        Mockito.verify(repository, Mockito.times(1)).delete(renting);
        Assertions.assertThat(deletedRenting).isNull();
    }

    @Test
    @DisplayName("Must throw an error when trying to delete an invalid renting")
    public void deleteInvalidRentingTest(){
        //cen??rio
        Renting renting = new Renting();

        //execu????o

        //verifica????o
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(renting));
        Mockito.verify(repository, Mockito.never()).delete(renting);

    }
}
