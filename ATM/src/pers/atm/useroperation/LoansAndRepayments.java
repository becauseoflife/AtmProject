package pers.atm.useroperation;

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

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.user.User;
import pers.atm.useroperation.loan.Loan;
import pers.atm.useroperation.loan.Repayments;

public class LoansAndRepayments {
	private JFrame loanJFrame;
	private User user;
	private String bankName;
	
	public LoansAndRepayments(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void loansAndRepaymentsInterface()
	{
		loanJFrame = new JFrame(user.getUserName() + " Loans and Return loan");
		loanJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户提示信息
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select server"));
		
		// 贷款按钮 和 还款按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 60, 10));
		JButton loanJButton = new JButton("Loan");
		JButton returnLoanJButton = new JButton("Return loan");
		buttonJPanel.add(loanJButton);
		buttonJPanel.add(returnLoanJButton);
		
		// 返回按钮
		JPanel backJPanel = new JPanel(new GridLayout(1, 2, 60, 10));
		JButton backJButton = new JButton("Cancel");
		backJPanel.add(new JLabel());
		backJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(40));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(buttonJPanel);
		verticall.add(Box.createVerticalStrut(80));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(backJPanel);
		
		
		loanJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		loanJFrame.add(verticall);
		loanJFrame.setVisible(true);				// 显示可见
		loanJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		loanJFrame.setSize(500, 320);				// 界面大小设置
		loanJFrame.setLocationRelativeTo(null);
		
		// 设置监听事件
		// 贷款
		loanJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loanJFrame.setVisible(false); // 隐藏此界面
			 	// 打开贷款界面
				new Loan(user, bankName).loanInterface();
			}
		});
		
		// 还款
		returnLoanJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loanJFrame.setVisible(false); // 隐藏此界面
				// 打开还款界面
				new Repayments(user, bankName).repaymentsInterface();
			}
		});
		
		// 返回按钮
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loanJFrame.setVisible(false); // 隐藏此界面

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
