/**
 * Represents the staff class holding onto each Staff's information
 * such as name, gender, employeeID, jobTitle. THe count's purpose here
 * is to ensure that when the staffList is printed it is printed in a
 * descending order.
 * @author  SS10G5
 * @version 1.0
 * @since   2021-10-25
*/

import java.io.Serializable;

public class Staff implements Serializable{
  private static int count = 1;
  private String name;
  private char gender;
  private int employeeID;
  private String jobTitle;
  
  public Staff(String name, char gender, String jobTitle) {
    this.name = name;
    this.gender = gender;
    this.employeeID = count;
    count++;
    this.jobTitle = jobTitle;
  }

  
  /** 
   * @return int
   */
  //getters and setter for count
  public static int getCount(){
    return count;
  }

  
  /** 
   * @param newCount
   */
  public static void setCount(int newCount){
    count = newCount;
  }
  
  
  /** 
   * @return String
   */
  public String getName(){
    return name;
  }
  
  
  /** 
   * @param name
   */
  public void setName(String name){
    this.name=name;
  }
  
  
  /** 
   * @return char
   */
  public char getGender(){
    return gender;
  }
  
  
  /** 
   * @param gender
   */
  public void setGender(char gender){
    this.gender=gender;
  }
  
  
  /** 
   * @return int
   */
  public int getEmployeeID(){
    return employeeID;
  }
  
  
  /** 
   * @param employeeID
   */
  public void setEmployeeID(int employeeID){
    this.employeeID=employeeID;
  }
  
  
  /** 
   * @return String
   */
  public String getJobTitle(){
    return jobTitle;
  }
  
  
  /** 
   * @param jobTitle
   */
  public void setJobTitle(String jobTitle){
    this.jobTitle=jobTitle;
  }
}  
