package com.mikaelsonbraz.parkingapi.api.ordered.service;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.ordered.model.repository.OrderedRepository;
import com.mikaelsonbraz.parkingapi.api.ordered.service.orderServiceImpl.OrderServiceImpl;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.spaceTypeENUM.SpaceType;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
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
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class OrderedServiceTest {

    OrderService service;

    @MockBean
    OrderedRepository repository;

    @Autowired
    @MockBean
    TestEntityManager entityManager;

    public Customer createCustomer(){
        return Customer.builder().idCustomer(1).name("João").cpf("111.222.333-44").build();
    }

    public ParkingSpace createSpace(){
        return ParkingSpace.builder().idSpace(1).spaceType(1).occupied(true).build();
    }

    public Renting createRenting(){
        return Renting.builder().idRenting(1).entryDate(LocalDateTime.of(2022, 4, 4, 12, 30)).hourPrice(2).dayPrice(40).parkingSpace(createSpace()).build();
    }

    public Ordered createOrder(){
        return Ordered.builder().idOrder(1).customer(createCustomer()).renting(createRenting()).build();
    }

    @BeforeEach
    public void setUp() {
        this.service = new OrderServiceImpl(repository);
    }

    @Test
    @DisplayName("Must save a order")
    public void saveOrderTest(){
        //cenário
        Ordered ordered = createOrder();
        Mockito.when(repository.save(ordered)).thenReturn(ordered);

        //execução
        Ordered savedOrdered = service.save(ordered);

        //verificação
        Assertions.assertThat(savedOrdered.getIdOrder()).isNotNull();
        Assertions.assertThat(savedOrdered.getCustomer().getIdCustomer()).isNotNull();
        Assertions.assertThat(savedOrdered.getRenting().getIdRenting()).isNotNull();
        Assertions.assertThat(savedOrdered.getRenting().getParkingSpace().getIdSpace()).isNotNull();
    }

    @Test
    @DisplayName("Must get a order by id")
    public void getByIdTest(){
        //cenário
        Ordered ordered = createOrder();
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(ordered));

        //execução
        Optional<Ordered> foundOrder = service.getById(ordered.getIdOrder());

        //verificação
        Assertions.assertThat(foundOrder.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Must return Optional.empty() when not find a orderr by id")
    public void orderNotFoundById(){
        //cenário
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        //execução
        Optional<Ordered> foundOrder = service.getById(1);

        //verificação
        Assertions.assertThat(foundOrder.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Must update a order")
    public void updateOrderTest(){
        //cenário
        Ordered ordered = createOrder();
        Mockito.when(repository.save(Mockito.any(Ordered.class))).thenReturn(ordered);

        //execução
        Ordered updatedOrdered = service.update(ordered);

        Assertions.assertThat(updatedOrdered.getIdOrder()).isEqualTo(ordered.getIdOrder());
        Assertions.assertThat(updatedOrdered.getCustomer()).isEqualTo(ordered.getCustomer());
        Assertions.assertThat(updatedOrdered.getRenting()).isEqualTo(ordered.getRenting());
    }

    @Test
    @DisplayName("Must throw IllegalArgumentException when order is null trying update")
    public void updateInvalidOrderTest(){
        //cenário
        Ordered ordered = new Ordered();

        //execução

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(ordered));
        Mockito.verify(repository, Mockito.never()).save(ordered);
    }

    @Test
    @DisplayName("Must delete a order")
    public void deleteOrderTest(){
        //cenário
        Ordered ordered = createOrder();
        entityManager.persist(ordered);

        //execução
        service.delete(ordered);
        Ordered deletedOrdered = entityManager.find(Ordered.class, ordered);

        //verificação
        Mockito.verify(repository, Mockito.times(1)).delete(ordered);
        Assertions.assertThat(deletedOrdered).isNull();
    }

    @Test
    @DisplayName("Must throw IllegalArgumentException when order id is null to delete")
    public void deleteInvalidOrderTest(){
        //cenário
        Ordered ordered = new Ordered();

        //execução

        //verificação
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(ordered));
        Mockito.verify(repository, Mockito.never()).delete(ordered);
    }

    @Test
    @DisplayName("Must update the order amount whe the order renting receives a departure date above 24h and unvered space type")
    public void updateOrderAmountTest(){
        //cenário
        Ordered ordered = createOrder();
        LocalDateTime hoje = LocalDateTime.of(2022, 4, 5, 12, 30);
        ordered.getRenting().setDepartureDate(hoje);
        Mockito.when(repository.save(Mockito.any(Ordered.class))).thenReturn(ordered);

        //execução
        Ordered updatedAmountOrdered = service.updateAmount(ordered);

        //execução
        Assertions.assertThat(updatedAmountOrdered.getIdOrder()).isNotNull();
        Assertions.assertThat(updatedAmountOrdered.getAmount()).isEqualTo(32L);
    }

    @Test
    @DisplayName("Must update the order amount when the renting receives a departure date less 24h and covered space type")
    public void updateOrderAmountTest2(){
        //cenário
        Ordered ordered = createOrder();
        LocalDateTime hoje = LocalDateTime.of(2022, 4, 5, 11, 30);
        ordered.getRenting().setDepartureDate(hoje);
        ordered.getRenting().getParkingSpace().setSpaceType(SpaceType.COVERED);
        Mockito.when(repository.save(Mockito.any(Ordered.class))).thenReturn(ordered);

        //execução
        Ordered updatedAmountOrdered = service.updateAmount(ordered);

        //verificação
        Assertions.assertThat(updatedAmountOrdered.getIdOrder()).isNotNull();
        Assertions.assertThat(updatedAmountOrdered.getAmount()).isEqualTo(46L);
    }

    @Test
    @DisplayName("Must throw BusinessException when trying update order amount with no departure date")
    public void updateInvalidOrderAmountTest(){
        //cenário
        Ordered ordered = createOrder();

        //execução

        //verificação
        org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> service.updateAmount(ordered));
        Mockito.verify(repository, Mockito.never()).save(ordered);
    }
}
