package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignEntryDao implements IDao<SignEntry, SignEntry> {

  @Override
  public List<SignEntry> fetchAllObjects() {
    List<SignEntry> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"displays\".\"Signage\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        String macadd = rs.getString("macadd");
        String devicename = rs.getString("devicename");
        Date date = Date.valueOf(rs.getString("date"));
        String locationname = rs.getString("locationname");
        DIRECTION direction = DIRECTION.valueOf(rs.getString("direction"));
        SignEntry sign = new SignEntry(macadd, devicename, date, locationname, direction);
        returnList.add(sign);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return returnList;
  }

  @Override
  public SignEntry updateRow(SignEntry type, SignEntry type2) {
    return null;
  }

  @Override
  public SignEntry addRow(SignEntry type) {
    SignEntry sign = null;
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"displays\".\"Signage\" (macadd, devicename, date, locationname, direction) VALUES (?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, type.getMacadd());
      ps.setString(2, type.getDevicename());
      ps.setDate(3, type.getDate());
      ps.setString(4, type.getLocationname());
      ps.setString(5, type.getDirection().toString());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      String macadd = rs.getString("macadd");
      String devicename = rs.getString("devicename");
      Date date = Date.valueOf(rs.getString("date"));
      String locationname = rs.getString("locationname");
      DIRECTION direction = DIRECTION.valueOf(rs.getString("direction"));
      sign = new SignEntry(macadd, devicename, date, locationname, direction);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return sign;
  }

  @Override
  public SignEntry deleteRow(SignEntry type) {
    return null;
  }

  @Override
  public SignEntry fetchObject(SignEntry key) {
    return null;
  }

  public void deleteVersion(String macadd, Date date) {
    DBConnection db = new DBConnection();
    try {
      String query = "DELETE FROM \"displays\".\"Signage\" WHERE macadd = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, macadd);
      ps.setDate(2, date);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
  }

  public void deleteAllVersions(String macadd) {
    DBConnection db = new DBConnection();
    try {
      String query = "DELETE FROM \"displays\".\"Signage\" WHERE macadd = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, macadd);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
  }

  public void updateDeviceName(String macadd, String newDeviceName) {
    DBConnection db = new DBConnection();
    try {
      String query = "UPDATE \"displays\".\"Signage\" SET devicename = ? WHERE macadd = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, newDeviceName);
      ps.setString(2, macadd);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
  }
}
