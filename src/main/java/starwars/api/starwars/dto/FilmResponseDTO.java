package starwars.api.starwars.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilmResponseDTO {
    private Long episode_id;
    private String title;
    private String release_date;
}
