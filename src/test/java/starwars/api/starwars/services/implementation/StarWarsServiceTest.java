package starwars.api.starwars.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.web.client.RestTemplate;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.exceptions.InvalidEpisodeIdException;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;
import starwars.api.starwars.jpa.entities.Film;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static starwars.api.starwars.utils.utils.formaterDate;

class StarWarsServiceTest {

    private StarWarsService starWarsService;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private DataBaseService dataBaseService;

    private String baseUrl;
    private int timeout;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        dataBaseService = mock(DataBaseService.class);
        baseUrl = "https://swapi.dev/api/films/";
        timeout = 30000;
        starWarsService = new StarWarsService(dataBaseService, restTemplate, baseUrl, timeout);
    }


    @Test
    void givenValidEpisodeId_whenGetFilms_thenSaveFilmSuccessfully() throws ParseException {
        String episodeId = "1";
        FilmDTO filmDTO = FilmDTO.builder().episode_id(123L).title("title test").release_date("2023-02-23").build();
        Film film = new Film();
        film.setEpisodeId("123");
        film.setReleaseDate(formaterDate("2023-02-23"));
        film.setTitle("title test");
        when(restTemplate.getForObject(eq(baseUrl + episodeId), eq(FilmDTO.class))).thenReturn(filmDTO);
        when(dataBaseService.save(any(FilmDTO.class))).thenReturn(film);

        starWarsService.getFilms(episodeId);

        verify(restTemplate, times(1)).getForObject(eq(baseUrl + episodeId), eq(FilmDTO.class));
        verify(dataBaseService, times(1)).save(any(FilmDTO.class));
    }

    @Test
    void givenNullEpisodeId_thenThrowException() {
        String nullEpisodeId = null;

        assertThrows(InvalidEpisodeIdException.class, () -> {
            starWarsService.validateEpisodeId(nullEpisodeId);
        });
    }

    @Test
    void givenEmptyEpisodeId_thenThrowException() {
        String emptyEpisodeId = "";

        assertThrows(InvalidEpisodeIdException.class, () -> {
            starWarsService.validateEpisodeId(emptyEpisodeId);
        });
    }

    @Test
    void givenInvalidFormatEpisodeId_thenThrowException() {
        String invalidFormatEpisodeId = "abc";

        assertThrows(InvalidEpisodeIdException.class, () -> {
            starWarsService.validateEpisodeId(invalidFormatEpisodeId);
        });
    }

    @Test
    void givenOutOfRangeEpisodeId_thenThrowException() {
        String outOfRangeEpisodeId = "10";

        assertThrows(InvalidEpisodeIdException.class, () -> {
            starWarsService.validateEpisodeId(outOfRangeEpisodeId);
        });
    }

    @Test
    void givenValidEpisodeId_thenNoException() {
        String validEpisodeId = "3";
        starWarsService.validateEpisodeId(validEpisodeId);
    }

    @Test
    void givenInvalidEpisodeId_thenThrowInvalidEpisodeIdException() {
        String invalidEpisodeId = "invalidId";
        when(restTemplate.getForObject("https://swapi.dev/api/films/" + invalidEpisodeId, FilmDTO.class))
                .thenThrow(StarWarsNotFoundException.class);

        assertThrows(InvalidEpisodeIdException.class, () -> {
            starWarsService.getFilms(invalidEpisodeId);
        });
    }

    @Test
    void givenNonexistentFilm_thenThrowStarWarsNotFoundException() {
        String nonexistentEpisodeId = "1";
        when(restTemplate.getForObject("https://swapi.dev/api/films/" + nonexistentEpisodeId, FilmDTO.class))
                .thenThrow(StarWarsNotFoundException.class);

        assertThrows(StarWarsNotFoundException.class, () -> {
            starWarsService.getFilms(nonexistentEpisodeId);
        });
    }
}