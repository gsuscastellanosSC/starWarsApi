package starwars.api.starwars.services;

import starwars.api.starwars.dto.FilmResponseDTO;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;

public interface StarWarsApi {
    FilmResponseDTO getFilms(String episodeId) throws StarWarsNotFoundException;
    void validateEpisodeId(String episodeId);
}
