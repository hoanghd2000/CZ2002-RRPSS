import java.io.Serializable;

public class Member implements Serializable{
    
    private static int count = 1;
    private int memberID;
    private String name;
    private String phoneNumber;

    public Member(String name, String phoneNumber) {
        this.memberID = count++;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Member.count = count;
    }
}
