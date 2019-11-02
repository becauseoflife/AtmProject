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
		// TODO 自动生成的方法存根
		
		// 实例化一个ATM对象
		Atm myAtm = new Atm("Bank of China", 10000.0, 500);			
		
		// 将ATM对象写入文件，，方便其他界面获取信息
		SetAndGetDataFile uFile = new SetAndGetDataFile();	
		if (uFile.saveObjectOutputFile(myAtm)) {
			System.out.println(myAtm.getAtmBankName() + " ATM machine was successfully created!");
		}else{
			// 提示是哪一家的银行的ATM
			System.out.println(myAtm.getAtmBankName() + " ATM already exists!");
		}
		
		// 创建登录界面
		new AtmLoginInterfane(myAtm.getAtmBankName()).loginInterface();
		
		// 创建授权人员
		AuthorizedBankStaff bankStaff = new AuthorizedBankStaff("001",  "admin", "admin");
		
		// 保存授权人员
		if (uFile.saveObjectOutputFile(bankStaff)) {
			System.out.println("Authorized person created successfully!");
			// 输出授权人员ID、账号、密码
			System.out.println("Authorized person ID:" + bankStaff.getBankStaffId() );
			System.out.println("Authorized personnel account:" + bankStaff.getBankStaffAccountNumber());
			System.out.println("Authorized personnel password:" + bankStaff.getBankStaffPassword());
		}else{
			System.out.println("Authorized personnel already exist!");	
			// 输出授权人员ID、账号、密码
			System.out.println("Authorized person ID:" + bankStaff.getBankStaffId() );
			System.out.println("Authorized personnel account:" + bankStaff.getBankStaffAccountNumber());
			System.out.println("Authorized personnel password:" + bankStaff.getBankStaffPassword());
		}

	}

}
