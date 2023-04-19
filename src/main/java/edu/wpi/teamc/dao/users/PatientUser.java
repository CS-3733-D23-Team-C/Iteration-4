package edu.wpi.teamc.dao.users;

public class PatientUser extends AbsUser {

  PatientUser(int id, String name, String username) {
    super(id, name, username);
  }

  public PatientUser(String name, String username) {
    super(name, username);
  }

  public PatientUser(String name) {
    super(name);
  }

  public PatientUser() {}
}
