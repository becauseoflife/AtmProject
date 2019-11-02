package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
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
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(8));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		// 设置便捷输入按钮
		JPanel inputBtnJPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		String buttonNameString[] = { "10000", "20000", "30000", "50000", "80000", "100000" };
		
		JButton buttons[] = new JButton[buttonNameString.length];
		for (int i = 0; i < buttonNameString.length; i++) {
			buttons[i] = new JButton(buttonNameString[i]);
			buttons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					inputMoneyJTextField.setText(e.getActionCommand());
				}
			});
			inputBtnJPanel.add(buttons[i]);
		}
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
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
				
				// 判断是否输入为0
				if (cashMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				// 判断输入是否为整百
				if (cashMoney % 100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 获取ATM信息
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// 更改ATM可提取的金额
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// 保存操作信息
				String opString = "Deposit " + cashMoney + " cash";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// 提示成功
				JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "successed!");
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + bankName + " of User\n" +
												"Bank Staff Account Number:\t" 	+ bankStaff.getBankStaffAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ cashMoney			 + "\n" +
												"ATM balance:\t\t" 				+ atm.getAtmMoney()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				putMoneIntoAtmJFrame.setVisible(false);
				new PrintCopy(bankStaff, bankName, printCopyContextString).printCopyInterface();
				
/*				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();*/
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
