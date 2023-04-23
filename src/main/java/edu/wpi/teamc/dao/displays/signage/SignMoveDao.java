package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SignMoveDao implements IDao<SignMove, SignMove> {
  @Override
  public List<SignMove> fetchAllObjects() {
    List<SignMove> signMoves = null;
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM \"displays\".\"SignMove\"";
      ResultSet rs = db.getConnection().createStatement().executeQuery(query);
      while (rs.next()) {
        int id = rs.getInt("id");
        Date date = rs.getDate("date");
        String directionstr = rs.getString("directions");
        SignMove signMove = new SignMove(id, date, directionstr);
        signMoves.add(signMove);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return signMoves;
  }

  @Override
  public SignMove updateRow(SignMove type, SignMove type2) {
    SignMove signMove = null;
    DBConnection db = new DBConnection();
    try {
      String query =
          "UPDATE \"displays\".\"SignMove\" SET id = ?, date = ?, directions = ? WHERE id = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, type2.id);
      ps.setDate(2, type2.date);
      ps.setString(3, type2.directions.toString());
      ps.setInt(4, type.id);
      ps.setDate(5, type.date);
      signMove = type2;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return signMove;
  }

  @Override
  public SignMove addRow(SignMove type) {
    SignMove signMove = null;
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"displays\".\"SignMove\" (id, date, directions) VALUES (?, ?, ?)";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, type.id);
      ps.setDate(2, type.date);
      ps.setString(3, type.directions.toString());
      signMove = type;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return signMove;
  }

  @Override
  public SignMove deleteRow(SignMove type) {
    SignMove signMove = null;
    DBConnection db = new DBConnection();
    try {
      String query = "DELETE FROM \"displays\".\"SignMove\" WHERE id = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, type.id);
      ps.setDate(2, type.date);
      signMove = type;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return signMove;
  }

  @Override
  public SignMove fetchObject(SignMove key) {
    SignMove signMove = null;
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM \"displays\".\"SignMove\" WHERE id = ? AND date = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, key.id);
      ps.setDate(2, key.date);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        Date date = rs.getDate("date");
        String directionstr = rs.getString("directions");
        signMove = new SignMove(id, date, directionstr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return signMove;
  }
}
