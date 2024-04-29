package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.GoodsCreateEditDto;
import org.bgs.repository.BaseIT;
import org.bgs.service.AccessoriesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class AccessoriesControllerTest extends BaseIT {

    private final MockMvc mockMvc;
    private final AccessoriesService accessoriesService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/accessories"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("accessory/accessories"))
                .andExpect(model().attributeExists("accessories"))
                .andExpect(model().attribute("accessories", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/accessories")
                        .param(GoodsCreateEditDto.Fields.name, "someName1")
                        .param(GoodsCreateEditDto.Fields.description, "DESC")
                        .param(GoodsCreateEditDto.Fields.price, "1000")
                        .param(GoodsCreateEditDto.Fields.quantity, "5")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/accessories/*")
                );
        accessoriesService.findAll()
                .stream()
                .filter(accessory -> accessory.getName().equals("someName1"))
                .findFirst()
                .orElseThrow();
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/accessories/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("accessory/accessory"))
                .andExpect(model().attributeExists("accessory"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/accessories/1/update")
                        .param(GoodsCreateEditDto.Fields.name, "someName1")
                        .param(GoodsCreateEditDto.Fields.description, "DESC")
                        .param(GoodsCreateEditDto.Fields.price, "1000")
                        .param(GoodsCreateEditDto.Fields.quantity, "5")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/accessories/1")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/accessories/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/accessories"));
    }
}
