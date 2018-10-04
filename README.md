# ticket-booking-service
---
Implementation of a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.

This application is developed using Spring Boot, Spring Data, Spring RESTful web services, Maven, PostgreSql, Docker,ActiveMQ.

### Assumptions
---
1. Users are provided seats based on the availability.
2. The seats pool are inserted into the seat table.
3. The users information need to be created before making selection.
4. The relation between seat table and user table is "Many to One".
5. Hold time for the seats is 30 seconds. If the user does not reserve the seats before 30 seconds, then the holds are removed, the status of the held seats go back to empty, and user has to send a request again to hold the seats.
6. No notification for the expiration of seat holds.


### Building Project
---
1. Clone the project
	
	```
	git clone https://github.com/zhengsz123/TicketService.git
	```
2. Install docker if needed.

3. Spin up the ActiveMq Artemis host using docker and leave it there.
      ```
      docker run -it --rm -e ENABLE_JMX=true -e JMX_PORT=1199 -e JMX_RMI_PORT=1198 -e ARTEMIS_USERNAME=admin -e ARTEMIS_PASSWORD=password123 -p61616:61616 -p8161:8161 vromero/activemq-artemis
      ```
3. Open a new command line window and Spin up the PostgreSql database server using Postgres docker image
   ```
    docker pull postgres
    ```
    ```
    docker run --name ticketserviceDemo -e POSTGRES_DB=ticket_service_Demo -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
   ```
4. Create Unit database for unit testing
    ```
     psql -h localhost -U admin -d postgres
     ```
     ```
     create database ticket_service_unit
    ```
5. Schema migration for creating tables in database
     ```
     mvn compile flyway:migrate -p prod
     ```
     ```
     mvn compile flyway:migrate -p unit
     ```
6. Packaging the maven project
     ```
     mvn compile package -DskipTests=true
     ```
7.  Run the Seeding function to seed venue data in to the seat table, when finish exit using control + c
    ```
    mvn exec:java -Dspring.profiles.active=dev
	```
8. Run the WebService jar type file to spin up the TicketService
    ```
    java -jar target/ticketservice-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/ticker_service_demo --spring.datasource.username=admin --spring.datasource.password=password
    ```
	