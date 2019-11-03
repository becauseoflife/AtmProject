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

public class Repayments {
	private JFrame repaymentsJFrame;
	private User user;
	private String bankName;
	
	public Repayments(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void repaymentsInterface()
	{
		repaymentsJFrame = new JFrame(user.getUserName() + " Return Loan");
		repaymentsJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 提示
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("You need to repay the loan"));
		
		// 还款额度显示框
		JPanel repaymentsJPanel = new JPanel();
		JTextArea repaymentsLimitJTextArea = new JTextArea(1, 15);
		
		String returnLoanLimitString = "\t" + user.getRepaymentAmount() + "\t          ";
		repaymentsLimitJTextArea.setText(returnLoanLimitString);
		repaymentsLimitJTextArea.setEditable(false);
		
		repaymentsJPanel.add(repaymentsLimitJTextArea);
		
		// 输入贷款金额框
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the amount of the loan you want to repay:"));
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
		verticall.add(repaymentsJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(buttonJPanel);
		
		
		repaymentsJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		repaymentsJFrame.add(verticall);
		repaymentsJFrame.setVisible(true);				// 显示可见
		repaymentsJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		repaymentsJFrame.setSize(570, 370);				// 界面大小设置
		repaymentsJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (inputLoanMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(repaymentsJFrame, "Please enter the amount of the loan!");
					return;
				}
				
				Double returnLoanMoney = Double.valueOf(inputLoanMoneyJTextField.getText().toString());
				
				// 判断是否为零
				if (returnLoanMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, Please enter the amount of the loan!");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				// 判断是否为整百
				if (returnLoanMoney%100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
				    inputLoanMoneyJTextField.setText("");
				    return;
				}
				// 判断是否超过了需要还贷款的数额
				if (user.getRepaymentAmount() < returnLoanMoney) {
					JOptionPane.showMessageDialog(repaymentsJFrame, "The amount you entered exceeds the amount you need to repay the loan,\n please re-enter");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				
				
				// 更改用户的余额
				user.setAvailableBalances(user.getAvailableBalances() - returnLoanMoney);
				user.setLoanLimit(user.getLoanLimit() + returnLoanMoney);
				user.setRepaymentAmount(user.getRepaymentAmount() - returnLoanMoney);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				
				// 保存操作信息
				String opString = "Return Loan "+ returnLoanMoney ;
				updateFile.saveOperationData(user, opString);

				// 提示成功
				JOptionPane.showMessageDialog(repaymentsJFrame, "successed!");
				
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Return Loan :\t\t" 			+ returnLoanMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Repayment amount:\t" 			+ user.getRepaymentAmount() 	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				repaymentsJFrame.setVisible(false);
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
				repaymentsJFrame.setVisible(false);
				new LoansAndRepayments(user, bankName).loansAndRepaymentsInterface();
				
			}
		});
		
	}
}
