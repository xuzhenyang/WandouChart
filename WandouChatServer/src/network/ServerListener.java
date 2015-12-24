package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import view.ServerView;

public class ServerListener
{

	ServerView serverView;
	ServerListenThread listenThread;

	public ServerListener(ServerView serverView)
	{

		this.serverView = serverView;

		try
		{
			ServerSocket serverSocket = new ServerSocket(4331);
			serverView.messageShow.append("Server started at " + new Date()
					+ '\n');
			listenThread = new ServerListenThread(serverSocket,
					serverView.messageShow);
			listenThread.start();

		}
		catch (IOException ex)
		{
			System.err.println(ex);
		}

	}
}
