package examstudent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class JDBCTest {
	@Test
	public void testGet() {
		// String sql = "SELECT id,name,email,birth FROM customer WHERE id = ?";
		// Customer customer = get(Customer.class, sql, 5);
		// System.out.println(customer);
		String sql = "SELECT Flow_ID flowId,Type,ID_Card idCard,Exam_Card examCard,Student_Name studentName,Location localion,Grade FROM student WHERE Flow_Id = ?";
		System.out.println(sql);
		Student studnet = get(Student.class, sql, 5);
	}

	public <T> T get(Class<T> clazz, String sql, Object... args) {
		T entity = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[1]);
			}
			resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				entity = clazz.newInstance();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultset, preparedStatement, connection);
		}
		return null;
	}

	public Customer getCustomer(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		Customer customer = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[1]);
			}
			resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				customer = new Customer();
				customer.setId(resultset.getInt(1));
				customer.setName(resultset.getString(2));
				customer.setEmail(resultset.getString(3));
				customer.setBirth(resultset.getDate(4));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultset, preparedStatement, connection);
		}
		return customer;
	}

	public Student getStudent(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		Student student = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[1]);
			}
			resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				student = new Student();
				student.setFlowID(resultset.getInt(1));
				student.setType(resultset.getInt(2));
				student.setIdCard(resultset.getString(3));
				student.setExamCard(resultset.getString(4));
				student.setStudentName(resultset.getString(5));
				student.setLocalion(resultset.getString(6));
				student.setGrade(resultset.getInt(7));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultset, preparedStatement, connection);
		}
		return student;
	}

	@Test
	public void testAddNewStudent() throws ClassNotFoundException, IOException, SQLException {
		Student student = getStudentFromConsole();
		addNewStudent2(student);
	}

	public void testGetStudent() throws ClassNotFoundException, SQLException, IOException {
		int searchType = getSearchTypeStudent();
		Student student = searchStudent(searchType);
		printStudent(student);
	}

	private void printStudent(Student student) {
		if (student != null) {
			System.out.println(student);
		} else {
			System.out.println("查无此人！");
		}

	}

	private Student searchStudent(int searchType) throws ClassNotFoundException, SQLException, IOException {
		String sql = "SELECT flowid,type,idcard,examcard,studentname,location,grade FROM student WHERE";
		Scanner scanner = new Scanner(System.in);
		if (searchType == 1) {
			System.out.println("请输入身份证号：");
			String examCard = scanner.next();
			sql = sql + " IDCard = '" + examCard + "'";
		} else {
			System.out.println("请输入准考证号：");
			String idCard = scanner.next();
			sql = sql + " ExamCard = '" + idCard + "'";
		}
		Student student = getStudent(sql);
		return student;
	}

	private Student getStudent(String sql) throws SQLException, ClassNotFoundException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		Student student = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);
			if (resultset.next()) {
				student = new Student(resultset.getInt(1), resultset.getInt(2), resultset.getString(3),
						resultset.getString(4), resultset.getString(5), resultset.getString(6), resultset.getInt(7));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultset, statement, connection);
		}
		return student;
	}

	private int getSearchTypeStudent() {
		System.out.println("请输入查询类型：1.身份证查询，2.准考证查询");
		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();
		if (type != 1 && type != 2) {
			System.out.println("输入错误，请重新操作！");
			throw new RuntimeException();
		}
		return type;
	}

	private Student getStudentFromConsole() {
		Scanner scanner = new Scanner(System.in);
		Student student = new Student();
		System.out.print("FlowID:");
		student.setFlowID(scanner.nextInt());
		System.out.print("Type:");
		student.setType(scanner.nextInt());
		System.out.print("IDCard:");
		student.setIdCard(scanner.next());
		System.out.print("ExamCard:");
		student.setExamCard(scanner.next());
		System.out.print("StudentName:");
		student.setStudentName(scanner.next());
		System.out.print("Location:");
		student.setLocalion(scanner.next());
		System.out.print("Grade:");
		student.setGrade(scanner.nextInt());
		return student;
	}

	public void addNewStudent2(Student student) throws ClassNotFoundException, IOException, SQLException {
		String sql = "INSERT INTO student(flowid,type,idcard,examcard,studentname,location,grade)"
				+ " VALUES(?,?,?,?,?,?,?)";
		JDBCTools.update(sql, student.getFlowID(), student.getType(), student.getIdCard(), student.getExamCard(),
				student.getStudentName(), student.getLocalion(), student.getGrade());
	}

	public void addNewStudent(Student student) throws ClassNotFoundException, IOException, SQLException {
		String sql = "INSERT INTO student " + " VALUES(" + student.getFlowID() + "," + student.getType() + ",'"
				+ student.getIdCard() + "','" + student.getExamCard() + "','" + student.getStudentName() + "','"
				+ student.getLocalion() + "'," + student.getGrade() + ")";
		JDBCTools.update(sql);
	}
}
