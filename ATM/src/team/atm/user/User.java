package team.atm.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -56592711779790277L;
	
	private String bankName;				// 银行名字
	private String userName;				// 用户名
	private String userAccountNumber;	// 用户账号
	private String userPassword;			// 用户密码
	private Double money;				// 用户余额
	
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
