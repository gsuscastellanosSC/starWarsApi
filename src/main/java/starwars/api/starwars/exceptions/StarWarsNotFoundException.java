package starwars.api.starwars.exceptions;

public class StarWarsNotFoundException extends RuntimeException {
    public StarWarsNotFoundException(String message) {
        super(message);
    }
}
