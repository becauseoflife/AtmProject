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

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.SaveMoney;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class SaveCheckMoney {
	private JFrame checkJFrame;
	private User user;
	private String bankName;
	
	public SaveCheckMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}

	// �ֽ������
	public void checkDepositInterface()
	{
		checkJFrame = new JFrame(user.getUserName() + " Check Deposit");
		checkJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ���븶������
		JPanel paymentBankJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		paymentBankJPanel.add(new Label("Payment Bank Name:"));
		JTextField paymentBankJTextField = new JTextField(15);
		paymentBankJPanel.add(paymentBankJTextField);
		
		// �����Ʊ���˺�
		JPanel drawerAccountJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		drawerAccountJPanel.add(new Label("Drawer Account:"));
		JTextField drawerAccountJTextField = new JTextField(15);

		drawerAccountJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		drawerAccountJPanel.add(drawerAccountJTextField);
		
		// ������
		JPanel checkDepsitJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		checkDepsitJPanel.add(new Label("Amount Of Check:"));
		JTextField inputMoneyJTextField = new JTextField(15);

		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));
		
		checkDepsitJPanel.add(inputMoneyJTextField);
		
		// ������;
		JPanel useJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		useJPanel.add(new Label("Purpose of Check:"));
		JTextField useJTextField = new JTextField(15);
		useJPanel.add(useJTextField);
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(paymentBankJPanel);
		verticall.add(drawerAccountJPanel);
		verticall.add(checkDepsitJPanel);
		verticall.add(useJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		checkJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		checkJFrame.add(verticall);
		checkJFrame.setVisible(true);				// ��ʾ�ɼ�
		checkJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		checkJFrame.setSize(500, 320);				// �����С����
		checkJFrame.setLocationRelativeTo(null);
		
		// �趨��ť�����¼�
		// ȷ��
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// �ж���������Ϣ�Ƿ�Ϊ��
				if (paymentBankJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Name of the payment bank!");
					return;
				}
				else if (drawerAccountJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the drawer's account number!");
					return;
				}
				else if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the amount of the check!");
					return;
				}
				else if (useJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(checkJFrame, "Please enter the purpose of the check!");
					return;
				}
				
				Double checkMoney = Double.valueOf(inputMoneyJTextField.getText().toString());
				
				// �ж��Ƿ�Ϊ��
				if (checkMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, Please enter the amount of the check!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// �����û��Ĳ��������
				user.setUnavailableBlances(user.getUnavailableBlances() + checkMoney);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(user);
				
				String opString = "Deposit "+ checkMoney +" in Check";
				updateFile.saveOperationData(user, opString);

				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(checkJFrame, "Waiting for verification!");
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// �ռ�����������
				String paymentBankString = paymentBankJTextField.getText();
				String drawerAccountString = drawerAccountJTextField.getText();
				String purposeString = useJTextField.getText();
				
				// ��ӡ����
				String printCopyContextString = "\t"+ user.getBankName() + " of User\n" +
												"Account Number:\t"   	 + user.getUserAccountNumber()	 + "\n" +
												"Payment Bank Name:\t"	 + paymentBankString			 + "\n" +
												"Drawer Account:\t"		 + drawerAccountString			 + "\n" +
												"Amount Of Check:\t" 	 + checkMoney					 + "\n" +
												"Purpose of Check:\t"	 + purposeString				 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 	 + datasString;
				
				// �򿪴�ӡ����
				checkJFrame.setVisible(false);
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
				checkJFrame.setVisible(false);
				new SaveMoney(user, bankName).SaveMoneyInterface();
				
			}
		});
		
	}
			
}
