package br.ifsul.lp3.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ifsul.lp3.model.Message;
import br.ifsul.lp3.model.User;
import br.ifsul.lp3.repository.MessageRepository;
import br.ifsul.lp3.repository.UserRepository;
import br.ifsul.lp3.utils.TypeReportEnum;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class ReportFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelLoading;
	private static DefaultListModel<String> model = new DefaultListModel<>();
	
	private static MessageRepository messageRepositoryStatic;
	private static UserRepository userRepositoryStatic;
	
	private final void loadMessages() {
		model.clear();
		List<Message> messages = messageRepositoryStatic.findAllByOrderByDateDesc();
		List<String> formattedMessages = new ArrayList<>();
		
		messages.forEach(message -> {
			@SuppressWarnings("deprecation")
			String formattedString =  message.getText() + " - " + message.getUser().getNickname() + " - " + message.getDate().toLocaleString();
			formattedMessages.add(formattedString);
		});
		model.addAll(formattedMessages);
		labelLoading.setText("Mensagens");
	}
	
	private final void loadNicknames() {
		model.clear();
		List<User> users = userRepositoryStatic.findAll();
		List<String> formattedUsers = new ArrayList<>();
		
		users.forEach(user -> {
			String formattedString = user.getNickname();
			formattedUsers.add(formattedString);
		});
		model.addAll(formattedUsers);
		labelLoading.setText("Nicknames");
	}

	public ReportFrame(MessageRepository messageRepository, UserRepository userRepository, TypeReportEnum type) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		messageRepositoryStatic = messageRepository;
		userRepositoryStatic = userRepository;
		
		JList<String> list = new JList<>(model);
		list.setModel(model);
		
		JLabel lblNewLabel = new JLabel("Relatório");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(166, 11, 193, 25);
		contentPane.add(lblNewLabel);
		
		JButton btnClose = new JButton("Voltar");
		btnClose.setBounds(20, 11, 89, 23);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ReportMenuFrame rmf = new ReportMenuFrame(userRepository, messageRepository);
				rmf.setVisible(true);
			}
		});
		contentPane.add(btnClose);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20, 40, 391, 210);
		this.getContentPane().add(scrollPane);
		
		//LABEL LOADING
		labelLoading = new JLabel("Carregando...");
		labelLoading.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelLoading.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(labelLoading);
		
		if(type == TypeReportEnum.MESSAGES) {
			this.loadMessages();
		} else {
			this.loadNicknames();
		}
	}
}
