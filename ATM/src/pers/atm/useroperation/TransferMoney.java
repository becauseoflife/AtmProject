package pers.atm.useroperation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		JButton transferJButton = new JButton("Transfer");
		JButton backJButton = new JButton("Cancle");
		buttonJPanel.add(transferJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(transferUserNameJPanel);
		verticall.add(Box.createVerticalStrut(10));
		verticall.add(transferMoneyJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		transferMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		transferMoneyJFrame.add(verticall);
		transferMoneyJFrame.setVisible(true);				// ��ʾ�ɼ�
		transferMoneyJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		transferMoneyJFrame.setSize(500, 320);				// �����С����
		transferMoneyJFrame.setLocationRelativeTo(null);
		
		
		// ע������¼�
		// ת�˰�ť
		transferJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
				// �ж��Ƿ���������Ϣ
				if (inputUserAccountJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "�������˻���");
					return;
				}
				else if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(transferMoneyJFrame, "������ת�˽�");
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
				String opString = "Received " + transMoney + " yuan from "+ user.getUserAccountNumber()+" User transfers";
				uFile.saveOperationData(transferUser, opString);
				
				opString = "Transfer " + transMoney +" yuan to " + transferUser.getUserAccountNumber() + " User";
				uFile.saveOperationData(user, opString);
				
				JOptionPane.showMessageDialog(transferMoneyJFrame, "seccussed!");
				transferMoneyJFrame.setVisible(false); //���ش˽���

				// ���ز�������
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
				}

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
