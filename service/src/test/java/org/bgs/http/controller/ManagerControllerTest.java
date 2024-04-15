package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.UserCreateEditDto;
import org.bgs.repository.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class ManagerControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/managers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("manager/managers"))
                .andExpect(model().attributeExists("managers"))
                .andExpect(model().attribute("managers", hasSize(1)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/managers")
                        .param(UserCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(UserCreateEditDto.Fields.phone, "122346")
                        .param(UserCreateEditDto.Fields.firstname, "Van")
                        .param(UserCreateEditDto.Fields.lastname, "Test")
                        .param(UserCreateEditDto.Fields.password, "12345")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")

                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/managers/3"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("manager/manager"))
                .andExpect(model().attributeExists("manager"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/managers/3/update")
                        .param(UserCreateEditDto.Fields.login, "test1@gmail.com")
                        .param(UserCreateEditDto.Fields.phone, "122346")
                        .param(UserCreateEditDto.Fields.firstname, "Van")
                        .param(UserCreateEditDto.Fields.lastname, "Test")
                        .param(UserCreateEditDto.Fields.password, "12345")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/managers/3")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/managers/3/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/managers"));
    }
}
