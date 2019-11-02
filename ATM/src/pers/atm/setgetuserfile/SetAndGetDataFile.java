package pers.atm.setgetuserfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import pers.atm.user.AuthorizedBankStaff;
import pers.atm.user.Atm;
import pers.atm.user.User;

public class SetAndGetDataFile {
	
	public SetAndGetDataFile(){};
	
	// ��User����д���ļ���
	public Boolean saveObjectOutputFile(User newUser)
	{
		String filePath = "UserData/" + newUser.getUserAccountNumber() + ".txt";
		File userFile = new File(filePath);
		if (!userFile.exists()) {
			FileOutputStream writeFile;

			try {
				writeFile = new FileOutputStream(userFile);
				ObjectOutputStream writeObject = new ObjectOutputStream(writeFile);
				
				writeObject.writeObject(newUser);
				writeObject.close();

			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// ��User������ļ��ж���
	public User readUserInputFile(String userAccountNumber)
	{
		String filePath = "UserData/" + userAccountNumber + ".txt";
		File userFile = new File(filePath);
		if (!userFile.exists()) {
			return null;
		}else {
			User user = new User();
			FileInputStream readFile;
			try {
				readFile = new FileInputStream(userFile);
				ObjectInputStream readUser = new ObjectInputStream(readFile);
				
				user = (User) readUser.readObject();
				readUser.close();
				
			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return user;
		}

	}
	
	// ����User����д���ļ���
	public Boolean updateObjectOutputFile(User newUser)
	{
		String filePath = "UserData/" + newUser.getUserAccountNumber() + ".txt";
		File userFile = new File(filePath);
		if (userFile.exists()) 
		{
			FileOutputStream writeFile;

			try {
				writeFile = new FileOutputStream(userFile);
				ObjectOutputStream writeObject = new ObjectOutputStream(writeFile);
				
				writeObject.writeObject(newUser);
				writeObject.close();

			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// ��Atm����д���ļ���
	public boolean saveObjectOutputFile(Atm newAtm)
	{
		String atmFilePath = "ATMdata/" + newAtm.getAtmBankName() + ".txt";
		File atmFile = new File(atmFilePath);
		if (!atmFile.exists()) {
			FileOutputStream writeFile;

			try {
				writeFile = new FileOutputStream(atmFile);
				ObjectOutputStream writeObject = new ObjectOutputStream(writeFile);
				
				writeObject.writeObject(newAtm);
				writeObject.close();

			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return true;
		}else {
			return false;
		}

	}
	
	// ����Atm����д���ļ���
	public boolean updateObjectOutputFile(Atm newAtm)
	{
		String atmFilePath = "ATMdata/" + newAtm.getAtmBankName() + ".txt";
		File atmFile = new File(atmFilePath);
		if (atmFile.exists()) {
			FileOutputStream writeFile;

			try {
				writeFile = new FileOutputStream(atmFile);
				ObjectOutputStream writeObject = new ObjectOutputStream(writeFile);
				
				writeObject.writeObject(newAtm);
				writeObject.close();

			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return true;
		}else {
			return false;
		}

	}
	
	// ��Atm������ļ��ж���
	public Atm readObjectInputFile(String bankName)
	{
		String atmFilePath = "ATMdata/" + bankName + ".txt";
		File atmFile = new File(atmFilePath);
		if (!atmFile.exists()) {
			return null;
		}else {
			Atm atm = new Atm();
			FileInputStream readFile;
			try {
				readFile = new FileInputStream(atmFile);
				ObjectInputStream readAtm = new ObjectInputStream(readFile);
				
				atm = (Atm) readAtm.readObject();
				readAtm.close();
				
			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return atm;
		}
	}
	
	// ��¼�û����в����ļ���
	public void saveOperationData(User user , String operationString)
	{
		String filePath = "UserOperationData/" + user.getUserAccountNumber() + ".txt";
		
		SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
		Date newData = new Date();			//��ǰʱ��
		String datasString = operationData.format(newData);		//����ǰʱ���ʽ
		
		String str = user.getBankName() + " User " + user.getUserAccountNumber() + "\t" + operationString + "\t" +datasString;
		
		try {
			FileOutputStream fos = new FileOutputStream(filePath, true);	// true ׷�ӵķ�ʽд���ļ�
			OutputStreamWriter dos = new OutputStreamWriter(fos);
			BufferedWriter writer = new BufferedWriter(dos);
			
			writer.append(str + "\r\n");	
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	// ��ȡ�û����в����ļ�¼
	public String readOperationData(String userAccountNumber)
	{
		String filePath = "UserOperationData/" + userAccountNumber + ".txt";
		String content = "";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader dis = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(dis);
			
			String str = "";
			
			while ((str = reader.readLine()) != null) {
				//System.out.println(str);
				content = content + str + "\n";
			}
			
			dis.close();
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//System.out.println("����ֵ��" + content);
		return content;
	}
	
	
	// ����Ȩ��Ա����д���ļ���
	public Boolean saveObjectOutputFile(AuthorizedBankStaff bankStaff)
	{
		String filePath = "ATMdata/" + bankStaff.getBankStaffAccountNumber() + ".txt";
		File userFile = new File(filePath);
		if (!userFile.exists()) {
			FileOutputStream writeFile;

			try {
				writeFile = new FileOutputStream(userFile);
				ObjectOutputStream writeObject = new ObjectOutputStream(writeFile);
				
				writeObject.writeObject(bankStaff);
				writeObject.close();

			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// ����Ȩ��Ա������ļ��ж���
	public AuthorizedBankStaff readBankStaffInputFile(String staffAccountSting)
	{
		String filePath = "ATMdata/" + staffAccountSting + ".txt";
		File userFile = new File(filePath);
		if (!userFile.exists()) {
			return null;
		}else {
			AuthorizedBankStaff staff = new AuthorizedBankStaff();
			FileInputStream readFile;
			try {
				readFile = new FileInputStream(userFile);
				ObjectInputStream readUser = new ObjectInputStream(readFile);
				
				staff = (AuthorizedBankStaff) readUser.readObject();
				readUser.close();
				
			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return staff;
		}

	}
	
	// ��¼��Ȩ��Ա���в����ļ���
	public void saveBankStaffOperationData(AuthorizedBankStaff bankStaff , String operationString)
	{
		String filePath = "ATMdata/" + bankStaff.getBankStaffId() + "OperationRecord.txt";
		
		SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//ʱ���ʽ
		Date newData = new Date();			//��ǰʱ��
		String datasString = operationData.format(newData);		//����ǰʱ���ʽ
		
		String str = bankStaff.getBankStaffId() + "\t" + operationString + "\t" +datasString;
		
		try {
			FileOutputStream fos = new FileOutputStream(filePath, true);	// true ׷�ӵķ�ʽд���ļ�
			OutputStreamWriter dos = new OutputStreamWriter(fos);
			BufferedWriter writer = new BufferedWriter(dos);
			
			writer.append(str + "\r\n");	
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	// ��ȡ��Ȩ��Ա���в����ļ�¼
	public String readbankStaffOperationData(String bankStaffId)
	{
		String filePath = "ATMdata/" + bankStaffId + "OperationRecord.txt";
		String content = "";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader dis = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(dis);
			
			String str = "";
			
			while ((str = reader.readLine()) != null) {
				//System.out.println(str);
				content = content + str + "\n";
			}
			
			dis.close();
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//System.out.println("����ֵ��" + content);
		return content;
	}
	
}


