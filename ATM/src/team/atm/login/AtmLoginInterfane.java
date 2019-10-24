package team.atm.login;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import team.atm.user.User;

public class AtmLoginInterfane {

	public User user;
	public JFrame loginFrame;
	
	// �û���¼����
	public AtmLoginInterfane()
	{
		loginFrame = new JFrame("��¼ATM"); 
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ��¼logo
		JPanel bankNamePanel = new JPanel();
		bankNamePanel.add(new JLabel("����������"));
		
		// �û��˺������
		JPanel userAccountPanle = new JPanel();
		userAccountPanle.add(new Label("Account:    "));
		JTextField userAccount = new JTextField(20);
		userAccountPanle.add(userAccount);
		
		// �û����������
		JPanel userPasswordPanel = new JPanel();
		userPasswordPanel.add(new Label("Password:"));
		JPasswordField userPassword = new JPasswordField(20);
		userPasswordPanel.add(userPassword);
		
		// �û���¼��ť��ע�ᰴť
		JPanel btnPanel = new JPanel();
		JButton loginButton = new JButton("Login");
		JButton registeredButton = new JButton("Registered");
		btnPanel.add(loginButton);
		btnPanel.add(registeredButton);
		
		// �������	
		Box verticall = Box.createVerticalBox();

		verticall.add(bankNamePanel);
		verticall.add(userAccountPanle);
		verticall.add(userPasswordPanel);
		verticall.add(btnPanel);

		loginFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		loginFrame.add(verticall);
		loginFrame.setVisible(true);				// ��ʾ�ɼ�
		loginFrame.pack(); 				// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		loginFrame.setSize(500, 300);;	// �����С����
		loginFrame.setLocationRelativeTo(null);
		
		
		// �����¼��ť
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ���ע�ᰴť
		registeredButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loginFrame.setVisible(false); // ��¼������ʧ
				registeredBankCard();		  // ����ע�����
			}
		});
		
	}
	
	// ����û�����
	public void CheckPassword()
	{
		
	}
	
	// �û�ע��
	public User registeredBankCard(){
		
		new AtmRegisteredInterface();
		return null;
		
	}
}
