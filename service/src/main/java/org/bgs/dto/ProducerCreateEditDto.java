package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.nodeModel.AddressNode;

@Value
@FieldNameConstants
public class ProducerCreateEditDto {

    String name;
    String producerInfo;
    AddressNode legalAddress;
}
