package pers.atm.useroperation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ChangePassword {
	private JFrame changePsdJFrame;
	private User user;
	private String bankName;
	
	public ChangePassword(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void ChangPasswordInterface() {
		changePsdJFrame = new JFrame(user.getUserAccountNumber() + " ChangePassword");
		changePsdJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�ԭ����
		JPanel useroldpasswordJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JTextField userOldPassword = new JTextField(20);
		JLabel userOldpsdJLabel = new JLabel("Old Password:", JLabel.CENTER);
		userOldpsdJLabel.setPreferredSize(new Dimension(120, 20));
		useroldpasswordJPanel.add(userOldpsdJLabel);
		useroldpasswordJPanel.add(userOldPassword);
		
		// �û�������
		JPanel userNewPasswordJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPasswordField userNewPassword = new JPasswordField(20);
		JLabel userNewPasswordJLabel = new JLabel("New Password:", JLabel.CENTER);
		userNewPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userNewPasswordJPanel.add(userNewPasswordJLabel);
		userNewPasswordJPanel.add(userNewPassword);
		
		// �ٴ�ȷ��������
		JPanel userNewPasswordAgainJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPasswordField userNewPasswordAgain = new JPasswordField(20);
		JLabel userNewPasswordAgainJLabel = new JLabel("Confirm new password:", JLabel.CENTER);
		userNewPasswordAgainJPanel.add(userNewPasswordAgainJLabel);
		userNewPasswordAgainJPanel.add(userNewPasswordAgain);
		
		// �ύ �� ȡ����ť
		JPanel btnJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okButton = new JButton("Change");
		JButton cancleButton = new JButton("Cancle");
		btnJPanel.add(okButton);
		btnJPanel.add(cancleButton);
		
		// ע�����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(useroldpasswordJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(userNewPasswordJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(userNewPasswordAgainJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(btnJPanel);
		
		changePsdJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		changePsdJFrame.add(verticall);
		changePsdJFrame.setVisible(true);				// ��ʾ�ɼ�
		changePsdJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		changePsdJFrame.setSize(500, 320);				// �����С����
		changePsdJFrame.setLocationRelativeTo(null);
		
		// ���ü����¼�
		// �޸İ�ť
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// ����û��Ƿ�����Ϊ�� 
				if (userOldPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(changePsdJFrame, "Please input the original password!");
					return;
				}
				else if (userNewPassword.getPassword().equals("")) {
					JOptionPane.showMessageDialog(changePsdJFrame, "Please enter a new password!");
					return;
				}
				else if (userNewPasswordAgain.getPassword().equals("")) {
					JOptionPane.showMessageDialog(changePsdJFrame, "Please confirm the new password again!");
					return;
				}
				
				String newPasswordString = new String(userNewPassword.getPassword());
				String newPasswordAgainString = new String(userNewPasswordAgain.getPassword());
				
				// �ж�ԭ���������Ƿ���ȷ
				if (!user.getUserPassword().equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "The original password is wrong! Please re-enter!");
					userOldPassword.setText("");
					return;
				}
				
				// �ж�ԭ�����Ƿ��������һ��
				if (String.valueOf(newPasswordString).equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "The original password and the new password cannot be the same!\n Please re-enter!");
					userNewPassword.setText("");
					userNewPasswordAgain.setText("");
					return;
				}
				
				// �ж����������Ƿ�����һ��
				if (newPasswordString.equals(newPasswordAgainString)) {
					// ��������
					user.setUserPassword(newPasswordString);
					
					// �����û��ļ�
					SetAndGetDataFile uFlie = new SetAndGetDataFile();
					uFlie.updateObjectOutputFile(user);
					
					// �ɹ�������
					JOptionPane.showMessageDialog(changePsdJFrame, "Change succeeded!");
					changePsdJFrame.setVisible(false);  // ���ش˽���
					// ���ز�������
					new ThisBankClientMenu(user, bankName).setThisBankMenu();
					
				}else{
					JOptionPane.showMessageDialog(changePsdJFrame, "The two passwords are different! Please re-enter!");
					userNewPassword.setText("");
					userNewPasswordAgain.setText("");
					return;
				}

			}
		});
		
		// ȡ����ť
		cancleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				changePsdJFrame.setVisible(false);  // ���ش˽���
				// ���ز�������
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
	}
}
