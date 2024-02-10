package com.example.flightreservationsystem.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.flightreservationsystem.models.ReservationDynamoModel;
import com.example.flightreservationsystem.models.UpdateReservationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlightSystemDao {

    @Autowired
    DynamoDBMapper dynamoDBMapper;

    public ReservationDynamoModel getReservation(String pnr){
        ReservationDynamoModel request = ReservationDynamoModel.builder().pnrNo(pnr).build();
        return dynamoDBMapper.load(request);
    }

    public String makeReservation(ReservationDynamoModel reservationDynamoModelRequest){
        dynamoDBMapper.save(reservationDynamoModelRequest);
        ReservationDynamoModel response = dynamoDBMapper.load(reservationDynamoModelRequest);
        return response.getPnrNo();
    }

    public String updateReservation(ReservationDynamoModel updateReservationModelRequest) {
        dynamoDBMapper.save(updateReservationModelRequest);
        return updateReservationModelRequest.getPnrNo();
    }

    public String cancelReservation(String pnr){
        ReservationDynamoModel request = ReservationDynamoModel.builder().pnrNo(pnr).build();
        ReservationDynamoModel existingReservationDynamoModel = dynamoDBMapper.load(request);
        existingReservationDynamoModel.setCancelled(true);
        dynamoDBMapper.save(existingReservationDynamoModel);
        return existingReservationDynamoModel.getPnrNo();
    }
}

