package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;
import java.sql.SQLException;
import java.util.List;

public class PatientUserDao implements IDao<PatientUser, Integer> {
  @Override
  public List<PatientUser> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public PatientUser updateRow(PatientUser type, PatientUser type2) throws SQLException {
    return null;
  }

  @Override
  public PatientUser addRow(PatientUser type) {
    return null;
  }

  @Override
  public PatientUser deleteRow(PatientUser type) throws SQLException {
    return null;
  }

  @Override
  public PatientUser fetchObject(Integer key) throws SQLException {
    return null;
  }
}
