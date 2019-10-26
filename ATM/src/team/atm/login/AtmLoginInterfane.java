package team.atm.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import team.atm.user.User;

public class AtmLoginInterfane {

	public JFrame loginFrame;
	public User user;
	private String bankName;
	
	public AtmLoginInterfane(String bankName) {
		super();
		this.bankName = bankName;
	}

	// �û���¼����
	public void loginInterface()
	{
		loginFrame = new JFrame("��¼ATM"); 
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ��¼logo
		JPanel bankNamePanel = new JPanel();
		bankNamePanel.add(new JLabel(getBankName()));
		
		// �û��˺������
		JPanel userAccountPanle = new JPanel();
		JLabel userAccountJLabel = new JLabel("Account:", JLabel.CENTER);
		userAccountJLabel.setPreferredSize(new Dimension(120, 20));
		userAccountPanle.add(userAccountJLabel);
		JTextField userAccount = new JTextField(20);
		userAccountPanle.add(userAccount);
		
		// �û����������
		JPanel userPasswordPanel = new JPanel();
		JLabel userPasswordJLabel = new JLabel("Password:", JLabel.CENTER);
		userPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userPasswordPanel.add(userPasswordJLabel);
		JPasswordField userPassword = new JPasswordField(20);
		userPasswordPanel.add(userPassword);
		
		// �û���¼��ť��ע�ᰴť
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
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

		loginFrame.setLayout(new BorderLayout());		// ������ʽ����
		loginFrame.add(verticall, BorderLayout.CENTER);
		loginFrame.setVisible(true);				// ��ʾ�ɼ�
		loginFrame.pack(); 				// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		loginFrame.setSize(500, 300);;	// �����С����
		loginFrame.setLocationRelativeTo(null);
		
		// �����¼��ť 	 ����û�����
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String inputAccuontString = userAccount.getText();
				String inputPasswordString = new String(userPassword.getPassword());
				
				// �ж��û������Ƿ�Ϊ��
				if (inputAccuontString.equals("")) {
					JOptionPane.showMessageDialog(loginFrame, "�������˺ţ�");
					return;
				}
				else if (inputPasswordString.equals("")) {
					JOptionPane.showMessageDialog(loginFrame, "���������룡");
					return;
				}
				
				//����û��˺�����
				String filePath = "UserData/" + inputAccuontString + ".txt";
				File userFile = new File(filePath);
				// �û�������
				if (!userFile.exists()) {
					JOptionPane.showMessageDialog(loginFrame, "�û��˺Ų����ڣ�����ע�ᣡ");
					userAccount.setText("");
					userPassword.setText("");
				}else{
				// ���û��ļ���������������
					try {
						FileInputStream readFile = new FileInputStream(userFile);
						ObjectInputStream readUser = new ObjectInputStream(readFile);
						
						user = (User) readUser.readObject();
						readUser.close();
						
						System.out.println("�û����˺ţ� " + user.getUserAccountNumber());
						System.out.println("�û������룺" + user.getUserPassword());
						System.out.println("�û���������룺" + String.valueOf(userPassword.getPassword()));
						// ��ȡ�û������������бȽ�
						
						if (String.valueOf(userPassword.getPassword()).equals(user.getUserPassword())) {
							JOptionPane.showMessageDialog(loginFrame, "��¼�ɹ�");
							// ��ת�����ܲ˵�����
							
							
						}else {
							JOptionPane.showMessageDialog(loginFrame, "����������������룡");
							userPassword.setText(""); // ������������ÿ�
						}
						
					} catch (IOException | ClassNotFoundException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		
		// ���ע�ᰴť   ��תע�����
		registeredButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loginFrame.setVisible(false); 			// ��¼������ʧ
				AtmRegisteredInterface registeredInterface = new AtmRegisteredInterface(getBankName());		  // ����ע�����
				// registeredInterface.bankName = getBankName();   // �������ƴ���ע�����
				// registeredInterface.carId = getCardId();		// ����ע�Ῠ�Ŵ���ע�����
				registeredInterface.registeredInterface();		// չʾ�û���¼����
				
			}
		});
		
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
