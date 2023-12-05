package starwars.api.starwars.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDTO {
    private HttpStatus status;
    private int statusCode;
    private String message;
}
