package pers.atm.useroperation.savemoney;

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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.Atm;
import pers.atm.user.User;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class SaveCashMoney {
	private JFrame cashJFrame;
	private User user;
	private String bankName;
	
	public SaveCashMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// �ֽ������
	public void cashDepositInterface()
	{
		cashJFrame = new JFrame(user.getUserName() + " CashDeposit");
		cashJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �������
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the Cash amount:"));
		JTextField inputMoneyJTextField = new JTextField(15);
		
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		cashDepsitJPanel.add(inputMoneyJTextField);
		
		
		// ���ñ�����밴ť
		JPanel inputBtnJPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		String buttonNameString[] = { "100", "200", "300", "500", "800", "1000" };
		
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
		
		
		cashJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		cashJFrame.add(verticall);
		cashJFrame.setVisible(true);				// ��ʾ�ɼ�
		cashJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		cashJFrame.setSize(500, 320);				// �����С����
		cashJFrame.setLocationRelativeTo(null);
		
		// �趨��ť�����¼�
		// ȷ��
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(cashJFrame, "Please enter the amount!");
					return;
				}
				
				Double cashMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// �ж��Ƿ�Ϊ��
				if (cashMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				// �ж��Ƿ�Ϊ����
				if (cashMoney%100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
				    inputMoneyJTextField.setText("");
				    return;
				}
				
				// �����û������
				user.setAvailableBalances(user.getAvailableBalances() + cashMoney);
				
				// ��ȡATM��Ϣ
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// ����ATM����ȡ�Ľ��
				atm.setAtmMoney(atm.getAtmMoney() + cashMoney);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				updateFile.updateObjectOutputFile(atm);
				
				// ���������Ϣ
				String opString = "Deposit "+ cashMoney +" yuan in Cash";
				updateFile.saveOperationData(user, opString);

				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(cashJFrame, "successed!");
				
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ cashMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				cashJFrame.setVisible(false);
				new PrintCopy(user, bankName, printCopyContextString).printCopyInterface();
				
/*				// ���ز�������
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
				}*/
				
			}
		});
		
		// ����
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				cashJFrame.setVisible(false);
				new SaveMoney(user, bankName).SaveMoneyInterface();
				
			}
		});
		
	}
			
}
