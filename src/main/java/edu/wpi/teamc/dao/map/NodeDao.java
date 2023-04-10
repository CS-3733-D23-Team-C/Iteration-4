package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NodeDao implements IDao<Node> {
  public List<Node> fetchAllObjects() {
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

  public int updateRow(Node orm, Node repl) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODE = "\"hospitalNode\".node";

      String queryUpdateNodesDB =
          "UPDATE  "
              + NODE
              + " SET \"nodeID\"=?, xcoord=?, ycoord=?, \"floorNum\"=?, building=? WHERE \"nodeID\"=?; ";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryUpdateNodesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setInt(2, repl.getXCoord());
      ps.setInt(3, repl.getYCoord());
      ps.setString(4, repl.getFloor());
      ps.setString(5, repl.getBuilding());
      ps.setInt(6, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return 1;
  }

  public int addRow(Node orm) {
    DBConnection dbConnection = new DBConnection();
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB =
          "INSERT INTO " + NODE + " (xcoord, ycoord, \"floorNum\", building) VALUES (?,?,?,?);";

      PreparedStatement ps = dbConnection.getConnection().prepareStatement(queryInsertNodesDB);

      ps.setInt(1, orm.getXCoord());
      ps.setInt(2, orm.getYCoord());
      ps.setString(3, orm.getFloor());
      ps.setString(4, orm.getBuilding());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbConnection.closeConnection();

    return 1;
  }

  public int deleteRow(Node orm) {
    DBConnection dbConnection = new DBConnection();
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

    return 1;
  }
}
