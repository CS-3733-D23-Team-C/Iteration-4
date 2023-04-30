package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.CApp;
// import static edu.wpi.teamc.CApp.wpiDB;
import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignEntryDao implements IDao<SignEntry, SignEntry> {

  @Override
  public List<SignEntry> fetchAllObjects() {
    List<SignEntry> returnList = new ArrayList<>();
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

  public static List<SignEntry> fetchAllObjectsExport(){
    return null;
  }
  @Override
  public SignEntry updateRow(SignEntry type, SignEntry type2) {
    return null;
  }

  @Override
  public SignEntry addRow(SignEntry type) {
    SignEntry sign = null;
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

  public void updateMacAddress(String oldmac, String newmac) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      String query = "UPDATE \"displays\".\"Signage\" SET macadd = ? WHERE macadd = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, newmac);
      ps.setString(2, oldmac);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("macadd,devicename,date,locationname,direction\n");
    for (SignEntry signEntry : fetchAllObjects()) {
      writer.write(
          signEntry.getMacadd()
              + ","
              + signEntry.getDevicename()
              + ","
              + signEntry.getDate()
              + ","
              + signEntry.getLocationname()
              + ","
              + signEntry.getDirection()
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
