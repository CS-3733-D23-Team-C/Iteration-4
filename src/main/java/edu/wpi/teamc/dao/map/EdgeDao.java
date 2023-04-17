package edu.wpi.teamc.dao.map;

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

public class EdgeDao implements IDao<Edge, Edge> {
  public List<Edge> fetchAllObjects() {
    List<Edge> databaseEdgeList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtEdge = db.getConnection().createStatement();
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries
      String queryDisplayEdges = "SELECT * FROM " + EDGE;

      ResultSet rsEdges = stmtEdge.executeQuery(queryDisplayEdges);
      while (rsEdges.next()) {
        int startNode = rsEdges.getInt("startNode");
        int endNode = rsEdges.getInt("endNode");
        Edge edge = new Edge(startNode, endNode);
        databaseEdgeList.add(edge);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return databaseEdgeList;
  }

  public Edge updateRow(Edge orm, Edge repl) {
    DBConnection db = new DBConnection();
    try {
      String EDGE = "\"hospitalNode\".edge";
      String queryUpdateEdgesDB =
          "UPDATE  "
              + EDGE
              + " SET \"startNode\"=?, \"endNode\"=? WHERE \"startNode\"=? AND \"endNode\"=?;";

      PreparedStatement ps = db.getConnection().prepareStatement(queryUpdateEdgesDB);

      ps.setInt(1, repl.getStartNode());
      ps.setInt(2, repl.getEndNode());
      ps.setInt(3, orm.getStartNode());
      ps.setInt(4, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  public Edge addRow(Edge orm) {
    DBConnection db = new DBConnection();
    try {
      String EDGE = "\"hospitalNode\".edge";
      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";

      PreparedStatement ps;
      ps = db.getConnection().prepareStatement(queryInsertEdgesDB);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public Edge deleteRow(Edge orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries

      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";
      String queryDeleteEdgesDB =
          "DELETE FROM " + EDGE + " WHERE \"startNode\"=? AND \"endNode\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteEdgesDB);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public Edge fetchObject(Edge orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries
      String queryDisplayEdges =
          "SELECT * FROM " + EDGE + " WHERE \"startNode\"=? AND \"endNode\"=?;";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDisplayEdges);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ResultSet rsEdges = ps.executeQuery();
      while (rsEdges.next()) {
        int startNode = rsEdges.getInt("startNode");
        int endNode = rsEdges.getInt("endNode");
        Edge edge = new Edge(startNode, endNode);
        return edge;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return null;
  }

  public boolean importCSV(String CSVfilepath) {
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

          addRow(edge);
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
    writer.write("startNodeID,endNodeID\n");
    for (Edge edge : fetchAllObjects()) {
      writer.write(edge.getStartNode() + "," + edge.getEndNode() + "\n");
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
