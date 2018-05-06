package examstudent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {
	public void update(String sql,Object ...args) throws ClassNotFoundException, IOException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++) {
				preparedStatement.setObject(i+1,args[i]);
			}
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	public <T> T get(Class<T> clazz,String sql,Object ...args) throws ClassNotFoundException, IOException, SQLException {
		T entity = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++ ) {
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Map<String,Object> values = new HashMap<String,Object>();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for(int i = 0;i < columnCount; i++) {
					String columnLabel = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(i+1);
					values.put(columnLabel, columnValue);
				}
				entity = clazz.newInstance();
				for(Map.Entry<String, Object> entry:values.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();
					ReflectionUtils.setFieldValue(entity, propertyName, value);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		return entity;
	}
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ...args){
		return null;
	}
	public <E> E getForValue(String sql,Object ...args) {
		return null;
	}
}
