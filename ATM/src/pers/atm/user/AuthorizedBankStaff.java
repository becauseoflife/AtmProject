package pers.atm.user;

import java.io.Serializable;

public class AuthorizedBankStaff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1801974707218135817L;
	public String bankStaffId;				// 授权人员号
	public String bankStaffAccountNumber;	// 授权人员账号哦
	public String bankStaffPassword;		// 授权人员密码
	
	public AuthorizedBankStaff() {};
	
	public AuthorizedBankStaff(String bankStaffId, String bankStaffAccountNumber, String bankStaffPassword) {
		super();
		this.bankStaffId = bankStaffId;
		this.bankStaffAccountNumber = bankStaffAccountNumber;
		this.bankStaffPassword = bankStaffPassword;
	}
	
	public String getBankStaffId() {
		return bankStaffId;
	}
	public String getBankStaffAccountNumber() {
		return bankStaffAccountNumber;
	}
	public String getBankStaffPassword() {
		return bankStaffPassword;
	}
	
	
}
