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
import starwars.api.starwars.services.implementation.StarWarsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StarWarsApiController.class)
class StarWarsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StarWarsService starWarsService;

    private FilmsDTO filmsDTO;
    private String id;

    @BeforeEach
    void setUp(){
        filmsDTO = FilmsDTO.builder().build();
        id = "1";
    }

    @Test
    void testSaveEpisodeInfo() {
        when(starWarsService.getFilms(id)).thenReturn(filmsDTO);
        assertDoesNotThrow(() ->
        {
            mockMvc.perform(MockMvcRequestBuilders.post("/films/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        });
    }
}