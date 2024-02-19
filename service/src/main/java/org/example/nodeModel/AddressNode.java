package org.example.nodeModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressNode {

    private String country;
    private String city;
    private String streetName;
    private int houseNumber;
    private int apartmentNumber;

public JsonNode getAddressConvertedToJsonNode(String country, String city, String streetName,
                                              int houseNumber, int apartmentNumber) {
        var objectMapper = new ObjectMapper();
        AddressNode address = AddressNode.builder()
                .country(country)
                .city(city)
                .streetName(streetName)
                .houseNumber(houseNumber)
                .apartmentNumber(apartmentNumber)
                .build();
        return objectMapper.valueToTree(address);
    }
}
