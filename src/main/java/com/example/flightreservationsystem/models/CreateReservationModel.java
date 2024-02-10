package com.example.flightreservationsystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateReservationModel {
    private String flightNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String to;
    private String from;
    private String travelDate;
    private String arrivalTime;
    private String departTime;
}
