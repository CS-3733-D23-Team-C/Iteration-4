package edu.wpi.teamc.dao.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUser extends AbsUser {
  private String userName;
  private String department;
  private String position;

  public EmployeeUser(int id, String name, String userName, String department, String position) {
    super(id, name);
    this.userName = userName;
    this.department = department;
    this.position = position;
  }

  public EmployeeUser(String name, String userName, String department, String position) {
    super(name);
    this.userName = userName;
    this.department = department;
    this.position = position;
  }

  public EmployeeUser() {
    super();
  }
}
