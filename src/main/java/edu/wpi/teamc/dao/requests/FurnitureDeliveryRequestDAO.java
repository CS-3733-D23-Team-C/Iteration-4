package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FurnitureDeliveryRequestDAO implements IDao<FurnitureDeliveryRequest> {
  DBConnection db = new DBConnection();

  public FurnitureDeliveryRequest addRow(
      Requester requester,
      String roomName,
      String additionalNotes,
      String furnitureType,
      String furnitureName,
      String eta) {
    String FURNDELREQ =
        "INSERT INTO FurnitureDeliveryRequest (requester, roomName, additionalNotes, furnitureType, furnitureName, eta) VALUES (?,?,?,?,?,?)";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(FURNDELREQ);
      ps.setString(1, requester.toString());
      ps.setString(2, roomName);
      ps.setString(3, additionalNotes);
      ps.setString(4, furnitureType);
      ps.setString(5, furnitureName);
      ps.setString(6, eta);
      ps.executeUpdate();
      String getLastInsertID = "SELECT SCOPE_IDENTITY()";
      PreparedStatement ps2 = db.getConnection().prepareStatement(getLastInsertID);
      return new FurnitureDeliveryRequest(
          ps2.executeUpdate(), requester, roomName, additionalNotes, furnitureType, eta);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public FurnitureDeliveryRequest updateRow(
      Requester requester,
      String roomName,
      String additionalNotes,
      String furnitureType,
      String furnitureName,
      String eta,
      Requester requester2,
      String roomName2,
      String additionalNotes2,
      String furnitureType2,
      String furnitureName2,
      String eta2) {
    String FURNDELREQ =
        "UPDATE FurnitureDeliveryRequest SET requester = ?, roomName = ?, additionalNotes = ?, furnitureType = ?, eta = ? WHERE requester = ? AND roomName = ? AND additionalNotes = ? AND furnitureType = ? AND eta = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(FURNDELREQ);
      ps.setString(1, requester.toString());
      ps.setString(2, roomName);
      ps.setString(3, additionalNotes);
      ps.setString(4, furnitureType);
      ps.setString(6, eta);
      ps.executeUpdate();
      return new FurnitureDeliveryRequest(
          0, requester, roomName, additionalNotes, furnitureType, eta);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public FurnitureDeliveryRequest deleteRow(
      Requester requester,
      String roomName,
      String additionalNotes,
      String furnitureType,
      String eta) {
    String FURNDELREQ =
        "DELETE FROM FurnitureDeliveryRequest WHERE requester = ? AND roomName = ? AND additionalNotes = ? AND furnitureType = ? AND eta = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(FURNDELREQ);
      ps.setString(1, requester.toString());
      ps.setString(2, roomName);
      ps.setString(3, additionalNotes);
      ps.setString(4, furnitureType);
      ps.setString(5, eta);
      ps.executeUpdate();
      return new FurnitureDeliveryRequest(
          0, requester, roomName, additionalNotes, furnitureType, eta);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<FurnitureDeliveryRequest> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public FurnitureDeliveryRequest updateRow(
      FurnitureDeliveryRequest type, FurnitureDeliveryRequest type2) throws SQLException {
    return null;
  }

  @Override
  public FurnitureDeliveryRequest addRow(FurnitureDeliveryRequest type) {
    return null;
  }

  @Override
  public FurnitureDeliveryRequest deleteRow(FurnitureDeliveryRequest type) throws SQLException {
    return null;
  }
}
