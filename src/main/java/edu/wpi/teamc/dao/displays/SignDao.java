package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignDao implements IDao<Sign, Sign> {

  @Override
  public List<Sign> fetchAllObjects() throws SQLException {
    List<Sign> returnList = new ArrayList<>();
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
        String locationname = rs.getString("locationname");
        Date date = java.sql.Date.valueOf(String.valueOf(rs.getTimestamp("date")));
        String direction = rs.getString("direction");
        String screenlocation = rs.getString("screenlocation");
        String location = rs.getString("location");
        Sign s = new Sign(locationname, (java.sql.Date) date, direction, screenlocation, location);
        returnList.add(s);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return returnList;
  }

  @Override
  public Sign updateRow(Sign orm, Sign repl) throws SQLException {
    Sign sign = null;
    DBConnection db = new DBConnection();
    try {
      String query =
          "UPDATE \"displays\".\"Signage\" SET locationname = ?, date = ?, direction = ?, screenlocation = ? WHERE locationname = ? AND date = ?";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getLocation());
      ps.setDate(2, repl.getDate());
      ps.setString(3, repl.getDirection());
      ps.setString(4, repl.getScreenlocation());
      ps.setString(5, orm.getLocation());
      ps.setDate(6, orm.getDate());
      ps.executeUpdate();
      sign = repl;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return sign;
  }

  @Override
  public Sign addRow(Sign type) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"displays\".\"Signage\" (locationname, date, direction, screenlocation, location) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.getLocationname());
      ps.setDate(2, type.getDate());
      ps.setString(3, type.getDirection());
      ps.setString(4, type.getScreenlocation());
      ps.setString(5, type.getLocation());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    db.closeConnection();

    return type;
  }

  @Override
  public Sign deleteRow(Sign type) throws SQLException {
    DBConnection db = new DBConnection();
    try {
      String q = "DELETE FROM \"displays\".\"Signage\" WHERE locationname = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(q);
      ps.setString(1, type.getLocationname());
      ps.setDate(2, type.getDate());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    db.closeConnection();
    return type;
  }

  @Override
  public Sign fetchObject(Sign key) throws SQLException {
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM \"displays\".\"Signage\" WHERE locationname = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, key.getLocationname());
      ps.setDate(2, key.getDate());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String locationname = rs.getString("locationname");
        Date date = java.sql.Date.valueOf(String.valueOf(rs.getTimestamp("date")));
        String direction = rs.getString("direction");
        String screenlocation = rs.getString("screenlocation");
        String location = rs.getString("location");
        Sign s = new Sign(locationname, (java.sql.Date) date, direction, screenlocation, location);
        return s;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    db.closeConnection();
    return null;
  }
}
