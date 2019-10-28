package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.MyAtm;
import pers.atm.user.User;

public class CheckBalances {
	private JFrame checkBalancesJFrame;
	private User user;
	private String bankName;
	
	public CheckBalances(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void CheckMoneyInterface()
	{
		// ��ȡATM��Ϣ
		SetAndGetDataFile atmFile = new SetAndGetDataFile();
		MyAtm atm  = atmFile.readObjectInputFile(bankName);
		
		checkBalancesJFrame = new JFrame(user.getUserName() + " CheckBanlances");
		checkBalancesJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�����ʾ
		JPanel userNameJPanel = new JPanel();
		userNameJPanel.add(new JLabel("�𾴵��û���" + user.getUserName()));
		
		// �û��˺ź������ʾ
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(8, 30);
		
		String userMoneyInfoString = "\n\n�����˺ţ�\t\t" + user.getUserAccountNumber() + "\n" +
								"�������Ϊ��\t\t" + user.getAvailableBalances() + "Ԫ\n" +
								"���������Ϊ��\t\t" + user.getUnavailableBlances() + "Ԫ\n" +
								"ATM��ȡ���Ϊ��\t" + atm.getAtmMoney() + "Ԫ\n" ;
		
		userMoneyInfoJTextArea.setText(userMoneyInfoString);
		userMoneyInfoJTextArea.setEditable(false);
		userMoneyInfoJPanel.add(userMoneyInfoJTextArea);
		
		// ���غʹ�ӡ��ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(userNameJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(buttonJPanel);
		
		
		checkBalancesJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		checkBalancesJFrame.add(verticall);
		checkBalancesJFrame.setVisible(true);				// ��ʾ�ɼ�
		checkBalancesJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		checkBalancesJFrame.setSize(500, 320);				// �����С����
		checkBalancesJFrame.setLocationRelativeTo(null);
		
		// ��ť�����¼�
		// ��ӡ��ť�����¼�
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ���ش��ڼ����¼�
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				checkBalancesJFrame.setVisible(false); // ���ص�ǰ����
				// ���ز�������
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
				}
			}
		});
		
	}

}
