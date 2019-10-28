package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class TransferMoney {
	private JFrame transferMoneyJFrame;
	private User user;
	private String bankName;
	
	public TransferMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void TransferMoneyInterface() 
	{
		transferMoneyJFrame = new JFrame(user.getUserName() + " TransferMoney");
		transferMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入转账用户
		JPanel transferUserNameJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		transferUserNameJPanel.add(new JLabel("Transfer User Account:"));
		
		JTextField inputUserAccountJTextField = new JTextField(15);
		inputUserAccountJTextField.setDocument(new NumberLenghtLimitedDmt(7));// 只能输入7位数字
		transferUserNameJPanel.add(inputUserAccountJTextField);
		
		// 提示输入金额框
		JPanel transferMoneyJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		transferMoneyJPanel.add(new JLabel("      Transfer amount:    "));
		
		JTextField inputMoneyJTextField = new JTextField(15);
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));// 只能输入7位数字
		transferMoneyJPanel.add(inputMoneyJTextField);
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		JButton transferJButton = new JButton("Transfer");
		JButton backJButton = new JButton("Cancle");
		buttonJPanel.add(transferJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(transferUserNameJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(transferMoneyJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		transferMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		transferMoneyJFrame.add(verticall);
		transferMoneyJFrame.setVisible(true);				// 显示可见
		transferMoneyJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		transferMoneyJFrame.setSize(500, 320);				// 界面大小设置
		transferMoneyJFrame.setLocationRelativeTo(null);
		
		
		// 注册监听事件
		// 转账按钮
		transferJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				// 判断是否输入了信息
				if (inputUserAccountJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "请输入账户！");
					return;
				}
				else if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "请输入转账金额！");
					return;
				}
				
				String uAccountNumber = inputUserAccountJTextField.getText();
				Double transMoney = Double.valueOf(inputMoneyJTextField.getText());
				
				// 判断此用户是否存在
				SetAndGetDataFile uFile = new SetAndGetDataFile();
				// 判断输入是否合法
				if ( uFile.readUserInputFile(uAccountNumber) == null) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "This user doesn't exist ! please enter again!");
					inputUserAccountJTextField.setText("");
					return;
				}
				// 输入的用户是自己
				else if (uFile.readUserInputFile(uAccountNumber).getUserAccountNumber().equals(user.getUserAccountNumber())) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Can't transfer to myself ! please enter again!");
					inputUserAccountJTextField.setText("");
					return;
				}
				// 输入的金额超过自己的账户余额
				else if (user.getAvailableBalances() < transMoney) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				//  输入条件成立	
				User transferUser = uFile.readUserInputFile(uAccountNumber);
				// 更新转账者和被转账者用户余额
				transferUser.setAvailableBalances(transferUser.getAvailableBalances() + transMoney);
				user.setAvailableBalances(user.getAvailableBalances() - transMoney);
				
				// 更新文件
				uFile.updateObjectOutputFile(transferUser);
				uFile.updateObjectOutputFile(user);
				
				// 记录用户的操作
				String opString = "Received " + transMoney + " yuan from "+ user.getUserAccountNumber()+" User transfers";
				uFile.saveOperationData(transferUser, opString);
				
				opString = "Transfer " + transMoney +" yuan to " + transferUser.getUserAccountNumber() + " User";
				uFile.saveOperationData(user, opString);
				
				JOptionPane.showMessageDialog(transferMoneyJFrame, "seccussed!");
				transferMoneyJFrame.setVisible(false); //隐藏此界面

				// 返回操作界面
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
				}

			}
		});
		
		// 返回按钮
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				transferMoneyJFrame.setVisible(false); //隐藏此界面
					
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
