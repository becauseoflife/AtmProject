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

	// 用户登录界面
	public void loginInterface()
	{
		loginJFrame = new JFrame("登录ATM"); 
		loginJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 登录logo
		JPanel bankNamePanel = new JPanel();
		bankNamePanel.add(new JLabel(bankName));
		
		// 用户账号输入框
		JPanel userAccountPanle = new JPanel();
		JLabel userAccountJLabel = new JLabel("Account:", JLabel.CENTER);
		userAccountJLabel.setPreferredSize(new Dimension(120, 20));
		userAccountPanle.add(userAccountJLabel);
		JTextField userAccount = new JTextField(20);
		userAccount.setDocument(new NumberLenghtLimitedDmt(7));	// 只能输入7位数字
		userAccountPanle.add(userAccount);
		
		// 用户密码输入框
		JPanel userPasswordPanel = new JPanel();
		JLabel userPasswordJLabel = new JLabel("Password:", JLabel.CENTER);
		userPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userPasswordPanel.add(userPasswordJLabel);
		JPasswordField userPassword = new JPasswordField(20);
		userPasswordPanel.add(userPassword);
		
		// 用户登录按钮和注册按钮
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
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

		loginJFrame.setLayout(new BorderLayout());		// 设置流式布局
		loginJFrame.add(verticall, BorderLayout.CENTER);
		loginJFrame.setVisible(true);				// 显示可见
		loginJFrame.pack(); 				// 调整此窗口的大小，以适合其子组件的首选大小和布局
		loginJFrame.setSize(500, 300);;	// 界面大小设置
		loginJFrame.setLocationRelativeTo(null);
		
		// 点击登录按钮 	 检查用户密码
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String inputAccuontString = userAccount.getText();
				String inputPasswordString = new String(userPassword.getPassword());
				
				// 判断用户输入是否为空
				if (inputAccuontString.equals("")) {
					JOptionPane.showMessageDialog(loginJFrame, "请输入账号！");
					return;
				}
				else if (inputPasswordString.equals("")) {
					JOptionPane.showMessageDialog(loginJFrame, "请输入密码！");
					return;
				}
				
				//检查用户账号密码
				SetAndGetDataFile useroutputFile = new SetAndGetDataFile();
				// 用户不存在
				if (useroutputFile.readUserInputFile(inputAccuontString) == null) {
					JOptionPane.showMessageDialog(loginJFrame, "用户账号不存在，请先注册！");
					// 账号密码输入框置空
					userAccount.setText("");
					userPassword.setText("");
				}else{
				// 打开用户文件，检测输入的密码
					User user = useroutputFile.readUserInputFile(inputAccuontString);
					

					// System.out.println("用户的账号： " + user.getUserAccountNumber());
					// System.out.println("用户的密码：" + user.getUserPassword());
					// System.out.println("用户输入的密码：" + String.valueOf(userPassword.getPassword()));
					
					// 获取用户输入的密码进行比较
					
					if (String.valueOf(userPassword.getPassword()).equals(user.getUserPassword())) {
						JOptionPane.showMessageDialog(loginJFrame, "登录成功");
						// 账号密码输入框置空
						userAccount.setText("");
						userPassword.setText("");
						
						loginJFrame.setVisible(false);  // 隐藏登录界面
						// 跳转至功能菜单界面
						ThisBankClientMenu menu = new ThisBankClientMenu(user, bankName);
						menu.setThisBankMenu();
						
					}else {
						JOptionPane.showMessageDialog(loginJFrame, "密码错误，请重新输入！");
						userPassword.setText(""); // 将密码输入框置空
					}
				}
			}
		});
		
		// 点击注册按钮   跳转注册界面
		registeredButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loginJFrame.setVisible(false); 			// 登录界面消失
				AtmRegisteredInterface registeredInterface = new AtmRegisteredInterface(bankName);		  // 创建注册界面
				// registeredInterface.bankName = getBankName();   // 银行名称传给注册界面
				// registeredInterface.carId = getCardId();		// 银行注册卡号传给注册界面
				registeredInterface.registeredInterface();		// 展示用户登录界面
				
			}
		});
		
	}

}
