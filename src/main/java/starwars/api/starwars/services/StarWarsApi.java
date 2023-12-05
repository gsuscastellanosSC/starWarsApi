package starwars.api.starwars.services;

import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;

public interface StarWarsApi {
    FilmDTO getFilms(String episodeId) throws StarWarsNotFoundException;
    void validateEpisodeId(String episodeId);
}
