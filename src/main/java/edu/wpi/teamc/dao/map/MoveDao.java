package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements IDao<Move> {
  public List<Move> fetchAllObjects() {
    List<Move> databaseMoveList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtMove = db.getConnection().createStatement();
      // table names
      String MOVE = "\"hospitalNode\".\"move\"";
      // queries
      String queryDisplayMoves = "SELECT * FROM " + MOVE;

      ResultSet rsMoves = stmtMove.executeQuery(queryDisplayMoves);

      while (rsMoves.next()) {
        int nodeID = rsMoves.getInt("nodeID");
        String longName = rsMoves.getString("longName");
        Date date = rsMoves.getDate("moveDate");
        databaseMoveList.add(new Move(nodeID, longName, date));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseMoveList;
  }

  public int updateRow(Move orm, Move repl) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryUpdateMovesDB =
          "UPDATE  "
              + MOVE
              + " SET \"nodeID\"=?, \"longName\"=?, \"moveDate\"=? WHERE \"nodeID\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryUpdateMovesDB);

      ps.setInt(1, repl.getNodeID());
      ps.setString(2, repl.getLongName());
      ps.setDate(3, repl.getDate());
      ps.setInt(4, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
  }

  public int addRow(Move orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryInsertMovesDB = "INSERT INTO " + MOVE + " VALUES (?,?,?); ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryInsertMovesDB);

      ps.setInt(1, orm.getNodeID());
      ps.setString(2, orm.getLongName());
      ps.setDate(3, orm.getDate());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
  }

  public int deleteRow(Move orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryDeleteMovesDB = "DELETE FROM " + MOVE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteMovesDB);
      ps.setInt(1, orm.getNodeID());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return 1;
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
