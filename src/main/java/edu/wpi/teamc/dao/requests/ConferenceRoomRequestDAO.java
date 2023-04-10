package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import javafx.scene.chart.ScatterChart;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest> {
    DBConnection db = new DBConnection();
    public ConferenceRoomRequest addRow(Requester requester, ConferenceRoom conferenceRoom, String note, String startTime, String endTime){
        String CONFREQ = "INSERT INTO ConferenceRoomRequest (requester, conferenceRoom, note, startTime, endTime) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requester.toString());
            ps.setString(2, conferenceRoom.toString());
            ps.setString(3, note);
            ps.setString(4, startTime);
            ps.setString(5, endTime);
            ps.executeUpdate();
            String getLastInsertID = "SELECT SCOPE_IDENTITY()";
            PreparedStatement ps2 = db.getConnection().prepareStatement(getLastInsertID);
            return new ConferenceRoomRequest(ps2.executeUpdate(), requester, conferenceRoom, note, startTime, endTime);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ConferenceRoomRequest deleteRow(Requester requester, ConferenceRoom conferenceRoom, String note, String startTime, String endTime){
        String CONFREQ = "DELETE FROM ConferenceRoomRequest WHERE requester = ? AND conferenceRoom = ? AND note = ? AND startTime = ? AND endTime = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requester.toString());
            ps.setString(2, conferenceRoom.toString());
            ps.setString(3, note);
            ps.setString(4, startTime);
            ps.setString(5, endTime);
            ps.executeUpdate();
            return new ConferenceRoomRequest(0, requester, conferenceRoom, note, startTime, endTime);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ConferenceRoomRequest updateRow(Requester requester, ConferenceRoom conferenceRoom, String note, String startTime, String endTime, Requester requester2, ConferenceRoom conferenceRoom2, String note2, String startTime2, String endTime2){
        String CONFREQ = "UPDATE ConferenceRoomRequest SET requester = ?, conferenceRoom = ?, note = ?, startTime = ?, endTime = ? WHERE requester = ? AND conferenceRoom = ? AND note = ? AND startTime = ? AND endTime = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, requester2.toString());
            ps.setString(2, conferenceRoom2.toString());
            ps.setString(3, note2);
            ps.setString(4, startTime2);
            ps.setString(5, endTime2);
            ps.setString(6, requester.toString());
            ps.setString(7, conferenceRoom.toString());
            ps.setString(8, note);
            ps.setString(9, startTime);
            ps.setString(10, endTime);
            ps.executeUpdate();
            return new ConferenceRoomRequest(0, requester2, conferenceRoom2, note2, startTime2, endTime2);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ConferenceRoomRequest> fetchAllObjects() throws SQLException {
        return null;
    }

    @Override
    public ConferenceRoomRequest updateRow(ConferenceRoomRequest type, ConferenceRoomRequest type2) throws SQLException {
        return null;
    }

    @Override
    public ConferenceRoomRequest updateDBRow(ConferenceRoomRequest type) throws SQLException {
        return null;
    }

    @Override
    public ConferenceRoomRequest addRow(ConferenceRoomRequest type) {
        return null;
    }

    @Override
    public ConferenceRoomRequest deleteRow(ConferenceRoomRequest type) throws SQLException {
        return null;
    }
}
