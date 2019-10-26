package pers.atm.menu;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.login.AtmLoginInterfane;

public class ThisBankClientMenu {
	public JFrame thisBankMenuJFrame;
	
	public void setThisBankMenu(){
		thisBankMenuJFrame = new JFrame();
		thisBankMenuJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//��ǩ
		JPanel tipJPanel = new JPanel();
		tipJPanel.add(new JLabel("Please select an operation"));
		
		// ���ܰ�ť
		JButton checkMoneyJButton = new JButton("Check Balance");
		JButton saveMoneyJButton = new JButton("Save Money");
		JButton getMoneyJButton = new JButton("Withdraw Money");
		JButton transferMoneyJButton = new JButton("Transfer Money");
		JButton changePasswordJButton = new JButton("Change Password");
		JButton exitLoginJButton = new JButton("Exit Login");
		
		// ���ܰ�ť���
		JPanel menuJPanel = new JPanel();
		
		menuJPanel.add(checkMoneyJButton);
		menuJPanel.add(saveMoneyJButton);
		menuJPanel.add(getMoneyJButton);
		menuJPanel.add(transferMoneyJButton);
		menuJPanel.add(changePasswordJButton);
		menuJPanel.add(exitLoginJButton);
		
		menuJPanel.setLayout(new GridLayout(3, 2, 30, 30));
		
		// ��ֱ����
		Box verticall = Box.createVerticalBox();
		
		verticall.add(tipJPanel);
		verticall.add(Box.createVerticalStrut(30));			// ����һ�����ɼ��Ĺ̶��߶ȵ���� �ſ�����
		verticall.add(menuJPanel);
		
		thisBankMenuJFrame.add(verticall);
		thisBankMenuJFrame.setLayout(new FlowLayout());
		thisBankMenuJFrame.setVisible(true);  			 // ��ʾ�ɼ�
		thisBankMenuJFrame.pack();						// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		thisBankMenuJFrame.setSize(500, 300);
		thisBankMenuJFrame.setLocationRelativeTo(null);
		
		// ע�ᰴť�����¼�
		
		// �鿴����
		checkMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ��Ǯ����
		saveMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ȡǮ����
		getMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ת�˹���
		transferMoneyJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// ��������
		changePasswordJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		// �˳���¼
		exitLoginJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				AtmLoginInterfane login = new AtmLoginInterfane();
				thisBankMenuJFrame.setVisible(false);  // ���ع��ܽ���
				login.loginInterface();     // ��ʾ��¼����
			}
		});
		
	}

}
