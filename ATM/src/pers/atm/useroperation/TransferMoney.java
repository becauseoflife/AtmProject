package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.printcopy.PrintCopy;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class TransferMoney {
	private JFrame transferMoneyJFrame;
	private User user;
	private String bankName;
	
	public TransferMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void TransferMoneyInterface() 
	{
		transferMoneyJFrame = new JFrame(user.getUserName() + " TransferMoney");
		transferMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ����ת���û�
		JPanel transferUserNameJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		transferUserNameJPanel.add(new JLabel("Transfer User Account:"));
		
		JTextField inputUserAccountJTextField = new JTextField(15);
		inputUserAccountJTextField.setDocument(new NumberLenghtLimitedDmt(7));// ֻ������7λ����
		transferUserNameJPanel.add(inputUserAccountJTextField);
		
		// ��ʾ�������
		JPanel transferMoneyJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		transferMoneyJPanel.add(new JLabel("      Transfer amount:    "));
		
		JTextField inputMoneyJTextField = new JTextField(15);
		inputMoneyJTextField.setDocument(new NumberLenghtLimitedDmt(7));// ֻ������7λ����
		transferMoneyJPanel.add(inputMoneyJTextField);
		
		// ���ñ�����밴ť
		JPanel inputBtnJPanel = new JPanel(new GridLayout(2, 9, 10, 10));
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
		JButton transferJButton = new JButton("Transfer");
		JButton backJButton = new JButton("Cancle");
		buttonJPanel.add(transferJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(transferUserNameJPanel);
		verticall.add(transferMoneyJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(40));
		verticall.add(buttonJPanel);
		
		transferMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		transferMoneyJFrame.add(verticall);
		transferMoneyJFrame.setVisible(true);				// ��ʾ�ɼ�
		transferMoneyJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		transferMoneyJFrame.setSize(550, 350);				// �����С����
		transferMoneyJFrame.setLocationRelativeTo(null);
		
		
		// ע������¼�
		// ת�˰�ť
		transferJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
				// �ж��Ƿ���������Ϣ
				if (inputUserAccountJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Please enter account!");
					return;
				}
				else if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Please enter the transfer amount!");
					return;
				}
				
				String uAccountNumber = inputUserAccountJTextField.getText();
				Double transMoney = Double.valueOf(inputMoneyJTextField.getText());
				
				// �жϴ��û��Ƿ����
				SetAndGetDataFile uFile = new SetAndGetDataFile();
				// �ж������Ƿ�Ϸ�
				if ( uFile.readUserInputFile(uAccountNumber) == null) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "This user doesn't exist ! please enter again!");
					inputUserAccountJTextField.setText("");
					return;
				}
				// ������û����Լ�
				else if (uFile.readUserInputFile(uAccountNumber).getUserAccountNumber().equals(user.getUserAccountNumber())) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Can't transfer to myself ! please enter again!");
					inputUserAccountJTextField.setText("");
					return;
				}
				// �ж������Ƿ�Ϊ���ٵ�����
				else if (transMoney % 100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
					inputMoneyJTextField.setText("");
					return;
				}
				// ����Ľ����Լ����˻����
				else if (user.getAvailableBalances() < transMoney) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				//  ������������	
				User transferUser = uFile.readUserInputFile(uAccountNumber);
				// ����ת���ߺͱ�ת�����û����
				transferUser.setAvailableBalances(transferUser.getAvailableBalances() + transMoney);
				user.setAvailableBalances(user.getAvailableBalances() - transMoney);
				
				// �����ļ�
				uFile.updateObjectOutputFile(transferUser);
				uFile.updateObjectOutputFile(user);
				
				// ��¼�û��Ĳ���
				String opString = "Receive " + transMoney + " yuan form " + user.getUserAccountNumber();
				uFile.saveOperationData(transferUser, opString);
				
				opString = "Transfer " + transMoney +" yuan to " + transferUser.getUserAccountNumber();
				uFile.saveOperationData(user, opString);
				
				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(transferMoneyJFrame, "seccussed!");
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Transfer amount:\t" 			+ transMoney				     + "\n" +
												"Transfer To User Account:\t"	+ transferUser.getUserAccountNumber()+ "\n"+
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				transferMoneyJFrame.setVisible(false); //���ش˽���
				new PrintCopy(user, bankName, printCopyContextString).printCopyInterface();
				
				
/*				// ���ز�������
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
				}*/

			}
		});
		
		// ���ذ�ť
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				transferMoneyJFrame.setVisible(false); //���ش˽���
					
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
