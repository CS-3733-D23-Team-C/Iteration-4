package edu.wpi.teamc.dao.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUser extends AbsUser {
  private String department;
  private String position;

  public EmployeeUser(String name) {
    super(name);
  }

  public EmployeeUser(int id, String name, String username, String department, String position) {
    super(id, name, username);
    this.department = department;
    this.position = position;
  }

  public EmployeeUser(String name, String username, String department, String position) {
    super(name, username);
    this.department = department;
    this.position = position;
  }

  public String toString() {
    return super.getUsername();
  }

  public EmployeeUser() {}
}
