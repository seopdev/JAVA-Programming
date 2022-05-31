
public class addgrade {
	String studentID,subject,grade,gradepoint,syear,semester;
	public addgrade(String str1, String str2, String str3, String str4, String str5, String str6) {
		this.studentID = str1;
		this.subject = str2;
		this.syear = str3;
		this.semester = str4;
		this.gradepoint = str5;
		this.grade = str6;
	}
	//리스트 아웃.
	public String getStudentID() {
		return studentID;
	}
	public String getSubject() {
		return subject;
	}
	public String getSyear() {
		return syear;
	}
	public String getSemester() {
		return semester;
	}
	public String getGradePoint() {
		return gradepoint;
	}
	public String getGrade() {
		return grade;
	}
	
	//리스트 편집.
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSyear(String syear) {
		this.syear = syear;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public void setGradePoint(String gradepoint) {
		this.gradepoint = gradepoint;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
