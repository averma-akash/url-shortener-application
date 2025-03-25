package url.shortener.appliction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import url.shortener.appliction.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

	Roles getById(Integer roleId);
}
