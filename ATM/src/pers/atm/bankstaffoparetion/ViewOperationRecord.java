package pers.atm.bankstaffoparetion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import pers.atm.menu.BankStaffOperationMenu;
import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class ViewOperationRecord {
	private JFrame operationRecordJFrame;
	private AuthorizedBankStaff bankStaff;
	private String bankName;
	
	public ViewOperationRecord(AuthorizedBankStaff bankStaff, String bankName) {
		super();
		this.bankStaff = bankStaff;
		this.bankName = bankName;
	}

	public void viewOperationsInterface()
	{	
		operationRecordJFrame = new JFrame(bankStaff.getBankStaffId() + " View Operation record");
		operationRecordJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�����ʾ
		JPanel staffIdJPanel = new JPanel();
		staffIdJPanel.add(new JLabel(bankStaff.getBankStaffId() + "Operation record" ) );
		
		// �û���ˮ�˵���ʾ
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(10, 40);
		
		String opInfo = "Bank Staff Id\t" + "Operation Context\t" + "Operation Time  \n" ;
		userMoneyInfoJTextArea.setText(opInfo);
		
		// ��ȡ������¼
		SetAndGetDataFile uFile = new SetAndGetDataFile();
		String OperationRecordString = uFile.readbankStaffOperationData(bankStaff.getBankStaffId());
		
		
		userMoneyInfoJTextArea.append(OperationRecordString);
		userMoneyInfoJTextArea.setEditable(false);
		
		// ������
		JScrollPane scroll = new JScrollPane(); 
		scroll.setViewportView(userMoneyInfoJTextArea);
		// ��ֱ�Զ���ʾ
		scroll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		// ˮƽ����
		scroll.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		userMoneyInfoJPanel.add(scroll);
		// ���غʹ�ӡ��ť
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 2, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(staffIdJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(20));
		verticall.add(buttonJPanel);
		
		
		operationRecordJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		operationRecordJFrame.add(verticall);
		operationRecordJFrame.setVisible(true);				// ��ʾ�ɼ�
		operationRecordJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		operationRecordJFrame.setSize(500, 350);				// �����С����
		operationRecordJFrame.setLocationRelativeTo(null);
		
		// ��ť�����¼�
		// ��ӡ��ť�����¼�
		printJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				// ��ȡATM�ļ���Ϣ
				SetAndGetDataFile uFile = new SetAndGetDataFile();
				Atm atm = uFile.readObjectInputFile(bankName); 
				// �жϴ�ӡֽ�Ƿ��㹻
				if (atm.getAtmPaper() > 0) {
					// ����ATM���Ĵ�ӡֽ
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					
					// ����ATM�ļ�
					uFile.updateObjectOutputFile(atm);
					
					// ��ʾ�ɹ���Ϣ
					JOptionPane.showMessageDialog(null, "Print successfully!");
					
					operationRecordJFrame.setVisible(false); // ���ص�ǰ����
					// ���ز�������
					new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
				}else {
					// ��ʾ��ӡֽ���� ���ܴ�ӡ
					JOptionPane.showMessageDialog(null, "Sorry, No paper!");
				}
			}
		});
		
		// ���ش��ڼ����¼�
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				operationRecordJFrame.setVisible(false); // ���ص�ǰ����
				// ���ز�������
				new BankStaffOperationMenu(bankStaff, bankName).setBankStaffOperationMenu();
			}
		});
		
	}
}
