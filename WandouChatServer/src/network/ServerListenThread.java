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
public class ServerListenThread extends Thread
{
	ServerSocket server;

	JComboBox combobox;
	JTextArea textArea;
	JTextField textfield;

	public boolean isStop;

	/*
	 * 聊天服务端的用户上线于下线侦听类
	 */
	public ServerListenThread(ServerSocket server, JTextArea textArea)
	{

		this.server = server;
		this.textArea = textArea;

		isStop = false;
	}

	public void run()
	{
		while (!isStop && !server.isClosed())
		{
			try
			{
				textArea.append("waiting...\n");
				Socket socket = server.accept();
				textArea.append("connect\n");
				(new ServerHandleThread(socket, textArea)).start();
			}
			catch (Exception e)
			{
			}
		}
	}
}
