# Distance Calculation Service - (SpringBoot)

This project has been generated using Spring Initializr template from the [Spring Initializr](https://start.spring.io/). 
It responsible for serving the REST endpoints to handle calculation of distance. As for now it limited to only calculate geographic distance between two UK postcode.

## Configuration

> **Requirements**:

1. JAVA `20`. Make sure Java version in local is same with this version.
2. Springboot `3.0.6`.
3. Docker
4. Docker Compose
5. Maven

## Installation

using docker

```bash
# start the postgres database container (unit tests will failed if the database is not running)
$ docker-compose up -d postgres

# build the container
$ mvn spring-boot:build-image

# run the container
$ docker-compose up -d
```

## Running the app

Project will be running at http://localhost:8081. Make call to `/swagger-ui/index.html` path to see available endpoints

## How to test the endpoints

1. Open the swagger page at http://localhost:8081/swagger-ui/index.html
2. Click on the endpoint you want to test
3. Click on the `Try it out` button
4. Fill the required parameter
5. Click on the `Execute` button
6. The result will be shown on the `Response body` section

You can also use `curl` to test the endpoints. For example:

1. Retrieve the distance between two postcode
```bash
$ curl --location --request GET 'http://localhost:8081/distance/BH1 3EF/BH1 3ET' \
--header 'Cookie: JSESSIONID=B7B0DC30DB3716711740616C098B3031'
```

2. Update the latitude and longitude of a postcode
```bash
$ curl --location --request PUT 'http://localhost:8081/location/postal/BD981GA' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B7B0DC30DB3716711740616C098B3031' \
--data-raw '{
    "latitude": 53.800509,
    "longitude": -1.836421
}'
```
