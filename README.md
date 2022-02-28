## Developer note:
  - This project developed with Java 11 and spring framework(spring boot v 2.6.3)
  - H2 database used for store data. save entities, persist request and response etc.
    - H2 database can be reached {project_url}/h2-console. Credentials for h2-console,
       ```
       - jdbcUrl: jdbc:h2:./data/demo
       - username: sa
       - password: 
      ```
  - Rest APIs documented with swagger on [Swagger](http://localhost:8080/swagger-ui) 
  - APIs serving on 8080 port.
  - Project uses spring token based security for authentication. Token must be taken in given endpoint with username and password credentials.
       ```
       - endpoint: /api/auth/login
       - username: getir
       - password: getir
      ```
   - Api-docs of rest client(ex: postman) can be reachable on ```${project.base}/api-docs```
   - Postman request collections are added on ```${project.base}/getir.postman_collection.json```
   - Integration and unit tests exist for service and controllers and run with mock services.
   - Logging is available
   - API urls: 
     - ```POST /api/auth/login``` to authenticate for jwt token
     - ```POST /api/customers``` to create customer
     - ```GET /api/customers``` to list customers
     - ```POST /api/books``` to create book
     - ```PUT /api/book/update-stock``` to update stock of existing book
     - ```POST /api/orders``` to create order
     - ```GET /api/orders/{id}``` to get order with id
     - ```GET /api/orders``` to list order with given start date and end date
     - ```GET /api/statistics/monthly``` to give monthly statistics

## Tech stack:

* Java 11
* H2 database
* Docker
* Swagger
* Mockito

## Design

![image](https://user-images.githubusercontent.com/9322357/155968084-43df853d-08cf-4ffc-aa16-100cb1685b4e.png)

* Customer has name, surname and email. Email field is unique for customers.
* Book has name and price. 
* Book has one to one relation with BookStock. BookStock has also quantity for stock information.
* 

* The project is started with designing database.
* Controllers and services and designed.
* Tests are written.

## Build

#Running steps:
- Go to project directory.
- Build project and create projects docker image.
  - ```./mvnw clean install```
- Build a docker image with desired tag
  - ```docker build -f .\docker\Dockerfile -t readingisgood:{tag} .```
- Run docker image with desired port
  - ```docker run -p {port}:8080 readingisgood:{tag}```  

**zkaratatar**
