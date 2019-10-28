package pers.atm.menu;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.login.AtmLoginInterfane;
import pers.atm.user.User;
import pers.atm.useroperation.CheckBalances;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.TransferMoney;
import pers.atm.useroperation.WithdrawMoney;

public class OtherBankClientMenu {
	private JFrame otherBankMenuJFrame;
	private User user;
	private String bankName;

	public OtherBankClientMenu(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	public void setOtherBankMenu(){
		otherBankMenuJFrame = new JFrame(bankName + " Operations");
		otherBankMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//��ǩ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// ���ܰ�ť
		JButton checkMoneyJButton = new JButton("Check Balance");
		JButton saveMoneyJButton = new JButton("Save Money");
		JButton getMoneyJButton = new JButton("Withdraw Money");
		JButton transferMoneyJButton = new JButton("Transfer Money");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// ���ܰ�ť���
		JPanel menuJPanel = new JPanel();
		
		menuJPanel.add(checkMoneyJButton);
		menuJPanel.add(saveMoneyJButton);
		menuJPanel.add(getMoneyJButton);
		menuJPanel.add(transferMoneyJButton);
		menuJPanel.add(new JLabel());			// ����������ռ�ø���
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(4, 2, 30, 30));
		
		// ��ֱ����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(menuJPanel);
		
		otherBankMenuJFrame.add(verticall);
		otherBankMenuJFrame.setLayout(new FlowLayout());
		otherBankMenuJFrame.setVisible(true);  			 // ��ʾ�ɼ�
		otherBankMenuJFrame.pack();						// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		otherBankMenuJFrame.setSize(500, 340);
		otherBankMenuJFrame.setLocationRelativeTo(null);
		
		// ע�ᰴť�����¼�
		
		// �鿴����
		checkMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
				otherBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ��������ѯ����
				new CheckBalances(user, bankName).CheckMoneyInterface();	
			}
		});
		
		// ��Ǯ����
		saveMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				otherBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ������Ǯ����
				new SaveMoney(user, bankName).SaveMoneyInterface();
			}
		});
		
		// ȡǮ����
		getMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				otherBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ����ȡǮ����
				new WithdrawMoney(user, bankName).outMoneyInterface();
			}
		});
		
		// ת�˹���
		transferMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				otherBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ����ת�˽���
				new TransferMoney(user, bankName).TransferMoneyInterface();
			}
		});
		
		// �˳���¼
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				otherBankMenuJFrame.setVisible(false);  // ���ع��ܽ���
				login.loginInterface();     // ��ʾ��¼����
			}
		});
		
	}

}
