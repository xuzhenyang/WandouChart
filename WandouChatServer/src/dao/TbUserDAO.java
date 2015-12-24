package dao;

import java.sql.SQLException;

import model.TbUser;
import dbutil.JDBCBase;
import exception.DBOperatorException;
import exception.SystemException;

public class TbUserDAO extends JDBCBase
{

	public TbUserDAO() throws SystemException
	{
		super(
				"com.mysql.jdbc.Driver",
				"jdbc:mysql://127.0.0.1/wandouchat",
				"root", "zucc");
	}

	public TbUserDAO(String driver, String url, String user, String pwd)
			throws SystemException
	{
		super(driver, url, user, pwd);
		// TODO Auto-generated constructor stub
	}

	public void userRegister(TbUser user) throws DBOperatorException
	{
		String sql = "insert into TbUser(userId, userPwd, userName) values ('" + user.getUserId() + "', '"
				+ user.getUserPwd() + "', '" + user.getUserName() + "' )";
		this.execute(sql);
	}

}
