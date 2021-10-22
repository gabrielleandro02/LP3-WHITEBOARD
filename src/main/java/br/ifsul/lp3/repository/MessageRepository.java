package br.ifsul.lp3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ifsul.lp3.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
}
