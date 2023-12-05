package starwars.api.starwars.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilmsDTO {
    private Long episode_id;
    private String title;
    private String release_date;
}
