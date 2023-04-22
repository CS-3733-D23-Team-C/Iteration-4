package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PatientUser;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnitureDeliveryRequestDAO implements IDao<FurnitureDeliveryRequest, Integer> {
  public List<FurnitureDeliveryRequest> fetchAllObjects() {
    List<FurnitureDeliveryRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();

    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"furnitureDeliveryRequest\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestid");
        String requester = rs.getString("requester");
        String furnitureType = rs.getString("furnituretype");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("eta");
        String deliveryLocation = rs.getString("roomname");
        String assignedto = rs.getString("assignedto");

        FurnitureDeliveryRequest request =
            new FurnitureDeliveryRequest(
                requestID,
                new PatientUser(requester),
                deliveryLocation,
                additionalNotes,
                furnitureType,
                deliveryTime);
        request.setAssignedto(assignedto);
        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public FurnitureDeliveryRequest addRow(FurnitureDeliveryRequest orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"ServiceRequests\".\"furnitureDeliveryRequest\" (Requester, furnitureType, additionalNotes, roomName, status, assignedto) VALUES (?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getFurnituretype());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getRoomName());
      ps.setString(5, orm.getStatus().toString());
      ps.setString(6, orm.getAssignedto());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestid");
      orm.requestID = (requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  public FurnitureDeliveryRequest updateRow(
      FurnitureDeliveryRequest orm, FurnitureDeliveryRequest orm2) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "UPDATE \"ServiceRequests\".\"furnitureDeliveryRequest\" SET Requester = ?, furnitureType = ?, additionalNotes = ?, ETA = ?, roomName = ?, assignedto = ? WHERE requestid = ?";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm2.getRequester().toString());
      ps.setString(2, orm2.getFurnituretype());
      ps.setString(3, orm2.getAdditionalNotes());
      ps.setString(4, orm2.getEta());
      ps.setString(5, orm2.getRoomName());
      ps.setString(6, orm2.getAssignedto());
      ps.setInt(7, orm.getRequestID());
      ps.executeUpdate();

      ResultSet rs = ps.getResultSet();
      rs.next();
      int requestID = rs.getInt("requestid");
      orm2.requestID = (requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm2;
  }

  public FurnitureDeliveryRequest deleteRow(FurnitureDeliveryRequest orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "DELETE FROM \"ServiceRequests\".\"furnitureDeliveryRequest\" WHERE requestid = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  @Override
  public FurnitureDeliveryRequest fetchObject(Integer key) throws SQLException {
    FurnitureDeliveryRequest request = null;
    try {
      DBConnection db = new DBConnection();
      String query =
          "SELECT * FROM \"ServiceRequests\".\"furnitureDeliveryRequest\" WHERE requestid = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, key);
      ResultSet rs = ps.executeQuery();
      rs.next();
      int requestID = rs.getInt("requestid");
      String requester = rs.getString("requester");
      String furnitureType = rs.getString("furnituretype");
      String additionalNotes = rs.getString("additionalNotes");
      String deliveryTime = rs.getString("eta");
      String deliveryLocation = rs.getString("roomname");
      String assignedto = rs.getString("assignedto");

      request =
          new FurnitureDeliveryRequest(
              requestID,
              new PatientUser(requester),
              deliveryLocation,
              additionalNotes,
              furnitureType,
              deliveryTime);
      request.setAssignedto(assignedto);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return request;
  }
}
