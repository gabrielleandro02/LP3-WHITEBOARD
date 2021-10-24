package br.ifsul.lp3.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ifsul.lp3.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	List<Message> findAllByDateBetweenOrderByDateDesc(Date dateStart, Date dateEnd);
	List<Message> findAllByOrderByDateDesc();
	
}
