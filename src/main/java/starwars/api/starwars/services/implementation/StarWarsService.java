package starwars.api.starwars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import starwars.api.starwars.dto.FilmDTO;
import starwars.api.starwars.dto.FilmResponseDTO;
import starwars.api.starwars.exceptions.InvalidEpisodeIdException;
import starwars.api.starwars.exceptions.StarWarsNotFoundException;
import starwars.api.starwars.jpa.entities.Film;
import starwars.api.starwars.jpa.repositories.FilmRepository;
import starwars.api.starwars.services.StarWarsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StarWarsService implements StarWarsApi {

    private final FilmRepository filmRepository;
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final int timeout;

    public StarWarsService(FilmRepository filmRepositry, RestTemplate restTemplate, @Value("${swapi.baseurl}") String baseUrl, @Value("${swapi.timeout}") int timeout, FilmRepository filmRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        ((SimpleClientHttpRequestFactory) factory).setConnectTimeout(timeout);
        ((SimpleClientHttpRequestFactory) factory).setReadTimeout(timeout);
        restTemplate.setRequestFactory(factory);
        this.filmRepository = filmRepository;
    }

    @Override
    public FilmResponseDTO getFilms(String episodeId) {
        validateEpisodeId(episodeId);

        String url = baseUrl.concat(episodeId);
        try {
            FilmDTO filmDTO = restTemplate.getForObject(url, FilmDTO.class);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
            Date date = formatter.parse(filmDTO.getRelease_date());
            Film film = new Film();
            film.setEpisodeId(String.valueOf(filmDTO.getEpisode_id()));
            film.setTitle(filmDTO.getTitle());
            film.setReleaseDate(date);
            Film saveFilm = filmRepository.save(film);
            FilmResponseDTO filmResponseDTO = FilmResponseDTO.builder()
                    .episode_id(Long.valueOf(film.getEpisodeId()))
                    .title(saveFilm.getTitle())
                    .release_date(filmDTO.getRelease_date())
                    .build();
            return filmResponseDTO;
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
