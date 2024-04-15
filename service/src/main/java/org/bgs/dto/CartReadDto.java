package org.bgs.dto;

import lombok.Value;


@Value
public class CartReadDto {

    Long id;
    CustomerReadDto user;
}
