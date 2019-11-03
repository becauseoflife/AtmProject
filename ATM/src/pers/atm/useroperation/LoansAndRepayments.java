package pers.atm.useroperation;

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

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.user.User;
import pers.atm.useroperation.loan.Loan;
import pers.atm.useroperation.loan.Repayments;

public class LoansAndRepayments {
	private JFrame loanJFrame;
	private User user;
	private String bankName;
	
	public LoansAndRepayments(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void loansAndRepaymentsInterface()
	{
		loanJFrame = new JFrame(user.getUserName() + " Loans and Return loan");
		loanJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û���ʾ��Ϣ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select server"));
		
		// ���ť �� ���ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 60, 10));
		JButton loanJButton = new JButton("Loan");
		JButton returnLoanJButton = new JButton("Return loan");
		buttonJPanel.add(loanJButton);
		buttonJPanel.add(returnLoanJButton);
		
		// ���ذ�ť
		JPanel backJPanel = new JPanel(new GridLayout(1, 2, 60, 10));
		JButton backJButton = new JButton("Cancel");
		backJPanel.add(new JLabel());
		backJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(40));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(buttonJPanel);
		verticall.add(Box.createVerticalStrut(80));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(backJPanel);
		
		
		loanJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		loanJFrame.add(verticall);
		loanJFrame.setVisible(true);				// ��ʾ�ɼ�
		loanJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		loanJFrame.setSize(500, 320);				// �����С����
		loanJFrame.setLocationRelativeTo(null);
		
		// ���ü����¼�
		// ����
		loanJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loanJFrame.setVisible(false); // ���ش˽���
			 	// �򿪴������
				new Loan(user, bankName).loanInterface();
			}
		});
		
		// ����
		returnLoanJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loanJFrame.setVisible(false); // ���ش˽���
				// �򿪻������
				new Repayments(user, bankName).repaymentsInterface();
			}
		});
		
		// ���ذ�ť
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				loanJFrame.setVisible(false); // ���ش˽���

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
