package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
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
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(8));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		// ���ñ�����밴ť
		JPanel inputBtnJPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		String buttonNameString[] = { "10000", "20000", "30000", "50000", "80000", "100000" };
		
		JButton buttons[] = new JButton[buttonNameString.length];
		for (int i = 0; i < buttonNameString.length; i++) {
			buttons[i] = new JButton(buttonNameString[i]);
			buttons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO �Զ����ɵķ������
					inputMoneyJTextField.setText(e.getActionCommand());
				}
			});
			inputBtnJPanel.add(buttons[i]);
		}
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
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
				
				// �ж��Ƿ�����Ϊ0
				if (cashMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				// �ж������Ƿ�Ϊ����
				if (cashMoney % 100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// ��ȡATM��Ϣ
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// ����ATM����ȡ�Ľ��
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// ���������Ϣ
				String opString = "Deposit " + cashMoney + " cash";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(putMoneIntoAtmJFrame, "successed!");
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + bankName + " of User\n" +
												"Bank Staff Account Number:\t" 	+ bankStaff.getBankStaffAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ cashMoney			 + "\n" +
												"ATM balance:\t\t" 				+ atm.getAtmMoney()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				putMoneIntoAtmJFrame.setVisible(false);
				new PrintCopy(bankStaff, bankName, printCopyContextString).printCopyInterface();
				
/*				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();*/
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
