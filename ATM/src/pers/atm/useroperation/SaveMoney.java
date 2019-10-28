package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.user.User;
import pers.atm.useroperation.savemoney.SaveCashMoney;
import pers.atm.useroperation.savemoney.SaveCheckMoney;

public class SaveMoney {
	private JFrame saveMoneyJFrame;
	private User user;
	private String bankName;
	
	public SaveMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void SaveMoneyInterface()
	{
		saveMoneyJFrame = new JFrame(user.getUserName() + " SaveMoney");
		saveMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û���ʾ��Ϣ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select server"));
		
		// �ֽ��ť �� ֧Ʊ��ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton cashJButton = new JButton("Cash Deposit");
		JButton checkJButton = new JButton("Check Deposit");
		buttonJPanel.add(cashJButton);
		buttonJPanel.add(checkJButton);
		
		// ���ذ�ť
		JPanel backJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 20));
		JButton backJButton = new JButton("Cancle");
		backJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(40));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(buttonJPanel);
		verticall.add(Box.createVerticalStrut(60));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����

		verticall.add(backJPanel);
		
		
		saveMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		saveMoneyJFrame.add(verticall);
		saveMoneyJFrame.setVisible(true);				// ��ʾ�ɼ�
		saveMoneyJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		saveMoneyJFrame.setSize(500, 320);				// �����С����
		saveMoneyJFrame.setLocationRelativeTo(null);
		
		// ���ü����¼�
		// �ֽ���
		cashJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				saveMoneyJFrame.setVisible(false); // ���ش˽���
			 	new SaveCashMoney(user, bankName).cashDepositInterface(); // �����ֽ������
			}
		});
		
		// ֧Ʊ���
		checkJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				saveMoneyJFrame.setVisible(false); // ���ش˽���
				new SaveCheckMoney(user, bankName).checkDepositInterface(); // ����֧Ʊ������
			}
		});
		
		// ���ذ�ť
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				saveMoneyJFrame.setVisible(false); // ���ش˽���

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
