package url.shortener.appliction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import url.shortener.appliction.model.ShortenedUrl;

@Repository
public interface ShortenedRepository extends JpaRepository<ShortenedUrl, Long> {

	Optional<ShortenedUrl> findByShortUrl(String longUrl);

}
