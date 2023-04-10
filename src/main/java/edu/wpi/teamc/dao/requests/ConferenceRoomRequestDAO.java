package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import javafx.scene.chart.ScatterChart;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest> {
    DBConnection db = new DBConnection();
    public int addRow(String requestID, String roomID, String date, String time, String duration, String description, String status) {
        String CONFREQ = "INSERT INTO ConferenceRoomRequest VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requestID);
            ps.setString(2, roomID);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, duration);
            ps.setString(6, description);
            ps.setString(7, status);
            ps.executeUpdate();
            db.closeConnection();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection();
            return 0;
        }
    }

    public int removeRow(String requestID, String roomID, String date, String time, String duration, String description, String status) {
        String CONFREQ = "DELETE FROM ConferenceRoomRequest WHERE requestID = ? AND roomID = ? AND date = ? AND time = ? AND duration = ? AND description = ? AND status = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requestID);
            ps.setString(2, roomID);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, duration);
            ps.setString(6, description);
            ps.setString(7, status);
            ps.executeUpdate();
            db.closeConnection();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection();
            return 0;
        }
    }

    public int deleteRow(String requestID, String roomID, String date, String time, String duration, String description, String status) {
        String CONFREQ = "DELETE FROM ConferenceRoomRequest WHERE requestID = ? AND roomID = ? AND date = ? AND time = ? AND duration = ? AND description = ? AND status = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requestID);
            ps.setString(2, roomID);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, duration);
            ps.setString(6, description);
            ps.setString(7, status);
            ps.executeUpdate();
            db.closeConnection();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection();
            return 0;
        }
    }

    @Override
    public List<ConferenceRoomRequest> fetchAllObjects() throws SQLException {
        return null;
    }

    @Override
    public int updateDBrow(ConferenceRoomRequest type) throws SQLException {
        return 0;
    }

    @Override
    public int updateDBRow(ConferenceRoomRequest type) throws SQLException {
        return 0;
    }

    @Override
    public int addRow(ConferenceRoomRequest type) {
        return 0;
    }

    @Override
    public int deleteRow(ConferenceRoomRequest type) throws SQLException {
        return 0;
    }
}
