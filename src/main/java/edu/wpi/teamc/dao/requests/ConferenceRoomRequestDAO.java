package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PatientUser;
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

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest, Integer> {
  public List<ConferenceRoomRequest> fetchAllObjects() {
    List<ConferenceRoomRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String conferenceRoom = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String startTime = rs.getString("startTime");
        String endTime = rs.getString("endTime");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String assignedto = rs.getString("assignedto");
        ConferenceRoomRequest request =
            new ConferenceRoomRequest(
                requestID,
                new PatientUser(requester),
                new ConferenceRoom(conferenceRoom, "", null),
                additionalNotes,
                startTime,
                endTime);
        request.setStatus(status);
        request.setAssignedto(assignedto);
        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public ConferenceRoomRequest addRow(ConferenceRoomRequest orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"ServiceRequests\".\"conferenceRoomRequest\" (Requester, roomName, status, startTime, endTime, additionalNotes, assignedto) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getStatus().toString());
      ps.setString(4, orm.getStartTime());
      ps.setString(5, orm.getEndTime());
      ps.setString(6, orm.getAdditionalNotes());
      ps.setString(7, orm.getAssignedto());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.requestID = requestID;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  public ConferenceRoomRequest updateRow(ConferenceRoomRequest orm, ConferenceRoomRequest repl) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // queries
      String query =
          "UPDATE "
              + table
              + " SET "
              + "requestid = ?, "
              + "requester = ?, "
              + "roomname = ?, "
              + "status = ?, "
              + "additionalnotes = ?, "
              + "starttime = ?, "
              + "endtime = ?, "
              + "assignedto = ? "
              + "WHERE requestid = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, repl.getRequestID());
      ps.setString(2, repl.getRequester().toString());
      ps.setString(3, repl.getRoomName());
      ps.setString(4, repl.getStatus().toString());
      ps.setString(5, repl.getAdditionalNotes());
      ps.setString(6, repl.getStartTime());
      ps.setString(7, repl.getEndTime());
      ps.setString(8, repl.getAssignedto());
      ps.setInt(9, orm.getRequestID());

      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  public ConferenceRoomRequest deleteRow(ConferenceRoomRequest orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE requestID = " + orm.getRequestID();

      stmtNode.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public ConferenceRoomRequest fetchObject(Integer key) {
    ConferenceRoomRequest request = null;
    try {
      DBConnection db = new DBConnection();
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // Query
      String query = "SELECT * FROM " + table + " WHERE requestID = " + key;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String conferenceRoom = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String startTime = rs.getString("startTime");
        String endTime = rs.getString("endTime");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String assignedto = rs.getString("assignedto");
        request =
            new ConferenceRoomRequest(
                requestID,
                new PatientUser(requester),
                new ConferenceRoom(conferenceRoom, "", null),
                additionalNotes,
                startTime,
                endTime);
        request.setStatus(status);
        request.setAssignedto(assignedto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return request;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write(
        "requestid,requester,roomname,status,additionalnotes,starttime,endtime,assignedto\n");
    for (ConferenceRoomRequest conferenceRoomRequest : fetchAllObjects()) {
      writer.write(
          conferenceRoomRequest.getRequestID()
              + ","
              + conferenceRoomRequest.getRequester()
              + ","
              + conferenceRoomRequest.getRoomName()
              + ","
              + conferenceRoomRequest.getStatus()
              + ","
              + conferenceRoomRequest.getAdditionalNotes()
              + ","
              + conferenceRoomRequest.getStartTime()
              + ","
              + conferenceRoomRequest.getEndTime()
              + ","
              + conferenceRoomRequest.getAssignedto()
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
