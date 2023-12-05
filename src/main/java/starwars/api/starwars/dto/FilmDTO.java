package starwars.api.starwars.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilmDTO {
    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String created;
    private String edited;
    private String url;
}
