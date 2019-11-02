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
		
		// 用户原密码
		JPanel useroldpasswordJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JTextField userOldPassword = new JTextField(20);
		JLabel userOldpsdJLabel = new JLabel("Old Password:", JLabel.CENTER);
		userOldpsdJLabel.setPreferredSize(new Dimension(120, 20));
		useroldpasswordJPanel.add(userOldpsdJLabel);
		useroldpasswordJPanel.add(userOldPassword);
		
		// 用户新密码
		JPanel userNewPasswordJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPasswordField userNewPassword = new JPasswordField(20);
		JLabel userNewPasswordJLabel = new JLabel("New Password:", JLabel.CENTER);
		userNewPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userNewPasswordJPanel.add(userNewPasswordJLabel);
		userNewPasswordJPanel.add(userNewPassword);
		
		// 再次确认新密码
		JPanel userNewPasswordAgainJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPasswordField userNewPasswordAgain = new JPasswordField(20);
		JLabel userNewPasswordAgainJLabel = new JLabel("Confirm new password:", JLabel.CENTER);
		userNewPasswordAgainJPanel.add(userNewPasswordAgainJLabel);
		userNewPasswordAgainJPanel.add(userNewPasswordAgain);
		
		// 提交 和 取消按钮
		JPanel btnJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okButton = new JButton("Change");
		JButton cancleButton = new JButton("Cancle");
		btnJPanel.add(okButton);
		btnJPanel.add(cancleButton);
		
		// 注册界面
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(useroldpasswordJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(userNewPasswordJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(userNewPasswordAgainJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(btnJPanel);
		
		changePsdJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		changePsdJFrame.add(verticall);
		changePsdJFrame.setVisible(true);				// 显示可见
		changePsdJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		changePsdJFrame.setSize(500, 320);				// 界面大小设置
		changePsdJFrame.setLocationRelativeTo(null);
		
		// 设置监听事件
		// 修改按钮
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 检查用户是否输入为空 
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
				
				// 判断原密码输入是否正确
				if (!user.getUserPassword().equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "The original password is wrong! Please re-enter!");
					userOldPassword.setText("");
					return;
				}
				
				// 判断原密码是否和新密码一样
				if (String.valueOf(newPasswordString).equals(userOldPassword.getText())) {
					JOptionPane.showMessageDialog(changePsdJFrame, "The original password and the new password cannot be the same!\n Please re-enter!");
					userNewPassword.setText("");
					userNewPasswordAgain.setText("");
					return;
				}
				
				// 判断两次密码是否输入一样
				if (newPasswordString.equals(newPasswordAgainString)) {
					// 更新密码
					user.setUserPassword(newPasswordString);
					
					// 更新用户文件
					SetAndGetDataFile uFlie = new SetAndGetDataFile();
					uFlie.updateObjectOutputFile(user);
					
					// 成功并返回
					JOptionPane.showMessageDialog(changePsdJFrame, "Change succeeded!");
					changePsdJFrame.setVisible(false);  // 隐藏此界面
					// 返回操作界面
					new ThisBankClientMenu(user, bankName).setThisBankMenu();
					
				}else{
					JOptionPane.showMessageDialog(changePsdJFrame, "The two passwords are different! Please re-enter!");
					userNewPassword.setText("");
					userNewPasswordAgain.setText("");
					return;
				}

			}
		});
		
		// 取消按钮
		cancleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				changePsdJFrame.setVisible(false);  // 隐藏此界面
				// 返回操作界面
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
	}
}
