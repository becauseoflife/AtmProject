package pers.atm.useroperation;

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

import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.MyAtm;
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
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton withdrawalJButton = new JButton("withdrawal");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(withdrawalJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(20));
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
					JOptionPane.showMessageDialog(outMoneyJFrame, "请输入金额！");
					return;
				}
				
				Double outMoney = Double.valueOf(inputMoneyJTextField.getText());
				// 判断输入取钱金额是否超过自己的余额
				if (user.getAvailableBalances() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// 条件都成立
				
				// 更新用户文件 和 ATM余额文件
				SetAndGetDataFile uFlie = new SetAndGetDataFile();
				
				user.setAvailableBalances(user.getAvailableBalances() - outMoney);
				uFlie.updateObjectOutputFile(user);
				
				// 获取ATM信息
				MyAtm atm = uFlie.readObjectInputFile(bankName);
				atm.setAtmMoney(atm.getAtmMoney() - outMoney);
				
				// 更新ATM信息
				uFlie.updateObjectOutputFile(atm);
				
				// 保存操作信息
				String opString = "Take out " + outMoney + " yuan";
				uFlie.saveOperationData(user.getUserAccountNumber(), opString);
				
				JOptionPane.showMessageDialog(outMoneyJFrame, "Successed!");
				outMoneyJFrame.setVisible(false);  // 隐藏此界面
				new ThisBankClientMenu(user, bankName).setThisBankMenu();  // 返回操作界面
			}
		});
		
		// 取消按钮
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				outMoneyJFrame.setVisible(false);  // 隐藏此界面
				// 返回操作界面
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
		
	}
}
