package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JTextArea;

import model.*;
import control.ClientLoginHandler;
import control.UserManager;
import control.NetworkCommand;
import dao.TbUserDAO;
import exception.BusinessException;

public class ServerHandleThread extends Thread
{
	private Socket socket;
	private JTextArea textArea;

	public ServerHandleThread(Socket socket, JTextArea textArea)
	{
		this.socket = socket;
		this.textArea = textArea;
	}

	public void run()
	{
		//		textArea.append("ServerHandleThread start\n");
		try
		{
			//			textArea.append("init input/output\n");
			ObjectInputStream inputFromCLient = null;
			ObjectOutputStream outputToCLient = null;
			try
			{
				//				textArea.append("get input/output\n");
				inputFromCLient = new ObjectInputStream(socket.getInputStream());
				outputToCLient = new ObjectOutputStream(
						socket.getOutputStream());

				//从客户端读取网络请求信息
				NetworkPackage cmd = (NetworkPackage) (inputFromCLient
						.readObject());
				InetAddress inetAddress = socket.getInetAddress();
				textArea.append("inetAddress: " + inetAddress.getHostAddress()
						+ " : " + socket.getPort() + " cmd : "
						+ cmd.getCommandName() + "\n");
				if (cmd.getCommandName().equals("login"))
				{
					textArea.append("handle client login\n");
					(new ClientLoginHandler()).handleCommand(cmd, textArea);
					UserManager um = new UserManager();
					List onlineUsers = um.loadAllOnlieUser();
					outputToCLient.writeObject(onlineUsers);
					//					//如果登陆成功 发送成功信息（暂时用String）
					//					outputToCLient.writeObject("login success");
				}
				else if (cmd.getCommandName().equals("register"))
				{
					TbUser user = (TbUser) cmd.getParam();
					UserManager um = new UserManager();
					um.userRegister(user);
					textArea.append("register : \n");
					textArea.append(user.toString());
					textArea.append("\n");

					//如果注册成功 发送成功信息（暂时用String）
					outputToCLient.writeObject("register success");
				}
				else if (cmd.getCommandName().equals("sendMessage"))
				{
					textArea.append("rhandle client send message\n");
					TbMessage message = (TbMessage) cmd.getParam();
					textArea.append("FromClient "
							+ inetAddress.getHostAddress() + " : "
							+ socket.getPort() + " fromId : "
							+ message.getFromUserId() + " toId : "
							+ message.getToUserId() + " send message : "
							+ message.getMessage() + " sendTime : "
							+ message.getSendTime() + "\n");
					//如果接收消息成功 发送成功信息（暂时用String）
					outputToCLient.writeObject("receive");

					//向接收客户端转发消息
					System.out.println("test1");
					TbUser toUser = (new TbUserDAO()).loadUserById(message
							.getToUserId());
					System.out.println("test2");
					System.out.println("ToClient " + toUser.getIp() + " : "
							+ toUser.getPort() + " fromId : "
							+ message.getFromUserId() + " toId : "
							+ message.getToUserId() + " send message : "
							+ message.getMessage() + " sendTime : "
							+ message.getSendTime() + "\n");
					(new NetworkCommand(toUser.getIp(), Integer.valueOf(toUser
							.getPort()))).sendMessage(message);
					System.out.println("test3");
					textArea.append("ToClient " + toUser.getIp() + " : "
							+ toUser.getPort() + " fromId : "
							+ message.getFromUserId() + " toId : "
							+ message.getToUserId() + " send message : "
							+ message.getMessage() + " sendTime : "
							+ message.getSendTime() + "\n");

				}

			}
			catch (Exception e)
			{
				if (!(e instanceof BusinessException))
					e.printStackTrace();
				//如果发生异常，则将异常信息也输出到客户端
				textArea.append("exception : " + e + "\n");
				outputToCLient.writeObject(e);
			}
			outputToCLient.close();
		}
		catch (Exception e)
		{
		}
	}
}
