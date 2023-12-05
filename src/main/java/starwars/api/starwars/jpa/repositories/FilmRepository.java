package starwars.api.starwars.jpa.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import starwars.api.starwars.jpa.entities.Film;

import java.util.Date;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Transactional
    @Modifying
    Film save(Film film);
    @Transactional
    @Modifying
    @Query("UPDATE Film f SET f.title = :title WHERE f.id = :id")
    void updateTitleById(Long id, String title);

    @Transactional
    @Modifying
    @Query("UPDATE Film f SET f.title = :title, f.releaseDate = :releaseDate WHERE f.id = :id")
    void updateTitleAndReleaseDateById(Long id, String title, Date releaseDate);

    @Transactional
    @Modifying
    void deleteById(Long id);
}
