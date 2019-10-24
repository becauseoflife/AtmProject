package team.atm.user;

import java.awt.FlowLayout;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginGui{//实现监听器的接口
    private JFrame frame;
    private JPanel p0,p1,p2,p3,p4;//p4包括确认密码时的输入框；点击register按钮出现

    private JTextField userName;
    private JTextField passWord,passwordCheck;
    private JButton login;
    private JButton register;
    Boolean regirsterable=true;//控制是否能注册的变量


    public  LoginGui() {
        frame=new JFrame("登录ATM");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗口的点击右上角的x的处理方式，这里设置的是退出程序
        p0=new JPanel();

        p0.add(new JLabel("中国邮政储蓄银行ATM"));
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
        frame.add(p4);//确认密码框
        frame.add(p3);


        frame.pack();
        frame.setVisible(true);
        p4.setVisible(true);
        show();
        /*****************************Login****************************/
    }
    public void show(){
        frame.setBounds(500,500,350,250);//设置大小
        frame.setLayout(new FlowLayout());//设置流式布局
    }  
}
    
    
    