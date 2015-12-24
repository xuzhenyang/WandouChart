package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.NetworkCommand;
import exception.BusinessException;
import exception.RemoteException;
import model.TbUser;

public class RegisterDialog extends JDialog implements ActionListener
{

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnRegister = new JButton("注册");
	private JButton btnCancel = new JButton("取消");
	private JLabel labelUserId = new JLabel("用户名：");
	private JLabel labelUserPwd = new JLabel("密    码：");
	private JLabel labelUserName = new JLabel("昵    称：");
	private JTextField edtUserId = new JTextField(15);
	private JPasswordField edtUserPwd = new JPasswordField(15);
	private JTextField edtUserName = new JTextField(15);

	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;

	public RegisterDialog(Frame owner)
	{
		super(owner, true);
		//界面布局
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnRegister);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUserId);
		workPane.add(edtUserId);
		workPane.add(labelUserPwd);
		workPane.add(edtUserPwd);
		workPane.add(labelUserName);
		workPane.add(edtUserName);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(260, 160);
		//屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		//      设置监听器
		btnRegister.addActionListener(this);
		btnCancel.addActionListener(this);

		System.out.println("test");
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// TODO 自动生成方法存根
		if (e.getSource() == btnCancel)
			System.exit(0);
		else if (e.getSource() == this.btnRegister)
		{
			//实现注册功能
			TbUser tbUser = new TbUser();
			tbUser.setUserId(this.edtUserId.getText());
			tbUser.setUserPwd(this.edtUserPwd.getText());
			tbUser.setUserName(this.edtUserName.getText());

			try
			{
				NetworkCommand.getServer().register(tbUser);
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BusinessException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		RegisterDialog dialog = new RegisterDialog(null);
	}

}
