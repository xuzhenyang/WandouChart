package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ClientListener
{

	ClientListenThread listenThread;
	ServerSocket serverSocket;

	public ClientListener()
	{

		try
		{
			//new ServerSocket(0) 用于自动分配空闲端口
			serverSocket = new ServerSocket(0);
			listenThread = new ClientListenThread(serverSocket);
			listenThread.start();

		}
		catch (IOException ex)
		{
			System.err.println(ex);
		}

	}

	public String getIp()
	{
		return serverSocket.getInetAddress().getHostAddress();
	}

	public String getPort()
	{
		return String.valueOf(serverSocket.getLocalPort());
	}
}
