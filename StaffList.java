import java.io.Serializable;
import java.util.ArrayList;

public class StaffList implements Serializable{
    
    // create a private arraylist of staff objects
    private ArrayList<Staff> staffList;

    public StaffList() {
        staffList = new ArrayList<Staff>();
    }

    // add getters and setters for stafflist
    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public void addStaff(String name, char gender, String jobTitle) {
        // create the staff object and add to array list
        staffList.add(new Staff(name, gender, jobTitle));
    }

    public void removeStaff(int employeeID) {
        // loop through list and remove object whose employeeID matches input
        for(int i = 0 ; i < staffList.size() ; i++) {
            if(staffList.get(i).getEmployeeID() == employeeID) {
                staffList.remove(i);
            }
        }
    }

    public Staff getStaff(int employeeID) {
        // loop through list and return object whose employeeID matches input
        for(int i = 0 ; i < staffList.size() ; i++) {
            if(staffList.get(i).getEmployeeID() == employeeID) {
                return staffList.get(i);
            }
        }
        return null;
    }
}
