package starwars.api.starwars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starwars.api.starwars.dto.FilmResponseDTO;
import starwars.api.starwars.jpa.entities.Film;
import starwars.api.starwars.services.implementation.StarWarsService;

import static starwars.api.starwars.constants.PathsControllers.*;

@RestController
@RequestMapping(PATH_CLIENTES_CONTROLLER)
@RequiredArgsConstructor
public class StarWarsApiController {

    private final StarWarsService starWarsService;

    @PostMapping(PATH_ID_PARAMETER)
    public ResponseEntity<FilmResponseDTO> saveEpisodeInfo(@PathVariable(ID_PARAMETER) int id) {
        return ResponseEntity.ok(starWarsService.getFilms(String.valueOf(id)));
    }
}
