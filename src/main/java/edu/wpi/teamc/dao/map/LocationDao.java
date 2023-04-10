package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
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
    return 0;
  }

  public int addRow(LocationName orm) {
    return 0;
  }

  public int deleteRow(LocationName orm) {
    return 0;
  }
}
