package starwars.api.starwars.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import starwars.api.starwars.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorDTO> handleNotFound(HttpClientErrorException ex) {
        LOGGER.error("Resource not found:: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.NO_CONTENT;
        int statusCode = status.value();
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .status(status)
                        .statusCode(statusCode)
                        .message( HttpStatus.NO_CONTENT.getReasonPhrase())
                        .build());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        LOGGER.error("Invalid episodeId:: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int statusCode = status.value();
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .status(status)
                        .statusCode(statusCode)
                        .message("error en la solicitud.")
                        .build());
    }

    @ExceptionHandler(InvalidEpisodeIdException.class)
    public ResponseEntity<ErrorDTO> handleTypeInvalidEpisodeIdException(InvalidEpisodeIdException ex) {
        LOGGER.error("Invalid episodeId:: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int statusCode = status.value();
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .status(status)
                        .statusCode(statusCode)
                        .message("error en la solicitud.")
                        .build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        LOGGER.error("Internal Server Error:: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        int statusCode = status.value();
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .status(status)
                        .statusCode(statusCode)
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build());
    }
}
