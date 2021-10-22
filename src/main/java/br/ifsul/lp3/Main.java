package br.ifsul.lp3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.ifsul.lp3.repository.MessageRepository;
import br.ifsul.lp3.repository.UserRepository;
import br.ifsul.lp3.view.MainFrame;

@SpringBootApplication
public class Main {
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected MessageRepository messageRepository;

	public static void main(String[] args) {
		SpringApplicationBuilder sab = new SpringApplicationBuilder(Main.class);
		sab.headless(false);
		
		ConfigurableApplicationContext app = sab.run(args);
		
		Main instance = (Main) app.getBean("main");		
		
		MainFrame mf = new MainFrame(instance.userRepository, instance.messageRepository);
				
		mf.setVisible(true);
	}

}
