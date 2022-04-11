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
``
mvn spring-boot:run
``
### Rodar teste de integração

``
mvn test
``
