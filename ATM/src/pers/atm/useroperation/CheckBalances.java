package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.MyAtm;
import pers.atm.user.User;

public class CheckBalances {
	private JFrame checkBalancesJFrame;
	private User user;
	private String bankName;
	
	public CheckBalances(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void CheckMoneyInterface()
	{
		// 获取ATM信息
		SetAndGetDataFile atmFile = new SetAndGetDataFile();
		MyAtm atm  = atmFile.readObjectInputFile(bankName);
		
		checkBalancesJFrame = new JFrame(user.getUserName() + " CheckBanlances");
		checkBalancesJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户名显示
		JPanel userNameJPanel = new JPanel();
		userNameJPanel.add(new JLabel("尊敬的用户：" + user.getUserName()));
		
		// 用户账号和余额显示
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(8, 30);
		
		String userMoneyInfoString = "\n\n您的账号：\t\t" + user.getUserAccountNumber() + "\n" +
								"可用余额为：\t\t" + user.getAvailableBalances() + "元\n" +
								"不可用余额为：\t\t" + user.getUnavailableBlances() + "元\n" +
								"ATM可取金额为：\t" + atm.getAtmMoney() + "元\n" ;
		
		userMoneyInfoJTextArea.setText(userMoneyInfoString);
		userMoneyInfoJTextArea.setEditable(false);
		userMoneyInfoJPanel.add(userMoneyInfoJTextArea);
		
		// 返回和打印按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(userNameJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(buttonJPanel);
		
		
		checkBalancesJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		checkBalancesJFrame.add(verticall);
		checkBalancesJFrame.setVisible(true);				// 显示可见
		checkBalancesJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		checkBalancesJFrame.setSize(500, 320);				// 界面大小设置
		checkBalancesJFrame.setLocationRelativeTo(null);
		
		// 按钮监听事件
		// 打印按钮监听事件
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		// 返回窗口监听事件
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				checkBalancesJFrame.setVisible(false); // 隐藏当前窗口
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
