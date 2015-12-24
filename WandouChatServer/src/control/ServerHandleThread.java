package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

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
				TbUser user = (TbUser)(inputFromCLient.readObject());
				UserManager um = new UserManager();
				um.userRegister(user);
				textArea.append(user.toString());
				textArea.append("\n");
			}
		}
		catch (Exception e)
		{
		}
	}

}
