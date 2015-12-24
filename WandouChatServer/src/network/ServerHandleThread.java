package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import control.ClientLoginHandler;
import control.NetworkCommand;
import control.UserManager;
import model.NetworkPackage;
import model.TbUser;

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
		try
		{
			ObjectInputStream inputFromCLient = new ObjectInputStream(
					socket.getInputStream());
			ObjectOutputStream outputToCLient = new ObjectOutputStream(
					socket.getOutputStream());

			while (true)
			{
				NetworkPackage cmd = (NetworkPackage) (inputFromCLient
						.readObject());
				if (cmd.getCommandName().equals("login"))
				{
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
		}
		catch (Exception e)
		{
		}
	}

}
