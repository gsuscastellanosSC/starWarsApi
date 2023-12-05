package starwars.api.starwars.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.exceptions.InvalidEpisodeIdException;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;
import starwars.api.starwars.jpa.entities.Film;
import starwars.api.starwars.jpa.repositories.FilmRepository;
import starwars.api.starwars.services.StarWarsApi;

import java.text.ParseException;

@Service
public class StarWarsService implements StarWarsApi {

    private final DataBaseService dataBaseService;
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final int timeout;

    public StarWarsService(DataBaseService dataBaseService, RestTemplate restTemplate, @Value("https://swapi.dev/api/films/") String baseUrl, @Value("30000") int timeout, FilmRepository filmRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        ((SimpleClientHttpRequestFactory) factory).setConnectTimeout(timeout);
        ((SimpleClientHttpRequestFactory) factory).setReadTimeout(timeout);
        restTemplate.setRequestFactory(factory);
        this.dataBaseService = dataBaseService;
    }

    @Override
    public FilmsDTO getFilms(String episodeId) {
        validateEpisodeId(episodeId);

        String url = baseUrl.concat(episodeId);
        try {
            FilmDTO filmDTO = restTemplate.getForObject(url, FilmDTO.class);
            Film saveFilm = dataBaseService.save(filmDTO);
            return FilmsDTO.builder()
                    .episode_id(Long.valueOf(saveFilm.getEpisodeId()))
                    .title(saveFilm.getTitle())
                    .release_date(filmDTO.getRelease_date())
                    .build();
        } catch (StarWarsNotFoundException exception) {
            throw new StarWarsNotFoundException("Film not found");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
