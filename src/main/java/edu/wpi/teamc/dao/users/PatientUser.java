package edu.wpi.teamc.dao.users;

import lombok.Getter;
import lombok.Setter;

public class PatientUser extends AbsUser {

  private int id;
  private String name;
  private String in;
  private String out;
  private String phone;
  private String room;



  private boolean activeText;

  public PatientUser(int id, String name, String in, String out, String phone, String room, boolean activeText) {
    super(id, name, in);
    this.out = out;
    this.phone = phone;
    this.room = room;
    this.activeText = activeText;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }


  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void setIn(String in) {
    this.in = in;
  }

  public String getPhone() {
    return phone;
  }

  public String getRoom() {
    return room;
  }
  public void setOut(String out) {
    this.out = out;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public void setActiveText(boolean activeText) {
    this.activeText = activeText;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getIn() {
    return in;
  }

  public String getOut() {
    return out;
  }

  public boolean isActiveText() {
    return activeText;
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

}
