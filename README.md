<h1>PRIVATE PARKING API</h1>
<a href="https://parking-api-oxi.herokuapp.com/swagger-ui/index.html#/">Aplicação rodando na nuvem</a>

API Restful com Java 1.8, Spring Boot 2.6.4, Maven 3.1.1, Swagger 3.0.0 e Jacoco 0.8.2 

![example workflow](https://github.com/mikaelsonbraz/private-parking_api/actions/workflows/maven.yml/badge.svg)
[![codecov](https://codecov.io/gh/mikaelsonbraz/private-parking_api/branch/master/graph/badge.svg?token=K5CJRCF24L)](https://codecov.io/gh/mikaelsonbraz/private-parking_api)
______________
<h2>GETTING STARTED</h2>
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git.

```
# Clone repository
git clone https://github.com/mikaelsonbraz/private-parking_api.git

cd parking-api

mvn spring-boot:run
```
________
<h2>ENDPOINTS</h2>
<h3>CUSTOMER</h3>

- POST http://localhost:8080/api/customers (Adiciona um novo customer)

- GET http://localhost:8080/api/customers (Lista todos os customers através de parâmetros)

- GET http://localhost:8080/api/customers/{id} (Lista um customer através do id)

- PUT http://localhost:8080/api/customers/{id} (Atualiza os dados de um customer através do id)

- DELETE http://localhost:8080/api/customers/{id} (Deleta um customer através do id)

<h3>PARKING SPACE</h3>
- POST http://localhost:8080/api/spaces (Adiciona uma nova vaga de estacionamento)

- GET http://localhost:8080/api/spaces/{id} (Lista uma vaga pelo id)

- DELETE http://localhost:8080/api/spaces/{id} (Deleta uma vaga de estacionamento pelo id)

<h3>RENTING</h3>
- POST http://localhost:8080/api/rents (Adiciona um novo aluguel)

- GET http://localhost:8080/api/rents/{id} (Lista um aluguel pelo id passado)

- DELETE http://localhost:8080/api/rents/{id} (Deleta um aluguel pelo id)

<h3>ORDERED</h3>
- POST http://localhost:8080/api/orders (Adiciona um novo pedido)

- GET http://localhost:8080/api/orders/{id} (Lista um pedido pelo id)

- PUT http://localhost:8080/api/orders/{id} (Atualiza os dados um pedido pelo id)

- PATCH http://localhost:8080/api/orders/{id} (Atualiza a data de partida de um aluguel pelo id e da o valor total do pedido)

- DELETE http://localhost:8080/api/orders/{id} (Delete um pedido pelo id)