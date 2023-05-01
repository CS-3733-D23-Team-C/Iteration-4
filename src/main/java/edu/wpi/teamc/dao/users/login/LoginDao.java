package edu.wpi.teamc.dao.users.login;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
        String email = rs.getString("email");
        String password = rs.getString("password");
        PERMISSIONS permissions = PERMISSIONS.valueOf(rs.getString("permissions"));
        String salt = rs.getString("salt");
        String otp = rs.getString("otp");
        Login login = new Login(username, password, permissions, salt, otp);
        login.setEmail(email);
        returnList.add(login);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  @Override
  public Login updateRow(Login orm, Login repl) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
              + "salt = ?,"
              + "otp = ?,"
              + "email = ?"
              + " WHERE username = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getUsername());
      ps.setString(2, repl.hashedPassword);
      ps.setString(3, repl.getPermissions().toString());
      ps.setString(4, repl.salt);
      ps.setString(5, repl.getOtp());
      ps.setString(6, repl.getEmail());
      ps.setString(7, orm.getUsername());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String table = "\"users\".\"login\"";
      // queries
      String query =
          "INSERT INTO "
              + table
              + " (username, password, permissions, salt, otp, email) VALUES (?,?,?,?,?,?)";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.getUsername());
      ps.setString(2, type.hashedPassword);
      ps.setString(3, type.getPermissions().toString());
      ps.setString(4, type.salt);
      ps.setString(5, type.getOtp());
      ps.setString(6, type.getEmail());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
    Login login = null;
    try {
      key = key.toLowerCase();
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
        String email = rs.getString("email");
        String password = rs.getString("password");
        PERMISSIONS permissions = PERMISSIONS.valueOf(rs.getString("permissions"));
        String salt = rs.getString("salt");
        String otp = rs.getString("otp");
        login = new Login(username, password, permissions, salt, otp);
        login.setEmail(email);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return login;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("username,password,permissions,salt,otp\n");
    for (Login login : fetchAllObjects()) {
      writer.write(
          login.getUsername()
              + ","
              + login.getHashedPassword()
              + ","
              + login.getPermissions()
              + ","
              + login.getSalt()
              + ","
              + login.getOtp()
              + "\n");
    }
    writer.close();
    return true;
  }

  static void createFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.createNewFile()) {
      System.out.println("File created: " + file.getName());
    } else {
      System.out.println("File already exists.");
    }
  }
}
