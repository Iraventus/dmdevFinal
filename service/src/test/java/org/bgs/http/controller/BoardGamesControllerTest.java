package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.BoardGamesCreateEditDto;
import org.bgs.dto.GoodsCreateEditDto;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;
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
public class BoardGamesControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/boardGames"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("boardGame/boardGames"))
                .andExpect(model().attributeExists("boardGames"))
                .andExpect(model().attribute("boardGames", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/boardGames")
                        .param(GoodsCreateEditDto.Fields.name, "someName1")
                        .param(GoodsCreateEditDto.Fields.description, "DESC")
                        .param(GoodsCreateEditDto.Fields.price, "1000")
                        .param(BoardGamesCreateEditDto.Fields.boardGameTheme, BoardGameTheme.COOP.toString())
                        .param(BoardGamesCreateEditDto.Fields.contents, "some content")
                        .param(BoardGamesCreateEditDto.Fields.localization, Localization.GE.toString())
                        .param(BoardGamesCreateEditDto.Fields.creator, "creator")
                        .param(GoodsCreateEditDto.Fields.quantity, "5")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/boardGames/*")

                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/boardGames/4"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("boardGame/boardGame"))
                .andExpect(model().attributeExists("boardGame"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/boardGames/4/update")
                        .param(GoodsCreateEditDto.Fields.name, "someName1")
                        .param(GoodsCreateEditDto.Fields.description, "DESC")
                        .param(GoodsCreateEditDto.Fields.price, "1000")
                        .param(BoardGamesCreateEditDto.Fields.boardGameTheme, BoardGameTheme.COOP.toString())
                        .param(BoardGamesCreateEditDto.Fields.contents, "some content")
                        .param(BoardGamesCreateEditDto.Fields.localization, Localization.GE.toString())
                        .param(BoardGamesCreateEditDto.Fields.creator, "creator")
                        .param(GoodsCreateEditDto.Fields.quantity, "5")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/boardGames/4")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/boardGames/4/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/boardGames"));
    }
}
