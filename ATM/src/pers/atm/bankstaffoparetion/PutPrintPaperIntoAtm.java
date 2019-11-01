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

public class PutPrintPaperIntoAtm {
	private JFrame putPrintPaperIntoAtmJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public PutPrintPaperIntoAtm(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	// �ֽ������
	public void putMoneyIntoAtmInterface()
	{
		putPrintPaperIntoAtmJFrame = new JFrame(bankStaff.getBankStaffId() + " Put Print Paper");
		putPrintPaperIntoAtmJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �������
		JPanel outPaperJPanel = new JPanel();
		outPaperJPanel.add(new Label("Please enter the paper to be stored:"));
		JTextField inputPaperJTextField = new JTextField(15);
		
		inputPaperJTextField.setDocument(new NumberLenghtLimitedDmt(8));
		
		outPaperJPanel.add(inputPaperJTextField);
		
		// ȷ�Ϻͷ��ذ�ť
		JPanel buttonJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JButton okJButton = new JButton("OK");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(okJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(Box.createVerticalStrut(50));
		verticall.add(outPaperJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		
		putPrintPaperIntoAtmJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		putPrintPaperIntoAtmJFrame.add(verticall);
		putPrintPaperIntoAtmJFrame.setVisible(true);				// ��ʾ�ɼ�
		putPrintPaperIntoAtmJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		putPrintPaperIntoAtmJFrame.setSize(500, 320);				// �����С����
		putPrintPaperIntoAtmJFrame.setLocationRelativeTo(null);
		
		// �趨��ť�����¼�
		// ȷ��
		okJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (inputPaperJTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "Please enter the amount!");
					return;
				}
				int paper = Integer.valueOf(inputPaperJTextField.getText().toString());
				
				// ��ȡATM��Ϣ
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				MyAtm atm  = atmFile.readObjectInputFile(bankName);
				
				// ����ATM��ӡֽ
				atm.setAtmPaper(atm.getAtmPaper() + paper);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// ���������Ϣ
				String opString = "Deposit " + paper + " printing papers";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// ���ز�������
				JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "successed!");
				putPrintPaperIntoAtmJFrame.setVisible(false);
				
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
		// ����
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				putPrintPaperIntoAtmJFrame.setVisible(false);
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
