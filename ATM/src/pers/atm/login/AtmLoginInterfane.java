package pers.atm.login;

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

import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class AtmLoginInterfane {

	private JFrame loginJFrame;
	private String bankName;
	
	public AtmLoginInterfane(String bankName) {
		super();
		this.bankName = bankName;
	}

	// �û���¼����
	public void loginInterface()
	{
		loginJFrame = new JFrame("��¼ATM"); 
		loginJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ��¼logo
		JPanel bankNamePanel = new JPanel();
		bankNamePanel.add(new JLabel(bankName));
		
		// �û��˺������
		JPanel userAccountPanle = new JPanel();
		JLabel userAccountJLabel = new JLabel("Account:", JLabel.CENTER);
		userAccountJLabel.setPreferredSize(new Dimension(120, 20));
		userAccountPanle.add(userAccountJLabel);
		JTextField userAccount = new JTextField(20);
		userAccount.setDocument(new NumberLenghtLimitedDmt(7));	// ֻ������7λ����
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

		loginJFrame.setLayout(new BorderLayout());		// ������ʽ����
		loginJFrame.add(verticall, BorderLayout.CENTER);
		loginJFrame.setVisible(true);				// ��ʾ�ɼ�
		loginJFrame.pack(); 				// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		loginJFrame.setSize(500, 300);;	// �����С����
		loginJFrame.setLocationRelativeTo(null);
		
		// �����¼��ť 	 ����û�����
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String inputAccuontString = userAccount.getText();
				String inputPasswordString = new String(userPassword.getPassword());
				
				// �ж��û������Ƿ�Ϊ��
				if (inputAccuontString.equals("")) {
					JOptionPane.showMessageDialog(loginJFrame, "�������˺ţ�");
					return;
				}
				else if (inputPasswordString.equals("")) {
					JOptionPane.showMessageDialog(loginJFrame, "���������룡");
					return;
				}
				
				//����û��˺�����
				SetAndGetDataFile useroutputFile = new SetAndGetDataFile();
				// �û�������
				if (useroutputFile.readUserInputFile(inputAccuontString) == null) {
					JOptionPane.showMessageDialog(loginJFrame, "�û��˺Ų����ڣ�����ע�ᣡ");
					// �˺�����������ÿ�
					userAccount.setText("");
					userPassword.setText("");
				}else{
				// ���û��ļ���������������
					User user = useroutputFile.readUserInputFile(inputAccuontString);
					

					// System.out.println("�û����˺ţ� " + user.getUserAccountNumber());
					// System.out.println("�û������룺" + user.getUserPassword());
					// System.out.println("�û���������룺" + String.valueOf(userPassword.getPassword()));
					
					// ��ȡ�û������������бȽ�
					
					if (String.valueOf(userPassword.getPassword()).equals(user.getUserPassword())) {
						JOptionPane.showMessageDialog(loginJFrame, "��¼�ɹ�");
						// �˺�����������ÿ�
						userAccount.setText("");
						userPassword.setText("");
						
						loginJFrame.setVisible(false);  // ���ص�¼����
						// ��ת�����ܲ˵�����
						ThisBankClientMenu menu = new ThisBankClientMenu(user, bankName);
						menu.setThisBankMenu();
						
					}else {
						JOptionPane.showMessageDialog(loginJFrame, "����������������룡");
						userPassword.setText(""); // ������������ÿ�
					}
				}
			}
		});
		
		// ���ע�ᰴť   ��תע�����
		registeredButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loginJFrame.setVisible(false); 			// ��¼������ʧ
				AtmRegisteredInterface registeredInterface = new AtmRegisteredInterface(bankName);		  // ����ע�����
				// registeredInterface.bankName = getBankName();   // �������ƴ���ע�����
				// registeredInterface.carId = getCardId();		// ����ע�Ῠ�Ŵ���ע�����
				registeredInterface.registeredInterface();		// չʾ�û���¼����
				
			}
		});
		
	}

}
