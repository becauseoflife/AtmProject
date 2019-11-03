package pers.atm.useroperation.loan;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.LoansAndRepayments;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class Repayments {
	private JFrame repaymentsJFrame;
	private User user;
	private String bankName;
	
	public Repayments(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// �ֽ������
	public void repaymentsInterface()
	{
		repaymentsJFrame = new JFrame(user.getUserName() + " Return Loan");
		repaymentsJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ��ʾ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("You need to repay the loan"));
		
		// ��������ʾ��
		JPanel repaymentsJPanel = new JPanel();
		JTextArea repaymentsLimitJTextArea = new JTextArea(1, 15);
		
		String returnLoanLimitString = "\t" + user.getRepaymentAmount() + "\t          ";
		repaymentsLimitJTextArea.setText(returnLoanLimitString);
		repaymentsLimitJTextArea.setEditable(false);
		
		repaymentsJPanel.add(repaymentsLimitJTextArea);
		
		// ����������
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the amount of the loan you want to repay:"));
		JTextField inputLoanMoneyJTextField = new JTextField(15);
		
		inputLoanMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(4));
		
		cashDepsitJPanel.add(inputLoanMoneyJTextField);
		
		
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
					inputLoanMoneyJTextField.setText(e.getActionCommand());
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
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(15));
		verticall.add(repaymentsJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(buttonJPanel);
		
		
		repaymentsJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		repaymentsJFrame.add(verticall);
		repaymentsJFrame.setVisible(true);				// ��ʾ�ɼ�
		repaymentsJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		repaymentsJFrame.setSize(570, 370);				// �����С����
		repaymentsJFrame.setLocationRelativeTo(null);
		
		// �趨��ť�����¼�
		// ȷ��
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (inputLoanMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(repaymentsJFrame, "Please enter the amount of the loan!");
					return;
				}
				
				Double returnLoanMoney = Double.valueOf(inputLoanMoneyJTextField.getText().toString());
				
				// �ж��Ƿ�Ϊ��
				if (returnLoanMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, Please enter the amount of the loan!");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				// �ж��Ƿ�Ϊ����
				if (returnLoanMoney%100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
				    inputLoanMoneyJTextField.setText("");
				    return;
				}
				// �ж��Ƿ񳬹�����Ҫ�����������
				if (user.getRepaymentAmount() < returnLoanMoney) {
					JOptionPane.showMessageDialog(repaymentsJFrame, "The amount you entered exceeds the amount you need to repay the loan,\n please re-enter");
					inputLoanMoneyJTextField.setText("");
					return;
				}
				
				
				// �����û������
				user.setAvailableBalances(user.getAvailableBalances() - returnLoanMoney);
				user.setLoanLimit(user.getLoanLimit() + returnLoanMoney);
				user.setRepaymentAmount(user.getRepaymentAmount() - returnLoanMoney);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				
				// ���������Ϣ
				String opString = "Return Loan "+ returnLoanMoney ;
				updateFile.saveOperationData(user, opString);

				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(repaymentsJFrame, "successed!");
				
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Return Loan :\t\t" 			+ returnLoanMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Repayment amount:\t" 			+ user.getRepaymentAmount() 	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				repaymentsJFrame.setVisible(false);
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
				repaymentsJFrame.setVisible(false);
				new LoansAndRepayments(user, bankName).loansAndRepaymentsInterface();
				
			}
		});
		
	}
}
