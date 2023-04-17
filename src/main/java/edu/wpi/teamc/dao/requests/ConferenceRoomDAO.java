package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConferenceRoomDAO implements IDao<ConferenceRoom> {

  DBConnection db = new DBConnection();

  public ConferenceRoom addRow(ConferenceRoom orm) {
    try {
      String CONFREQ =
          "INSERT INTO ConferenceRoomRequest (longName, shortName, availability) VALUES (?,?,?)";
      PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
      {
        ps.setString(1, orm.getLongName());
        ps.setString(2, orm.getShortName());
        ps.setBoolean(3, orm.getAvailability());
        ps.executeUpdate();
        db.closeConnection();
        return null;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      db.closeConnection();
      return null;
    }
  }

  public ConferenceRoom deleteRow(ConferenceRoom orm) {
    String CONFREQ =
        "DELETE FROM ConferenceRoomRequest WHERE longName = ? AND shortName = ? AND availability = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
      ps.setString(1, orm.getLongName());
      ps.setString(2, orm.getShortName());
      ps.setBoolean(3, orm.getAvailability());
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
  public ConferenceRoom updateRow(ConferenceRoom orm, ConferenceRoom repl) {
    String CONFREQ =
        "UPDATE ConferenceRoomRequest SET longName = ?, shortName = ?, availability = ? WHERE longName = ? AND shortName = ? AND availability = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(CONFREQ);
      ps.setString(1, repl.getLongName());
      ps.setString(2, repl.getShortName());
      ps.setBoolean(3, repl.getAvailability());
      ps.setString(4, orm.getLongName());
      ps.setString(5, orm.getShortName());
      ps.setBoolean(6, orm.getAvailability());
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
  public List<ConferenceRoom> fetchAllObjects() throws SQLException {
    return null;
  }
}
