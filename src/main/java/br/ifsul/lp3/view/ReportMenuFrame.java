package br.ifsul.lp3.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import br.ifsul.lp3.repository.MessageRepository;
import br.ifsul.lp3.repository.UserRepository;
import br.ifsul.lp3.utils.TypeReportEnum;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ReportMenuFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public ReportMenuFrame(UserRepository userRepository, MessageRepository messageRepository) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Relatório");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(166, 11, 101, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Selecione o tipo de dados para exibição");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(88, 82, 325, 41);
		contentPane.add(lblNewLabel_1);
		
		JButton btnClose = new JButton("Voltar");
		btnClose.setBounds(43, 11, 89, 23);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame mf = new MainFrame(userRepository, messageRepository);
				mf.setVisible(true);
			}
		});
		contentPane.add(btnClose);
		
		JButton btnReportNicknames = new JButton("Exibir Todos Nicknames");
		btnReportNicknames.setBounds(43, 174, 159, 23);
		btnReportNicknames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ReportFrame rf = new ReportFrame(messageRepository, userRepository, TypeReportEnum.NICKNAMES);
				rf.setVisible(true);
			}
		});
		contentPane.add(btnReportNicknames);
		
		JButton btnReportMessages = new JButton("Exibir Mensagens");
		btnReportMessages.setBounds(228, 174, 166, 23);
		btnReportMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ReportFrame rf = new ReportFrame(messageRepository, userRepository, TypeReportEnum.MESSAGES);
				rf.setVisible(true);
			}
		});
		contentPane.add(btnReportMessages);
	}
}
