package examstudent;

public class Student {
	private int flowID;
	private int type;
	private String idCard;
	private String examCard;
	private String studentName;
	private String localion;
	private int grade;

	public Student(int flowID, int type, String idCard, String examCard, String studentName, String localion,
			int grade) {
		super();
		this.flowID = flowID;
		this.type = type;
		this.idCard = idCard;
		this.examCard = examCard;
		this.studentName = studentName;
		this.localion = localion;
		this.grade = grade;
	}

	public int getFlowID() {
		return flowID;
	}

	public void setFlowID(int flowID) {
		this.flowID = flowID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getExamCard() {
		return examCard;
	}

	public void setExamCard(String examCard) {
		this.examCard = examCard;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getLocalion() {
		return localion;
	}

	public void setLocalion(String localion) {
		this.localion = localion;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Student() {

	}

	@Override
	public String toString() {
		return "Student [flowID=" + flowID + ", type=" + type + ", idCard=" + idCard + ", examCard=" + examCard
				+ ", studentName=" + studentName + ", localion=" + localion + ", grade=" + grade + "]";
	}

}
