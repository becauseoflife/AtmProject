package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.MyAtm;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class PutMoneyIntoAtm {
	private JFrame putMoneIntoAtmJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public PutMoneyIntoAtm(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void putMoneyIntoAtmInterface()
	{
		putMoneIntoAtmJFrame = new JFrame(bankStaff.getBankStaffId() + " CashDeposit");
		putMoneIntoAtmJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入金额框
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the amount to be deposited:"));
		JTextField inputMoneyJTextField = new JTextField(15);
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(10));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		
		putMoneIntoAtmJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		putMoneIntoAtmJFrame.add(verticall);
		putMoneIntoAtmJFrame.setVisible(true);				// 显示可见
		putMoneIntoAtmJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		putMoneIntoAtmJFrame.setSize(500, 320);				// 界面大小设置
		putMoneIntoAtmJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "Please enter the amount!");
					return;
				}
				Double cashMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// 获取ATM信息
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				MyAtm atm  = atmFile.readObjectInputFile(bankName);
				
				// 更改ATM可提取的金额
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// 保存操作信息
				String opString = "Deposit " + cashMoney + " cash";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// 返回操作界面
				JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "successed!");
				putMoneIntoAtmJFrame.setVisible(false);
				
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
		// 返回
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				putMoneIntoAtmJFrame.setVisible(false);
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
