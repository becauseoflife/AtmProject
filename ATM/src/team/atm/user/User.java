package team.atm.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -56592711779790277L;
	
	private String bankName;				// ��������
	private String userName;				// �û���
	private String userAccountNumber;	// �û��˺�
	private String userPassword;			// �û�����
	private Double money;				// �û����
	
	public User(String bankName, String userName, String userAccountNumber, String userPassword,
			Double money) {
		super();
		this.bankName = bankName;
		this.userName = userName;
		this.userAccountNumber = userAccountNumber;
		this.userPassword = userPassword;
		this.money = money;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccountNumber() {
		return userAccountNumber;
	}

	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
