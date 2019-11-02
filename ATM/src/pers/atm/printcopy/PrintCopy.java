package pers.atm.printcopy;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.element.Element;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.menu.OtherBankClientMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class PrintCopy {
	private JFrame printCopyJFrame;
	private User user;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	private String printCopyContextString;
	
	// ���캯��
	public PrintCopy(User user, String bankName, String printCopyContextString) {
		super();
		this.user = user;
		this.bankName = bankName;
		this.printCopyContextString = printCopyContextString;
		this.bankStaff = null;		// ʹ������Ȩ��ԱΪ��
	}
	
	// �ع�
	public PrintCopy(AuthorizedBankStaff bankStaff, String bankName, String printCopyContextString) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
		this.printCopyContextString = printCopyContextString;
		this.user = null;		// ʹ�û��˺�Ϊ��
	}
	
	public void printCopyInterface()
	{
		if (bankStaff == null) {
			printCopyJFrame = new JFrame(user.getUserName() + " Print Copy");
		}else {
			printCopyJFrame = new JFrame(bankStaff.getBankStaffId() + "Print Copy");
		}
		
		printCopyJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�����ʾ
		JPanel infoJPanel = new JPanel();
		infoJPanel.add(new JLabel("Print the copy as"));
		
		// �û��˺ź������ʾ
		JPanel printCopyInfoJPanel = new JPanel();
		JTextArea printInfoInfoJTextArea = new JTextArea(11, 30);
		
		printInfoInfoJTextArea.setText("\n");
		
		// ��ʾ��ӡ�ĸ�������
		printInfoInfoJTextArea.append(printCopyContextString);
		
		printInfoInfoJTextArea.setEditable(false);
		printCopyInfoJPanel.add(printInfoInfoJTextArea);
		
		// ���غʹ�ӡ��ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton cancleJButton = new JButton("Cancle");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(cancleJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(infoJPanel);
		verticall.add(printCopyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		printCopyJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		printCopyJFrame.add(verticall);
		printCopyJFrame.setVisible(true);				// ��ʾ�ɼ�
		printCopyJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		printCopyJFrame.setSize(550, 370);				// �����С����
		printCopyJFrame.setLocationRelativeTo(null);
		
		// ��ť�����¼�
		// ��ӡ��ť�����¼�
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// ��ȡATM����Ϣ�ļ�
				SetAndGetDataFile atmFile = new SetAndGetDataFile();
				Atm atm = atmFile.readObjectInputFile(bankName);
				
				// �жϴ�ӡֽ�Ƿ��㹻
				if (atm.getAtmPaper() > 0) {
					// ����ATM���Ĵ�ӡֽ
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// ����ATM�ļ�
					atmFile.updateObjectOutputFile(atm);
					
					// ��ʾ�ɹ���Ϣ
					JOptionPane.showMessageDialog(printCopyJFrame, "Print successfully!");
					
					printCopyJFrame.setVisible(false); // ���ص�ǰ����
					// ���ز�������
					if (bankStaff == null) {	// �û�ʹ�ô�ӡ����
						if (user.getBankName().equals(bankName)) {
							new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
						}
						else{
							new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
						}
					}else {		// ��Ȩ��Աʹ�ô�ӡ����
						new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu(); // �򿪲�����Ա����
					}
				}else {
					// ��ʾ��ӡֽ���� ���ܴ�ӡ
					JOptionPane.showMessageDialog(printCopyJFrame, "Sorry, No paper!");
				}
				
			}
		});
		
		// ���ش��ڼ����¼�
		cancleJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				printCopyJFrame.setVisible(false); // ���ص�ǰ����
				// ���ز�������
				if (bankStaff == null) {
					if (user.getBankName().equals(bankName)) {
						new ThisBankClientMenu(user, bankName).setThisBankMenu();	// �����в�������
					}
					else{
						new OtherBankClientMenu(user, bankName).setOtherBankMenu(); // �������в�������
					}
				}else {
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu(); // �򿪲�����Ա����
				}
			}
		});
		
	}

}
