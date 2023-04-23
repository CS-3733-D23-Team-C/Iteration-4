package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KioskDao implements IDao<Kiosk, String> {
  @Override
  public List<Kiosk> fetchAllObjects() {
    List<Kiosk> kiosks = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM Kiosk";
      ResultSet rs = db.getConnection().createStatement().executeQuery(query);
      while (rs.next()) {
        String id = rs.getString("id");
        int signid = rs.getInt("signid");
        String kioskname = rs.getString("kioskname");
        kiosks.add(new Kiosk(id,kioskname, signid));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return kiosks;
  }

  @Override
  public Kiosk updateRow(Kiosk type, Kiosk type2) {
    Kiosk kiosk = null;
    DBConnection db = new DBConnection();
    try {
      String query = "UPDATE Kiosk SET id = ?, signid = ?, kioskname =? WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type2.id);
      ps.setInt(2, type2.signid);
        ps.setString(3, type2.kioskname);
      ps.setString(4, type.id);
      kiosk = type2;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return kiosk;
  }

  @Override
  public Kiosk addRow(Kiosk type) {
    Kiosk kiosk = null;
    DBConnection db = new DBConnection();
    try {
      String query = "INSERT INTO Kiosk (id, signid, kioskname) VALUES (?, ?, ?)";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.id);
      ps.setInt(2, type.signid);
        ps.setString(3, type.kioskname);
      kiosk = type;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return kiosk;
  }

  @Override
  public Kiosk deleteRow(Kiosk type) {
    Kiosk kiosk = null;
    DBConnection db = new DBConnection();
    try {
      String query = "DELETE FROM Kiosk WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, type.id);
      kiosk = type;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return kiosk;
  }

  @Override
  public Kiosk fetchObject(String key) {
    Kiosk kiosk = null;
    DBConnection db = new DBConnection();
    try {
      String query = "SELECT * FROM Kiosk WHERE id = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, key);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String id = rs.getString("id");
        int signid = rs.getInt("signid");
        String kioskname = rs.getString("kioskname");
        kiosk = new Kiosk(id, kioskname, signid);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return kiosk;
  }
}
