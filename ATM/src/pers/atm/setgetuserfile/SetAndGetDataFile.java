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
	
	// 将User对象写入文件中
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// 将User对象从文件中读出
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return user;
		}

	}
	
	// 更新User对象写入文件中
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// 将Atm对象写入文件中
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return true;
		}else {
			return false;
		}

	}
	
	// 更新Atm对象写入文件中
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return true;
		}else {
			return false;
		}

	}
	
	// 将Atm对象从文件中读出
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return atm;
		}
	}
	
	// 记录用户进行操作文件的
	public void saveOperationData(User user , String operationString)
	{
		String filePath = "UserOperationData/" + user.getUserAccountNumber() + ".txt";
		
		SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
		Date newData = new Date();			//当前时间
		String datasString = operationData.format(newData);		//处理当前时间格式
		
		String str = user.getBankName() + " User " + user.getUserAccountNumber() + "\t" + operationString + "\t" +datasString;
		
		try {
			FileOutputStream fos = new FileOutputStream(filePath, true);	// true 追加的方式写入文件
			OutputStreamWriter dos = new OutputStreamWriter(fos);
			BufferedWriter writer = new BufferedWriter(dos);
			
			writer.append(str + "\r\n");	
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	// 读取用户进行操作的记录
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//System.out.println("返回值：" + content);
		return content;
	}
	
	
	// 将授权人员对象写入文件中
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	// 将授权人员对象从文件中读出
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return staff;
		}

	}
	
	// 记录授权人员进行操作文件的
	public void saveBankStaffOperationData(AuthorizedBankStaff bankStaff , String operationString)
	{
		String filePath = "ATMdata/" + bankStaff.getBankStaffId() + "OperationRecord.txt";
		
		SimpleDateFormat operationData = new SimpleDateFormat("yy-MM-dd HH:mm:ss");	//时间格式
		Date newData = new Date();			//当前时间
		String datasString = operationData.format(newData);		//处理当前时间格式
		
		String str = bankStaff.getBankStaffId() + "\t" + operationString + "\t" +datasString;
		
		try {
			FileOutputStream fos = new FileOutputStream(filePath, true);	// true 追加的方式写入文件
			OutputStreamWriter dos = new OutputStreamWriter(fos);
			BufferedWriter writer = new BufferedWriter(dos);
			
			writer.append(str + "\r\n");	
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	// 读取授权人员进行操作的记录
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//System.out.println("返回值：" + content);
		return content;
	}
	
}


