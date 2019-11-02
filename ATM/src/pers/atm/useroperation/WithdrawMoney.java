package pers.atm.useroperation;

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

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.Atm;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class WithdrawMoney {
	private JFrame outMoneyJFrame;
	private User user;
	private String bankName;
	
	public WithdrawMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void outMoneyInterface()
	{
		outMoneyJFrame = new JFrame(user.getUserName() + " WithdrawMoney");
		outMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入金额框
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the withdrawal amount:"));
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
		JButton withdrawalJButton = new JButton("withdrawal");
		JButton backJButton = new JButton("Cancle");
		buttonJPanel.add(withdrawalJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(buttonJPanel);
		
		
		outMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		outMoneyJFrame.add(verticall);
		outMoneyJFrame.setVisible(true);				// 显示可见
		outMoneyJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		outMoneyJFrame.setSize(500, 320);				// 界面大小设置
		outMoneyJFrame.setLocationRelativeTo(null);
		
		// 注册监听事件
		// 取钱按钮
		withdrawalJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 判断用户是否输入
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Please enter the amount!");
					return;
				}
				
				// 更新用户文件 和 ATM余额文件
				SetAndGetDataFile uFlie = new SetAndGetDataFile();
				
				// 获取ATM信息
				Atm atm = uFlie.readObjectInputFile(bankName);
				
				Double outMoney = Double.valueOf(inputMoneyJTextField.getText());
				// 判断输入取钱金额是否超过自己的余额
				if (user.getAvailableBalances() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				// 判断ATM余额是否足够
				else if (atm.getAtmMoney() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "ATM balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 判断是否为零
				if (outMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 判断是否为整百
				if (outMoney % 100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 条件都成立
				
				user.setAvailableBalances(user.getAvailableBalances() - outMoney);
				uFlie.updateObjectOutputFile(user);
				
				// 更新ATM余额
				atm.setAtmMoney(atm.getAtmMoney() - outMoney);
				
				// 更新ATM信息
				uFlie.updateObjectOutputFile(atm);
				
				// 保存操作信息
				String opString = "Take out " + outMoney + " yuan";
				uFlie.saveOperationData(user, opString);
				
				// 提示成功
				JOptionPane.showMessageDialog(outMoneyJFrame, "Successed!");
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Take Out:\t\t" 			+ outMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				outMoneyJFrame.setVisible(false);  // 隐藏此界面
				new PrintCopy(user, bankName, printCopyContextString).printCopyInterface();
				
/*				// 返回操作界面
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
				}*/
			}
		});
		
		// 取消按钮
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				outMoneyJFrame.setVisible(false);  // 隐藏此界面
				
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
