package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnitureDeliveryRequestDAO implements IDao<FurnitureDeliveryRequest> {
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
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String furnitureType = rs.getString("furnitureType");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("deliveryTime");
        String deliveryLocation = rs.getString("deliveryLocation");

        FurnitureDeliveryRequest request =
            new FurnitureDeliveryRequest(
                requestID,
                new Requester(requestID, requester),
                furnitureType,
                additionalNotes,
                deliveryTime,
                deliveryLocation);
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
          "INSERT INTO \"ServiceRequests\".\"furnitureDeliveryRequest\" (Requester, furnitureType, additionalNotes, roomName, status) VALUES (?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getFurniture());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getRoomName());
      ps.setString(5, orm.getStatus().toString());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.setRequestID(requestID);
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
          "UPDATE \"ServiceRequests\".\"furnitureDeliveryRequest\" SET Requester = ?, furnitureType = ?, additionalNotes = ?, ETA = ?, roomName = ? WHERE requestID = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setString(1, orm2.getRequester().toString());
      ps.setString(2, orm2.getFurniture());
      ps.setString(3, orm2.getAdditionalNotes());
      ps.setString(4, orm2.getEta());
      ps.setString(5, orm2.getEta());
      ps.setInt(6, orm.getRequestID());
      ps.executeUpdate();

      ResultSet rs = ps.getResultSet();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm2.setRequestID(requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm2;
  }

  public FurnitureDeliveryRequest deleteRow(FurnitureDeliveryRequest orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "DELETE FROM \"ServiceRequests\".\"furnitureDeliveryRequest\" WHERE requestID = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);

      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }
}
