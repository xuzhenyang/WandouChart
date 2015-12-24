package control;

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

	JComboBox combobox;
	JTextArea textArea;
	JTextField textfield;

	public boolean isStop;

	/*
	 * 聊天服务端的用户上线于下线侦听类
	 */
	public ClientListenThread(ServerSocket server, JTextArea textArea)
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
				(new ClientHandleThread(socket, textArea)).start();
			}
			catch (Exception e)
			{
			}
		}
	}
}
