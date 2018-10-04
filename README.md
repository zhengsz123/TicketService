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

### Approach
---
1. Created User and Seat object and created related table and col in database
3. The relation between seat and user is many to one, the user_id will be the f_k and stores in the seat table.
2. For Finding number of seats available method, I used spring data JPA to generate findSeatsByStatus query to return seats that SeatStatus = EMPTY.
3. When holding the seats, for findAndHoldSeat, I gave the user certain numbers of seats and change the SeatStatus to HOLD, and save the user object in the seat table.
4. For reserveSeats method, I got the list of seats that the SeatStatus = HOLD from the database from this user, change the status to reserved, and create the confirmation code for the user.
4. Cancel holding mechanism: When executing findAndHoldSeatMethod, after 30s I will send a queue message to the JMS queue system. Once the JMS listener get the message, it will go
check the user table if the current user has any confirmation code, if there's no confirmation code for the user, it is gonna roll back,
which means all selected seats' status will become EMPTY again, and the corresponding user_id will become null again. 


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
     mvn compile flyway:migrate -P prod -Ddb_url= localhost:5432/ticket_service_demo -Ddb_password=password -Ddb_username=admin
     ```
     ```
     mvn compile flyway:migrate -P unit -Ddb_url= localhost:5432/ticket_service_unit -Ddb_password=password -Ddb_username=admin
     ```
6. Packaging And Unit Testing the maven project use the first command, packaging without unit test use the second command
     ```
     mvn compile package
     ```        
     ```
     mvn compile package -DskipTests=true
     ```
7.  Run the Seeding function to seed venue data in to the seat table.
    ```
    mvn clean compile exec:java -Dspring.profiles.active=dev
	```
8. Run the WebService jar type file to spin up the TicketService
    ```
    java -jar target/ticketservice-1.0-SNAPSHOT.jar -Dspring.profiles.active=prod --spring.datasource.url=jdbc:postgresql://localhost:5432/ticker_service_demo --spring.datasource.username=admin --spring.datasource.password=password
    ```
### WebServiceDemo
---
Seat data seeded into database

 ![seededData](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/Seeded%20data.png)

1.  User sign up to get email 
    ```
    POST - http://localhost:8080/api/user/signup
    ```
    Requestbody
    ```
    {
      "firstName" : "zhang",
      "lastName" : "zhengshi",
      "email":"zhengsz@vt.edu"
    }
    ``` 

2.Find the number of seats available within the venue,
	Note: available seats are seats that are neither held nor reserved.
	* Total seats available in all venues:
	
		GET - http://localhost:8080/api/seat/status
		
 ![FindNumOfSeats](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/findNumOfSeatsEmpty.png)
3.Find and hold the best available seats on behalf of a customer, 
Note: each ticket hold should expire within 30 seconds.
In the demo I pass numOfSeats as 2 and email as user's email using @PathPram.
    ```
        PATCH - http://localhost:8080/api/seat/status?numOfSeats=2&email=zhengsz@vt.edu   
    ```
 ResponseEntity:
	
        {
            "id": 5,
            "col": 5,
            "row": 1,
            "status": 1,
            "user": {
                "id": 1,
                "firstName": "zhang",
                "lastName": "zhengshi",
                "email": "zhengsz@vt.edu",
                "confirmationCode": null
            }
        },
        {
            "id": 6,
            "col": 6,
            "row": 1,
            "status": 1,
            "user": {
                "id": 1,
                "firstName": "zhang",
                "lastName": "zhengshi",
                "email": "zhengsz@vt.edu",
                "confirmationCode": null
            }
        }
	  
	
 ![findAndHoldSeats](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/findAndHoldSeats.png)
	
4.This request will expire after 30 seconds. Before that, user has to reserve the seats using the web service in the following request.
	Reserve and commit a specific group of held seats for a customer

	    
    PATCH - http://localhost:8080/api/user/confirmed?numOfSeats=2&email=zhengsz@vt.edu	
        
	
ResponseEntity:
	
    {
        "id": 1,
        "firstName": "zhang",
        "lastName": "zhengshi",
        "email": "zhengsz@vt.edu",
        "confirmationCode": "a2fa2bce-4e69-4705-9826-1cc728d35594"
    }
	
![reserveSeats](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/reservedSeats.png)

5.After the reservation, this is what we get from database for the user info and the selected seats.
![selectedSeats](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/SelectedSeatsInSeatTable.png)
![userInfoAfterReservation](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/UserInfoAfterReservation'.png)

	
### Testing Results
---
Tests are done using JUnit. Tests are run using the command

```
mvn compile test -Dspring.profiles.active=unit
```


![testResults](https://github.com/zhengsz123/TicketService/blob/master/WebServiceDEMO/UnitTesting.png)

### DB Schema
---
The application is designed using PostgreSql. Data migration using Flyway plugin.


### Todo List In The Future
---
1. Create another spring boot module for the JMS listener to get the JMS message
2. Separate domain, service,repo layer to another module and inject it into other worker modules to build microservice.
3. The TicketService provided does not give user option to pick particular seats based on rows and col, with the row and col information stored in the database,
I could let the user pick their desired seat, rather than assign them seats based on availability.
4. Write more unit-test to make a better coverage for my webservice.