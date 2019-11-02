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
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;

public class CheckAtmBalance {
	private JFrame checkAtmBalancesJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public CheckAtmBalance(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	public void checkAtmMoneyInterface()
	{
		// ��ȡATM��Ϣ
		SetAndGetDataFile atmFile = new SetAndGetDataFile();
		Atm atm  = atmFile.readObjectInputFile(bankName);
		
		checkAtmBalancesJFrame = new JFrame(bankStaff.getBankStaffId() + " CheckBanlances");
		checkAtmBalancesJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ��Ȩ��Աid��ʾ
		JPanel bankStaffNameJPanel = new JPanel();
		bankStaffNameJPanel.add(new JLabel("Authorized person Id��" + bankStaff.getBankStaffId()));
		
		// ATM���ʹ�ӡֽ��ʾ
		JPanel atmMoneyInfoJPanel = new JPanel();
		JTextArea atmMoneyInfoJTextArea = new JTextArea(6, 30);
		
		String atmInfoString = "\n\nThe balance of the ATM is:\t" + atm.getAtmMoney() + "\n" +
									"The printing paper of ATM is:\t" + atm.getAtmPaper() + "\n";
		
		atmMoneyInfoJTextArea.setText(atmInfoString);
		atmMoneyInfoJTextArea.setEditable(false);
		atmMoneyInfoJPanel.add(atmMoneyInfoJTextArea);
		
		// ���غʹ�ӡ��ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(bankStaffNameJPanel);
		verticall.add(atmMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		checkAtmBalancesJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		checkAtmBalancesJFrame.add(verticall);
		checkAtmBalancesJFrame.setVisible(true);				// ��ʾ�ɼ�
		checkAtmBalancesJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		checkAtmBalancesJFrame.setSize(500, 320);				// �����С����
		checkAtmBalancesJFrame.setLocationRelativeTo(null);
		
		// ��ť�����¼�
		// ��ӡ��ť�����¼�
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// �жϴ�ӡֽ�Ƿ��㹻
				if (atm.getAtmPaper() > 0) {
					// ����ATM���Ĵ�ӡֽ
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// ����ATM�ļ�
					atmFile.updateObjectOutputFile(atm);
					
					// ��ʾ�ɹ���Ϣ
					JOptionPane.showMessageDialog(null, "Print successfully!");
					
					checkAtmBalancesJFrame.setVisible(false); // ���ص�ǰ����
					// ���ز�������
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
				}else {
					// ��ʾ��ӡֽ���� ���ܴ�ӡ
					JOptionPane.showMessageDialog(null, "Sorry, No paper!");
				}
			}
		});
		
		// ���ش��ڼ����¼�
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				checkAtmBalancesJFrame.setVisible(false); // ���ص�ǰ����
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
