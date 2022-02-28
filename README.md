Developer note:
  - This project developed with Java 11 and spring framework(spring boot v 2.6.3)
  - H2 database used for store data. save entities, persist request and response etc.

## Tech stack:

* Java 11
* H2 database
* Docker
* swagger


#Running steps:
- Go to project directory.
- Build project and create projects docker image.
  - ```./mvnw clean install```
- Build a docker image with desired tag
  - ```docker build -f .\docker\Dockerfile -t readingisgood:{tag} .```
- Run docker image with desired port
  - ```docker run -p {port}:8080 readingisgood:{tag}```  

**zkaratatar**
