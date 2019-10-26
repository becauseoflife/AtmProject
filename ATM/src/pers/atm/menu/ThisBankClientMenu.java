package pers.atm.menu;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.login.AtmLoginInterfane;

public class ThisBankClientMenu {
	public JFrame thisBankMenuJFrame;
	
	public void setThisBankMenu(){
		thisBankMenuJFrame = new JFrame();
		thisBankMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//标签
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// 功能按钮
		JButton checkMoneyJButton = new JButton("Check Balance");
		JButton saveMoneyJButton = new JButton("Save Money");
		JButton getMoneyJButton = new JButton("Withdraw Money");
		JButton transferMoneyJButton = new JButton("Transfer Money");
		JButton changePasswordJButton = new JButton("Change Password");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// 功能按钮面板
		JPanel menuJPanel = new JPanel();
		
		menuJPanel.add(checkMoneyJButton);
		menuJPanel.add(saveMoneyJButton);
		menuJPanel.add(getMoneyJButton);
		menuJPanel.add(transferMoneyJButton);
		menuJPanel.add(changePasswordJButton);
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(3, 2, 30, 30));
		
		// 垂直布局
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(menuJPanel);
		
		thisBankMenuJFrame.add(verticall);
		thisBankMenuJFrame.setLayout(new FlowLayout());
		thisBankMenuJFrame.setVisible(true);  			 // 显示可见
		thisBankMenuJFrame.pack();						// 调整此窗口的大小，以适合其子组件的首选大小和布局
		thisBankMenuJFrame.setSize(500, 300);
		thisBankMenuJFrame.setLocationRelativeTo(null);
		
		// 注册按钮监听事件
		
		// 查看余额功能
		checkMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 存钱功能
		saveMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 取钱功能
		getMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 转账功能
		transferMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 更改密码
		changePasswordJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 退出登录
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				AtmLoginInterfane login = new AtmLoginInterfane();
				thisBankMenuJFrame.setVisible(false);  // 隐藏功能界面
				login.loginInterface();     // 显示登录界面
			}
		});
		
	}

}
