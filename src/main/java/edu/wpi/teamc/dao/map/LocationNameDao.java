package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationNameDao implements IDao<LocationName, String> {
  public List<LocationName> fetchAllObjects() {
    List<LocationName> databaseLocationNameList = new ArrayList<>();
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

  public LocationName updateRow(String oldLongName, LocationName repl) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
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
      ps.setString(4, oldLongName);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return repl;
  }

  public LocationName updateRow(LocationName orm, LocationName repl) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

    return repl;
  }

  public LocationName addRow(LocationName orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

    return orm;
  }

  public LocationName deleteRow(LocationName orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
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

    return orm;
  }

  @Override
  public LocationName fetchObject(String key) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    LocationName locationName = null;
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryDisplayLocationNames = "SELECT * FROM " + LOCATIONNAME + " WHERE \"longName\"=?";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDisplayLocationNames);
      ps.setString(1, key);
      ResultSet rsLocationNames = ps.executeQuery();
      while (rsLocationNames.next()) {
        String locationNameLong = rsLocationNames.getString("longName");
        String locationNameShort = rsLocationNames.getString("shortName");
        String nodeType = rsLocationNames.getString("nodeType");
        locationName = new LocationName(locationNameLong, locationNameShort, nodeType);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return locationName;
  }

  // TODO delete this method and use method from facade in the edit map controller
  public String deleteRow(String longName) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryDeleteLocationNamesDB = "DELETE FROM " + LOCATIONNAME + " WHERE \"longName\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteLocationNamesDB);

      ps.setString(1, longName);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return longName;
  }

  public boolean importCSV(String CSVfilepath) {
    // Regular expression to match each row
    String regex = "(.*),(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine();
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String longName = matcher.group(1);
          String shortName = matcher.group(2);
          String nodeType = matcher.group(3);
          LocationName locationName = new LocationName(longName, shortName, nodeType);
          addRow(locationName);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("longName,shortName,nodeType\n");
    for (LocationName locationName : fetchAllObjects()) {
      writer.write(
          locationName.getLongName()
              + ","
              + locationName.getShortName()
              + ","
              + locationName.getNodeType()
              + "\n");
    }
    writer.close();
    return true;
  }

  static void createFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.createNewFile()) {
      System.out.println("File created: " + file.getName());
    } else {
      System.out.println("File already exists.");
    }
  }
}
