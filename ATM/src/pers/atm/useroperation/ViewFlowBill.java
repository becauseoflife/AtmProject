package pers.atm.useroperation;

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

import pers.atm.menu.ThisBankClientMenu;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class ViewFlowBill {
	private JFrame viewWaterBillJFrame;
	private User user;
	private String bankName;
	
	public ViewFlowBill(User user, String bankName) {
		super();
		this.user = user;
		this.bankName = bankName;
	}
	
	public void ViewWaterBillInterface()
	{	
		viewWaterBillJFrame = new JFrame(user.getUserName() + " View Flow Bill");
		viewWaterBillJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// �û�����ʾ
		JPanel userNameJPanel = new JPanel();
		userNameJPanel.add(new JLabel("Dear user:" + user.getUserName()));
		
		// �û���ˮ�˵���ʾ
		JPanel userMoneyInfoJPanel = new JPanel();
		JTextArea userMoneyInfoJTextArea = new JTextArea(10, 45);
		
		String opInfo = "User Account\t\t" + "Operation Context\t" + "Operation Time  \n" ;
		userMoneyInfoJTextArea.setText(opInfo);
		
		SetAndGetDataFile uFile = new SetAndGetDataFile();
		String waterBillString = uFile.readOperationData(user.getUserAccountNumber());
		// ����������ˮ�˵������ı�������ȥ
		userMoneyInfoJTextArea.append(waterBillString);
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
		JPanel buttonJPanel = new JPanel(new GridLayout(1, 10, 20, 10));
		JButton printJButton = new JButton("Print");
		JButton backJButton = new JButton("Back");
		buttonJPanel.add(printJButton);
		buttonJPanel.add(backJButton);
		
		Box verticall = Box.createVerticalBox();
		
		verticall.add(userNameJPanel);
		verticall.add(userMoneyInfoJPanel);
		verticall.add(Box.createVerticalStrut(30));
		verticall.add(buttonJPanel);
		
		
		viewWaterBillJFrame.setLayout(new FlowLayout(FlowLayout.CENTER));		// ������ʽ����
		viewWaterBillJFrame.add(verticall);
		viewWaterBillJFrame.setVisible(true);				// ��ʾ�ɼ�
		viewWaterBillJFrame.pack();  	 			// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		viewWaterBillJFrame.setSize(600, 350);				// �����С����
		viewWaterBillJFrame.setLocationRelativeTo(null);
		
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
					// ����ATM���Ĵ�ӡֽ����Ϣ
					atm.setAtmPaper(atm.getAtmPaper() - 1);
					// ����ATM�ļ���Ϣ
					uFile.updateObjectOutputFile(atm);
					// ��ʾ��ӡ�ɹ�
					JOptionPane.showMessageDialog(viewWaterBillJFrame, "Print successfully!");
					
					viewWaterBillJFrame.setVisible(false); // ���ص�ǰ����
					// ���ز�������
					new ThisBankClientMenu(user, bankName).setThisBankMenu();
				}else {
					// ��ʾ��ӡֽ���� ���ܴ�ӡ
					JOptionPane.showMessageDialog(viewWaterBillJFrame, "Sorry, No paper!");
				}
			}
		});
		
		// ���ش��ڼ����¼�
		backJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				viewWaterBillJFrame.setVisible(false); // ���ص�ǰ����
				// ���ز�������
				new ThisBankClientMenu(user, bankName).setThisBankMenu();
			}
		});
		
	}
}
