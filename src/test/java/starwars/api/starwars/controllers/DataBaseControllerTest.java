package starwars.api.starwars.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.services.implementation.DataBaseService;

import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataBaseController.class)
class DataBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    DataBaseService dataBaseService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUpdateEpisodeInfo() {
        FilmsDTO filmsDTO = FilmsDTO.builder()
                .episode_id(1L)
                .title("Updated Title")
                .release_date("2023-12-31")
                .build();

        String requestContent = "[{" +
                "\"episode_id\": 1," +
                "\"title\": \"Updated Title\"," +
                "\"release_date\": \"2023-12-31\"" +
                "}]";

        assertDoesNotThrow(() ->
        {
            mockMvc.perform(MockMvcRequestBuilders.put("/bdfilms/update", 1)
                            .content(requestContent)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        });

        verify(dataBaseService).updateTitleAndReleaseDateById(Collections.singletonList(filmsDTO));
    }

    @Test
    void givenId_whenDeleteEpisodeInfo_thenDeleteSuccess() throws Exception {
        Long idToDelete = 1L;

        mockMvc.perform(delete("/bdfilms/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(dataBaseService).deleteById(idToDelete);
    }
}