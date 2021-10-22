package br.ifsul.lp3.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

public class BoardFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMessageText;
	
	private DefaultListModel<String> model = new DefaultListModel<>();
	
	private final List<String> reloadMessages(MessageRepository messageRepository) {
		List<Message> messages = messageRepository.findAll();
		List<String> formmatedMessages = new ArrayList<>();
		
		messages.forEach(message -> {
			String formattedString =  message.getText() + " - " + message.getUser().getNickname() + " - " + message.getDate().toLocaleString();
			formmatedMessages.add(formattedString);
		});
		return formmatedMessages;
	}

	public BoardFrame(MessageRepository messageRepository, User userActive) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		model.addAll(this.reloadMessages(messageRepository));
				
		JList<String> messagesList = new JList<>(model);
		
		messagesList.setModel(model);
		messagesList.setSelectedIndex(1);
		
		messagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(messagesList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20, 40, 386, 170);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        
		//TITLE OF PAGE
		JLabel lblNewLabel = new JLabel("Quadro Branco");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(139, 11, 145, 25);
		contentPane.add(lblNewLabel);
		
		//MESSAGE TEXT FIELD
		tfMessageText = new JTextField();
		tfMessageText.setBounds(20, 225, 296, 25);
		tfMessageText.setColumns(1);
		contentPane.add(tfMessageText);
		
		//BUTTON OF SEND MESSAGE
		JButton btnSend = new JButton("Enviar");
		btnSend.setBounds(332, 225, 74, 25);	
		btnSend.addActionListener((ActionEvent evt) -> {
			if(tfMessageText.getText().length() > 0) {
				Message newMessage = new Message();
				newMessage.setText(tfMessageText.getText());
				newMessage.setUser(userActive);
				newMessage.setDate(new Date());
				
				newMessage = messageRepository.save(newMessage);
				
				if(newMessage.getId() != null) {
					tfMessageText.setText("");
					model.clear();
					model.addAll(this.reloadMessages(messageRepository));
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível enviar a mensagem! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);	
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "Você deve inserir um texto para enviar!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
        });
		contentPane.add(btnSend);
		
		//SEPARATOR
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(10, 213, 403, 11);
		contentPane.add(separator, BorderLayout.CENTER);
		
	}
}
