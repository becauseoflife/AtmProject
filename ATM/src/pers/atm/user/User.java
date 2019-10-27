package pers.atm.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -56592711779790277L;
	
	private String bankName;				// 银行名字
	private String userName;				// 用户名
	private String userAccountNumber;	// 用户账号
	private String userPassword;			// 用户密码
	private Double availableBalances;	// 用户可用余额
	private Double unAvailableBlances;	// 不可用余额
	
	public User(){};
	
	public User(String bankName, String userName, String userAccountNumber, String userPassword,
			Double availableBalances, Double unavailableBlances) {
		super();
		this.bankName = bankName;
		this.userName = userName;
		this.userAccountNumber = userAccountNumber;
		this.userPassword = userPassword;
		this.availableBalances = availableBalances;
		this.unAvailableBlances = unavailableBlances;
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

	public Double getAvailableBalances() {
		return availableBalances;
	}

	public void setAvailableBalances(Double money) {
		this.availableBalances = money;
	}

	public Double getUnavailableBlances() {
		return unAvailableBlances;
	}

	public void setUnavailableBlances(Double unavailableBlances) {
		unAvailableBlances = unavailableBlances;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
