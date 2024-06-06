# URLShorten
In this folder you will find a sample URL shortener project using Spring and mySQL.

# What you will need to start the application
  * Maven 3.2+
  * JDK 1.8 or later
  * MySQL server 8.0 and MySQL workbench
  * IntelliJ IDEA
  * Postman

# How to start the application
  * Install the above programs, making sure the JAVA_Path variable and maven are installed correctly.
  * After installing MySQL create a database called: **urlshortener** in the MySQL workbench. 
  * Make sure the database is configured to run on:
    - spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    - spring.datasource.url = jdbc:mysql://localhost:3306/urlshortener
    - spring.datasource.username = root
    - spring.datasource.password = test1234
    - spring.jpa.show-sql = true
    - spring.jpa.hibernate.ddl-auto = update
    - spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
  * These are the settings under \url-shortener\src\main\resources\application.properties. They need to be the same so they can connect. 
  * Then you will create two tables. 
    * One using the following command to create the URL table:
      - CREATE TABLE `shortened_url` (
        `id` int NOT NULL AUTO_INCREMENT,
        `shortenedurl` varchar(8) NOT NULL,
        `longurl` varchar(1000) NOT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    * One using the following command to create the clicks table:
      - CREATE TABLE `shortened_url_clicks` (
        `id` int NOT NULL,
        `shortenedurlid` int DEFAULT NULL,
        `datetime` datetime DEFAULT NULL,
        PRIMARY KEY (`id`),
        KEY `shortened_url_id_idx` (`shortenedurlid`),
        CONSTRAINT `shortened_url_id` FOREIGN KEY (`shortenedurlid`) REFERENCES `shortened_url` (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  * Navigate to url-shortener folder. You should see this folder structure:
    - .idea
    - .mvn
    - src
    - target
    - .gitignore
    - HELP.md
    - mvnw
    - mvnw
    - pom
  * open a command prompt at this location. Then run **mvnw spring-boot:run** in the command line. The application should run. 
  * After the application is running and the mysql server is running with the database and tables created. You can now open postman to test the endpoints.

# APIs
 My project consists of 3 endpoints.
  * One POST endpoint that when submitting a URL **through the body** should return a JSON in the following format:
    - localhost:8080/api/1/urlShortener/
    - the url would go in the **BODY**. If you are using postman make sure you are putting the URL there in the **BODY** section.
    - {
        "id": 38,
        "shortenedurl": "ojnkihen",
        "longurl": "https://www.youtube.com/"
      }
  * One GET endpoint that when submitting a **short URL at the end of the main the URL** should redirect you to the link the short URL is connected to. 
    - Ex: localhost:8080/api/1/urlShortener/ojnkihen  
    - what you see in the console for postman:
      *  localhost:8080/api/1/urlShortener/ojnkihen -> 302
      *  https://www.youtube.com/ -> 200
    - So it will redirect from the endpoint and then show the 200 success from the current URL.
  * One GET endpoint that when submitting a **short URL at the end of the main the URL** should send you back a JSON with a list of clicks and what date/time those         clicks came in. The "shortenedurlid" is the id column of the URL table. 
    - Ex: localhost:8080/api/1/urlShortener/clicks/ojnkihen  
    - what you see in the console for postman:
      * [
          {
              "id": 39,
              "datetime": "2022-05-19 21:32:21",
              "shortenedurlid": 38
          }
        ]
        
# Testing

  * The way I ran my tests is through Intellij. When I open the project in intellij and navigate to \url-shortener\src\test\java\com\anthonydelarosa\urlshortener\
  * There should be three tests files.
    - ClickDateTimeServiceTests.java
    - ShortenedURLServiceTests.java
    - UrlShortenerControllerTests.java
  * One set of tests is for the ClickDateTimeService, one set of tests for the ShortenedURLService and one set of test for the UrlShortenController. 
  * The way I run the tests is by double clicking the file and then pressing the green play buttons on the left to test each test case.
  * To run the tests in the command prompt navigate to the url-shortener folder and then enter **mvnw test** and hit enter. It should give you the results of the           tests.
