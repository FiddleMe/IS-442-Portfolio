# IS-442-Portfolio

Portfolio App for IS442 Project
Dependencies:

- Docker Dektop
- Maven
- NodeJS
- npm

SETUP BACKEND

1. Open Docker Desktop
2. `docker compose up`

To start up Springboot backend

1. `cd BackendServices`
2. `mvn clean install`
3. `mvn spring-boot:run`
4. goto localhost:8080, login using default username & password, oopPortfolio tables should be created.

SETUP FRONTEND
1. `cd public/portfolio-app`
2. `npm install`

To start up React frontend
1. `cd public/portfolio-app`
2. `npm start`
3. go to http://localhost:3000/ on browser
   
