/**
 * MemberList class
 * 
 * @author SS10 G5 
 * @version 3.0
 * @since 2021-11-02
 */

import java.io.Serializable;
import java.util.Hashtable;

public class MemberList implements Serializable {
    
    private Hashtable<Integer, Member> members;

    public MemberList() {
        members = new Hashtable<Integer, Member>();
    }

    public void addMember(String name, String phoneNumber) {
        Member member = new Member(name, phoneNumber);
        members.put(member.getMemberID(), member);
    }

    public void removeMember(int memberID) {
        members.remove(memberID);
    }

    public boolean verifyMember(int id, String phoneNumber){
        Member member = members.get(id);
        if(member.getPhoneNumber().equals(phoneNumber)){
            return true;
        }
        return false;
    }

    public void changeName(int id, String phoneNumber, String name){
        boolean verified = false;
        Member member = members.get(id);
        if(member.getPhoneNumber().equals(phoneNumber)){
            member.setName(name);
            verified = true;
            System.out.println("member name changed successfully!");
        }
        if(!verified){
            System.out.println("Member not found");
        }
    }

    public void changeNumber(int id, String name, String newNumber){
        boolean verified = false;
        Member member = members.get(id);
        if(member.getName().equals(name)){
            member.setPhoneNumber(newNumber);
            verified = true;
            System.out.println("member number changed successfully!");
        }
        if(!verified){
            System.out.println("Member not found");
        }
    }

    public void printMemberList(){
        System.out.println("memberID\tName");
        for(Member member : members.values()){
            System.out.println(member.getMemberID() + "\t\t" + member.getName());
        }
    }
    
}
