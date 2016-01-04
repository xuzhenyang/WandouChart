package control;

import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import model.NetworkPackage;
import model.TbUser;
import dao.TbUserDAO;
import exception.BusinessException;
import exception.DBOperatorException;
import exception.SystemException;

public class ClientLoginHandler extends NetworkCommandHandler
{

	@Override
	public Object handleCommand(NetworkPackage networkPackage)
			throws BusinessException, SystemException, DBOperatorException
	{
		// TODO Auto-generated method stub
		TbUserDAO userDao = new TbUserDAO();
		TbUser user = (TbUser) networkPackage.getParam();
		if (user == null)
			throw new BusinessException("没有用户信息");
		if (user.getUserId() == null || user.getUserId().trim().equals(""))
			throw new BusinessException("用户名不能为空");
		if (user.getUserPwd() == null || user.getUserPwd().trim().equals(""))
			throw new BusinessException("密码不能为空");

		TbUser result = userDao.loadUserById(user.getUserId());
		if (result == null)
			throw new BusinessException("帐号不正确");
		if (!user.getUserPwd().equals(result.getUserPwd()))
			throw new BusinessException("密码错误");

		return result;
	}

	public Object handleCommand(NetworkPackage networkPackage,
			JTextArea textArea) throws BusinessException, SystemException,
			DBOperatorException
	{
		// TODO Auto-generated method stub
		textArea.append("handle client login\n");

		TbUserDAO userDao = new TbUserDAO();
		TbUser user = (TbUser) networkPackage.getParam();
		//登陆信息中不包含昵称，所以需要原始数据
		TbUser userOrigin = userDao.loadUserById(user.getUserId());
		if (user == null)
			throw new BusinessException("没有用户信息");
		if (user.getUserId() == null || user.getUserId().trim().equals(""))
			throw new BusinessException("用户名不能为空");
		if (user.getUserPwd() == null || user.getUserPwd().trim().equals(""))
			throw new BusinessException("密码不能为空");

		TbUser result = userDao.loadUserById(user.getUserId());
		if (result == null)
			throw new BusinessException("帐号不正确");
		if (!user.getUserPwd().equals(result.getUserPwd()))
			throw new BusinessException("密码错误");

		user.setUserName(userOrigin.getUserName());
		user.setRecentLoginDate(new Timestamp(new Date().getTime()));
		user.setRecentLogoutDate(new Timestamp(new Date().getTime()));
		user.setState("online");
		userDao.modifyUser(user);

		textArea.append("Client " + user.getIp() + " : " + user.getPort()
				+ " login success\n");

		//return result;
		return user;
	}
}
