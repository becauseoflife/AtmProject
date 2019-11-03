package pers.atm.useroperation.loan;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.LoansAndRepayments;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class Loan {
	private JFrame loanJFrame;
	private User user;
	private String bankName;
	
	public Loan(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void loanInterface()
	{
		loanJFrame = new JFrame(user.getUserName() + " Loan");
		loanJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 提示贷款的额度为
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Your loan limit"));
		
		// 贷款额度显示框
		JPanel loanLimitJPanel = new JPanel();
		JTextArea loanLimitJTextArea = new JTextArea(1, 15);
		
		String loanLimitString = "\t" + user.getLoanLimit() + "\t          ";
		loanLimitJTextArea.setText(loanLimitString);
		loanLimitJTextArea.setEditable(false);
		
		loanLimitJPanel.add(loanLimitJTextArea);
		
		// 输入贷款金额框
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the amount you want to loan:"));
		JTextField inputLoanMoneyJTextField = new JTextField(15);
		
		inputLoanMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(4));
		
		cashDepsitJPanel.add(inputLoanMoneyJTextField);
		
		
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
					inputLoanMoneyJTextField.setText(e.getActionCommand());
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
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(15));
		verticall.add(loanLimitJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(buttonJPanel);
		
		
		loanJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		loanJFrame.add(verticall);
		loanJFrame.setVisible(true);				// 显示可见
		loanJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		loanJFrame.setSize(500, 370);				// 界面大小设置
		loanJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (inputLoanMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(loanJFrame, "Please enter the amount of the loan!");
					return;
				}
				
				Double loanMoney = Double.valueOf(inputLoanMoneyJTextField.getText().toString());
				
				// 判断是否为零
				if (loanMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, Please enter the amount of the loan!");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				// 判断是否为整百
				if (loanMoney%100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
				    inputLoanMoneyJTextField.setText("");
				    return;
				}
				// 判断是否超过了贷款端的额度
				if (user.getLoanLimit() < loanMoney) {
					JOptionPane.showMessageDialog(null, "The amount you entered exceeds the limit of your loan, please re-enter!");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				
				
				// 更改用户的余额
				user.setAvailableBalances(user.getAvailableBalances() + loanMoney);
				user.setLoanLimit(user.getLoanLimit() - loanMoney);
				user.setRepaymentAmount(user.getRepaymentAmount() + loanMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				
				// 保存操作信息
				String opString = "Loan "+ loanMoney + "	";
				updateFile.saveOperationData(user, opString);

				// 提示成功
				JOptionPane.showMessageDialog(loanJFrame, "successed!");
				
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Loan Money:\t\t" 				+ loanMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Repayment amount:\t" 			+ user.getRepaymentAmount() 	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				loanJFrame.setVisible(false);
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
				loanJFrame.setVisible(false);
				new LoansAndRepayments(user, bankName).loansAndRepaymentsInterface();
				
			}
		});
		
	}
}
