package pers.atm.bankstaffoparetion;

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
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;

public class CheckAtmBalance {
	private JFrame checkAtmBalancesJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public CheckAtmBalance(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	public void checkAtmMoneyInterface()
	{
		// 获取ATM信息
		SetAndGetDataFile atmFile = new SetAndGetDataFile();
		Atm atm  = atmFile.readObjectInputFile(bankName);
		
		checkAtmBalancesJFrame = new JFrame(bankStaff.getBankStaffId() + " CheckBanlances");
		checkAtmBalancesJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 授权人员id显示
		JPanel bankStaffNameJPanel = new JPanel();
		bankStaffNameJPanel.add(new JLabel("Authorized person Id：" + bankStaff.getBankStaffId()));
		
		// ATM余额和打印纸显示
		JPanel atmMoneyInfoJPanel = new JPanel();
		JTextArea atmMoneyInfoJTextArea = new JTextArea(6, 30);
		
		String atmInfoString = "\n\nThe balance of the ATM is:\t" + atm.getAtmMoney() + "\n" +
									"The printing paper of ATM is:\t" + atm.getAtmPaper() + "\n";
		
		atmMoneyInfoJTextArea.setText(atmInfoString);
		atmMoneyInfoJTextArea.setEditable(false);
		atmMoneyInfoJPanel.add(atmMoneyInfoJTextArea);
		
		// 返回和打印按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(bankStaffNameJPanel);
		verticall.add(atmMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		checkAtmBalancesJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		checkAtmBalancesJFrame.add(verticall);
		checkAtmBalancesJFrame.setVisible(true);				// 显示可见
		checkAtmBalancesJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		checkAtmBalancesJFrame.setSize(500, 320);				// 界面大小设置
		checkAtmBalancesJFrame.setLocationRelativeTo(null);
		
		// 按钮监听事件
		// 打印按钮监听事件
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 判断打印纸是否足够
				if (atm.getAtmPaper() > 0) {
					// 更新ATM机的打印纸
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// 更新ATM文件
					atmFile.updateObjectOutputFile(atm);
					
					// 提示成功信息
					JOptionPane.showMessageDialog(null, "Print successfully!");
					
					checkAtmBalancesJFrame.setVisible(false); // 隐藏当前窗口
					// 返回操作界面
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
				}else {
					// 提示打印纸不足 不能打印
					JOptionPane.showMessageDialog(null, "Sorry, No paper!");
				}
			}
		});
		
		// 返回窗口监听事件
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				checkAtmBalancesJFrame.setVisible(false); // 隐藏当前窗口
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
