package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;
import java.sql.SQLException;
import java.util.List;

public class LoginDao implements IDao<Login, String> {
  @Override
  public List<Login> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public Login updateRow(Login type, Login type2) throws SQLException {
    return null;
  }

  @Override
  public Login addRow(Login type) {
    return null;
  }

  @Override
  public Login deleteRow(Login type) throws SQLException {
    return null;
  }

  @Override
  public Login fetchObject(String key) throws SQLException {
    return null;
  }
}
