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

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class SaveCheckMoney {
	private JFrame checkJFrame;
	private User user;
	private String bankName;
	
	public SaveCheckMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void checkDepositInterface()
	{
		checkJFrame = new JFrame(user.getUserName() + " Check Deposit");
		checkJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入付款行名
		JPanel paymentBankJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		paymentBankJPanel.add(new Label("Payment Bank Name:"));
		JTextField paymentBankJTextField = new JTextField(15);
		paymentBankJPanel.add(paymentBankJTextField);
		
		// 输入出票人账号
		JPanel drawerAccountJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		drawerAccountJPanel.add(new Label("Drawer Account:"));
		JTextField drawerAccountJTextField = new JTextField(15);

		drawerAccountJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		drawerAccountJPanel.add(drawerAccountJTextField);
		
		// 输入金额
		JPanel checkDepsitJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		checkDepsitJPanel.add(new Label("Amount Of Check:"));
		JTextField inputMoneyJTextField = new JTextField(15);

		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		checkDepsitJPanel.add(inputMoneyJTextField);
		
		// 输入用途
		JPanel useJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		useJPanel.add(new Label("Purpose of Check:"));
		JTextField useJTextField = new JTextField(15);
		useJPanel.add(useJTextField);
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(paymentBankJPanel);
		verticall.add(drawerAccountJPanel);
		verticall.add(checkDepsitJPanel);
		verticall.add(useJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		checkJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		checkJFrame.add(verticall);
		checkJFrame.setVisible(true);				// 显示可见
		checkJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		checkJFrame.setSize(500, 320);				// 界面大小设置
		checkJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 判断输入框的信息是否为空
				if (paymentBankJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Name of the payment bank!");
					return;
				}
				else if (drawerAccountJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the drawer's account number!");
					return;
				}
				else if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the amount of the check!");
					return;
				}
				else if (useJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(checkJFrame, "Please enter the purpose of the check!");
					return;
				}
				
				Double checkMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// 判断是否为零
				if (checkMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, Please enter the amount of the check!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 更改用户的不可用余额
				user.setUnavailableBlances(user.getUnavailableBlances() + checkMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				
				String opString = "Deposit "+ checkMoney +" in Check";
				updateFile.saveOperationData(user, opString);

				// 提示成功
				JOptionPane.showMessageDialog(checkJFrame, "Waiting for verification!");
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 收集输入框的内容
				String paymentBankString = paymentBankJTextField.getText();
				String drawerAccountString = drawerAccountJTextField.getText();
				String purposeString = useJTextField.getText();
				
				// 打印内容
				String printCopyContextString = "\t"+ user.getBankName() + " of User\n" +
												"Account Number:\t"   	 + user.getUserAccountNumber()	 + "\n" +
												"Payment Bank Name:\t"	 + paymentBankString			 + "\n" +
												"Drawer Account:\t"		 + drawerAccountString			 + "\n" +
												"Amount Of Check:\t" 	 + checkMoney					 + "\n" +
												"Purpose of Check:\t"	 + purposeString				 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 	 + datasString;
				
				// 打开打印界面
				checkJFrame.setVisible(false);
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
				checkJFrame.setVisible(false);
				new SaveMoney(user, bankName).SaveMoneyInterface();
				
			}
		});
		
	}
			
}
