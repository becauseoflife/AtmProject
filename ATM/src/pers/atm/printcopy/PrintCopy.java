package pers.atm.printcopy;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.element.Element;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class PrintCopy {
	private JFrame printCopyJFrame;
	private User user;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	private String printCopyContextString;
	
	// 构造函数
	public PrintCopy(User user, String bankName, String printCopyContextString) {
		super();
		this.user = user;
		this.bankName = bankName;
		this.printCopyContextString = printCopyContextString;
		this.bankStaff = null;		// 使银行授权人员为空
	}
	
	// 重构
	public PrintCopy(AuthorizedBankStaff bankStaff, String bankName, String printCopyContextString) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
		this.printCopyContextString = printCopyContextString;
		this.user = null;		// 使用户账号为空
	}
	
	public void printCopyInterface()
	{
		if (bankStaff == null) {
			printCopyJFrame = new JFrame(user.getUserName() + " Print Copy");
		}else {
			printCopyJFrame = new JFrame(bankStaff.getBankStaffId() + "Print Copy");
		}
		
		printCopyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户名显示
		JPanel infoJPanel = new JPanel();
		infoJPanel.add(new JLabel("Print the copy as"));
		
		// 用户账号和余额显示
		JPanel printCopyInfoJPanel = new JPanel();
		JTextArea printInfoInfoJTextArea = new JTextArea(11, 30);
		
		printInfoInfoJTextArea.setText("\n");
		
		// 显示打印的副本内容
		printInfoInfoJTextArea.append(printCopyContextString);
		
		printInfoInfoJTextArea.setEditable(false);
		printCopyInfoJPanel.add(printInfoInfoJTextArea);
		
		// 返回和打印按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton cancleJButton = new JButton("Cancle");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(cancleJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(infoJPanel);
		verticall.add(printCopyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		printCopyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		printCopyJFrame.add(verticall);
		printCopyJFrame.setVisible(true);				// 显示可见
		printCopyJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		printCopyJFrame.setSize(550, 370);				// 界面大小设置
		printCopyJFrame.setLocationRelativeTo(null);
		
		// 按钮监听事件
		// 打印按钮监听事件
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 获取ATM的信息文件
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm = atmFile.readObjectInputFile(bankName);
				
				// 判断打印纸是否足够
				if (atm.getAtmPaper() > 0) {
					// 更新ATM机的打印纸
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// 更新ATM文件
					atmFile.updateObjectOutputFile(atm);
					
					// 提示成功信息
					JOptionPane.showMessageDialog(printCopyJFrame, "Print successfully!");
					
					printCopyJFrame.setVisible(false); // 隐藏当前窗口
					// 返回操作界面
					if (bankStaff == null) {	// 用户使用打印功能
						if (user.getBankName().equals(bankName)) {
							new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
						}
						else{
							new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
						}
					}else {		// 授权人员使用打印功能
						new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu(); // 打开操作人员界面
					}
				}else {
					// 提示打印纸不足 不能打印
					JOptionPane.showMessageDialog(printCopyJFrame, "Sorry, No paper!");
				}
				
			}
		});
		
		// 返回窗口监听事件
		cancleJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				printCopyJFrame.setVisible(false); // 隐藏当前窗口
				// 返回操作界面
				if (bankStaff == null) {
					if (user.getBankName().equals(bankName)) {
						new ThisBankClientMenu(user, bankName).setThisBankMenu();	// 本银行操作界面
					}
					else{
						new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // 其他银行操作界面
					}
				}else {
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu(); // 打开操作人员界面
				}
			}
		});
		
	}

}
