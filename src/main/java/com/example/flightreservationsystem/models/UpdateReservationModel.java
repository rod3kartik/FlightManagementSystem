package com.example.flightreservationsystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReservationModel {
    private String pnrNo;
    private String to;
    private String from;
    private String arrivalTime;
    private String departTime;
    private String travelDate;
    private String flightNumber;

}
