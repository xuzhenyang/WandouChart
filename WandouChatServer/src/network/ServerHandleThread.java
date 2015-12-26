package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import model.*;
import control.ClientLoginHandler;
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
		textArea.append("ServerHandleThread start\n");
		try
		{
			textArea.append("init input/output\n");
			ObjectInputStream inputFromCLient = null;
			ObjectOutputStream outputToCLient = null;
			try
			{
				textArea.append("get input/output\n");
				inputFromCLient = new ObjectInputStream(socket.getInputStream());
				outputToCLient = new ObjectOutputStream(
						socket.getOutputStream());

				//从客户端读取网络请求信息
				/*NetworkPackage cmd = (NetworkPackage) (inputFromCLient
						.readObject());*/
				NetworkPackage cmd = (NetworkPackage) (inputFromCLient.readObject());
				textArea.append("cmd : " + cmd.getCommandName() + "\n");
				if (cmd.getCommandName().equals("login"))
				{
					textArea.append("handle client login\n");
					(new ClientLoginHandler()).handleCommand(cmd);
					textArea.append("login successful\n");
				}
				else if (cmd.getCommandName().equals("register"))
				{
					TbUser user = (TbUser) cmd.getParam();
					//					UserManager um = new UserManager();
					//				um.userRegister(user);
					textArea.append("register : \n");
					textArea.append(user.toString());
					textArea.append("\n");
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
			//如果登陆成功 发送成功信息（暂时用String）
			outputToCLient.writeObject("login success");
			outputToCLient.close();
		}
		catch (Exception e)
		{
		}
	}
}
