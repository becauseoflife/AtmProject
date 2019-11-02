package pers.atm.useroperation.savemoney;

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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.Atm;
import pers.atm.user.User;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class SaveCashMoney {
	private JFrame cashJFrame;
	private User user;
	private String bankName;
	
	public SaveCashMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void cashDepositInterface()
	{
		cashJFrame = new JFrame(user.getUserName() + " CashDeposit");
		cashJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入金额框
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the Cash amount:"));
		JTextField inputMoneyJTextField = new JTextField(15);
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		
		// 设置便捷输入按钮
		JPanel inputBtnJPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		String buttonNameString[] = { "100", "200", "300", "500", "800", "1000" };
		
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
		
		
		cashJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		cashJFrame.add(verticall);
		cashJFrame.setVisible(true);				// 显示可见
		cashJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		cashJFrame.setSize(500, 320);				// 界面大小设置
		cashJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(cashJFrame, "Please enter the amount!");
					return;
				}
				
				Double cashMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// 判断是否为零
				if (cashMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				// 判断是否为整百
				if (cashMoney%100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
				    inputMoneyJTextField.setText("");
				    return;
				}
				
				// 更改用户的余额
				user.setAvailableBalances(user.getAvailableBalances() + cashMoney);
				
				// 获取ATM信息
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// 更改ATM可提取的金额
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				updateFile.updateObjectOutputFile(atm);
				
				// 保存操作信息
				String opString = "Deposit "+ cashMoney +" yuan in Cash";
				updateFile.saveOperationData(user, opString);

				// 提示成功
				JOptionPane.showMessageDialog(cashJFrame, "successed!");
				
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ cashMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				cashJFrame.setVisible(false);
				new PrintCopy(user, bankName, printCopyContextString).printCopyInterface();
				
/*				// 返回操作界面
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
				}*/
				
			}
		});
		
		// 返回
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				cashJFrame.setVisible(false);
				new SaveMoney(user, bankName).SaveMoneyInterface();
				
			}
		});
		
	}
			
}
