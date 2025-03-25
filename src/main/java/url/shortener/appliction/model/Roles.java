package url.shortener.appliction.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ROLE_NAME")
	private String roleName;
	@Column(name = "CREATED_AT")
	private Date createdAt;

}
