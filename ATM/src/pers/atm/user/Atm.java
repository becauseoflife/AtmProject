package pers.atm.user;

import java.io.Serializable;

public class Atm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4551972467143357704L;
	private String atmBankName;		// ATM���������е�����
	private Double atmMoney;			// ATM���
	private int atmPaper;			// ATM��ӡֽ
	
	public Atm(){};
	
	public Atm(String atmBankName, Double atmMoney, int atmPaper) {
		super();
		this.atmBankName = atmBankName;
		this.atmMoney = atmMoney;
		this.atmPaper = atmPaper;
	}

	public Double getAtmMoney() {
		return atmMoney;
	}

	public void setAtmMoney(Double atmMoney) {
		this.atmMoney = atmMoney;
	}

	public int getAtmPaper() {
		return atmPaper;
	}

	public void setAtmPaper(int atmPaper) {
		this.atmPaper = atmPaper;
	}

	public String getAtmBankName() {
		return atmBankName;
	}
}
