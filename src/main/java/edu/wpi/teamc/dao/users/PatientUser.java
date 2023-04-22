package edu.wpi.teamc.dao.users;

public class PatientUser extends AbsUser {
  private int id;
  private String name;
  private String in;
  private String out;
  private String phone;
  private String room;

  public PatientUser(int id, String name, String in, String out, String phone, String room) {
    super(id, name, in);
    this.out = out;
    this.phone = phone;
    this.room = room;
  }


  public String toString() {
    return super.getName();
  }

  public String getTimeIn() {
    return in;
  }

  public String getTimeOut() {
    return out;
  }

  public String getRoom(){
    return room;
  }

  public String getPhone() {
    return phone;
  }
}
