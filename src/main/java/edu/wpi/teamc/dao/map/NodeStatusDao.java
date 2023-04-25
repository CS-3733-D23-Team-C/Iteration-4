package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NodeStatusDao implements IDao<NodeStatus, Integer> {
  public List<NodeStatus> fetchAllObjects() {
    List<NodeStatus> databaseNodeStatusList = new ArrayList<>();
    DBConnection db = new DBConnection();

    try {
      Statement stmtNodeStatus = db.getConnection().createStatement();
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      // queries
      String queryDisplayNodeStatus = "SELECT * FROM " + NODESTATUS;

      ResultSet rsNodeStatus = stmtNodeStatus.executeQuery(queryDisplayNodeStatus);

      while (rsNodeStatus.next()) {
        int nodeID = rsNodeStatus.getInt("nodeid");
        NODE_STATUS status = NODE_STATUS.valueOf(rsNodeStatus.getString("status"));

        databaseNodeStatusList.add(new NodeStatus(nodeID, status));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseNodeStatusList;
  }

  public NodeStatus updateRow(int oldNodeID, NodeStatus repl) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";

      String queryUpdateNodesDB =
          "UPDATE  " + NODESTATUS + " SET nodeid= ?, status = ? WHERE nodeid = ?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryUpdateNodesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setString(2, repl.getStatus().toString());
      ps.setInt(3, oldNodeID);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return null;
  }

  public NodeStatus updateRow(NodeStatus orm, NodeStatus repl) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";

      String queryUpdateNodesDB =
          "UPDATE  " + NODESTATUS + " SET nodeid= ?, status = ? WHERE nodeid = ?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryUpdateNodesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setString(2, repl.getStatus().toString());
      ps.setInt(3, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return repl;
  }

  public NodeStatus addRow(NodeStatus orm) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODESTATUS + " (nodeid,status) VALUES (?,?);";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryInsertNodesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setString(2, orm.getStatus().toString());

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

  public NodeStatus deleteRow(NodeStatus orm) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      String queryDeleteNodesDB = "DELETE FROM " + NODESTATUS + " WHERE nodeid = ?; ";

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
  public NodeStatus fetchObject(Integer key) {
    NodeStatus nodeStatus = null;
    DBConnection db = new DBConnection();

    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + NODESTATUS + " WHERE nodeid = " + key;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);

      while (rsNodes.next()) {
        int nodeID = rsNodes.getInt("nodeid");
        NODE_STATUS status = NODE_STATUS.valueOf(rsNodes.getString("status"));

        nodeStatus = new NodeStatus(nodeID, status);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return nodeStatus;
  }

  public int deleteRow(int nodeID) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      String queryDeleteNodesDB = "DELETE FROM " + NODESTATUS + " WHERE nodeid = ?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryDeleteNodesDB);

      ps.setInt(1, nodeID);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return nodeID;
  }

  public void importRow(NodeStatus orm) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODESTATUS = "\"hospitalNode\".\"nodeStatus\"";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODESTATUS + " VALUES (?,?);";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryInsertNodesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setString(2, orm.getStatus().toString());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("nodeid,status\n");
    // Write each Node into the CSV file
    for (Node node : fetchAllNodeObjects()) {
      writer.write(node.getNodeID() + "," + "OPEN" + "\n");
    }
    writer.close();
    return true;
  }

  public List<Node> fetchAllNodeObjects() {
    List<Node> databaseNodeList = new ArrayList<>();
    DBConnection db = new DBConnection();

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

        databaseNodeList.add(new Node(nodeID, xCoord, yCoord, floor, building));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseNodeList;
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
