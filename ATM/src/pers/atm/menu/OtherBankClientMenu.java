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
import pers.atm.user.User;
import pers.atm.useroperation.CheckBalances;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.TransferMoney;
import pers.atm.useroperation.WithdrawMoney;

public class OtherBankClientMenu {
	private JFrame otherBankMenuJFrame;
	private User user;
	private String bankName;

	public OtherBankClientMenu(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	public void setOtherBankMenu(){
		otherBankMenuJFrame = new JFrame(bankName + " Operations");
		otherBankMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//标签
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// 功能按钮
		JButton checkMoneyJButton = new JButton("Check Balance");
		JButton saveMoneyJButton = new JButton("Save Money");
		JButton getMoneyJButton = new JButton("Withdraw Money");
		JButton transferMoneyJButton = new JButton("Transfer Money");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// 功能按钮面板
		JPanel menuJPanel = new JPanel();
		
		menuJPanel.add(checkMoneyJButton);
		menuJPanel.add(saveMoneyJButton);
		menuJPanel.add(getMoneyJButton);
		menuJPanel.add(transferMoneyJButton);
		menuJPanel.add(new JLabel());			// 加入空组件，占用格子
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(4, 2, 30, 30));
		
		// 垂直布局
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(menuJPanel);
		
		otherBankMenuJFrame.add(verticall);
		otherBankMenuJFrame.setLayout(new FlowLayout());
		otherBankMenuJFrame.setVisible(true);  			 // 显示可见
		otherBankMenuJFrame.pack();						// 调整此窗口的大小，以适合其子组件的首选大小和布局
		otherBankMenuJFrame.setSize(500, 340);
		otherBankMenuJFrame.setLocationRelativeTo(null);
		
		// 注册按钮监听事件
		
		// 查看余额功能
		checkMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				otherBankMenuJFrame.setVisible(false);  // 隐藏此界面
				// 创建余额查询界面
				new CheckBalances(user, bankName).CheckMoneyInterface();	
			}
		});
		
		// 存钱功能
		saveMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				otherBankMenuJFrame.setVisible(false);  // 隐藏此界面
				// 创建存钱界面
				new SaveMoney(user, bankName).SaveMoneyInterface();
			}
		});
		
		// 取钱功能
		getMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				otherBankMenuJFrame.setVisible(false);  // 隐藏此界面
				// 创建取钱界面
				new WithdrawMoney(user, bankName).outMoneyInterface();
			}
		});
		
		// 转账功能
		transferMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				otherBankMenuJFrame.setVisible(false);  // 隐藏此界面
				// 创建转账界面
				new TransferMoney(user, bankName).TransferMoneyInterface();
			}
		});
		
		// 退出登录
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				otherBankMenuJFrame.setVisible(false);  // 隐藏功能界面
				login.loginInterface();     // 显示登录界面
			}
		});
		
	}

}
