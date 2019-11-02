package pers.atm.login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class AtmRegisteredInterface {
	private JFrame registeredFrame;
	private String bankName;
	private String[] allBankNames = {"China Merchants Bank", "China Construction Bank", "Agricultural Bank of China" };

	public AtmRegisteredInterface(String bankName) {
		super();
		this.bankName = bankName;
	}

	public void registeredInterface(){
		registeredFrame = new JFrame("registered");
		registeredFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ע����Ϣ����
		JPanel noteJPanel = new JPanel();
		noteJPanel.add(new JLabel("Please enter the following information:"));
		
		// ��������
		JPanel bankNameJPanel = new JPanel(new FlowLayout());
		JComboBox<String> allBankNamesJComboBox = new JComboBox<String>();
		allBankNamesJComboBox.addItem(bankName);
		for (int i = 0; i < allBankNames.length; i++) {
			allBankNamesJComboBox.addItem(allBankNames[i]);
		}
		allBankNamesJComboBox.setMaximumRowCount(allBankNames.length + 1);
		allBankNamesJComboBox.setPreferredSize(new Dimension(222,20));
		
		JLabel bankNameJLabel = new JLabel("         BankName:         ");
		bankNameJPanel.setPreferredSize(new Dimension(120, 30));
		bankNameJPanel.add(bankNameJLabel);
		bankNameJPanel.add(allBankNamesJComboBox);

		
		// �û�����
		JPanel userNameJPanel = new JPanel();
		JTextField userName = new JTextField(20);
		JLabel userNameJLabel = new JLabel("UserName:", JLabel.CENTER);
		userNameJLabel.setPreferredSize(new Dimension(120,20));
		userNameJPanel.add(userNameJLabel);
		userNameJPanel.add(userName);
		
		// �û��˺�
		JPanel userAccountJPanel = new JPanel();
		JTextField userAccount = new JTextField(20);
		userAccount.setDocument(new NumberLenghtLimitedDmt(14));	// ֻ������7λ����
		JLabel userAccountJLabel = new JLabel("UserAccount:", JLabel.CENTER);
		userAccountJLabel.setPreferredSize(new Dimension(120, 20));
		userAccountJPanel.add(userAccountJLabel);
		userAccountJPanel.add(userAccount);
		
		// �û�����
		JPanel userPasswordJPanel = new JPanel();
		JPasswordField userPassword = new JPasswordField(20);
		JLabel userPasswordJLabel = new JLabel("UserPassword:", JLabel.CENTER);
		userPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userPasswordJPanel.add(userPasswordJLabel);
		userPasswordJPanel.add(userPassword);
		
		// �ٴ�ȷ������
		JPanel userPasswordAgainJPanel = new JPanel();
		JPasswordField userPasswordAgain = new JPasswordField(20);
		JLabel userPasswordAgainJLabel = new JLabel("ConfirmPassword:    ", JLabel.RIGHT);
		userPasswordAgainJPanel.add(userPasswordAgainJLabel);
		userPasswordAgainJPanel.add(userPasswordAgain);
		
		// �ύ �� ȡ����ť
		JPanel btnJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okButton = new JButton("ok");
		JButton cancleButton = new JButton("cancle");
		btnJPanel.add(okButton);
		btnJPanel.add(cancleButton);
		
		// ע�����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(noteJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(bankNameJPanel);
		verticall.add(userNameJPanel);
		verticall.add(userAccountJPanel);
		verticall.add(userPasswordJPanel);
		verticall.add(userPasswordAgainJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(btnJPanel);
		
		registeredFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		registeredFrame.add(verticall);
		registeredFrame.setVisible(true);				// ��ʾ�ɼ�
		registeredFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		registeredFrame.setSize(500, 320);				// �����С����
		registeredFrame.setLocationRelativeTo(null);
		
		// �û����OK��ťʱ
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String bankNameString = allBankNamesJComboBox.getSelectedItem().toString();
				String userNameString =  userName.getText().toString();
				String userAccountNumberString = userAccount.getText().toString();
				String userPasswordString = new String(userPassword.getPassword());
				String userPasswAgainString = new String(userPasswordAgain.getPassword());
				// �ж��û������Ƿ�Ϊ�գ��Լ��������������Ƿ���ȷ
				if (userName.getText().equals("")) {
					JOptionPane.showMessageDialog(registeredFrame, "User name cannot be empty!");
					return;
				}
				else if (userAccountNumberString.equals("")) {
					JOptionPane.showMessageDialog(registeredFrame, "User account cannot be empty!");
					return;
				}
				else if (userPasswordString.equals("")) {
					JOptionPane.showMessageDialog(registeredFrame, "User password cannot be empty!");
					return;
				}
				else if (userPasswAgainString.equals("")) {
					JOptionPane.showMessageDialog(registeredFrame, "Reconfirm password cannot be empty!");
					return;
				}
				else if (!userPasswAgainString.equals(userPasswordString)) {
					JOptionPane.showMessageDialog(registeredFrame, "The two passwords are different! Please re-enter!");
					userPassword.setText("");
					userPasswordAgain.setText("");
					return;
				}
				
				// �����ļ�
				SetAndGetDataFile userfile = new SetAndGetDataFile();	
				
				// �ж��û��ļ��Ƿ���ڣ���������Ϊ�û������ļ���ע��ɹ�
				if (userfile.readUserInputFile(userAccountNumberString) == null) {
					
					//System.out.println(allBankNamesJComboBox.getSelectedItem());
					
					User newUser = new User(		// �����û�����
							bankNameString, 
							userNameString, 
							userAccountNumberString, 
							userPasswordString, 
							100.0,
							0.0);
					
					// д���ļ�����
					userfile.saveObjectOutputFile(newUser);
					
					JOptionPane.showMessageDialog(registeredFrame, "Registration succeeded!");
					registeredFrame.setVisible(false);  // ע�����ر�
					AtmLoginInterfane login = new AtmLoginInterfane(bankName);
					login.loginInterface();// ���ص�¼����
				}else {
					JOptionPane.showMessageDialog(registeredFrame, "User already exists!");
				}
			}
		});
		
		// ���û����ȡ��ʱ
		cancleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				registeredFrame.setVisible(false);   // ע�����ر�
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				login.loginInterface();// ���ص�¼����
			}
		});
		
	}

}
