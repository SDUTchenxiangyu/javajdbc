package examstudent;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class JDBCTools {
	public static void update(String sql,Object ...args) throws ClassNotFoundException, IOException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i<args.length;i++) {
				preparedStatement.setObject(i+1, args[i]);
			}
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
		
	}
	public static void update(String sql) throws ClassNotFoundException, IOException, SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseDB(null,statement,connection);
		}
	}
	public static void releaseDB(ResultSet resultset,Statement statement,Connection connection) {
		if(resultset != null) {
			try {
				resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Test
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
		String user = null;
		String password = null;
		String url = null;
		String driver = null;
		Properties properties = new Properties();
		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
		properties.load(in);
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		url = properties.getProperty("url");
		driver = properties.getProperty("driver");
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}
}
