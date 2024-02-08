package com.example.flightreservationsystem;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class FlightReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightReservationSystemApplication.class, args);
    }

}
