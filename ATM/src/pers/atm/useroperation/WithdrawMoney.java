package pers.atm.useroperation;

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

import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.MyAtm;
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
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton withdrawalJButton = new JButton("withdrawal");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(withdrawalJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(cashDepsitJPanel);
		verticall.add(Box.createVerticalStrut(20));
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
					JOptionPane.showMessageDialog(outMoneyJFrame, "�������");
					return;
				}
				
				Double outMoney = Double.valueOf(inputMoneyJTextField.getText());
				// �ж�����ȡǮ����Ƿ񳬹��Լ������
				if (user.getAvailableBalances() < outMoney) {
					JOptionPane.showMessageDialog(outMoneyJFrame, "Your account balance is insufficient ! please enter again!");
					inputMoneyJTextField.setText("");
					return;
				}
				
				// ����������
				
				// �����û��ļ� �� ATM����ļ�
				SetAndGetDataFile uFlie = new SetAndGetDataFile();
				
				user.setAvailableBalances(user.getAvailableBalances() - outMoney);
				uFlie.updateObjectOutputFile(user);
				
				// ��ȡATM��Ϣ
				MyAtm atm = uFlie.readObjectInputFile(bankName);
				atm.setAtmMoney(atm.getAtmMoney() - outMoney);
				
				// ����ATM��Ϣ
				uFlie.updateObjectOutputFile(atm);
				
				// ���������Ϣ
				String opString = "Take out " + outMoney + " yuan";
				uFlie.saveOperationData(user.getUserAccountNumber(), opString);
				
				JOptionPane.showMessageDialog(outMoneyJFrame, "Successed!");
				outMoneyJFrame.setVisible(false);  // ���ش˽���
				new ThisBankClientMenu(user, bankName).setThisBankMenu();  // ���ز�������
			}
		});
		
		// ȡ����ť
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				outMoneyJFrame.setVisible(false);  // ���ش˽���
				// ���ز�������
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
		
	}
}
