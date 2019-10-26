package pers.atm.useroperation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pers.atm.user.User;

public class CheckBalances {
	public JFrame checkBalancesJFrame;
	private User user;
	
	public CheckBalances(){};
	
	public void CheckMoneyInterface()
	{
		checkBalancesJFrame = new JFrame();
		checkBalancesJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 
	}

}
