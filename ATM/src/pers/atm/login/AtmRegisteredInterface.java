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
		
		// 注册信息提醒
		JPanel noteJPanel = new JPanel();
		noteJPanel.add(new JLabel("Please enter the following information:"));
		
		// 银行名字
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

		
		// 用户姓名
		JPanel userNameJPanel = new JPanel();
		JTextField userName = new JTextField(20);
		JLabel userNameJLabel = new JLabel("UserName:", JLabel.CENTER);
		userNameJLabel.setPreferredSize(new Dimension(120,20));
		userNameJPanel.add(userNameJLabel);
		userNameJPanel.add(userName);
		
		// 用户账号
		JPanel userAccountJPanel = new JPanel();
		JTextField userAccount = new JTextField(20);
		userAccount.setDocument(new NumberLenghtLimitedDmt(14));	// 只能输入7位数字
		JLabel userAccountJLabel = new JLabel("UserAccount:", JLabel.CENTER);
		userAccountJLabel.setPreferredSize(new Dimension(120, 20));
		userAccountJPanel.add(userAccountJLabel);
		userAccountJPanel.add(userAccount);
		
		// 用户密码
		JPanel userPasswordJPanel = new JPanel();
		JPasswordField userPassword = new JPasswordField(20);
		JLabel userPasswordJLabel = new JLabel("UserPassword:", JLabel.CENTER);
		userPasswordJLabel.setPreferredSize(new Dimension(120, 20));
		userPasswordJPanel.add(userPasswordJLabel);
		userPasswordJPanel.add(userPassword);
		
		// 再次确认密码
		JPanel userPasswordAgainJPanel = new JPanel();
		JPasswordField userPasswordAgain = new JPasswordField(20);
		JLabel userPasswordAgainJLabel = new JLabel("ConfirmPassword:    ", JLabel.RIGHT);
		userPasswordAgainJPanel.add(userPasswordAgainJLabel);
		userPasswordAgainJPanel.add(userPasswordAgain);
		
		// 提交 和 取消按钮
		JPanel btnJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okButton = new JButton("ok");
		JButton cancleButton = new JButton("cancle");
		btnJPanel.add(okButton);
		btnJPanel.add(cancleButton);
		
		// 注册界面
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
		
		registeredFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		registeredFrame.add(verticall);
		registeredFrame.setVisible(true);				// 显示可见
		registeredFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		registeredFrame.setSize(500, 320);				// 界面大小设置
		registeredFrame.setLocationRelativeTo(null);
		
		// 用户点击OK按钮时
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String bankNameString = allBankNamesJComboBox.getSelectedItem().toString();
				String userNameString =  userName.getText().toString();
				String userAccountNumberString = userAccount.getText().toString();
				String userPasswordString = new String(userPassword.getPassword());
				String userPasswAgainString = new String(userPasswordAgain.getPassword());
				// 判断用户输入是否为空，以及密码两次输入是否正确
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
				
				// 对象文件
				SetAndGetDataFile userfile = new SetAndGetDataFile();	
				
				// 判断用户文件是否存在，不存在则为用户创建文件，注册成功
				if (userfile.readUserInputFile(userAccountNumberString) == null) {
					
					//System.out.println(allBankNamesJComboBox.getSelectedItem());
					
					User newUser = new User(		// 创建用户对象
							bankNameString, 
							userNameString, 
							userAccountNumberString, 
							userPasswordString, 
							100.0,
							0.0);
					
					// 写入文件保存
					userfile.saveObjectOutputFile(newUser);
					
					JOptionPane.showMessageDialog(registeredFrame, "Registration succeeded!");
					registeredFrame.setVisible(false);  // 注册界面关闭
					AtmLoginInterfane login = new AtmLoginInterfane(bankName);
					login.loginInterface();// 返回登录界面
				}else {
					JOptionPane.showMessageDialog(registeredFrame, "User already exists!");
				}
			}
		});
		
		// 当用户点击取消时
		cancleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				registeredFrame.setVisible(false);   // 注册界面关闭
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				login.loginInterface();// 返回登录界面
			}
		});
		
	}

}
