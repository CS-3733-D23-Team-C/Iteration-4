package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConferenceRoomDAO implements IDao<ConferenceRoomRequest> {

    DBConnection db = new DBConnection();


    public int addRow(String longName, String shortName, Boolean availability) {
        try{
            String CONFREQ = "INSERT INTO ConferenceRoomRequest (longName, shortName, availability) VALUES (?,?,?)";
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            {
                ps.setString(1, longName);
                ps.setString(2, shortName);
                ps.setBoolean(3, availability);
                ps.executeUpdate();
                db.closeConnection();
                return 1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection();
            return 0;
        }
    }

    public int removeRow(String longName, String shortName, Boolean availability) {
        String CONFREQ = "DELETE FROM ConferenceRoomRequest WHERE longName = ? AND shortName = ? AND availability = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, longName);
            ps.setString(2, shortName);
            ps.setBoolean(3, availability);
            ps.executeUpdate();
            db.closeConnection();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection();
            return 0;
        }
    }

    /**
     *
     */
    public int updateRow(String longName, String shortName, Boolean availability) {
        String CONFREQ = "UPDATE ConferenceRoomRequest SET longName = ?, shortName = ?, availability = ? WHERE longName = ? AND shortName = ? AND availability = ?";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
            ps.setString(1, longName);
            ps.setString(2, shortName);
            ps.setBoolean(3, availability);
            ps.setString(4, longName);
            ps.setString(5, shortName);
            ps.setBoolean(6, availability);
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
