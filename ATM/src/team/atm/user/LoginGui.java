package team.atm.user;

import java.awt.FlowLayout;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginGui{//ʵ�ּ������Ľӿ�
    private JFrame frame;
    private JPanel p0,p1,p2,p3,p4;//p4����ȷ������ʱ������򣻵��register��ť����

    private JTextField userName;
    private JTextField passWord,passwordCheck;
    private JButton login;
    private JButton register;
    Boolean regirsterable=true;//�����Ƿ���ע��ı���


    public  LoginGui() {
        frame=new JFrame("��¼ATM");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//���ô��ڵĵ�����Ͻǵ�x�Ĵ���ʽ���������õ����˳�����
        p0=new JPanel();

        p0.add(new JLabel("�й�������������ATM"));
        frame.add(p0);

        p1=new JPanel();
        p1.add(new JLabel("\tUserName:"));
        userName=new JTextField(20);
        p1.add(userName);

        p2=new JPanel();
        p2.add(new JLabel("\tPassword:"));
        passWord=new JTextField(20);
        p2.add(passWord);


        p3=new JPanel();

        login=new JButton("     Login    ");
        register=new JButton("   Register   ");
        p3.add(login);
        p3.add(register);

        p4=new JPanel();
        p4.add(new JLabel("PasswordCheck:"));
        passwordCheck=new JTextField(20);
        p4.add(passwordCheck);


        frame.add(p1);
        frame.add(p2);
        frame.add(p4);//ȷ�������
        frame.add(p3);


        frame.pack();
        frame.setVisible(true);
        p4.setVisible(true);
        show();
        /*****************************Login****************************/
    }
    public void show(){
        frame.setBounds(500,500,350,250);//���ô�С
        frame.setLayout(new FlowLayout());//������ʽ����
    }  
}
    
    
    