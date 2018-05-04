package examstudent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class JDBCTest {
	@Test
	public void testAddNewStudent() throws ClassNotFoundException, IOException, SQLException {
		Student student = getStudentFromConsole();
		addNewStudent(student);
	}
	
	public void testGetStudent() throws ClassNotFoundException, SQLException, IOException {
		int searchType = getSearchTypeStudent();
		Student student = searchStudent(searchType);
		printStudent(student);
	}

	private void printStudent(Student student) {
		if(student != null) {
			System.out.println(student);
		}else {
			System.out.println("���޴��ˣ�");
		}

	}

	private Student searchStudent(int searchType) throws ClassNotFoundException, SQLException, IOException {
		String sql = "SELECT flowid,type,idcard,examcard,studentname,location,grade FROM student WHERE";
		Scanner scanner = new Scanner(System.in);
		if (searchType == 1) {
			System.out.println("���������֤�ţ�");
			String examCard = scanner.next();
			sql = sql + " IDCard = '" + examCard + "'";
		} else {
			System.out.println("������׼��֤�ţ�");
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
		System.out.println("�������ѯ���ͣ���1.���֤��ѯ��2.׼��֤��ѯ��");
		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();
		if (type != 1 && type != 2) {
			System.out.println("�����������������룡");
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

	public void addNewStudent(Student student) throws ClassNotFoundException, IOException, SQLException {
		String sql = "INSERT INTO student " + " VALUES(" + student.getFlowID() + "," + student.getType() + ",'"
				+ student.getIdCard() + "','" + student.getExamCard() + "','" + student.getStudentName() + "','"
				+ student.getLocalion() + "'," + student.getGrade() + ")";
		JDBCTools.update(sql);
	}
}
