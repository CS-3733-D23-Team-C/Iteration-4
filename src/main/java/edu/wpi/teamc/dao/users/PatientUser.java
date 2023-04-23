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

  public PatientUser(String name) {
    this.name = name;
  }

  public boolean validPatient(String name, String phone) {
    if (this.name == name && this.phone == phone) {
      return true;
    } else {
      return false;
    }
  }

  public String toString() {
    return super.getName();
  }

  public String getTimeIn() {
    return this.in;
  }

  public String getTimeOut() {
    return this.out;
  }

  public String getRoom() {
    return this.room;
  }

  public String getPhone() {
    return this.phone;
  }
}
