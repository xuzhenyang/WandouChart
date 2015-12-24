package network;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.net.*;

/*
 * 服务端的侦听类
 */
public class ClientListenThread extends Thread
{
	ServerSocket server;

	public boolean isStop;

	/*
	 * 聊天服务端的用户上线于下线侦听类
	 */
	public ClientListenThread(ServerSocket server)
	{

		this.server = server;

		isStop = false;
	}

	public void run()
	{
		while (!isStop && !server.isClosed())
		{
			try
			{
				Socket socket = server.accept();
				(new ClientHandleThread(socket)).start();
			}
			catch (Exception e)
			{
			}
		}
	}
}
