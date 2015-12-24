package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientHandleThread extends Thread
{
	private Socket socket;

	public ClientHandleThread(Socket socket)
	{
		this.socket = socket;
	}

	public void run()
	{
		try
		{
			DataInputStream inputFromCLient = new DataInputStream(
					socket.getInputStream());
			DataOutputStream outputToCLient = new DataOutputStream(
					socket.getOutputStream());

			while (true)
			{
			}
		}
		catch (Exception e)
		{
		}
	}

}
