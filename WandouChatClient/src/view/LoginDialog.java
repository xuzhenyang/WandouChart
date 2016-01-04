package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import network.ClientListener;
import control.NetworkCommand;
import exception.BusinessException;
import exception.RemoteException;
import model.TbUser;

public class LoginDialog extends JDialog implements ActionListener
{

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnLogin = new JButton("登录");
	private JButton btnCancel = new JButton("取消");
	private JButton btnRegister = new JButton("注册");
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	private ClientListener clientListener;

	public LoginDialog(Frame owner)
	{
		super(owner, true);
		//界面布局
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		toolBar.add(this.btnRegister);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 140);
		//屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		//      设置监听器
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		edtUserId.addActionListener(this);
		edtPwd.addActionListener(this);

		//添加框架的关闭事件处理
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		clientListener = new ClientListener();

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// TODO 自动生成方法存根
		if (e.getSource() == btnCancel)
			System.exit(0);
		else if (e.getSource() == edtUserId)
		{//用户名输入框中打回车，则光标进入密码输入框
			this.edtPwd.requestFocus();
		}
		else if (e.getSource() == this.btnRegister)
		{
			//实现注册功能，请补充
		}
		else
		{//密码框中打回车或点击登录按钮
			TbUser tbUser = new TbUser();
			tbUser.setUserId(this.edtUserId.getText());
			tbUser.setUserPwd(this.edtPwd.getText());
			tbUser.setIp(clientListener.getIp());
			tbUser.setPort(clientListener.getPort());

			try
			{
				Object[] param = (Object[]) NetworkCommand.getServer().login(
						tbUser);
				NetworkCommand.currentUser = (TbUser) param[0];
				List onlineUsers = (List) param[1];
				this.setVisible(false);
				new MainView(NetworkCommand.currentUser, onlineUsers);
				System.out.println("ip:" + tbUser.getIp() + " : "
						+ tbUser.getPort());
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BusinessException e1)
			{
				// TODO Auto-generated catch block
				//				e1.printStackTrace();
				System.out.println(e1.getMessage());
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public static void main(String[] args)
	{
		LoginDialog dialog = new LoginDialog(null);
	}

}
