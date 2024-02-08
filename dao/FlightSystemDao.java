package com.example.flightreservationsystem.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.flightreservationsystem.models.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlightSystemDao {

    @Autowired
    DynamoDBMapper dynamoDBMapper;

    public Reservation getReservation(String pnr){
        Reservation request = Reservation.builder().pnrNo(pnr).build();
        return dynamoDBMapper.load(request);
    }

    public String makeReservation(Reservation reservationRequest){
        dynamoDBMapper.save(reservationRequest);
        Reservation response = dynamoDBMapper.load(reservationRequest);
        return response.getPnrNo();
    }


}

