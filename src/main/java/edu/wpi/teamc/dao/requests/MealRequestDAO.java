package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MealRequestDAO {
    DBConnection db = new DBConnection();

    public MealRequest addRow(int requestID, Requester requester, String roomName, String note, Meal meal, String eta){
        String MEALREQ = "INSERT INTO MealRequest (requestID, requester, roomName, note, meal, eta) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = db.getConnection().prepareStatement(MEALREQ);
            ps.setInt(1, requestID);
            ps.setString(2, requester.toString());
            ps.setString(3, roomName);
            ps.setString(4, note);
            ps.setString(5, meal.toString());
            ps.setString(6, eta);
            ps.executeUpdate();
            String getLastInsertID = "SELECT SCOPE_IDENTITY()";
            PreparedStatement ps2 = db.getConnection().prepareStatement(getLastInsertID);
            return new MealRequest(ps2.executeUpdate(), requester, roomName, note, meal, eta);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


  public int updateRow(String longName, String shortName, Boolean availability) {
    return 0;
  }
}
