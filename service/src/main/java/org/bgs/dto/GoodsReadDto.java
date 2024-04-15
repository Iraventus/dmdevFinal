package org.bgs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class GoodsReadDto {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Integer price;
}
