package starwars.api.starwars.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmResponseDTO;
import starwars.api.starwars.jpa.entities.Film;
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

    private FilmResponseDTO filmResponseDTO;
    private String id;

    @BeforeEach
    void setUp(){
        filmResponseDTO = FilmResponseDTO.builder().build();
        id = "1";
    }

    @Test
    void testSaveEpisodeInfo() {
        when(starWarsService.getFilms(id)).thenReturn(filmResponseDTO);
        assertDoesNotThrow(() ->
        {
            mockMvc.perform(MockMvcRequestBuilders.post("/films/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        });
    }
}