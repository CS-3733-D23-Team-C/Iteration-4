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

public class NodeDao implements IDao<Node, Integer> {
  public List<Node> fetchAllObjects() {
    List<Node> databaseNodeList = new ArrayList<>();
    DBConnection db = new DBConnection(CApp.getWpiDB());

    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + NODE;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);

      while (rsNodes.next()) {
        int nodeID = rsNodes.getInt("nodeID");
        int xCoord = rsNodes.getInt("xcoord");
        int yCoord = rsNodes.getInt("ycoord");
        String floor = rsNodes.getString("floorNum");
        String building = rsNodes.getString("building");
        NODE_STATUS status = NODE_STATUS.valueOf(rsNodes.getString("status"));

        databaseNodeList.add(new Node(nodeID, xCoord, yCoord, floor, building, status));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseNodeList;
  }

  public Node updateRow(int oldNodeID, Node repl) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";

      String queryUpdateNodesDB =
          "UPDATE  "
              + NODE
              + " SET \"nodeID\"=?, xcoord=?, ycoord=?, \"floorNum\"=?, building=?, status = ? WHERE \"nodeID\"=?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryUpdateNodesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setInt(2, repl.getXCoord());
      ps.setInt(3, repl.getYCoord());
      ps.setString(4, repl.getFloor());
      ps.setString(5, repl.getBuilding());
      ps.setString(6, repl.getStatus().toString());
      ps.setInt(7, oldNodeID);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return null;
  }

  public Node updateRow(Node orm, Node repl) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";

      String queryUpdateNodesDB =
          "UPDATE  "
              + NODE
              + " SET \"nodeID\"=?, xcoord=?, ycoord=?, \"floorNum\"=?, building=?, status = ? WHERE \"nodeID\"=?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryUpdateNodesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setInt(2, repl.getXCoord());
      ps.setInt(3, repl.getYCoord());
      ps.setString(4, repl.getFloor());
      ps.setString(5, repl.getBuilding());
      ps.setString(6, repl.getStatus().toString());
      ps.setInt(7, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return repl;
  }

  public Node addRow(Node orm) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB =
          "INSERT INTO "
              + NODE
              + " (xcoord, ycoord, \"floorNum\", building, status) VALUES (?,?,?,?,?);";

      PreparedStatement ps =
          dbConnection
              .getConnection()
              .prepareStatement(queryInsertNodesDB, Statement.RETURN_GENERATED_KEYS);

      ps.setInt(1, orm.getXCoord());
      ps.setInt(2, orm.getYCoord());
      ps.setString(3, orm.getFloor());
      ps.setString(4, orm.getBuilding());
      ps.setString(5, orm.getStatus().toString());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      orm.setNodeID(rs.getInt(1));
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return orm;
  }

  public Node deleteRow(Node orm) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      String queryDeleteNodesDB = "DELETE FROM " + NODE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryDeleteNodesDB);

      ps.setInt(1, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return orm;
  }

  @Override
  public Node fetchObject(Integer key) {
    Node node = null;
    DBConnection db = new DBConnection(CApp.getWpiDB());

    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + NODE + " WHERE \"nodeID\" = " + key;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);

      while (rsNodes.next()) {
        int nodeID = rsNodes.getInt("nodeID");
        int xCoord = rsNodes.getInt("xcoord");
        int yCoord = rsNodes.getInt("ycoord");
        String floor = rsNodes.getString("floorNum");
        String building = rsNodes.getString("building");
        NODE_STATUS status = NODE_STATUS.valueOf(rsNodes.getString("status"));

        node = new Node(nodeID, xCoord, yCoord, floor, building, status);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return node;
  }

  public int deleteRow(int nodeID) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      String queryDeleteNodesDB = "DELETE FROM " + NODE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryDeleteNodesDB);

      ps.setInt(1, nodeID);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return nodeID;
  }

  public void importRow(Node orm) {
    DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODE + " VALUES (?,?,?,?,?,?);";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryInsertNodesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setInt(2, orm.getXCoord());
      ps.setInt(3, orm.getYCoord());
      ps.setString(4, orm.getFloor());
      ps.setString(5, orm.getBuilding());
      ps.setString(6, orm.getStatus().toString());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();
  }

  public boolean importCSV(String CSVfilepath) {
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
          importRow(node);
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
    writer.write("nodeID,xcoord,ycoord,floorNum,building,status\n");
    // Write each Node into the CSV file
    for (Node node : fetchAllObjects()) {
      writer.write(
          node.getNodeID()
              + ","
              + node.getXCoord()
              + ","
              + node.getYCoord()
              + ","
              + node.getFloor()
              + ","
              + node.getBuilding()
              + ","
              + node.getStatus()
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
