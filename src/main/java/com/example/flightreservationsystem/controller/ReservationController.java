package com.example.flightreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.flightreservationsystem.models.Reservation;
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
    public Reservation getReservationByPnr(@PathVariable String pnr) {
        return this.dao.getReservation(pnr);
    }
    @PostMapping("/cancel/{pnr}")
    public String cancelReservationByPnr(@PathVariable String pnr) {
        return this.dao.cancelReservation(pnr);
    }


    @PostMapping("/add")
    public String addReservation(@RequestBody Reservation request){
        try {
            String requestTime = DateTime.now().toString();
            Reservation reservation = Reservation.builder().email(request.getEmail())
                    .arrivalTime(request.getArrivalTime())
                    .pnrNo(request.getPnrNo())
                    .bookingDate(requestTime)
                    .flightNumber(request.getFlightNumber())
                    .departTime(request.getDepartTime())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .from(request.getFrom())
                    .travelDate(request.getTravelDate())
                    .to(request.getTo())
                    .build();
            return this.dao.makeReservation(reservation);
        }
        catch(Exception e){
            log.info("Exception while making a reservation", e);
            return e.toString();
        }
    }

}