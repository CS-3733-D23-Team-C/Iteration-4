package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Requester req = new Requester(0, rs.getString("Requester"));
        String roomName = rs.getString("roomName");
        String flower = rs.getString("flower");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalNotes");
        String eta = rs.getString("ETA");
        FlowerDeliveryRequest fdr =
            new FlowerDeliveryRequest(requestID, req, roomName, flower, additionalNotes);
        fdr.setStatus(status);
        fdr.setEta(eta);
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
              + " SET req=?, roomName=?, \"flower\"=?, additionalNotes=?, status =?, eta=? WHERE requestID=?;";

      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setString(1, repl.getRequester().toString());
      ps.setString(2, repl.getRoomName());
      ps.setString(3, repl.getFlower());
      ps.setString(4, repl.getAdditionalNotes());
      ps.setString(5, repl.getStatus().toString());
      ps.setString(6, repl.getEta());

      ps.setInt(7, orm.getRequestID());

      ps.execute();
      fdr =
          new FlowerDeliveryRequest(
              orm.getRequestID(),
              repl.getRequester(),
              repl.getRoomName(),
              repl.getFlower(),
              repl.getAdditionalNotes());
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
              + " (requester, roomName, flower, additionalNotes, status) VALUES (?,?,?,?,?);";

      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getFlower());
      ps.setString(4, orm.getAdditionalNotes());
      ps.setString(5, orm.getStatus().toString());

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
  public FlowerDeliveryRequest fetchObject(Integer key) throws SQLException {
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
        Requester req = new Requester(0, rs.getString("Requester"));
        String roomName = rs.getString("roomName");
        String flower = rs.getString("flower");
        STATUS status = STATUS.valueOf(rs.getString("status"));
        String additionalNotes = rs.getString("additionalNotes");
        String eta = rs.getString("ETA");
        fdr = new FlowerDeliveryRequest(requestID, req, roomName, flower, additionalNotes);
        fdr.setStatus(status);
        fdr.setEta(eta);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fdr;
  }
}
