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

public class OfficeSuppliesRequestDAO implements IDao<OfficeSuppliesRequest, Integer> {

  public List<OfficeSuppliesRequest> fetchAllObjects() {
    DBConnection db = new DBConnection();
    List<OfficeSuppliesRequest> returnList = new ArrayList<>();
    String table = "\"ServiceRequests\".\"officeSupplyRequest\"";
    // queries
    String query = "SELECT * FROM " + table;
    try {
      ResultSet rs = db.getConnection().prepareStatement(query).executeQuery();
      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        IUser req = new PatientUser(rs.getString("Requester"));
        String roomName = rs.getString("roomname");
        String supplies = rs.getString("officesupplytype");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalnotes");
        String eta = rs.getString("eta");
        String assignedto = rs.getString("assignedto");
        OfficeSuppliesRequest request =
            new OfficeSuppliesRequest(requestID, req, roomName, supplies, additionalNotes);
        request.setStatus(status);
        request.setEta(eta);
        request.setAssignedto(assignedto);
        returnList.add(request);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return returnList;
  }

  public OfficeSuppliesRequest addRow(OfficeSuppliesRequest orm) {
    DBConnection db = new DBConnection();
    String table = "\"ServiceRequests\".\"officeSupplyRequest\"";
    // queries
    String query =
        "INSERT INTO "
            + table
            + " (requester, roomName, officesupplytype, additionalNotes, status, assignedto) VALUES (?,?,?,?,?,?);";
    try {
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getOfficesupplytype());
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

  public OfficeSuppliesRequest deleteRow(OfficeSuppliesRequest orm) {
    DBConnection db = new DBConnection();
    OfficeSuppliesRequest request = orm;
    String table = "\"ServiceRequests\".\"officeSupplyRequest\"";
    // queries
    String query = "DELETE FROM " + table + " WHERE requestid = ? ;";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
      request = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return request;
  }

  @Override
  public OfficeSuppliesRequest fetchObject(Integer key) {
    OfficeSuppliesRequest request = null;
    try {
      DBConnection db = new DBConnection();
      String table = "\"ServiceRequests\".\"officeSupplyRequest\"";
      // queries
      String query = "SELECT * FROM " + table + " WHERE requestid = " + key + ";";
      ResultSet rs = db.getConnection().prepareStatement(query).executeQuery();
      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        IUser req = new PatientUser(rs.getString("Requester"));
        String roomName = rs.getString("roomname");
        String supplies = rs.getString("officesupplytype");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalnotes");
        String eta = rs.getString("eta");
        String assignedto = rs.getString("assignedto");
        request = new OfficeSuppliesRequest(requestID, req, roomName, supplies, additionalNotes);
        request.setStatus(status);
        request.setEta(eta);
        request.setAssignedto(assignedto);
      }
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return request;
  }

  public OfficeSuppliesRequest updateRow(OfficeSuppliesRequest orm, OfficeSuppliesRequest repl) {
    DBConnection db = new DBConnection();
    OfficeSuppliesRequest request = null;
    String table = "\"ServiceRequests\".\"officeSupplyRequest\"";
    // queries
    String query =
        "UPDATE "
            + table
            + " SET requestid = ?, requester = ?, status = ?, additionalNotes = ?, officesupplytype = ?, eta = ?, roomname = ?, assignedto = ? WHERE requestid = ?;";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, repl.getRequestID());
      ps.setString(2, repl.getRequester().toString());
      ps.setString(3, repl.getStatus().toString());
      ps.setString(4, repl.getAdditionalNotes());
      ps.setString(5, repl.getOfficesupplytype());
      ps.setString(6, repl.getEta());
      ps.setString(7, repl.getRoomName());
      ps.setString(8, repl.getAssignedto());
      ps.setInt(9, orm.getRequestID());
      ps.executeQuery();

      request = repl;
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return request;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write(
        "requestid,requester,roomname,status,additionalnotes,eta,officesupplytype,assignedto\n");
    for (OfficeSuppliesRequest officeSuppliesRequest : fetchAllObjects()) {
      writer.write(
          officeSuppliesRequest.getRequestID()
              + ","
              + officeSuppliesRequest.getRequester()
              + ","
              + officeSuppliesRequest.getRoomName()
              + ","
              + officeSuppliesRequest.getStatus()
              + ","
              + officeSuppliesRequest.getAdditionalNotes()
              + ","
              + officeSuppliesRequest.getEta()
              + ","
              + officeSuppliesRequest.getOfficesupplytype()
              + ","
              + officeSuppliesRequest.getAssignedto()
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
