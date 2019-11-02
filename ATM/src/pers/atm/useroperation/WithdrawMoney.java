package pers.atm.useroperation;

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
import pers.atm.user.Atm;
import pers.atm.user.User;
import pers.atm.useroperation.inputlimitclass.NumberLenghtLimitedDmt;

public class WithdrawMoney {
	private JFrame outMoneyJFrame;
	private User user;
	private String bankName;
	
	public WithdrawMoney(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void outMoneyInterface()
	{
		outMoneyJFrame = new JFrame(user.getUserName() + " WithdrawMoney");
		outMoneyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �������
		JPanel cashDepsitJPanel = new JPanel();
		cashDepsitJPanel.add(new Label("Please enter the withdrawal amount:"));
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
		JButton withdrawalJButton = new JButton("withdrawal");
		JButton backJButton = new JButton("Cancle");
		buttonJPanel.add(withdrawalJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(buttonJPanel);
		
		
		outMoneyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		outMoneyJFrame.add(verticall);
		outMoneyJFrame.setVisible(true);				// ��ʾ�ɼ�
		outMoneyJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		outMoneyJFrame.setSize(500, 320);				// �����С����
		outMoneyJFrame.setLocationRelativeTo(null);
		
		// ע������¼�
		// ȡǮ��ť
		withdrawalJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// �ж��û��Ƿ�����
				if (inputMoneyJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Please enter the amount!");
					return;
				}
				
				// �����û��ļ� �� ATM����ļ�
				SetAndGetDataFile uFlie = new SetAndGetDataFile();
				
				// ��ȡATM��Ϣ
				Atm atm = uFlie.readObjectInputFile(bankName);
				
				Double outMoney = Double.valueOf(inputMoneyJTextField.getText());
				// �ж�����ȡǮ����Ƿ񳬹��Լ������
				if (user.getAvailableBalances() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				// �ж�ATM����Ƿ��㹻
				else if (atm.getAtmMoney() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "ATM balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// �ж��Ƿ�Ϊ��
				if (outMoney == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// �ж��Ƿ�Ϊ����
				if (outMoney % 100 != 0) {
					JOptionPane.showMessageDialog(null, "Can only enter a whole hundred numbers! Please re-enter!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// ����������
				
				user.setAvailableBalances(user.getAvailableBalances() - outMoney);
				uFlie.updateObjectOutputFile(user);
				
				// ����ATM���
				atm.setAtmMoney(atm.getAtmMoney() - outMoney);
				
				// ����ATM��Ϣ
				uFlie.updateObjectOutputFile(atm);
				
				// ���������Ϣ
				String opString = "Take out " + outMoney + " yuan";
				uFlie.saveOperationData(user, opString);
				
				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(outMoneyJFrame, "Successed!");
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + user.getBankName() + " of User\n" +
												"Account Number:\t" 			+ user.getUserAccountNumber()    + "\n" +
												"Take Out:\t\t" 			+ outMoney						 + "\n" +
												"Available Account Balance:\t" 	+ user.getAvailableBalances()	 + "\n" +
												"Unavailable Account Balance:\t"+ user.getUnavailableBlances()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				outMoneyJFrame.setVisible(false);  // ���ش˽���
				new PrintCopy(user, bankName, printCopyContextString).printCopyInterface();
				
/*				// ���ز�������
				if (user.getBankName().equals(bankName)) {
					new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
				}else {
					new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
				}*/
			}
		});
		
		// ȡ����ť
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				outMoneyJFrame.setVisible(false);  // ���ش˽���
				
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
