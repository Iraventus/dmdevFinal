package org.board_games_shop.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.board_games_shop.nodeModel.AddressNode;

@Value
@FieldNameConstants
public class ProducerReadDto {

    Long id;
    String name;
    String producerInfo;
    AddressNode legalAddress;
}
