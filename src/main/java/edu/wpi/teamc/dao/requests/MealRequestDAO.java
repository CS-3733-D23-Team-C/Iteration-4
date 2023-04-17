package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MealRequestDAO implements IDao<MealRequest, Integer> {
  public List<MealRequest> fetchAllObjects() {
    List<MealRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"mealRequest\"";
      // Query
      String query = "SELECT * FROM " + table;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String mealType = rs.getString("meal");
        String roomName = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("ETA");
        MealRequest request =
            new MealRequest(
                requestID,
                new Requester(requestID, requester),
                roomName,
                additionalNotes,
                new Meal(mealType, ""));
        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public MealRequest addRow(MealRequest orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "INSERT INTO \"ServiceRequests\".\"mealRequest\" (Requester, meal, additionalNotes, ETA, roomName, status) VALUES (?,?,?,?,?, ?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getMeal().toString());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getEta());
      ps.setString(5, orm.getRoomName());
      ps.setString(6, orm.getStatus().toString());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.setRequestID(requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public MealRequest updateRow(MealRequest orm, MealRequest orm2) {
    DBConnection db = new DBConnection();
    MealRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "UPDATE \"ServiceRequests\".\"mealRequest\" SET Requester = ?, meal = ?, additionalNotes = ?, ETA = ?, roomName = ? WHERE requestID = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, orm2.getRequester().toString());
      ps.setString(2, orm2.getMeal().getMealName());
      ps.setString(3, orm2.getAdditionalNotes());
      ps.setString(4, orm2.getEta());
      ps.setString(5, orm2.getRoomName());
      ps.setInt(6, orm.getRequestID());
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm2;
  }

  public MealRequest deleteRow(MealRequest orm) {
    DBConnection db = new DBConnection();
    MealRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query = "DELETE FROM \"ServiceRequests\".\"mealRequest\" WHERE requestID = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public MealRequest fetchObject(Integer key) throws SQLException {
    MealRequest request = null;
    try {
      DBConnection db = new DBConnection();
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"mealRequest\"";
      // Query
      String query = "SELECT * FROM " + table + " WHERE requestID = " + key;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String mealType = rs.getString("meal");
        String roomName = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("ETA");
        request =
            new MealRequest(
                requestID,
                new Requester(requestID, requester),
                roomName,
                additionalNotes,
                new Meal(mealType, ""));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return request;
  }
}
