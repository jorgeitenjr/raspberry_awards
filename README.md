# Raspberry Awards

## Requisitos
Java 11 ou superior instalado

### Instruções
Arquivos com a lista de indicações importadas ao iniciar a aplicação:
``
/src/main/resouces/movielist.csv
``
### Alterar o arquivo - src/main/resources/application.properties
``
file.to.import=movielist.csv
``
### Rodar a API

``./mvnw spring-boot:run`` ou ``mvnw spring-boot:run``

### Rodar teste de integração

``./mvnw test`` ou ``mvnw test``

### Endpoint com os dados dos produtores

``http://localhost:8080/producers``
