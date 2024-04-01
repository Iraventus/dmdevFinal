package org.board_games_shop.http.controller;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CustomerCreateEditDto;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.board_games_shop.nodeModel.AddressNode.getAddressConvertedToJsonNode;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CustomerControllerTest extends BaseIT {

    private static final String COUNTRY = "someCountry";
    private static final int apartmentNumber = 12;
    private static final String CITY = "someCity";
    private static final int houseNumber = 3;
    private static final String STREET_NAME = "someStreetName";
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/customers")
                        .param(CustomerCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(CustomerCreateEditDto.Fields.phone, "122346")
                        .param(CustomerCreateEditDto.Fields.firstname, "Van")
                        .param(CustomerCreateEditDto.Fields.lastname, "Test")
                        .param(CustomerCreateEditDto.Fields.password, "12345")
                        .param(CustomerCreateEditDto.Fields.birthDate, "2000-01-01")
                        .param(CustomerCreateEditDto.Fields.address, getAddressConvertedToJsonNode(
                                COUNTRY,
                                CITY,
                                STREET_NAME,
                                houseNumber,
                                apartmentNumber).textValue())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/customers/*")

                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/customers/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("customer/customer"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/customers/1/update")
                        .param(CustomerCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(CustomerCreateEditDto.Fields.phone, "122346")
                        .param(CustomerCreateEditDto.Fields.firstname, "Van")
                        .param(CustomerCreateEditDto.Fields.lastname, "Test")
                        .param(CustomerCreateEditDto.Fields.password, "12345")
                        .param(CustomerCreateEditDto.Fields.birthDate, "2000-01-01")
                        .param(CustomerCreateEditDto.Fields.address, getAddressConvertedToJsonNode(
                                COUNTRY,
                                CITY,
                                STREET_NAME,
                                houseNumber,
                                apartmentNumber).textValue())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/customers/1")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/customers/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/customers"));
    }
}
