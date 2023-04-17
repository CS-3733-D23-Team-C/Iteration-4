package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.IOrm;

import java.io.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveDao implements IDao<Move> {
  public List<IOrm> fetchAllObjects() {
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

  public Move updateRow(Move orm, Move repl) {
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

    return repl;
  }

  public Move addRow(Move orm) {
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

    return orm;
  }

  public Move deleteRow(Move orm) {
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

    return orm;
  }

  public int deleteRow(int nodeID) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryDeleteMovesDB = "DELETE FROM " + MOVE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteMovesDB);
      ps.setInt(1, nodeID);

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();

    return nodeID;
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
          int nodeID = Integer.valueOf(matcher.group(1));
          String longName = matcher.group(2);
          String dateString = matcher.group(3);
          Date moveDate = returnDate(dateString);
          Move move = new Move(nodeID, longName, moveDate);
          addRow(move);
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
    writer.write("nodeID,longName,moveDate\n");
    for (Move move : fetchAllObjects()) {
      writer.write(move.getNodeID() + "," + move.getLongName() + "," + move.getDate() + "\n");
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
