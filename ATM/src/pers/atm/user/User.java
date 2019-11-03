package pers.atm.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -56592711779790277L;
	
	private String bankName;				// ��������
	private String userName;				// �û���
	private String userAccountNumber;	// �û��˺�
	private String userPassword;			// �û�����
	private Double availableBalances;	// �û��������
	private Double unAvailableBlances;	// ���������
	private Double loanLimit;  			// ����Ķ��
	private Double repaymentAmount;		// ���������
	
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
		this.loanLimit = 3000.0;
		this.repaymentAmount = 0.0;
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
	
	public Double getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(Double loanLimit) {
		this.loanLimit = loanLimit;
	}

	public Double getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(Double repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
