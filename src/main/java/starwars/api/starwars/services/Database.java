package starwars.api.starwars.services;

import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.jpa.entities.Film;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface Database {
    Film save(FilmDTO filmDTO) throws ParseException;
    void updateTitleAndReleaseDateById(List<FilmsDTO> filmsDTO);
    void deleteById(Long id);
}
