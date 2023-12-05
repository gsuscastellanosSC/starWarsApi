package starwars.api.starwars.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.jpa.entities.Film;
import starwars.api.starwars.jpa.repositories.FilmRepository;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataBaseServiceTest {

    @InjectMocks
    private DataBaseService dataBaseService;
    @Mock
    private FilmRepository filmRepository;

    private FilmDTO filmDTO;
    private FilmsDTO filmsDTO;

    @BeforeEach
    void setUp() {
        filmDTO = FilmDTO.builder().build();
        filmsDTO = FilmsDTO.builder().build();
    }

    @Test
    void givenFilmDTO_whenSave_thenFilmSavedSuccessfully() throws ParseException {
        filmDTO.setEpisode_id(1L);
        filmDTO.setTitle("Title");
        filmDTO.setRelease_date("2023-12-31");

        Date parsedDate = DataBaseService.formaterDate("2023-12-31");
        Film expectedFilm = new Film();
        expectedFilm.setEpisodeId("1");
        expectedFilm.setTitle("Title");
        expectedFilm.setReleaseDate(parsedDate);

        when(filmRepository.save(any(Film.class))).thenReturn(expectedFilm);

        Film savedFilm = dataBaseService.save(filmDTO);

        verify(filmRepository, times(1)).save(any(Film.class));
        assertEquals(expectedFilm, savedFilm);
    }


    @Test
    public void givenFilmsDTOList_whenUpdateTitleAndReleaseDateById_thenUpdateSuccessfully() throws ParseException {
        filmsDTO.setEpisode_id(1L);
        filmsDTO.setTitle("Updated Title");
        filmsDTO.setRelease_date("2023-12-31");

        dataBaseService.updateTitleAndReleaseDateById(Collections.singletonList(filmsDTO));

        verify(filmRepository, times(1)).updateTitleAndReleaseDateById(eq(1L), eq("Updated Title"), any(Date.class));
    }

    @Test
    public void givenId_whenDeleteById_thenDeleteSuccessfully() {
        Long idToDelete = 1L;

        dataBaseService.deleteById(idToDelete);

        verify(filmRepository, times(1)).deleteById(idToDelete);
    }
}