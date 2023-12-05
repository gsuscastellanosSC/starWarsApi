package starwars.api.starwars.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.jpa.entities.Film;
import starwars.api.starwars.jpa.repositories.FilmRepository;
import starwars.api.starwars.services.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseService implements Database {

    private final FilmRepository filmRepository;

    @Override
    public Film save(FilmDTO filmDTO) throws ParseException {
        Date date = formaterDate(filmDTO.getRelease_date());
        Film film = new Film();
        film.setEpisodeId(String.valueOf(filmDTO.getEpisode_id()));
        film.setTitle(filmDTO.getTitle());
        film.setReleaseDate(date);
        return filmRepository.save(film);
    }

    @Override
    public void updateTitleAndReleaseDateById(List<FilmsDTO> filmsDTO) {

        filmsDTO.forEach(filmDTO -> {
            {
                try {
                    filmRepository.updateTitleAndReleaseDateById(filmDTO.getEpisode_id(), filmDTO.getTitle(), formaterDate(filmDTO.getRelease_date()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static Date formaterDate(String string) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        return formatter.parse(string);
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
