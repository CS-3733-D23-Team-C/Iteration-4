package edu.wpi.teamc.dao.requests;
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

public class GiftBasketDAO implements IDao<GiftBasketRequest, Integer>  {

    //this one should work
    public List<GiftBasketRequest> fetchAllObjects() {
        List<GiftBasketRequest> returnList = new ArrayList<>();
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getConnection().createStatement();
            // Table Name
            String table = "\"ServiceRequests\".\"GiftBasketRequest\"";
            // Query
            String query = "SELECT * FROM " + table;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Get all the data from the table
                int requestID = rs.getInt("requestID");
                String requester = rs.getString("requester");
                String status = rs.getString("status");
                String additionalNotes = rs.getString("additionalNotes");
                String deliveryTime = rs.getString("ETA");
                String roomName = rs.getString("roomName");
                String assignedTo = rs.getString("assignedTo");
                String gift = rs.getString("giftBasket");
                GiftBasketRequest request =
                        new GiftBasketRequest(
                                requestID,
                                new PatientUser(requester),
                                roomName,
                                additionalNotes,
                                new GiftBasket(gift, ""));

                request.setEta(deliveryTime);
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
        DBConnection db = new DBConnection();
        try {
            Statement stmtNode = db.getConnection().createStatement();
            String query =
                    "INSERT INTO \"ServiceRequests\".\"giftBasketRequest\" (requester, status, additionalNotes, giftBasket, eta, assignedto) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps =
                    db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, orm.getRequester().toString());
            ps.setString(4, orm.getGift().toString());
            ps.setString(3, orm.getAdditionalNotes());
            ps.setString(5, orm.getEta());
            ps.setString(6, orm.getRoomName());
            ps.setString(2, orm.getStatus().toString());
            ps.setString(7, orm.getAssignedto());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            orm.requestID = (rs.getInt("requestID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        return orm;
    }

    public GiftBasketRequest updateRow(GiftBasketRequest orm, GiftBasketRequest orm2) {
        DBConnection db = new DBConnection();
        GiftBasketRequest request = null;
        try {
            Statement stmtNode = db.getConnection().createStatement();
            String query =
                    "UPDATE \"ServiceRequests\".\"giftBasketRequest\" SET requestID = ?, requester = ?, status = ?, additionalnotes = ?, GiftBasket = ?, eta= ?, roomname = ?, assignedto = ? WHERE requestID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setInt(1, orm2.getRequestID());
            ps.setString(2, orm2.getRequester().toString());
            ps.setString(3, orm2.getStatus().toString());
            ps.setString(4, orm2.getAdditionalNotes());
            ps.setString(5, orm2.getGift().toString());
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
        DBConnection db = new DBConnection();
        GiftBasketRequest request = null;
        try {
            Statement stmtNode = db.getConnection().createStatement();
            String query = "DELETE FROM \"ServiceRequests\".\"giftBasketRequest\" WHERE requestID = ?";
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
            DBConnection db = new DBConnection();
            Statement stmt = db.getConnection().createStatement();
            // Table Name
            String table = "\"ServiceRequests\".\"giftBasketRequest\"";
            // Query
            String query = "SELECT * FROM " + table + " WHERE requestID = " + key;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Get all the data from the table
                int requestID = rs.getInt("requestID");
                String requester = rs.getString("Requester");
                String GiftBasketType = rs.getString("GiftBasket");
                String roomName = rs.getString("roomName");
                String additionalNotes = rs.getString("additionalNotes");
                String deliveryTime = rs.getString("ETA");
                request =
                        new GiftBasketRequest(
                                requestID,
                                new PatientUser(requester),
                                roomName,
                                additionalNotes,
                                new GiftBasket(GiftBasketType, ""));
                request.setEta(deliveryTime);
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
        writer.write("requestid,requester,status,additionalnotes,GiftBasket,eta,roomname,assignedto\n");
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
                            + GiftBasketRequest.getGift()
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
