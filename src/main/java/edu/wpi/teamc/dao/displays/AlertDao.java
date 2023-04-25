package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDao implements IDao<Alert, Integer> {
  @Override
  public List<Alert> fetchAllObjects() {
    List<Alert> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"displays\".\"Alert\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String type = rs.getString("type");
        Timestamp startdate = Timestamp.valueOf(rs.getString("startdate"));
        Timestamp enddate = Timestamp.valueOf(rs.getString("enddate"));
        Alert request = new Alert(id, title, description, type, startdate, enddate);
        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return returnList;
  }

  @Override
  public Alert updateRow(Alert type, Alert type2) {
    Alert alert = null;
    DBConnection db = new DBConnection();
    try {
      String query =
          "UPDATE \"displays\".\"Alert\" SET title = ?, description = ?, type = ?, startdate = ?, enddate = ? WHERE id = ?";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, type2.getTitle());
      ps.setString(2, type2.getDescription());
      ps.setString(3, type2.getType());
      ps.setTimestamp(4, type2.getStartdate());
      ps.setTimestamp(5, type2.getEnddate());
      ps.setInt(6, type2.getId());
      ps.executeUpdate();
      alert = type2;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return alert;
  }

  @Override
  public Alert addRow(Alert type) {
    DBConnection db = new DBConnection();
    Alert alert = null;
    try {
      String query =
          "INSERT INTO \"displays\".\"Alert\" (title, description, type, startdate, enddate) VALUES (?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, type.getTitle());
      ps.setString(2, type.getDescription());
      ps.setString(3, type.getType());
      ps.setTimestamp(4, type.getStartdate());
      ps.setTimestamp(5, type.getEnddate());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        alert =
            new Alert(
                rs.getInt(1),
                type.getTitle(),
                type.getDescription(),
                type.getType(),
                type.getStartdate(),
                type.getEnddate());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return alert;
  }

  @Override
  public Alert deleteRow(Alert type) {
    Alert alert = type;
    DBConnection db = new DBConnection();
    try {
      String query = "DELETE FROM \"displays\".\"Alert\" WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, type.getId());
      ps.executeUpdate();
      alert = null;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return alert;
  }

  @Override
  public Alert fetchObject(Integer key) {
    DBConnection db = new DBConnection();
    Alert alert = null;
    try {
      String query = "SELECT * FROM \"displays\".\"Alert\" WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, key);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String type = rs.getString("type");
        Timestamp startdate = Timestamp.valueOf(rs.getString("startdate"));
        Timestamp enddate = Timestamp.valueOf(rs.getString("enddate"));
        alert = new Alert(id, title, description, type, startdate, enddate);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return alert;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("id,title,description,type,startdate,enddate\n");
    for (Alert alert : fetchAllObjects()) {
      writer.write(
          alert.getId()
              + ","
              + alert.getTitle()
              + ","
              + alert.getDescription()
              + ","
              + alert.getType()
              + ","
              + alert.getStartdate()
              + ","
              + alert.getEnddate()
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
