package starwars.api.starwars.services;

import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;

public interface StarWarsApi {
    FilmsDTO getFilms(String episodeId) throws StarWarsNotFoundException;
    void validateEpisodeId(String episodeId);
}
