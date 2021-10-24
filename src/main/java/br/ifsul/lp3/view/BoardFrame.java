package br.ifsul.lp3.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import br.ifsul.lp3.model.Message;
import br.ifsul.lp3.model.User;
import br.ifsul.lp3.repository.MessageRepository;
import br.ifsul.lp3.repository.UserRepository;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class BoardFrame extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMessageText;
	
	private static DefaultListModel<String> model = new DefaultListModel<>();
	
	private static MessageRepository messageRepositoryStatic;
	
	//UPDATE MESSAGES FUNCTION
	private final void reloadMessages() {
		
		Calendar dateMax = Calendar.getInstance();
		
		Calendar dateMin = Calendar.getInstance();
		dateMin.add(Calendar.HOUR, -1);
		
		model.clear();
		List<Message> messages = messageRepositoryStatic.findAllByDateBetweenOrderByDateDesc(dateMin.getTime(), dateMax.getTime());
		List<String> formattedMessages = new ArrayList<>();
		
		messages.forEach(message -> {
			@SuppressWarnings("deprecation")
			String formattedString =  message.getText() + " - " + message.getUser().getNickname() + " - " + message.getDate().toLocaleString();
			formattedMessages.add(formattedString);
		});
		model.addAll(formattedMessages);
	}

	public BoardFrame(MessageRepository messageRepository, User userActive, UserRepository userRepository) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		messageRepositoryStatic = messageRepository;
		contentPane.setLayout(null);
		
		JList<String> messagesList = new JList<>(model);
		
		messagesList.setModel(model);
		messagesList.setSelectedIndex(1);
		
		messagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(messagesList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20, 40, 386, 170);
		this.getContentPane().add(scrollPane);

        
		//TITLE OF PAGE
		JLabel lblNewLabel = new JLabel("Quadro Branco");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(139, 4, 145, 25);
		contentPane.add(lblNewLabel);
		
		//MESSAGE TEXT FIELD
		tfMessageText = new JTextField();
		tfMessageText.setBounds(20, 225, 296, 25);
		tfMessageText.setColumns(1);
		contentPane.add(tfMessageText);
		
		//BUTTON OF SEND MESSAGE
		JButton btnSend = new JButton("Enviar");
		btnSend.setBounds(329, 225, 77, 25);	
		btnSend.addActionListener((ActionEvent evt) -> {
			if(tfMessageText.getText().length() > 0) {
				Message newMessage = new Message();
				newMessage.setText(tfMessageText.getText());
				newMessage.setUser(userActive);
				newMessage.setDate(new Date());
				
				newMessage = messageRepository.save(newMessage);
				
				if(newMessage.getId() != null) {
					tfMessageText.setText("");
					this.reloadMessages();
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível enviar a mensagem! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);	
				}
			} else {
				JOptionPane.showMessageDialog(null, "Você deve inserir um texto para enviar!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
        });
		contentPane.add(btnSend);
		
		//BUTTON CLOSE ROOM
		JButton btnClose = new JButton("Sair da Sala");
		btnClose.setBounds(20, 6, 109, 23);
		btnClose.addActionListener((ActionEvent evt) -> {
			dispose();
			MainFrame mf = new MainFrame(userRepository, messageRepository);
			mf.setVisible(true);
        });
		contentPane.add(btnClose);
		
		//START THREAD
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível atualizar mensagens.", "Erro", JOptionPane.ERROR_MESSAGE);	
		}
		this.reloadMessages();
		System.out.println("THREAD RODOU");
	}
}
