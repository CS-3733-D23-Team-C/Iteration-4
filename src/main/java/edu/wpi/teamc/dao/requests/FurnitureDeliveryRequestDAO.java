package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FurnitureDeliveryRequestDAO implements IDao<FurnitureDeliveryRequest> {
    public List<FurnitureDeliveryRequest> fetchAllObjects(){
        List<FurnitureDeliveryRequest> returnList = new ArrayList<>();
        DBConnection db = new DBConnection();

        try{
            Statement stmt = db.getConnection().createStatement();
            //Table Name
            String table = "\"ServiceRequest\".\"furnitureDeliveryRequest\"";
            //Query
            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                //Get all the data from the table
                String requestID = rs.getString("requestID");
                String requester = rs.getString("Requester");
                String furnitureType = rs.getString("furnitureType");
                String additionalNotes = rs.getString("additionalNotes");
                String deliveryTime = rs.getString("deliveryTime");
                String deliveryLocation = rs.getString("deliveryLocation");

                FurnitureDeliveryRequest request = new FurnitureDeliveryRequest(requestID, requester, furnitureType, additionalNotes, deliveryTime, deliveryLocation);
                returnList.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FurnitureDeliveryRequest addRow(FurnitureDeliveryRequest orm){
        DBConnection db = new DBConnection();
        FurnitureDeliveryRequest request = null;
        try{
            String query = "INSERT INTO \"ServiceRequest\".\"furnitureDeliveryRequest\" (requestID, Requester, furnitureType, additionalNotes, deliveryTime, deliveryLocation) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);

            ps.setString(1, orm.getRequester().toString());
            ps.setString(2, orm.getFurnitureType());
            ps.setString(3, orm.getAdditionalNotes());
            ps.setString(4, orm.getDeliveryTime());
            ps.setString(5, orm.getDeliveryLocation());
            ps.executeUpdate();

            ResultSet rs = ps.getResultSet();
            rs.next();
            int requestID = rs.getInt("requestID");
            request = new FurnitureDeliveryRequest(requestID, orm.getRequester(), orm.getFurnitureType(), orm.getAdditionalNotes(), orm.getDeliveryTime(), orm.getDeliveryLocation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    public FurnitureDeliveryRequest updateRow(FurnitureDeliveryRequest orm, FurnitureDeliveryRequest orm2){
        DBConnection db = new DBConnection();
        FurnitureDeliveryRequest request = null;
        try{
            String query = "UPDATE \"ServiceRequest\".\"furnitureDeliveryRequest\" SET requestID = ?, Requester = ?, furnitureType = ?, additionalNotes = ?, deliveryTime = ?, deliveryLocation = ? WHERE requestID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);

            ps.setString(1, orm2.getRequester().toString());
            ps.setString(2, orm2.getFurnitureType());
            ps.setString(3, orm2.getAdditionalNotes());
            ps.setString(4, orm2.getDeliveryTime());
            ps.setString(5, orm2.getDeliveryLocation());
            ps.setString(6, orm.getRequestID());
            ps.executeUpdate();

            ResultSet rs = ps.getResultSet();
            rs.next();
            int requestID = rs.getInt("requestID");
            request = new FurnitureDeliveryRequest(requestID, orm2.getRequester(), orm2.getFurnitureType(), orm2.getAdditionalNotes(), orm2.getDeliveryTime(), orm2.getDeliveryLocation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    public FurnitureDeliveryRequest deleteRow(FurnitureDeliveryRequest orm){
        DBConnection db = new DBConnection();
        FurnitureDeliveryRequest request = null;
        try{
            String query = "DELETE FROM \"ServiceRequest\".\"furnitureDeliveryRequest\" WHERE requestID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);

            ps.setString(1, orm.getRequestID());
            ps.executeUpdate();

            ResultSet rs = ps.getResultSet();
            rs.next();
            int requestID = rs.getInt("requestID");
            request = new FurnitureDeliveryRequest(requestID, orm.getRequester(), orm.getFurnitureType(), orm.getAdditionalNotes(), orm.getDeliveryTime(), orm.getDeliveryLocation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }
}
