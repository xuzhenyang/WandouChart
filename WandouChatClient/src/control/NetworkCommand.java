package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import exception.BusinessException;
import exception.RemoteException;
import model.NetworkPackage;
import model.TbUser;

public class NetworkCommand
{
	private static NetworkCommand messageServer = null;

	private TbUser currentUser = null;

	private String serverip = null;

	private int serverport = 0;

	private Socket sock = null;

	private NetworkCommand(String ip, int port)
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
			messageServer = new NetworkCommand("127.0.0.1", 4331);
		}
		return messageServer;
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
			in = new ObjectInputStream(sock.getInputStream());
/*
 * 暂时先不考虑处理返回对象
 *  * 
 * 			Object result = (Object) in.readObject();//获得返回对象
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
			}*/
			return null;

		}
		catch (IOException ex)
		{
			throw new RemoteException("网络读写错误！");
		}
/*		catch (ClassNotFoundException e)
		{
			// TODO 自动生成 catch 块
			throw new RemoteException("服务器返回类型错误！");
		}*/
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
	public synchronized void login(TbUser user) throws RemoteException,
			BusinessException
	{
		this.currentUser = (TbUser) this.docmd("login", user);

	}

}
