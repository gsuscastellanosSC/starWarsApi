package starwars.api.starwars.exceptions;

public class InvalidEpisodeIdException extends RuntimeException {
    public InvalidEpisodeIdException(String message) {
        super(message);
    }
}