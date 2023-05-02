package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.CApp;
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

public class GiftBasketDAO implements IDao<GiftBasketRequest, Integer> {

  // this one should work
  public List<GiftBasketRequest> fetchAllObjects() {
    List<GiftBasketRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"giftBasketRequest\"";
      // Query
      String query = "SELECT * FROM " + table;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestid");
        String requester = rs.getString("requester");
        String status = rs.getString("status");
        String additionalNotes = rs.getString("additionalnotes");
        String deliveryTime = rs.getString("eta");
        String roomName = rs.getString("roomName");
        String assignedTo = rs.getString("assignedto");
        String giftbasket = rs.getString("giftbasket");
        GiftBasketRequest request =
            new GiftBasketRequest(
                requestID,
                new PatientUser(requester),
                additionalNotes,
                giftbasket,
                deliveryTime,
                roomName);

        //        request.setEta(deliveryTime);
        request.setStatus(STATUS.valueOf(status));
        request.setAssignedto(assignedTo);

        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public GiftBasketRequest addRow(GiftBasketRequest orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "INSERT INTO \"ServiceRequests\".\"giftBasketRequest\" (requester, roomname, additionalnotes, giftbasket, eta, status, assignedto) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getGiftbasket().toString());
      ps.setString(5, orm.getEta());
      ps.setString(6, orm.getStatus().toString());
      ps.setString(7, orm.getAssignedto());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      orm.requestID = (rs.getInt("requestid"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public GiftBasketRequest updateRow(GiftBasketRequest orm, GiftBasketRequest orm2) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    GiftBasketRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "UPDATE \"ServiceRequests\".\"giftBasketRequest\" SET requestid = ?, requester = ?, status = ?, additionalnotes = ?, giftbasket = ?, eta= ?, roomname = ?, assignedto = ? WHERE requestid = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, orm2.getRequestID());
      ps.setString(2, orm2.getRequester().toString());
      ps.setString(3, orm2.getStatus().toString());
      ps.setString(4, orm2.getAdditionalNotes());
      ps.setString(5, orm2.getGiftbasket().toString());
      ps.setString(6, orm2.getEta());
      ps.setString(7, orm2.getRoomName());
      ps.setString(8, orm2.getAssignedto());
      ps.setInt(9, orm.getRequestID());
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm2;
  }

  public GiftBasketRequest deleteRow(GiftBasketRequest orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    GiftBasketRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query = "DELETE FROM \"ServiceRequests\".\"giftBasketRequest\" WHERE requestid = ?";
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
  public GiftBasketRequest fetchObject(Integer key) {
    GiftBasketRequest request = null;
    try {
      DBConnection db = new DBConnection(CApp.getWpiDB());
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"giftBasketRequest\"";
      // Query
      String query = "SELECT * FROM " + table + " WHERE requestid = " + key;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestid");
        String requester = rs.getString("requester");
        String giftbasket = rs.getString("giftbasket");
        String roomName = rs.getString("roomname");
        String additionalNotes = rs.getString("additionalnotes");
        String deliveryTime = rs.getString("eta");
        request =
            new GiftBasketRequest(
                requestID,
                new PatientUser(requester),
                roomName,
                additionalNotes,
                giftbasket,
                deliveryTime);
        //        request.setEta(deliveryTime);
        request.setStatus(STATUS.valueOf(rs.getString("status")));
        request.setAssignedto(rs.getString("assignedto"));
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
    writer.write("requestid,requester,status,additionalnotes,giftbasket,eta,roomname,assignedto\n");
    for (GiftBasketRequest GiftBasketRequest : fetchAllObjects()) {
      writer.write(
          GiftBasketRequest.getRequestID()
              + ","
              + GiftBasketRequest.getRequester()
              + ","
              + GiftBasketRequest.getStatus()
              + ","
              + GiftBasketRequest.getAdditionalNotes()
              + ","
              + GiftBasketRequest.getGiftbasket()
              + ","
              + GiftBasketRequest.getEta()
              + ","
              + GiftBasketRequest.getRoomName()
              + ","
              + GiftBasketRequest.getAssignedto()
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
