package pers.atm.useroperation;

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

import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class ViewFlowBill {
	private JFrame viewWaterBillJFrame;
	private User user;
	private String bankName;
	
	public ViewFlowBill(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void ViewWaterBillInterface()
	{	
		viewWaterBillJFrame = new JFrame(user.getUserName() + " View Flow Bill");
		viewWaterBillJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 用户名显示
		JPanel userNameJPanel = new JPanel();
		userNameJPanel.add(new JLabel("Dear user:" + user.getUserName()));
		
		// 用户流水账单显示
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(10, 45);
		
		String opInfo = "User Account\t\t" + "Operation Context\t" + "Operation Time  \n" ;
		userMoneyInfoJTextArea.setText(opInfo);
		
		SetAndGetDataFile uFile = new SetAndGetDataFile();
		String waterBillString = uFile.readOperationData(user.getUserAccountNumber());
		// 将读出的流水账单加入文本内容中去
		userMoneyInfoJTextArea.append(waterBillString);
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
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 10, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(userNameJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		viewWaterBillJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// 设置流式布局
		viewWaterBillJFrame.add(verticall);
		viewWaterBillJFrame.setVisible(true);				// 显示可见
		viewWaterBillJFrame.pack();  	 			// 调整此窗口的大小，以适合其子组件的首选大小和布局
		viewWaterBillJFrame.setSize(600, 350);				// 界面大小设置
		viewWaterBillJFrame.setLocationRelativeTo(null);
		
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
					// 更新ATM机的打印纸张信息
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					// 跟新ATM文件信息
					uFile.updateObjectOutputFile(atm);
					// 提示打印成功
					JOptionPane.showMessageDialog(viewWaterBillJFrame, "Print successfully!");
					
					viewWaterBillJFrame.setVisible(false); // 隐藏当前窗口
					// 返回操作界面
					new ThisBankClientMenu(user, bankName).setThisBankMenu();
				}else {
					// 提示打印纸不足 不能打印
					JOptionPane.showMessageDialog(viewWaterBillJFrame, "Sorry, No paper!");
				}
			}
		});
		
		// 返回窗口监听事件
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				viewWaterBillJFrame.setVisible(false); // 隐藏当前窗口
				// 返回操作界面
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
	}
}
