package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginDao implements IDao<Login, String> {
  @Override
  public List<Login> fetchAllObjects() {
    List<Login> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"users\".\"login\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        String username = rs.getString("username");
        String password = rs.getString("password");
        String permissions = rs.getString("permissions");
        String salt = rs.getString("salt");
        Login login = new Login(username, password, permissions, salt);
        returnList.add(login);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  @Override
  public Login updateRow(Login orm, Login repl) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"users\".\"login\"";
      // queries
      String query =
          "UPDATE "
              + table
              + " SET "
              + "username = ?, "
              + "password = ?, "
              + "permissions = ?, "
              + "salt = ?"
              + " WHERE username = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getUsername());
      ps.setString(2, repl.getHashedPassword());
      ps.setString(3, repl.getPermissions());
      ps.setString(4, repl.getSalt());
      ps.setString(5, repl.getUsername());
      ps.execute();
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  @Override
  public Login addRow(Login type) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"users\".\"login\"";
      // queries
      String query =
          "INSERT INTO " + table + " (username, password, permissions, salt) VALUES (?,?,?,?)";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.getUsername());
      ps.setString(2, type.getHashedPassword());
      ps.setString(3, type.getPermissions());
      ps.setString(4, type.getSalt());
      ps.execute();
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return type;
  }

  @Override
  public Login deleteRow(Login type) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"users\".\"login\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE username = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.getUsername());
      ps.execute();
      db.closeConnection();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return type;
  }

  @Override
  public Login fetchObject(String key) {
    DBConnection db = new DBConnection();
    Login login = null;
    key = key.toLowerCase();
    try {
      // table names
      String table = "\"users\".\"login\"";
      // queries
      String query = "SELECT * FROM " + table + " WHERE username = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, key);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        // Get all the data from the table
        String username = rs.getString("username");
        String password = rs.getString("password");
        String permissions = rs.getString("permissions");
        String salt = rs.getString("salt");
        login = new Login(username, password, permissions, salt);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return login;
  }
}
