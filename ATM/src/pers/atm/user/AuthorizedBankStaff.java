package pers.atm.user;

import java.io.Serializable;

public class AuthorizedBankStaff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1801974707218135817L;
	public String bankStaffId;				// ��Ȩ��Ա��
	public String bankStaffAccountNumber;	// ��Ȩ��Ա�˺�Ŷ
	public String bankStaffPassword;		// ��Ȩ��Ա����
	
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
