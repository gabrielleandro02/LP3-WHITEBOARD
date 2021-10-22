package br.ifsul.lp3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifsul.lp3.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByNickname(String nickname);
}
