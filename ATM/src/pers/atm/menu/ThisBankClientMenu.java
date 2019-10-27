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
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.MyAtm;
import pers.atm.user.User;
import pers.atm.useroperation.ChangePassword;
import pers.atm.useroperation.CheckBalances;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.TransferMoney;
import pers.atm.useroperation.WithdrawMoney;

public class ThisBankClientMenu {
	private JFrame thisBankMenuJFrame;
	private User user;
	private String bankName;

	public ThisBankClientMenu(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	public void setThisBankMenu(){
		thisBankMenuJFrame = new JFrame(bankName + " Operations");
		thisBankMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//��ǩ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// ���ܰ�ť
		JButton checkMoneyJButton = new JButton("Check Balance");
		JButton saveMoneyJButton = new JButton("Save Money");
		JButton getMoneyJButton = new JButton("Withdraw Money");
		JButton transferMoneyJButton = new JButton("Transfer Money");
		JButton changePasswordJButton = new JButton("Change Password");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// ���ܰ�ť���
		JPanel menuJPanel = new JPanel();
		
		menuJPanel.add(checkMoneyJButton);
		menuJPanel.add(saveMoneyJButton);
		menuJPanel.add(getMoneyJButton);
		menuJPanel.add(transferMoneyJButton);
		menuJPanel.add(changePasswordJButton);
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(3, 2, 30, 30));
		
		// ��ֱ����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(menuJPanel);
		
		thisBankMenuJFrame.add(verticall);
		thisBankMenuJFrame.setLayout(new FlowLayout());
		thisBankMenuJFrame.setVisible(true);  			 // ��ʾ�ɼ�
		thisBankMenuJFrame.pack();						// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		thisBankMenuJFrame.setSize(500, 300);
		thisBankMenuJFrame.setLocationRelativeTo(null);
		
		// ע�ᰴť�����¼�
		
		// �鿴����
		checkMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
				thisBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ��������ѯ����
				new CheckBalances(user, bankName).CheckMoneyInterface();	
			}
		});
		
		// ��Ǯ����
		saveMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				thisBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ������Ǯ����
				new SaveMoney(user, bankName).SaveMoneyInterface();
			}
		});
		
		// ȡǮ����
		getMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				thisBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ����ȡǮ����
				new WithdrawMoney(user, bankName).outMoneyInterface();
			}
		});
		
		// ת�˹���
		transferMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				thisBankMenuJFrame.setVisible(false);  // ���ش˽���
				// ����ת�˽���
				new TransferMoney(user, bankName).TransferMoneyInterface();
			}
		});
		
		// ��������
		changePasswordJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				thisBankMenuJFrame.setVisible(false);  // ���ش˽���
				// �����޸��������
				new ChangePassword(user, bankName).ChangPasswordInterface();
			}
		});
		
		// �˳���¼
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				thisBankMenuJFrame.setVisible(false);  // ���ع��ܽ���
				login.loginInterface();     // ��ʾ��¼����
			}
		});
		
	}

}
