public class Staff extends Order{
  private String name;
  private char gender;
  private int employeeID;
  private String jobTitle;
  
  public Staff(String name, char gender, int employeeID, String jobTitle) {
        this.name = name;
        this.gender = gender;
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
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
  
  public String setJobTitle(String jobTitle){
    this.jobTitle=jobTitle;
  }
}  
