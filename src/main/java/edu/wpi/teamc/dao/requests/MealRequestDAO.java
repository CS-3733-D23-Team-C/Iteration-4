package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealRequestDAO {
    public List<MealRequest> fetchAllObjects() throws SQLException {
        List<MealRequest> returnList = new ArrayList<>();
        DBConnection db = new DBConnection();
        try {
            statment stmt = db.getConnection().createStatement();
            //Table Name
            String table = "\"ServiceRequest\".\"mealRequest\"";
            //Query
            String query = "SELECT * FROM " + table;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Get all the data from the table
                String requestID = rs.getString("requestID");
                String requester = rs.getString("Requester");
                String mealType = rs.getString("mealType");
                String additionalNotes = rs.getString("additionalNotes");
                String deliveryTime = rs.getString("deliveryTime");
                String deliveryLocation = rs.getString("deliveryLocation");
                MealRequest request = new MealRequest(requestID, requester, mealType, additionalNotes, deliveryTime, deliveryLocation);
                returnList.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MealRequest addRow(MealRequest orm){
        DBConnection db = new DBConnection();
        MealRequest request = null;
        try{
            Statment stmtNode = db.getConnection().createStatement();
            String query = "INSERT INTO \"ServiceRequest\".\"mealRequest\" (requestID, Requester, mealType, additionalNotes, deliveryTime, deliveryLocation) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, orm.getRequester().toString());
            ps.setString(2, orm.getMealType());
            ps.setString(3, orm.getAdditionalNotes());
            ps.setString(4, orm.getDeliveryTime());
            ps.setString(5, orm.getDeliveryLocation());
            ps.executeUpdate();
            ResultSet rs = ps.getResultSet();
            rs.next();
            request = new MealRequest(rs.getString("requestID"), rs.getString("Requester"), rs.getString("mealType"), rs.getString("additionalNotes"), rs.getString("deliveryTime"), rs.getString("deliveryLocation"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        return request;
    }

    public MealRequest updateRow(MealRequest orm, MealRequest orm2){
        DBConnection db = new DBConnection();
        MealRequest request = null;
        try{
            Statment stmtNode = db.getConnection().createStatement();
            String query = "UPDATE \"ServiceRequest\".\"mealRequest\" SET requestID = ?, Requester = ?, mealType = ?, additionalNotes = ?, deliveryTime = ?, deliveryLocation = ? WHERE requestID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, orm2.getRequester().toString());
            ps.setString(2, orm2.getMealType());
            ps.setString(3, orm2.getAdditionalNotes());
            ps.setString(4, orm2.getDeliveryTime());
            ps.setString(5, orm2.getDeliveryLocation());
            ps.setString(6, orm.getRequestID());
            ps.executeUpdate();
            ResultSet rs = ps.getResultSet();
            rs.next();
            request = new MealRequest(rs.getString("requestID"), rs.getString("Requester"), rs.getString("mealType"), rs.getString("additionalNotes"), rs.getString("deliveryTime"), rs.getString("deliveryLocation"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        return request;
    }

    public MealRequest deleteRow(MealRequest orm){
        DBConnection db = new DBConnection();
        MealRequest request = null;
        try{
            Statment stmtNode = db.getConnection().createStatement();
            String query = "DELETE FROM \"ServiceRequest\".\"mealRequest\" WHERE requestID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, orm.getRequestID());
            ps.executeUpdate();
            ResultSet rs = ps.getResultSet();
            rs.next();
            request = new MealRequest(rs.getString("requestID"), rs.getString("Requester"), rs.getString("mealType"), rs.getString("additionalNotes"), rs.getString("deliveryTime"), rs.getString("deliveryLocation"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        return request;
    }
}

