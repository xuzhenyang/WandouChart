package dbutil;

import java.sql.*;

import exception.DBOperatorException;
import exception.SystemException;

public class JDBCBase
{
	private String jdbcUrl;

	private String dbUser, dbPwd;

	protected Connection conn = null;

	public JDBCBase(String driver, String url, String user, String pwd)
			throws SystemException
	{
		this.jdbcUrl = url;
		this.dbUser = user;
		this.dbPwd = pwd;
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SystemException("类路径中找不到数据库驱动程序，可能是类库没有加载或驱动程序类名错误");
		}

	}

	protected void connectToDb() throws DBOperatorException
	{
		try
		{
			if (conn == null || conn.isClosed()) //所有每个子类对象共享一个数据库连接
				conn = DriverManager.getConnection(this.jdbcUrl, this.dbUser,
						this.dbPwd);
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("连接数据库失败");
		}

	}

	public void closeConnection()
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				// TODO 自动生成 catch 块
				e.printStackTrace();

			}
			conn = null;
		}

	}

	public ResultSet query(String sql) throws DBOperatorException
	{
		this.connectToDb();

		ResultSet result = null;
		try
		{
			Statement st = conn.createStatement();
			result = st.executeQuery(sql);
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("执行sql语句失败，sql：" + sql);
		}
		return result;
	}

	public ResultSet query(String sql, java.util.Date[] dateParams)
			throws DBOperatorException
	{
		this.connectToDb();

		ResultSet result = null;
		try
		{
			PreparedStatement st = conn.prepareStatement(sql);
			for (int i = 0; i < dateParams.length; i++)
			{
				if (dateParams[i] != null)
					st.setTimestamp(i + 1,
							new Timestamp(dateParams[i].getTime()));
				else
					st.setDate(i + 1, null);
			}
			result = st.executeQuery();
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("执行sql语句失败，sql：" + sql);
		}
		return result;
	}

	public int execute(String sql) throws DBOperatorException
	{
		this.connectToDb();
		int result = -1;
		try
		{
			Statement st = conn.createStatement();
			result = st.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("执行sql语句失败，sql：" + sql);
		}
		return result;
	}

	public int execute(String sql, java.util.Date[] dateParams)
			throws DBOperatorException
	{
		this.connectToDb();
		int result = -1;
		try
		{
			PreparedStatement st = conn.prepareStatement(sql);
			for (int i = 0; i < dateParams.length; i++)
			{
				if (dateParams[i] != null)
				{
					st.setTimestamp(i + 1,
							new Timestamp(dateParams[i].getTime()));
				}
				else
					st.setDate(i + 1, null);
			}
			result = st.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new DBOperatorException("执行sql语句失败，sql：" + sql);
		}
		return result;
	}
}
