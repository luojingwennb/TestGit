package oop;
/**
 * 作业场景描述：
 * 老师上课用到的教具有：笔记本电脑，激光笔，投影仪等，在上课的过程中
 * 会叫到学生回答问题。
 * @author Administrator
 *
 */

public class ClassON {
	public static void main(String[] args) {
		LaserPen jp= new LaserPen(); //实例化激光笔对象
		Projector cm = new Projector();//实例化投影仪对象 
		Teacher yanther=new Teacher();//实例化老师对象
		NotebookComputer nc = new NotebookComputer();
		Student s = new Student();
		
		yanther.setStudent(s);
		yanther.getStudent().student();
		yanther.setTools(nc);
		yanther.playTools();
	}

}
