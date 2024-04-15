package org.bgs.http.rest;

import lombok.RequiredArgsConstructor;
import org.bgs.repository.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class BoardGamesRestControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/boardGames"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/boardGames/4"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Arkham Horror"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/v1/boardGames")
                        .content("{\"name\" : \"someName\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api/v1/boardGames/4")
                        .content("{\"name\" : \"someName\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        status().is2xxSuccessful()
                );
    }

    @Test
    void deleteGame() throws Exception {
        mockMvc.perform(delete("/api/v1/boardGames/4"))
                .andExpect(
                        status().is2xxSuccessful()
                );
    }
}
