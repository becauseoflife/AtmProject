package pers.atm.main;

/**
 * finished in 2019/11/1
 * by Li Longpan 
 */

import pers.atm.login.AtmLoginInterfane;
import pers.atm.setgetuserfile.SetAndGetDataFile;
import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;

public class AtmMain {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		
		// ʵ����һ��ATM����
		Atm myAtm = new Atm("Bank of China", 10000.0, 500);			
		
		// ��ATM����д���ļ������������������ȡ��Ϣ
		SetAndGetDataFile uFile = new SetAndGetDataFile();	
		if (uFile.saveObjectOutputFile(myAtm)) {
			System.out.println(myAtm.getAtmBankName() + " ATM machine was successfully created!");
		}else{
			// ��ʾ����һ�ҵ����е�ATM
			System.out.println(myAtm.getAtmBankName() + " ATM already exists!");
		}
		
		// ������¼����
		new AtmLoginInterfane(myAtm.getAtmBankName()).loginInterface();
		
		// ������Ȩ��Ա
		AuthorizedBankStaff bankStaff = new AuthorizedBankStaff("001",  "admin", "admin");
		
		// ������Ȩ��Ա
		if (uFile.saveObjectOutputFile(bankStaff)) {
			System.out.println("Authorized person created successfully!");
			// �����Ȩ��ԱID���˺š�����
			System.out.println("Authorized person ID:" + bankStaff.getBankStaffId() );
			System.out.println("Authorized personnel account:" + bankStaff.getBankStaffAccountNumber());
			System.out.println("Authorized personnel password:" + bankStaff.getBankStaffPassword());
		}else{
			System.out.println("Authorized personnel already exist!");	
			// �����Ȩ��ԱID���˺š�����
			System.out.println("Authorized person ID:" + bankStaff.getBankStaffId() );
			System.out.println("Authorized personnel account:" + bankStaff.getBankStaffAccountNumber());
			System.out.println("Authorized personnel password:" + bankStaff.getBankStaffPassword());
		}

	}

}
