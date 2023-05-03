package edu.wpi.teamc.dao.users;

import io.jsonwebtoken.lang.Objects;
import java.sql.Timestamp;

public class PatientUser extends AbsUser {

  private Timestamp in;
  private Timestamp out;
  private String phone;
  private String room;

  private boolean activeText;

  public PatientUser(
      int id,
      String name,
      Timestamp in,
      Timestamp out,
      String phone,
      String room,
      String activeText) {
    setName(name);
    setId(id);
    this.in = in;
    this.out = out;
    this.phone = phone;
    this.room = room;
    this.activeText = booleanValOf(activeText);
  }

  public PatientUser(
      int id,
      String name,
      Timestamp in,
      Timestamp out,
      String phone,
      String room,
      boolean activeText) {
    setName(name);
    setId(id);
    this.in = in;
    this.out = out;
    this.phone = phone;
    this.room = room;
    this.activeText = activeText;
  }

  public PatientUser(
      int id, String name, String in, String out, String phone, String room, String activeText) {

    // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    setName(name);
    setId(id);
    this.in = null;
    this.out = null;
    this.phone = phone;
    this.room = room;
    this.activeText = booleanValOf(activeText);
  }

  public boolean booleanValOf(String check) {
    if (check.equals("true")) {
      return true;
    } else if (check.equals("false")) {
      return false;
    } else {
      return false;
    }
  }

  public void setIn(Timestamp in) {
    this.in = in;
  }

  public String getPhone() {
    return phone;
  }

  public String getRoom() {
    return room;
  }

  public void setOut(Timestamp out) {
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

  public Timestamp getIn() {
    return in;
  }

  public Timestamp getOut() {
    return out;
  }

  public boolean isActiveText() {
    return activeText;
  }

  public PatientUser() {}

  public PatientUser(String name) {
    setName(name);
  }

  /*public boolean validPatient(String name, String phone) {
    if (this.name == name && this.phone == phone) {
      return true;
    } else {
      return false;
    }
  }*/

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PatientUser pat) {
      return Objects.nullSafeEquals(this.getName(), pat.getName())
          && this.getId() == pat.getId()
          && Objects.nullSafeEquals(this.in, pat.in)
          && Objects.nullSafeEquals(this.out, pat.out)
          && Objects.nullSafeEquals(this.room, pat.room)
          && Objects.nullSafeEquals(this.phone, pat.phone)
          && this.activeText == pat.activeText;

    } else {
      return false;
    }
  }

  public String toString() {
    return getName();
  }
}
