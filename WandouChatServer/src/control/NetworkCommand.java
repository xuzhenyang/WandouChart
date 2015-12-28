package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import network.NetUtil;
import model.NetworkPackage;
import model.TbMessage;
import model.TbUser;
import exception.BusinessException;
import exception.RemoteException;

/*
 * NetworkCommand类
 * 用于封装所有的网络请求命令
 * */
public class NetworkCommand
{
	private static NetworkCommand messageServer = null;

	private TbUser currentUser = null;

	private String serverip = null;

	private int serverport = 0;

	private Socket sock = null;

	public NetworkCommand(String ip, int port)
	{
		serverip = ip;
		serverport = port;
		// 这两个信息可写入相应的配置文件，然后从配置文件读取
	}

	public static synchronized NetworkCommand getServer()
	{
		if (messageServer == null)
		{
			//			 这两个信息可写入相应的配置文件，然后从配置文件读取
			/*			for(int i = 5000; i < 5200; i++)
						{
							if(!NetUtil.isLoclePortUsing(i))
							{
			//					System.out.println("" + i + NetUtil.isLoclePortUsing(i));
								messageServer = new NetworkCommand("127.0.0.1", i);
								break;
							}
						}*/
			messageServer = new NetworkCommand("127.0.0.1", 4331);
		}
		return messageServer;
	}

	public String showState()
	{
		return "ip : " + serverip + " port : " + serverport;
	}

	private void connectToServer() throws RemoteException
	{
		try
		{
			sock = new Socket(serverip, serverport);
		}
		catch (Exception e)
		{
			throw new RemoteException("无法连接服务器" + serverip + ",端口："
					+ serverport);
		}
	}

	private void disconnectFromServer()
	{
		try
		{
			sock.close();
		}
		catch (IOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		sock = null;
	}

	/*
	 * docmd方法用于处理：发送一个命令，并获得返回
	 */
	private Object docmd(String commandName, Object param)
			throws RemoteException, BusinessException
	{
		this.connectToServer();
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		//封装网络请求包
		NetworkPackage cmd = new NetworkPackage();
		cmd.setCommandName(commandName);
		cmd.setParam(param);
		try
		{
			out = new ObjectOutputStream(sock.getOutputStream());
			out.writeObject(cmd);//发送命令对象
			System.out.println("send cmd : " + commandName + " to " + serverip
					+ ":" + serverport + "\n");
			in = new ObjectInputStream(sock.getInputStream());
			Object result = (Object) in.readObject();//获得返回对象
			System.out.println("get result\n");
			//服务器处理结束后可能传回结果信息或异常信息
			//对于BusinessException则直接抛出
			//对于其他异常统一封装为RemoteException
			if (result != null)
			{
				if (result instanceof BusinessException)
					throw (BusinessException) result;
				else if (result instanceof Exception)
					throw new RemoteException("服务器错误");
				else
					return result;
			}
			return null;

		}
		catch (IOException ex)
		{
			throw new RemoteException("网络读写错误！");
		}
		catch (ClassNotFoundException e)
		{
			// TODO 自动生成 catch 块
			throw new RemoteException("服务器返回类型错误！");
		}
		finally
		{
			this.disconnectFromServer();
		}

	}

	public TbUser getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(TbUser currentUser)
	{
		this.currentUser = currentUser;
	}

	//	 
	public synchronized void checkLogin(TbUser user) throws RemoteException,
			BusinessException
	{
		this.currentUser = (TbUser) this.docmd("checkLogin", user);

	}

	public synchronized void checkRegister(TbUser user) throws RemoteException,
			BusinessException
	{
		this.currentUser = (TbUser) this.docmd("checkRegister", user);
	}

	public synchronized void returnAllOnlineUser(List onlineUsers)
			throws RemoteException, BusinessException
	{
		this.docmd("checkRegister", onlineUsers);
	}

	public synchronized void sendMessage(TbMessage message)
			throws RemoteException, BusinessException
	{
		this.docmd("sendMessage", message);
	}

}
