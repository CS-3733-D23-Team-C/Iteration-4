package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportCSV {
  DBConnection dbConnection = new DBConnection();
  Connection connection;

  public Boolean importAllCSV(
      String CSVfilepathNode,
      String CSVfilepathEdge,
      String CSVfilepathMove,
      String CSVfilepathLocationName) {

    try {
      connection = dbConnection.getConnection();
      // clear database
      nukeDatabase();
      // import CSV files
      importNodeCSV(CSVfilepathNode);
      importEdgeCSV(CSVfilepathEdge);
      importLocationNameCSV(CSVfilepathLocationName);
      importMoveCSV(CSVfilepathMove);
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  public void importNodeCSV(String CSVfilepath) {
    // Regular expression to match each row
    String regex = "(\\d+),(\\d+),(\\d+),(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          int nodeID = Integer.parseInt(matcher.group(1));
          int xCoord = Integer.parseInt(matcher.group(2));
          int yCoord = Integer.parseInt(matcher.group(3));
          String floor = matcher.group(4);
          String building = matcher.group(5);
          Node node = new Node(nodeID, xCoord, yCoord, floor, building);
          importNodeRow(node);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void importNodeRow(Node orm) {
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODE + " VALUES (?,?,?,?,?);";

      PreparedStatement ps = connection.prepareStatement(queryInsertNodesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setInt(2, orm.getXCoord());
      ps.setInt(3, orm.getYCoord());
      ps.setString(4, orm.getFloor());
      ps.setString(5, orm.getBuilding());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void importLocationNameCSV(String CSVfilepath) {
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
          importLocationRow(locationName);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public LocationName importLocationRow(LocationName orm) {
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryInsertLocationNamesDB = "INSERT INTO " + LOCATIONNAME + " VALUES (?,?,?); ";

      PreparedStatement ps = connection.prepareStatement(queryInsertLocationNamesDB);

      ps.setString(1, orm.getLongName());
      ps.setString(2, orm.getShortName());
      ps.setString(3, orm.getNodeType());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return orm;
  }

  private void importMoveCSV(String CSVfilepath) {
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
          int nodeID = Integer.valueOf(matcher.group(1));
          String longName = matcher.group(2);
          String dateString = matcher.group(3);
          Date moveDate = returnDate(dateString);
          Move move = new Move(nodeID, longName, moveDate);
          importMoveRow(move);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Move importMoveRow(Move orm) {
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryInsertMovesDB = "INSERT INTO " + MOVE + " VALUES (?,?,?); ";

      PreparedStatement ps = connection.prepareStatement(queryInsertMovesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setString(2, orm.getLongName());
      ps.setDate(3, orm.getDate());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return orm;
  }

  private void importEdgeCSV(String CSVfilepath) {
    // Regular expression to match each row
    String regex = "(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine();
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          int startNodeID = Integer.valueOf(matcher.group(1));
          int endNodeID = Integer.valueOf(matcher.group(2));

          Edge edge = new Edge(startNodeID, endNodeID);

          importEdgeRow(edge);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Edge importEdgeRow(Edge orm) {
    try {
      String EDGE = "\"hospitalNode\".edge";
      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";

      PreparedStatement ps;
      ps = connection.prepareStatement(queryInsertEdgesDB);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return orm;
  }

  public void nukeDatabase() {
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      String Edge = "\"hospitalNode\".edge";
      String Move = "\"hospitalNode\".move";
      String LocationName = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryDeleteNodes = "DELETE FROM " + NODE + ";";
      String queryDeleteEdges = "DELETE FROM " + Edge + ";";
      String queryDeleteMoves = "DELETE FROM " + Move + ";";
      String queryDeleteLocationNames = "DELETE FROM " + LocationName + ";";

      PreparedStatement psDeleteNodes = connection.prepareStatement(queryDeleteNodes);
      PreparedStatement psDeleteEdges = connection.prepareStatement(queryDeleteEdges);
      PreparedStatement psDeleteMoves = connection.prepareStatement(queryDeleteMoves);
      PreparedStatement psDeleteLocationNames =
          connection.prepareStatement(queryDeleteLocationNames);

      psDeleteEdges.executeUpdate();
      psDeleteMoves.executeUpdate();
      psDeleteNodes.executeUpdate();
      psDeleteLocationNames.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Date returnDate(String dateString) {
    // function to convert to yyyy-mm-dd
    SimpleDateFormat[] formats =
        new SimpleDateFormat[] {
          new SimpleDateFormat("d/M/yyyy"),
          new SimpleDateFormat("dd/M/yyyy"),
          new SimpleDateFormat("dd/MM/yyyy"),
          new SimpleDateFormat("d/MM/yyyy")
        };
    for (SimpleDateFormat format : formats) {
      try {
        java.util.Date utilDate = format.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
      } catch (ParseException e) {
        // ignore and try next format
      }
    }
    return null;
  }
}
