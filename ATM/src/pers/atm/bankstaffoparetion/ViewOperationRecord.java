package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class ViewOperationRecord {
	private JFrame operationRecordJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public ViewOperationRecord(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	public void viewOperationsInterface()
	{	
		operationRecordJFrame = new JFrame(bankStaff.getBankStaffId() + " View Operation record");
		operationRecordJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户名显示
		JPanel staffIdJPanel = new JPanel();
		staffIdJPanel.add(new JLabel(bankStaff.getBankStaffId() + "Operation record" ) );
		
		// 用户流水账单显示
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(10, 40);
		
		String opInfo = "Bank Staff Id\t" + "Operation Context\t" + "Operation Time  \n" ;
		userMoneyInfoJTextArea.setText(opInfo);
		
		// 读取操作记录
		SetAndGetDataFile uFile = new SetAndGetDataFile();
		String OperationRecordString = uFile.readbankStaffOperationData(bankStaff.getBankStaffId());
		
		
		userMoneyInfoJTextArea.append(OperationRecordString);
		userMoneyInfoJTextArea.setEditable(false);
		
		// 滚动条
		JScrollPane scroll = new JScrollPane(); 
		scroll.setViewportView(userMoneyInfoJTextArea);
		// 垂直自动显示
		scroll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		// 水平隐藏
		scroll.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		userMoneyInfoJPanel.add(scroll);
		// 返回和打印按钮
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(staffIdJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		
		operationRecordJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		operationRecordJFrame.add(verticall);
		operationRecordJFrame.setVisible(true);				// 显示可见
		operationRecordJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		operationRecordJFrame.setSize(500, 350);				// 界面大小设置
		operationRecordJFrame.setLocationRelativeTo(null);
		
		// 按钮监听事件
		// 打印按钮监听事件
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				// 获取ATM文件信息
				SetAndGetDataFile uFile = new SetAndGetDataFile();
				Atm atm = uFile.readObjectInputFile(bankName); 
				// 判断打印纸是否足够
				if (atm.getAtmPaper() > 0) {
					// 更新ATM机的打印纸
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// 更新ATM文件
					uFile.updateObjectOutputFile(atm);
					
					// 提示成功信息
					JOptionPane.showMessageDialog(null, "Print successfully!");
					
					operationRecordJFrame.setVisible(false); // 隐藏当前窗口
					// 返回操作界面
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
				}else {
					// 提示打印纸不足 不能打印
					JOptionPane.showMessageDialog(null, "Sorry, No paper!");
				}
			}
		});
		
		// 返回窗口监听事件
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				operationRecordJFrame.setVisible(false); // 隐藏当前窗口
				// 返回操作界面
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
