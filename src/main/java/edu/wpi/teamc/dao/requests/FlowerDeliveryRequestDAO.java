package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.IUser;
import edu.wpi.teamc.dao.users.PatientUser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FlowerDeliveryRequestDAO implements IDao<FlowerDeliveryRequest, Integer> {
  public List<FlowerDeliveryRequest> fetchAllObjects() {
    List<FlowerDeliveryRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();

    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"flowerRequest\"";
      // queries
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmtNode.executeQuery(query);

      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        PatientUser req = new PatientUser(rs.getString("Requester"));
        String roomName = rs.getString("roomName");
        String flower = rs.getString("flower");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalNotes");
        String eta = rs.getString("ETA");
        String assignedto = rs.getString("assignedto");
        FlowerDeliveryRequest fdr =
            new FlowerDeliveryRequest(requestID, req, roomName, flower, additionalNotes);
        fdr.setStatus(status);
        fdr.setEta(eta);
        fdr.setAssignedto(assignedto);
        returnList.add(fdr);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return returnList;
  }

  public FlowerDeliveryRequest updateRow(FlowerDeliveryRequest orm, FlowerDeliveryRequest repl) {
    DBConnection db = new DBConnection();
    FlowerDeliveryRequest fdr = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"flowerRequest\"";
      // queries
      String query =
          "UPDATE "
              + table
              + " SET requestid = ?, requester = ?, roomname = ?, status = ?, additionalnotes =? , eta = ?, flower = ?, assignedto = ? WHERE requestid=?;";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, repl.getID());
      ps.setString(2, repl.getRequester().toString());
      ps.setString(3, repl.getRoomName());
      ps.setString(4, repl.getStatus().toString());
      ps.setString(5, repl.getAdditionalNotes());
      ps.setString(6, repl.getEta());
      ps.setString(7, repl.getFlower());
      ps.setString(8, orm.getAssignedto());
      ps.setInt(9, orm.getRequestID());

      ps.execute();
      fdr =
          new FlowerDeliveryRequest(
              orm.getRequestID(),
              repl.getRequester(),
              repl.getRoomName(),
              repl.getFlower(),
              repl.getAdditionalNotes());
      fdr.setStatus(repl.getStatus());
      fdr.setEta(repl.getEta());
      fdr.setAssignedto(repl.getAssignedto());
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return fdr;
  }

  public FlowerDeliveryRequest addRow(FlowerDeliveryRequest orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"flowerRequest\"";
      // queries
      String query =
          "INSERT INTO "
              + table
              + " (requester, roomName, flower, additionalNotes, status, assignedto) VALUES (?,?,?,?,?,?);";

      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getFlower());
      ps.setString(4, orm.getAdditionalNotes());
      ps.setString(5, orm.getStatus().toString());
      ps.setString(6, orm.getAssignedto());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.requestID = (requestID);
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public FlowerDeliveryRequest deleteRow(FlowerDeliveryRequest orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"flowerRequest\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE requestID=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setInt(1, orm.getRequestID());

      ps.execute();
      orm = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public FlowerDeliveryRequest fetchObject(Integer key) {
    FlowerDeliveryRequest fdr = null;
    try {
      DBConnection db = new DBConnection();
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"flowerRequest\"";
      // queries
      String query = "SELECT * FROM " + table + " WHERE requestID = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, key);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        IUser req = new PatientUser(rs.getString("Requester"));
        String roomName = rs.getString("roomName");
        String flower = rs.getString("flower");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalNotes");
        String eta = rs.getString("ETA");
        String assignedto = rs.getString("assignedto");
        fdr = new FlowerDeliveryRequest(requestID, req, roomName, flower, additionalNotes);
        fdr.setStatus(status);
        fdr.setEta(eta);
        fdr.setAssignedto(assignedto);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fdr;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("requestid,requester,roomname,status,additionalnotes,eta,flower,assignedto\n");
    for (FlowerDeliveryRequest flowerDeliveryRequest : fetchAllObjects()) {
      writer.write(
          flowerDeliveryRequest.getRequestID()
              + ","
              + flowerDeliveryRequest.getRequester()
              + ","
              + flowerDeliveryRequest.getRoomName()
              + ","
              + flowerDeliveryRequest.getStatus()
              + ","
              + flowerDeliveryRequest.getAdditionalNotes()
              + ","
              + flowerDeliveryRequest.getEta()
              + ","
              + flowerDeliveryRequest.getFlower()
              + ","
              + flowerDeliveryRequest.getAssignedto()
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
