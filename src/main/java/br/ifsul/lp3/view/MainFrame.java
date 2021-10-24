package br.ifsul.lp3.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ifsul.lp3.model.User;
import br.ifsul.lp3.repository.MessageRepository;
import br.ifsul.lp3.repository.UserRepository;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSeparator;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nicknameField;

	public MainFrame(UserRepository userRepository, MessageRepository messageRepository) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bem Vindo!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(163, 11, 109, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Para acessar a sala, insira um nickname:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(84, 47, 272, 19);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nickname");
		lblNewLabel_2.setBounds(190, 73, 67, 14);
		contentPane.add(lblNewLabel_2);
		
		nicknameField = new JTextField();
		nicknameField.setBounds(150, 89, 134, 20);
		contentPane.add(nicknameField);
		nicknameField.setColumns(100);
		
		JButton btnEnter = new JButton("Entrar");
		btnEnter.setBounds(170, 120, 99, 23);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nicknameField.getText().length() > 0 && nicknameField.getText().length() <= 100) {
					
					Optional<User> foundUser = userRepository.findByNickname(nicknameField.getText());
					
					User newUser = new User();
					
					if(foundUser.isPresent()) {
						newUser = foundUser.get();
						JOptionPane.showMessageDialog(null, "Bem Vindo de volta " + newUser.getNickname() + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					} else {
						newUser.setNickname(nicknameField.getText());
						newUser = userRepository.save(newUser);
						JOptionPane.showMessageDialog(null, "Bem Vindo " + newUser.getNickname() + "! Seu cadastro foi realizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					}
					
					dispose();
					BoardFrame bf = new BoardFrame(messageRepository, newUser, userRepository);
					bf.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Você deve inserir um nickname de até 100 cacteres para acessar!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnEnter);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(24, 164, 386, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1_1 = new JLabel("Gerar relatório");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(175, 185, 125, 19);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnReport = new JButton("Ver Relatório");
		btnReport.setBackground(Color.CYAN);
		btnReport.setBounds(150, 215, 134, 23);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ReportMenuFrame rmf = new ReportMenuFrame(userRepository, messageRepository);
				rmf.setVisible(true);
			}
		});
		contentPane.add(btnReport);
	}
}
