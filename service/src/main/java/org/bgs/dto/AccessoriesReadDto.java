package org.bgs.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AccessoriesReadDto extends GoodsReadDto {

    ProducerReadDto producer;

    @Builder
    public AccessoriesReadDto(Long id, String name, String description, Integer quantity, Integer price, ProducerReadDto producer) {
        super(id, name, description, quantity, price);
        this.producer = producer;
    }
}
