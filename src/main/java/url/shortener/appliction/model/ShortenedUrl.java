package url.shortener.appliction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shortened_urls")
public class ShortenedUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "shortened_urls_seq", allocationSize = 1)
	@Column(name="ID", nullable = false, unique = true)
	private Long id;

	@Column(name="SHORT_URL",nullable = false, unique = true)
	private String shortUrl;

	@Column(name="LONG_URL",nullable = false)
	private String longUrl;

	@Column(name="CREATED_BY",nullable = false)
	private String createdBy;
	
}