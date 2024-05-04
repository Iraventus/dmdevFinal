package org.bgs.dto;

import lombok.Value;
import org.bgs.entity.Status;

import java.time.Instant;

@Value
public class OrderReadDto {

    Long id;
    Status status;
    Instant reservationEndDate;
    CustomerReadDto user;
}
