package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class ClientListener
{

	ClientListenThread listenThread;

	public ClientListener()
	{


		try
		{
			ServerSocket serverSocket = new ServerSocket(4331);
			listenThread = new ClientListenThread(serverSocket);
			listenThread.start();

		}
		catch (IOException ex)
		{
			System.err.println(ex);
		}

	}
}
