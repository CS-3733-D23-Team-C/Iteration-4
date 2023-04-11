package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest> {
  public List<ConferenceRoomRequest> fetchAllObjects() throws SQLException {
    List<ConferenceRoomRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statment stmt = db.getConnection().createStatement();
      //Table Name
      String table = "\"ServiceRequest\".\"conferenceRoomRequest\"";
      //Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        //Get all the data from the table
        String requestID = rs.getString("requestID");
        String requester = rs.getString("Requester");
        String conferenceRoom = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String startTime = rs.getString("startTime");
        String endTime = rs.getString("endTime");

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;

  }

  public ConferenceRoomRequest addRow(ConferenceRoomRequest orm){
    DBConnection db = new DBConnection();
    ConferenceRoomRequest request = null;
    try{
      String query = "INSERT INTO \"ServiceRequest\".\"conferenceRoomRequest\" (requestID, Requester, roomName, additionalNotes, startTime, endTime) VALUES (?,?,?,?,?,?)";
      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getStartTime());
      ps.setString(5, orm.getEndTime());
      ps.executeUpdate();

      ResultSet rs = ps.getResultSet();
      rs.next();
      int requestID = rs.getInt("requestID");
      request = new ConferenceRoomRequest(requestID, orm.getRequester(), orm.getRoomName(), orm.getAdditionalNotes(), orm.getStartTime(), orm.getEndTime());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ConferenceRoomRequest updateRow(ConferenceRoomRequest orm, ConferenceRoomRequest repl) throws SQLException {
    DBConnection db = new DBConnection();
    ConferenceRoomRequest crr = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // queries
      String query = ""  ;

      PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, orm.getRequester().toString());
        ps.setString(2, orm.getRoomName());
        ps.setString(3, orm.getAdditionalNotes());
        ps.setString(4, orm.getStartTime());
        ps.setString(5, orm.getEndTime());

        ps.setString(6, orm.getRequestID());

      ps.execute();


      crr = new ConferenceRoomRequest(orm.getRequestID(), orm.getRequester(), orm.getRoomName(), orm.getAdditionalNotes(), orm.getStartTime(), orm.getEndTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return crr;
  }

    public ConferenceRoomRequest deleteRow(ConferenceRoomRequest orm) throws SQLException {
        DBConnection db = new DBConnection();
        ConferenceRoomRequest crr = null;
        try {
        Statement stmtNode = db.getConnection().createStatement();
        // table names
        String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
        // queries
        String query = "DELETE FROM " + table + " WHERE requestID = " + orm.getRequestID();

        stmtNode.executeUpdate(query);

        crr = new ConferenceRoomRequest(orm.getRequestID(), orm.getRequester(), orm.getRoomName(), orm.getAdditionalNotes(), orm.getStartTime(), orm.getEndTime());
        } catch (Exception e) {
        e.printStackTrace();
        }
        db.closeConnection();
        return crr;
    }

}




