package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.IOrm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapHistoryDao implements IDao<MapHistory> {

  public List<IOrm> fetchAllObjects() {
    List<MapHistory> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtLocationName = db.getConnection().createStatement();
      // table names
      String MAPHISTORY = "\"hospitalNode\".\"mapHistory\"";
      // queries
      String queryDisplayLocationNames = "SELECT * FROM " + MAPHISTORY;
      ResultSet rsLocationNames = stmtLocationName.executeQuery(queryDisplayLocationNames);
      while (rsLocationNames.next()) {
        int id = rsLocationNames.getInt("id");
        String action = rsLocationNames.getString("action");
        String nodepk = rsLocationNames.getString("nodepk");
        String table = rsLocationNames.getString("table");
        Timestamp timestamp = rsLocationNames.getTimestamp("timestamp");
        returnList.add(new MapHistory(id, action, nodepk, table, timestamp));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return returnList;
  }

  public MapHistory updateRow(MapHistory type, MapHistory type2) throws SQLException {
    System.out.println("Cannot update from MapHistory, can only add baby");
    return null;
  }

  public MapHistory addRow(MapHistory type) {
    DBConnection db = new DBConnection();
    MapHistory returnMapHistory = null;
    try {
      // table names
      String MAPHISTORY = "\"hospitalNode\".\"mapHistory\"";
      // queries
      String queryUpdateLocationNamesDB =
          "INSERT INTO "
              + MAPHISTORY
              + " (\"action\", \"table\", \"timestamp\",\"nodepk\") VALUES (?, ?, ?, ?); ";

      PreparedStatement ps =
          db.getConnection()
              .prepareStatement(queryUpdateLocationNamesDB, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, type.getAction());
      ps.setString(2, type.getTable());
      ps.setTimestamp(3, type.getTimestamp());
      ps.setString(4, type.getNodepk());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      returnMapHistory =
          new MapHistory(
              rs.getInt(1),
              type.getAction(),
              type.getNodepk(),
              type.getTable(),
              type.getTimestamp());
    } catch (Exception e) {
      e.printStackTrace();
      return returnMapHistory;
    }

    db.closeConnection();
    return returnMapHistory;
  }

  public MapHistory deleteRow(MapHistory type) throws SQLException {
    System.out.println("Cannot delete from MapHistory, can only add baby");
    return null;
  }
}
