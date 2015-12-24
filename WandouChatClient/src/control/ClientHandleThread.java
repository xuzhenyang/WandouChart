package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientHandleThread extends Thread
{
	private Socket socket;
	private JTextArea textArea;

	public ClientHandleThread(Socket socket, JTextArea textArea)
	{
		this.socket = socket;
		this.textArea = textArea;
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
				textArea.append(inputFromCLient.readUTF());
				textArea.append("\n");
			}
		}
		catch (Exception e)
		{
		}
	}

}
