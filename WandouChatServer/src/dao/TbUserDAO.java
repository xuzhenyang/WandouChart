package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.TbUser;
import dbutil.JDBCBase;
import exception.DBOperatorException;
import exception.SystemException;

public class TbUserDAO extends JDBCBase
{

	public TbUserDAO() throws SystemException
	{
		super("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/wandouchat",
				"root", "zucc");
	}

	public TbUserDAO(String driver, String url, String user, String pwd)
			throws SystemException
	{
		super(driver, url, user, pwd);
		// TODO Auto-generated constructor stub
	}

	//用户注册
	public void userRegister(TbUser user) throws DBOperatorException
	{
		String sql = "insert into TbUser(userId, userPwd, userName) values ('"
				+ user.getUserId() + "', '" + user.getUserPwd() + "', '"
				+ user.getUserName() + "' )";
		this.execute(sql);
	}

	//通过ID获取用户信息
	public TbUser loadUserById(String userId) throws DBOperatorException
	{
		TbUser user = null;
		String sql = "select * from tbUser where userId='" + userId + "'";
		ResultSet rs = this.query(sql);

		try
		{
			if (rs.next())
			{
				user = new TbUser();
				user.setUserId(rs.getString("userId"));
				user.setUserPwd(rs.getString("userPwd"));
				user.setUserName(rs.getString("userName"));
				user.setIp(rs.getString("ip"));
				user.setPort(rs.getString("port"));
				user.setRecentLoginDate(rs.getDate("recentLoginDate"));
				user.setRecentLogoutDate(rs.getDate("recentLogoutDate"));
				user.setState(rs.getString("state"));
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBOperatorException("数据库数据读取失败");
		}
		return user;
	}

	private List loadUsersBySql(String sql) throws DBOperatorException
	{
		ResultSet rs = this.query(sql);

		List result = new ArrayList();
		try
		{
			while (rs.next())
			{
				TbUser user = new TbUser();
				user.setUserId(rs.getString("userId"));
				user.setUserPwd(rs.getString("userPwd"));
				user.setUserName(rs.getString("userName"));
				user.setIp(rs.getString("ip"));
				user.setPort(rs.getString("port"));
				user.setRecentLoginDate(rs.getDate("recentLoginDate"));
				user.setRecentLogoutDate(rs.getDate("recentLogoutDate"));
				user.setState(rs.getString("state"));
				result.add(user);
			}
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("数据库数据读取失败，可能是字段名错误");
		}
		return result;
	}

	public List loadAllOnlineUsers() throws DBOperatorException
	{
		String sql = "select * from tbuser where state = 'online'";
		return this.loadUsersBySql(sql);
	}

	public void modifyUser(TbUser user) throws DBOperatorException
	{
		String sql = "update tbuser set userName='" + user.getUserName() + "'"
				+ ",userPwd='" + user.getUserPwd() + "'" + ",recentLoginDate='"
				+ user.getRecentLoginDate() + "'" + ",recentLogoutDate='"
				+ user.getRecentLogoutDate() + "'" + ",ip='" + user.getIp()
				+ "'" + ",port='" + user.getPort() + "'" + ",state='"
				+ user.getState() + "'" + " where userid='" + user.getUserId()
				+ "'";
		this.execute(sql);
	}

}
