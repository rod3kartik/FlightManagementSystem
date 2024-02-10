package com.example.flightreservationsystem.controller;

import com.amazonaws.util.StringUtils;
import com.example.flightreservationsystem.models.CreateReservationModel;
import com.example.flightreservationsystem.models.UpdateReservationModel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.flightreservationsystem.models.ReservationDynamoModel;
import com.example.flightreservationsystem.dao.FlightSystemDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private FlightSystemDao dao;

    @GetMapping("/view/{pnr}")
    public ReservationDynamoModel getReservationByPnr(@PathVariable String pnr) throws Exception {
        if(this.dao.getReservation(pnr) == null){
            throw new Exception("Not a valid pnr");
        }
        return this.dao.getReservation(pnr);
    }

    @PostMapping("/cancel/{pnr}")
    public String cancelReservationByPnr(@PathVariable String pnr) {
        return this.dao.cancelReservation(pnr);
    }
    @PostMapping("/update")
    public String updateReservation(@RequestBody UpdateReservationModel request) throws Exception {
        if(this.dao.getReservation(request.getPnrNo()) == null){
            throw new Exception("Not a valid pnr");
        }
        ReservationDynamoModel existingReservation = this.dao.getReservation(request.getPnrNo());
        if(existingReservation.isCancelled()){
            throw new Exception("Cancelled reservation cannot be modified");
        }
        try {
            String requestTime = DateTime.now().toString();
            ReservationDynamoModel reservationDynamoModel = ReservationDynamoModel.builder()
                    .arrivalTime(getPropertiesFromUser(request.getArrivalTime(),existingReservation.getArrivalTime()))
                    .bookingDate(requestTime)
                    .pnrNo(getPropertiesFromUser(request.getPnrNo(),existingReservation.getPnrNo()))
                    .flightNumber(getPropertiesFromUser(request.getFlightNumber(),existingReservation.getFlightNumber()))
                    .departTime(getPropertiesFromUser(request.getDepartTime(),existingReservation.getDepartTime()))
                    .from(getPropertiesFromUser(request.getFrom(),existingReservation.getFrom()))
                    .travelDate(getPropertiesFromUser(request.getTravelDate(),existingReservation.getTravelDate()))
                    .to(getPropertiesFromUser(request.getTo(),existingReservation.getTo()))
                    .build();

            return this.dao.updateReservation(reservationDynamoModel);
        }
        catch(Exception e){
            log.info("Exception while updating reservation {}",request.getPnrNo(), e);
            return e.toString();
        }
    }

    @PostMapping("/add")
    public String addReservation(@RequestBody CreateReservationModel request){
        try {
            String requestTime = DateTime.now().toString();
            ReservationDynamoModel reservationDynamoModel = ReservationDynamoModel.builder()
                    .email(request.getEmail())
                    .arrivalTime(request.getArrivalTime())
                    .bookingDate(requestTime)
                    .flightNumber(request.getFlightNumber())
                    .departTime(request.getDepartTime())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .from(request.getFrom())
                    .travelDate(request.getTravelDate())
                    .to(request.getTo())
                    .build();
            return this.dao.makeReservation(reservationDynamoModel);
        }
        catch(Exception e){
            log.info("Exception while making a reservation", e);
            return e.toString();
        }
    }

    private String getPropertiesFromUser(String newProperty, String oldProperty){
        return StringUtils.isNullOrEmpty(newProperty) ? oldProperty : newProperty;
    }
}