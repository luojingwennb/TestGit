package oop;

/**
 * 老师类
 *
 */
public class Teacher {
	private TeachingTools tools;
	private Student student;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * 类的关联方式
	 * 构造函数，通过set方法
	 * @param tools
	 */
	public void setTools(TeachingTools tools) {
		this.tools = tools;
	}


	/**
	 * 老师使用教具的方法
	 */
	public void playTools(){
		tools.turnNo();
	}
	public void question(){
		student.student();
	}
	
}
