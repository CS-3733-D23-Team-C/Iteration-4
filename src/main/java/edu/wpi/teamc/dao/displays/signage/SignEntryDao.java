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
  public Sign updateRow(Sign orm, Sign repl) {
    Sign sign = null;
    DBConnection db = new DBConnection();
    try {
      String query = "UPDATE \"displays\".\"Signage\" SET id = ?, name = ? WHERE id = ?;";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, repl.id);
      ps.setString(2, repl.name);
      ps.setInt(3, orm.id);
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
      String query = "INSERT INTO \"displays\".\"Signage\" (name) VALUES (?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, type.getName());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        type.id = rs.getInt(1);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    db.closeConnection();

    return type;
  }

  @Override
  public Sign deleteRow(Sign type) {
    DBConnection db = new DBConnection();
    try {
      String q = "DELETE FROM \"displays\".\"Signage\" WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(q);
      ps.setInt(1, type.id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    db.closeConnection();
    return type;
  }

  @Override
  public Sign fetchObject(Integer key) {
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM \"displays\".\"Signage\" WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, key);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Sign s = new Sign(name);
        s.id = id;
        return s;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    db.closeConnection();
    return null;
  }
}
