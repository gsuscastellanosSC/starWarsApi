package starwars.api.starwars.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.exceptions.InvalidEpisodeIdException;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;
import starwars.api.starwars.services.StarWarsApi;

@Service
public class StarWarsService implements StarWarsApi {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public StarWarsService(RestTemplate restTemplate, @Value("${swapi.baseurl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }


    @Override
    public FilmDTO getFilms(String episodeId){
        validateEpisodeId(episodeId);

        String url = baseUrl.concat(episodeId);
        try {
            return restTemplate.getForObject(url, FilmDTO.class);
        }catch (StarWarsNotFoundException exception){
            throw new StarWarsNotFoundException("Film not found");
        }
    }

    @Override
    public void validateEpisodeId(String episodeId) {
        if (!isValidEpisodeId(episodeId)) {
            throw new InvalidEpisodeIdException("Error en la solicitud.");
        }
    }

    private boolean isValidEpisodeId(String episodeId) {
        return episodeId.matches("\\d+") && episodeId != null && episodeId.length() <= 2 && Integer.parseInt(episodeId) <= 6;
    }
}
