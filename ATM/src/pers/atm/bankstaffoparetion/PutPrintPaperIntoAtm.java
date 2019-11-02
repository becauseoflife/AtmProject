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
		
		inputPaperJTextField.setDocument(new NumberLenghtLimitedDmt(6));
		
		outPaperJPanel.add(inputPaperJTextField);
		
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
					inputPaperJTextField.setText(e.getActionCommand());
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
		verticall.add(outPaperJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(inputBtnJPanel);
		verticall.add(Box.createVerticalStrut(50));
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
				
				// �ж��Ƿ�����Ϊ0
				if (paper == 0) {
					JOptionPane.showMessageDialog(null, "Cannot enter 0, please enter amount!");
					inputPaperJTextField.setText("");
					return;
				}
				
				// ��ȡATM��Ϣ
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm  = atmFile.readObjectInputFile(bankName);
				
				// ����ATM��ӡֽ
				atm.setAtmPaper(atm.getAtmPaper() + paper);
				
				// �����ļ�
				SetAndGetDataFile updateFile = new SetAndGetDataFile();
				updateFile.updateObjectOutputFile(atm);

				// ���������Ϣ
				String opString = "Deposit " + paper + " printing papers";
				updateFile.saveBankStaffOperationData(bankStaff, opString);
				
				// ��ʾ�ɹ�
				JOptionPane.showMessageDialog(putPrintPaperIntoAtmJFrame, "successed!");
				
				// ��ȡ��Ǯ��ʱ�� ��ӡ������Ҫ��ʱ��
				SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
				Date newData = new Date();			//��ǰʱ��
				String datasString = operationData.format(newData);		//����ǰʱ���ʽ
				
				// ��ӡ����������
				String printCopyContextString = "\t" + bankName + " of User\n" +
												"Bank Staff Account Number:\t" 	+ bankStaff.getBankStaffAccountNumber()    + "\n" +
												"Deposit Cash:\t\t" 			+ paper				 + "\n" +
												"ATM printing paper:\t" 		+ atm.getAtmPaper()	 + "\n" +
												"Operating Time:\t" 			+ datasString;
				
				// �򿪴�ӡ����
				putPrintPaperIntoAtmJFrame.setVisible(false);
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
				putPrintPaperIntoAtmJFrame.setVisible(false);
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
