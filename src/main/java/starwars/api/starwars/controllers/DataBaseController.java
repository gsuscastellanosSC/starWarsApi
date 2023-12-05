package starwars.api.starwars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starwars.api.starwars.dto.FilmsDTO;
import starwars.api.starwars.services.implementation.DataBaseService;

import java.util.List;

import static starwars.api.starwars.constants.PathsControllers.*;

@RestController
@RequestMapping(PATH_BD_FILMS_CONTROLLER)
@RequiredArgsConstructor
public class DataBaseController {

    private final DataBaseService dataBaseService;

    @PutMapping(PATH_BD_UPDATE_FILMS_CONTROLLER)
    public ResponseEntity updateEpisodeInfo(@RequestBody List<FilmsDTO> filmsDTO) {
        dataBaseService.updateTitleAndReleaseDateById(filmsDTO);
        return ResponseEntity.ok("Films updated successfully");
    }

    @DeleteMapping(PATH_BD_DELETE_FILMS_CONTROLLER)
    public ResponseEntity deleteEpisodeInfo(@PathVariable(ID_PARAMETER) Long id) {
        dataBaseService.deleteById(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }
}
