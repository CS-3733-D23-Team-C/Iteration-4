package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;
import java.sql.SQLException;
import java.util.List;

public class AdminUserDao implements IDao<AdminUser, Integer> {
  @Override
  public List<AdminUser> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public AdminUser updateRow(AdminUser type, AdminUser type2) throws SQLException {
    return null;
  }

  @Override
  public AdminUser addRow(AdminUser type) {
    return null;
  }

  @Override
  public AdminUser deleteRow(AdminUser type) throws SQLException {
    return null;
  }

  @Override
  public AdminUser fetchObject(Integer key) throws SQLException {
    return null;
  }
}
