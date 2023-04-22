package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientUserDao implements IDao<PatientUser, Integer> {
  @Override
  public List<PatientUser> fetchAllObjects() throws SQLException {
    List<PatientUser> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();

    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "users.patient";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int id = rs.getInt("patientID");
        String name = rs.getString("name");
        String notes = rs.getString("additionalNotes");
        String timeIn = rs.getString("timeIn");
        String timeOut = rs.getString("timeOut");
        String phoneNumber = rs.getString("phoneNumber");
        String roomID = rs.getString("roomID");

        PatientUser patient = new PatientUser(id, name, notes, timeIn, timeOut, phoneNumber, roomID);
        returnList.add(patient);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
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
