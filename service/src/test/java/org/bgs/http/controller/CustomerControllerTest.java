package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerCreateEditDto;
import org.bgs.dto.UserCreateEditDto;
import org.bgs.repository.BaseIT;
import org.bgs.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.bgs.nodeModel.AddressNode.getAddressConvertedToJsonNode;
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
    private final CustomerRepository customerRepository;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(4)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/customers")
                        .param(UserCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(UserCreateEditDto.Fields.phone, "122346")
                        .param(UserCreateEditDto.Fields.firstname, "Van")
                        .param(UserCreateEditDto.Fields.lastname, "Test")
                        .param(UserCreateEditDto.Fields.password, "12345")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")
                        .param(CustomerCreateEditDto.Fields.address, getAddressConvertedToJsonNode(
                                COUNTRY,
                                CITY,
                                STREET_NAME,
                                houseNumber,
                                apartmentNumber).textValue())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
        customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getLogin().equals("test1@gmail.com"))
                .findFirst()
                .orElseThrow();
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
                        .param(UserCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(UserCreateEditDto.Fields.phone, "122346")
                        .param(UserCreateEditDto.Fields.firstname, "Van")
                        .param(UserCreateEditDto.Fields.lastname, "Test")
                        .param(UserCreateEditDto.Fields.password, "12345")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")
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
