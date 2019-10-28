package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.user.User;
import pers.atm.useroperation.savemoney.SaveCashMoney;
import pers.atm.useroperation.savemoney.SaveCheckMoney;

public class SaveMoney {
	private JFrame saveMoneyJFrame;
	private User user;
	private String bankName;
	
	public SaveMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void SaveMoneyInterface()
	{
		saveMoneyJFrame = new JFrame(user.getUserName() + " SaveMoney");
		saveMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户提示信息
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select server"));
		
		// 现金存款按钮 和 支票存款按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton cashJButton = new JButton("Cash Deposit");
		JButton checkJButton = new JButton("Check Deposit");
		buttonJPanel.add(cashJButton);
		buttonJPanel.add(checkJButton);
		
		// 返回按钮
		JPanel backJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 20));
		JButton backJButton = new JButton("Cancle");
		backJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(40));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(buttonJPanel);
		verticall.add(Box.createVerticalStrut(60));			// 创建一个不可见的固定高度的组件 撑开内容

		verticall.add(backJPanel);
		
		
		saveMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		saveMoneyJFrame.add(verticall);
		saveMoneyJFrame.setVisible(true);				// 显示可见
		saveMoneyJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		saveMoneyJFrame.setSize(500, 320);				// 界面大小设置
		saveMoneyJFrame.setLocationRelativeTo(null);
		
		// 设置监听事件
		// 现金存款
		cashJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				saveMoneyJFrame.setVisible(false); // 隐藏此界面
			 	new SaveCashMoney(user, bankName).cashDepositInterface(); // 创建现金存款界面
			}
		});
		
		// 支票存款
		checkJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				saveMoneyJFrame.setVisible(false); // 隐藏此界面
				new SaveCheckMoney(user, bankName).checkDepositInterface(); // 创建支票存款界面
			}
		});
		
		// 返回按钮
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				saveMoneyJFrame.setVisible(false); // 隐藏此界面

				// 返回操作界面
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
				}
			}
		});
	
	}
	
	
}
