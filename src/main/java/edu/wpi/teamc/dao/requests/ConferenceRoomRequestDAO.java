package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest> {

  static DBConnection db = new DBConnection();

  public static ConferenceRoomRequest addRow(ConferenceRoomRequest orm) {
    String CONFREQ = "INSERT INTO ConferenceRoomRequest VALUES (?,?,?,?,?,?,?)";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
      ps.setInt(1, orm.getRequestID());
      ps.setString(2, orm.getRequester().toString());
      ps.setString(3, orm.getRoomName());
      ps.setString(4, orm.getStartTime());
      ps.setString(5, orm.getEndTime());
      ps.setString(6, orm.getStatus().toString());
      ps.setString(7, orm.getAdditionalNotes());

      ps.executeUpdate();
      db.closeConnection();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      db.closeConnection();
      return null;
    }
  }

  public ConferenceRoomRequest deleteRow(ConferenceRoomRequest orm) {
    String CONFREQ = "DELETE FROM ConferenceRoomRequest WHERE requestID = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
      db.closeConnection();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      db.closeConnection();
      return null;
    }
  }

  @Override
  public List<ConferenceRoomRequest> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public ConferenceRoomRequest updateRow(ConferenceRoomRequest orm, ConferenceRoomRequest repl)
      throws SQLException {
    return null;
  }
}
