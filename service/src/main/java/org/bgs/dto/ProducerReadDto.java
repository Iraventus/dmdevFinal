package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.nodeModel.AddressNode;

@Value
@FieldNameConstants
public class ProducerReadDto {

    Long id;
    String name;
    String producerInfo;
    AddressNode legalAddress;
}
