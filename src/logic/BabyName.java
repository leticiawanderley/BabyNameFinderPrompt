package logic;

import java.io.Serializable;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class BabyName implements Serializable{
	private String name;
	private int gender;
	private int grade = 0;
	
	/**
	 * Constructor of BabyName
	 * @param name
	 * 		Name of the baby
	 * @param gender
	 * 		Gender of the baby (0 for boys and 1 for girls)
	 * @throws Exception if the name or the gender is invalid
	 */
	
	public BabyName(String name, int gender)throws Exception{
		this.setName(name);
		this.setGender(gender);
	}
	
	/**
	 * Retrieves the name of the baby 
	 * @return name
	 * 		Name of the baby
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets a new name for the baby
	 * @param name
	 * @throws Exception if the name given is invalid
	 */
	public void setName(String name) throws Exception{
		if(name == null || name.equals(""))
			throw new Exception("Invalid name!");
		this.name = name;
	}
	
	/**
	 * Retrieves the grade of the name
	 * @return grade
	 * 		Grade of the name
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * Sets a new grade for the name
	 * @param grade
	 * @throws Exception if the grade given is invalid
	 */
	public void setGrade(int grade) throws Exception{
		if (grade < 0)
			throw new Exception("Invalid grade!");			
		this.grade = grade;
	}
	
	/**
	 * Retrieves the gender of the name
	 * @return gender
	 * 		Gender of the name
	 */
	public int getGender() {
		return gender;
	}
	
	/**
	 * Sets a new gender for the baby
	 * @param gender
	 * @throws Exception if the gender given is invalid.
	 */
	public void setGender(int gender) throws Exception{
		if(gender < 0)
			throw new Exception("Invalid gender!");
		this.gender = gender;
	}
}