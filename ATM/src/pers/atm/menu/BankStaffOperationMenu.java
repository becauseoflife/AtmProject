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

import pers.atm.bankstaffoparetion.CheckAtmBalance;
import pers.atm.bankstaffoparetion.PutMoneyIntoAtm;
import pers.atm.bankstaffoparetion.PutPrintPaperIntoAtm;
import pers.atm.bankstaffoparetion.ViewOperationRecord;
import pers.atm.login.AtmLoginInterfane;
import pers.atm.user.AuthorizedBankStaff;

public class BankStaffOperationMenu {
	private JFrame bankStaffOperationMenuJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;

	public BankStaffOperationMenu(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}



	public void setBankStaffOperationMenu(){
		bankStaffOperationMenuJFrame = new JFrame(bankStaff.getBankStaffId() + " Operations");
		bankStaffOperationMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//��ǩ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// ���ܰ�ť
		JButton viewAtmBalanceJButton = new JButton("View ATM balance");
		JButton putMoneyJButton = new JButton("Put money into ATM");
		JButton putPrintPaperJButton = new JButton("Storing printing paper");
		JButton viewOperationRecordJButton = new JButton("View Operation record");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// ���ܰ�ť���
		JPanel menuJPanel = new JPanel();
		menuJPanel.add(viewAtmBalanceJButton);
		menuJPanel.add(putMoneyJButton);
		menuJPanel.add(putPrintPaperJButton);
		menuJPanel.add(viewOperationRecordJButton);
		menuJPanel.add(new JLabel());
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(3, 2, 30, 30));
		
		// ��ֱ����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(menuJPanel);
		
		bankStaffOperationMenuJFrame.add(verticall);
		bankStaffOperationMenuJFrame.setLayout(new FlowLayout());
		bankStaffOperationMenuJFrame.setVisible(true);  			 // ��ʾ�ɼ�
		bankStaffOperationMenuJFrame.pack();						// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		bankStaffOperationMenuJFrame.setSize(500, 340);
		bankStaffOperationMenuJFrame.setLocationRelativeTo(null);
		
		// ע�ᰴť�����¼�
		
		// �鿴ATM����
		viewAtmBalanceJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
				bankStaffOperationMenuJFrame.setVisible(false);  // ���ش˽���
				// ��������ѯ����
				new CheckAtmBalance(bankStaff, bankName).checkAtmMoneyInterface();
			}
		});
		
		// ��ATM��Ǯ����
		putMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				bankStaffOperationMenuJFrame.setVisible(false);  // ���ش˽���
				new PutMoneyIntoAtm(bankStaff, bankName).putMoneyIntoAtmInterface();
			}
		});
		
		// �Ӵ�ӡֽ����
		putPrintPaperJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				bankStaffOperationMenuJFrame.setVisible(false);  // ���ش˽���
				new PutPrintPaperIntoAtm(bankStaff, bankName).putPaperIntoAtmInterface();
			}
		});
		
		// �鿴������¼����
		viewOperationRecordJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				bankStaffOperationMenuJFrame.setVisible(false);  // ���ش˽���
				// �鿴������¼����
				new ViewOperationRecord(bankStaff, bankName).viewOperationsInterface();
			}
		});
		
		// �˳���¼
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				AtmLoginInterfane login = new AtmLoginInterfane(bankName);
				bankStaffOperationMenuJFrame.setVisible(false);  // ���ع��ܽ���
				login.loginInterface();     // ��ʾ��¼����
			}
		});
		
	}
}
