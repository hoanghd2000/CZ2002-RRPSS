public class Staff{
  private static int count = 0;
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

  //getters and setter for count
  public static int getCount(){
    return count;
  }

  public static void setCount(int newCount){
    count = newCount;
  }
  
  public String getName(){
    return name;
  }
  
  public void setName(String name){
    this.name=name;
  }
  
  public char getGender(){
    return gender;
  }
  
  public void setGender(char gender){
    this.gender=gender;
  }
  
  public int getEmployeeID(){
    return employeeID;
  }
  
  public void setEmployeeID(int employeeID){
    this.employeeID=employeeID;
  }
  
  public String getJobTitle(){
    return jobTitle;
  }
  
  public void setJobTitle(String jobTitle){
    this.jobTitle=jobTitle;
  }
}  
