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

import pers.atm.bankstaffoparetion.CheckAtmBalance;
import pers.atm.bankstaffoparetion.PutMoneyIntoAtm;
import pers.atm.bankstaffoparetion.PutPrintPaperIntoAtm;
import pers.atm.bankstaffoparetion.ViewOperationRecord;
import pers.atm.login.AtmLoginInterfane;
import pers.atm.user.AuthorizedBankStaff;

public class BankStaffOperationMenu {
	private JFrame bankStaffOperationMenuJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;

	public BankStaffOperationMenu(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}



	public void setBankStaffOperationMenu(){
		bankStaffOperationMenuJFrame = new JFrame(bankStaff.getBankStaffId() + " Operations");
		bankStaffOperationMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//标签
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// 功能按钮
		JButton viewAtmBalanceJButton = new JButton("View ATM balance");
		JButton putMoneyJButton = new JButton("Put money into ATM");
		JButton putPrintPaperJButton = new JButton("Storing printing paper");
		JButton viewOperationRecordJButton = new JButton("View Operation record");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// 功能按钮面板
		JPanel menuJPanel = new JPanel();
		menuJPanel.add(viewAtmBalanceJButton);
		menuJPanel.add(putMoneyJButton);
		menuJPanel.add(putPrintPaperJButton);
		menuJPanel.add(viewOperationRecordJButton);
		menuJPanel.add(new JLabel());
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(3, 2, 30, 30));
		
		// 垂直布局
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// 创建一个不可见的固定高度的组件 撑开内容
		verticall.add(menuJPanel);
		
		bankStaffOperationMenuJFrame.add(verticall);
		bankStaffOperationMenuJFrame.setLayout(new FlowLayout());
		bankStaffOperationMenuJFrame.setVisible(true);  			 // 显示可见
		bankStaffOperationMenuJFrame.pack();						// 调整此窗口的大小，以适合其子组件的首选大小和布局
		bankStaffOperationMenuJFrame.setSize(500, 340);
		bankStaffOperationMenuJFrame.setLocationRelativeTo(null);
		
		// 注册按钮监听事件
		
		// 查看ATM余额功能
		viewAtmBalanceJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				bankStaffOperationMenuJFrame.setVisible(false);  // 隐藏此界面
				// 创建余额查询界面
				new CheckAtmBalance(bankStaff, bankName).checkAtmMoneyInterface();
			}
		});
		
		// 给ATM加钱功能
		putMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				bankStaffOperationMenuJFrame.setVisible(false);  // 隐藏此界面
				new PutMoneyIntoAtm(bankStaff, bankName).putMoneyIntoAtmInterface();
			}
		});
		
		// 加打印纸功能
		putPrintPaperJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				bankStaffOperationMenuJFrame.setVisible(false);  // 隐藏此界面
				new PutPrintPaperIntoAtm(bankStaff, bankName).putPaperIntoAtmInterface();
			}
		});
		
		// 查看操作记录功能
		viewOperationRecordJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				bankStaffOperationMenuJFrame.setVisible(false);  // 隐藏此界面
				// 查看操作记录界面
				new ViewOperationRecord(bankStaff, bankName).viewOperationsInterface();
			}
		});
		
		// 退出登录
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				bankStaffOperationMenuJFrame.setVisible(false);  // 隐藏功能界面
				login.loginInterface();     // 显示登录界面
			}
		});
		
	}
}
