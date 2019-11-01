package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.MyAtm;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class PutMoneyIntoAtm {
	private JFrame putMoneIntoAtmJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public PutMoneyIntoAtm(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	// �ֽ������
	public void putMoneyIntoAtmInterface()
	{
		putMoneIntoAtmJFrame = new JFrame(bankStaff.getBankStaffId() + " CashDeposit");
		putMoneIntoAtmJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �������
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the amount to be deposited:"));
		JTextField inputMoneyJTextField = new JTextField(15);
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(10));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		
		putMoneIntoAtmJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		putMoneIntoAtmJFrame.add(verticall);
		putMoneIntoAtmJFrame.setVisible(true);				// ��ʾ�ɼ�
		putMoneIntoAtmJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		putMoneIntoAtmJFrame.setSize(500, 320);				// �����С����
		putMoneIntoAtmJFrame.setLocationRelativeTo(null);
		
		// �趨��ť�����¼�
		// ȷ��
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "Please enter the amount!");
					return;
				}
				Double cashMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// ��ȡATM��Ϣ
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				MyAtm atm  = atmFile.readObjectInputFile(bankName);
				
				// ����ATM����ȡ�Ľ��
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// ���������Ϣ
				String opString = "Deposit " + cashMoney + " cash";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// ���ز�������
				JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "successed!");
				putMoneIntoAtmJFrame.setVisible(false);
				
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
		// ����
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				putMoneIntoAtmJFrame.setVisible(false);
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
