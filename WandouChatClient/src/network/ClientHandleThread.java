package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JTextArea;

import view.ChatView;
import model.NetworkPackage;
import model.TbMessage;

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
			ObjectInputStream inputFromCLient = null;
			ObjectOutputStream outputToCLient = null;

			try
			{
				inputFromCLient = new ObjectInputStream(socket.getInputStream());
				outputToCLient = new ObjectOutputStream(
						socket.getOutputStream());

				NetworkPackage cmd = (NetworkPackage) (inputFromCLient
						.readObject());
				System.out.println(cmd.getCommandName());
				if (cmd.getCommandName().equals("sendMessage"))
				{
					System.out.println("get message");
					TbMessage message = (TbMessage) cmd.getParam();
					ChatView.appendMessage(message);
					//如果接收消息成功 发送成功信息（暂时用String）
					outputToCLient.writeObject("receive");
				}
			}
			catch (Exception e)
			{
			}
		}
		catch (Exception e)
		{
		}
	}

}
