package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ProducerReadDto;
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
public class ProducerControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/producers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("producer/producers"))
                .andExpect(model().attributeExists("producers"))
                .andExpect(model().attribute("producers", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/producers")
                        .param(ProducerReadDto.Fields.name, "someName1")
                        .param(ProducerReadDto.Fields.producerInfo, "INFO")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/producers/*")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/producers/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("producer/producer"))
                .andExpect(model().attributeExists("producer"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/producers/1/update")
                        .param(ProducerReadDto.Fields.name, "someName1")
                        .param(ProducerReadDto.Fields.producerInfo, "INFO")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/producers/1")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/producers/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/producers"));
    }
}
