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
		
		inputPaperJTextField.setDocument(new NumberLenghtLimitedDmt(8));
		
		outPaperJPanel.add(inputPaperJTextField);
		
		// 确认和返回按钮
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(outPaperJPanel);
		verticall.add(Box.createVerticalStrut(20));
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
				
				// 获取ATM信息
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				MyAtm atm  = atmFile.readObjectInputFile(bankName);
				
				// 更改ATM打印纸
				atm.setAtmPaper(atm.getAtmPaper() + paper);
				
				// 更新文件
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// 保存操作信息
				String opString = "Deposit " + paper + " printing papers";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// 返回操作界面
				JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "successed!");
				putPrintPaperIntoAtmJFrame.setVisible(false);
				
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
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
