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
	
	// 用户登录界面
	public AtmLoginInterfane()
	{
		loginFrame = new JFrame("登录ATM"); 
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 登录logo
		JPanel bankNamePanel = new JPanel();
		bankNamePanel.add(new JLabel("面包裸贷银行"));
		
		// 用户账号输入框
		JPanel userAccountPanle = new JPanel();
		userAccountPanle.add(new Label("Account:    "));
		JTextField userAccount = new JTextField(20);
		userAccountPanle.add(userAccount);
		
		// 用户密码输入框
		JPanel userPasswordPanel = new JPanel();
		userPasswordPanel.add(new Label("Password:"));
		JPasswordField userPassword = new JPasswordField(20);
		userPasswordPanel.add(userPassword);
		
		// 用户登录按钮和注册按钮
		JPanel btnPanel = new JPanel();
		JButton loginButton = new JButton("Login");
		JButton registeredButton = new JButton("Registered");
		btnPanel.add(loginButton);
		btnPanel.add(registeredButton);
		
		// 登入界面	
		Box verticall = Box.createVerticalBox();

		verticall.add(bankNamePanel);
		verticall.add(userAccountPanle);
		verticall.add(userPasswordPanel);
		verticall.add(btnPanel);

		loginFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		loginFrame.add(verticall);
		loginFrame.setVisible(true);				// 显示可见
		loginFrame.pack(); 				// 调整此窗口的大小，以适合其子组件的首选大小和布局
		loginFrame.setSize(500, 300);;	// 界面大小设置
		loginFrame.setLocationRelativeTo(null);
		
		
		// 点击登录按钮
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 点击注册按钮
		registeredButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loginFrame.setVisible(false); // 登录界面消失
				registeredBankCard();		  // 创建注册界面
			}
		});
		
	}
	
	// 检查用户密码
	public void CheckPassword()
	{
		
	}
	
	// 用户注册
	public User registeredBankCard(){
		
		new AtmRegisteredInterface();
		return null;
		
	}
}
