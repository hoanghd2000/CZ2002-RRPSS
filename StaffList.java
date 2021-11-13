/**
 * Represents an arrayList of Staff objects. This is where the
 * methods for addition, removal, finding and printing of staff objects are 
 * formulated. A staffList must have at least 1 staff object.
 * @author  SS10G5
 * @version 1.0
 * @since   2021-10-25
*/

import java.io.Serializable;
import java.util.ArrayList;

public class StaffList implements Serializable{
    
    // create a private arraylist of staff objects
    private ArrayList<Staff> staffList;

    public StaffList() {
        staffList = new ArrayList<Staff>();
    }

    
    /** 
     * @return ArrayList<Staff>
     */
    // add getters and setters for stafflist
    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

	/** 
     * @param ArrayList<Staff>
     */
    public void setStaffList(ArrayList<Staff> staffList) {
		this.staffList = staffList;
	}

	/** 
     * @param name
     * @param gender
     * @param jobTitle
     */
    public void addStaff(String name, char gender, String jobTitle) {
        // create the staff object and add to array list
        staffList.add(new Staff(name, gender, jobTitle));
    }

    
    /** 
     * @param employeeID
     */
    public void removeStaff(int employeeID) {
        // loop through list and remove object whose employeeID matches input
        for(int i = 0 ; i < staffList.size() ; i++) {
            if(staffList.get(i).getEmployeeID() == employeeID) {
                staffList.remove(i);
            }
        }
    }

    
    /** 
     * @param employeeID
     * @return Staff
     */
    public Staff getStaff(int employeeID) {
        // loop through list and return object whose employeeID matches input
        for(int i = 0 ; i < staffList.size() ; i++) {
            if(staffList.get(i).getEmployeeID() == employeeID) {
                return staffList.get(i);
            }
        }
        return null;
    }

    public void printStaffList(){
        System.out.println("StaffID" + "\tName\tPosition");
        for(int i = 0 ; i < staffList.size() ; i++) {
            System.out.println(staffList.get(i).getEmployeeID() + "\t" + staffList.get(i).getName() + "\t" + staffList.get(i).getJobTitle());
        }
    }
}
