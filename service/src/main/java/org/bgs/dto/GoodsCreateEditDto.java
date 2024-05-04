package org.bgs.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@AllArgsConstructor
@Data
public abstract class GoodsCreateEditDto {

    private String name;
    private String description;
    @Min(0)
    private Integer quantity;
    private Integer price;
}
