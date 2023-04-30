package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;
import java.util.List;

public class AdminUserDao implements IDao<AdminUser, Integer> {
  @Override
  public List<AdminUser> fetchAllObjects() {
    return null;
  }

  @Override
  public AdminUser updateRow(AdminUser type, AdminUser type2) {
    return null;
  }

  @Override
  public AdminUser addRow(AdminUser type) {
    return null;
  }

  @Override
  public AdminUser deleteRow(AdminUser type) {
    return null;
  }

  @Override
  public AdminUser fetchObject(Integer key) {
    return null;
  }

  public void exportCSV(String filePath) {}
}
