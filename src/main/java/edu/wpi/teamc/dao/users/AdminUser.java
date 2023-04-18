package edu.wpi.teamc.dao.users;

public class AdminUser extends AbsUser {
  AdminUser(int id, String name, String username) {
    super(id, name, username);
  }

  public AdminUser(String name, String username) {
    super(name, username);
  }
}
