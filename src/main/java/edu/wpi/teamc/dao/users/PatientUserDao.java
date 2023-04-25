package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientUserDao implements IDao<PatientUser, Integer> {
  @Override
  public List<PatientUser> fetchAllObjects(){
    List<PatientUser> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();

    try (Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(" SELECT * FROM \"users\".\"patient\" ");
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        // Get all the data from the table
        int id = rs.getInt("id");
        String name = rs.getString("name");
        // String notes = rs.getString("additionalNotes");
        String timeIn = rs.getString("checkin");
        String timeOut = rs.getString("checkout");
        String phoneNumber = rs.getString("phone");
        String roomID = rs.getString("room");
        Boolean activeText = rs.getBoolean("activetext");

        PatientUser patient = new PatientUser(id, name, timeIn, timeOut, phoneNumber, roomID, activeText);
        returnList.add(patient);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  @Override
  public PatientUser updateRow(PatientUser orm, PatientUser repl){
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"users\".\"patient\"";
      // queries
      String query =
          "UPDATE patient SET name = ?, checkin = ?, checkout = ?, phone = ?, room = ?, activetext = ? WHERE id = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getName());
      ps.setString(2, repl.getIn());
      ps.setString(3, repl.getOut());
      ps.setString(4, repl.getPhone());
      ps.setString(5, repl.getRoom());
      ps.setBoolean(6, repl.isActiveText());
      ps.setInt(7, orm.getId());
      ps.execute();
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  @Override
  public PatientUser addRow(PatientUser orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"users\".\"patient\" (id, name, checkin, checkout, phone, room, activetext) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setInt(1, orm.getId());
      ps.setString(2, orm.getName());
      ps.setString(3, orm.getIn());
      ps.setString(4, orm.getOut());
      ps.setString(5, orm.getPhone());
      ps.setString(6, orm.getRoom());
      ps.setBoolean(7, orm.isActiveText());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int id = rs.getInt("id");
      orm =
          new PatientUser(
              id, orm.getName(), orm.getIn(), orm.getOut(), orm.getPhone(), orm.getRoom(), orm.isActiveText());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  @Override
  public PatientUser deleteRow(PatientUser orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"users\".\"patient\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE id = " + orm.getId();
      stmtNode.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public PatientUser fetchObject(Integer key) {
    return null;
  }
}
