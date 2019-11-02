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

public class PutPrintPaperIntoAtm {
	private JFrame putPrintPaperIntoAtmJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public PutPrintPaperIntoAtm(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	// 现金存款界面
	public void putMoneyIntoAtmInterface()
	{
		putPrintPaperIntoAtmJFrame = new JFrame(bankStaff.getBankStaffId() + " Put Print Paper");
		putPrintPaperIntoAtmJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 输入金额框
		JPanel outPaperJPanel = new JPanel();
		outPaperJPanel.add(new Label("Please enter the paper to be stored:"));
		JTextField inputPaperJTextField = new JTextField(15);
		
		inputPaperJTextField.setDocument(new NumberLenghtLimitedDmt(6));
		
		outPaperJPanel.add(inputPaperJTextField);
		
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
					inputPaperJTextField.setText(e.getActionCommand());
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
		verticall.add(outPaperJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(buttonJPanel);
		
		
		putPrintPaperIntoAtmJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		putPrintPaperIntoAtmJFrame.add(verticall);
		putPrintPaperIntoAtmJFrame.setVisible(true);				// 显示可见
		putPrintPaperIntoAtmJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		putPrintPaperIntoAtmJFrame.setSize(500, 320);				// 界面大小设置
		putPrintPaperIntoAtmJFrame.setLocationRelativeTo(null);
		
		// 设定按钮监听事件
		// 确认
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (inputPaperJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "Please enter the amount!");
					return;
				}
				int paper = Integer.valueOf(inputPaperJTextField.getText().toString());
				
				// 判断是否输入为0
				if (paper == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputPaperJTextField.setText("");
					return;
				}
				
				// 获取ATM信息
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// 更改ATM打印纸
				atm.setAtmPaper(atm.getAtmPaper() + paper);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// 保存操作信息
				String opString = "Deposit " + paper + " printing papers";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// 提示成功
				JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "successed!");
				
				// 获取存钱的时间 打印副本需要的时间
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
				Date newData = new Date();			//当前时间
				String datasString = operationData.format(newData);		//处理当前时间格式
				
				// 打印副本的内容
				String printCopyContextString = "\t" + bankName + " of User\n" +
												"Bank Staff Account Number:\t" 	+ bankStaff.getBankStaffAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ paper				 + "\n" +
												"ATM printing paper:\t" 		+ atm.getAtmPaper()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// 打开打印界面
				putPrintPaperIntoAtmJFrame.setVisible(false);
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
				putPrintPaperIntoAtmJFrame.setVisible(false);
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
