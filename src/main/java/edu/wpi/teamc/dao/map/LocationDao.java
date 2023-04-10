package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationDao implements IDao<LocationName> {
  public List<LocationName> fetchAllObjects() {
    List<LocationName> databaseLocationNameList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtLocationName = db.getConnection().createStatement();
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryDisplayLocationNames = "SELECT * FROM " + LOCATIONNAME;
      ResultSet rsLocationNames = stmtLocationName.executeQuery(queryDisplayLocationNames);
      while (rsLocationNames.next()) {
        String locationNameLong = rsLocationNames.getString("longName");
        String locationNameShort = rsLocationNames.getString("shortName");
        String nodeType = rsLocationNames.getString("nodeType");
        databaseLocationNameList.add(
            new LocationName(locationNameLong, locationNameShort, nodeType));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseLocationNameList;
  }

  public int updateRow(LocationName orm, LocationName repl) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryUpdateLocationNamesDB =
          "UPDATE  "
              + LOCATIONNAME
              + " SET \"longName\"=?, \"shortName\"=?, \"nodeType\"=? WHERE \"longName\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryUpdateLocationNamesDB);

      ps.setString(1, repl.getLongName());
      ps.setString(2, repl.getShortName());
      ps.setString(3, repl.getNodeType());
      ps.setString(4, orm.getLongName());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
  }

  public int addRow(LocationName orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryInsertLocationNamesDB = "INSERT INTO " + LOCATIONNAME + " VALUES (?,?,?); ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryInsertLocationNamesDB);

      ps.setString(1, orm.getLongName());
      ps.setString(2, orm.getShortName());
      ps.setString(3, orm.getNodeType());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
  }

  public int deleteRow(LocationName orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryDeleteLocationNamesDB = "DELETE FROM " + LOCATIONNAME + " WHERE \"longName\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteLocationNamesDB);

      ps.setString(1, orm.getLongName());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
  }
}
