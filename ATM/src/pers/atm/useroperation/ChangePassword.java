package pers.atm.useroperation;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
		changePsdJFrame = new JFrame(user.getUserAccountNumber() + "ChangePassword");
		changePsdJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�ԭ����
		JPanel useroldpasswordJPanel = new JPanel();
		JTextField userOldPassword = new JTextField(20);
		JLabel userOldpsdJLabel = new JLabel("Old Password:", JLabel.CENTER);
		userOldpsdJLabel.setPreferredSize(new Dimension(120, 20));
		useroldpasswordJPanel.add(userOldpsdJLabel);
		useroldpasswordJPanel.add(userOldPassword);
		
		// �û�������
		JPanel userNewPasswordJPanel = new JPanel();
		JPasswordField userNewPassword = new JPasswordField(20);
		JLabel userNewPasswordJLabel = new JLabel("New Password:", JLabel.CENTER);
		userNewPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userNewPasswordJPanel.add(userNewPasswordJLabel);
		userNewPasswordJPanel.add(userNewPassword);
		
		// �ٴ�ȷ��������
		JPanel userNewPasswordAgainJPanel = new JPanel();
		JPasswordField userNewPasswordAgain = new JPasswordField(20);
		JLabel userNewPasswordAgainJLabel = new JLabel("New Password Again:", JLabel.CENTER);
		userNewPasswordAgainJPanel.add(userNewPasswordAgainJLabel);
		userNewPasswordAgainJPanel.add(userNewPasswordAgain);
		
		// �ύ �� ȡ����ť
		JPanel btnJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
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
		verticall.add(Box.createVerticalStrut(20));
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
					JOptionPane.showMessageDialog(changePsdJFrame, "������ԭ���룡");
					return;
				}
				else if (userNewPassword.getPassword().equals("")) {
					JOptionPane.showMessageDialog(changePsdJFrame, "�����������룡");
					return;
				}
				else if (userNewPasswordAgain.getPassword().equals("")) {
					JOptionPane.showMessageDialog(changePsdJFrame, "���ٴ�ȷ�����룡");
					return;
				}
				
				String newPasswordString = new String(userNewPassword.getPassword());
				String newPasswordAgainString = new String(userNewPasswordAgain.getPassword());
				
				// �ж�ԭ���������Ƿ���ȷ
				if (!user.getUserPassword().equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "ԭ����������������룡");
					userOldPassword.setText("");
					return;
				}
				
				// �ж�ԭ�����Ƿ��������һ��
				if (String.valueOf(newPasswordString).equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "ԭ����������벻����ͬ�����������룡");
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
					JOptionPane.showMessageDialog(changePsdJFrame, "���ĳɹ���");
					changePsdJFrame.setVisible(false);  // ���ش˽���
					// ���ز�������
					new ThisBankClientMenu(user, bankName).setThisBankMenu();
					
				}else{
					JOptionPane.showMessageDialog(changePsdJFrame, "������������벻һ�������������룡");
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
