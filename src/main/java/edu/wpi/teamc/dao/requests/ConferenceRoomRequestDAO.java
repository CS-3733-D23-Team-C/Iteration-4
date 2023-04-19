package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PatientUser;
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
      ps.setString(6, orm.getAssingedto());
      ps.setString(7, orm.getAdditionalNotes());
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
              + "Requester = ?, "
              + "roomName = ?, "
              + "additionalNotes = ?, "
              + "startTime = ?, "
              + "endTime = ?, "
              + "assignedto = ?, "
              + "WHERE requestID = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getRequester().toString());
      ps.setString(2, repl.getRoomName());
      ps.setString(3, repl.getAdditionalNotes());
      ps.setString(4, repl.getStartTime());
      ps.setString(5, repl.getEndTime());
      ps.setString(6, repl.getAssingedto());
      ps.setInt(7, orm.getRequestID());

      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  public ConferenceRoomRequest deleteRow(ConferenceRoomRequest orm) throws SQLException {
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
  public ConferenceRoomRequest fetchObject(Integer key) throws SQLException {
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
        request.setAssingedto(assignedto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return request;
  }
}
