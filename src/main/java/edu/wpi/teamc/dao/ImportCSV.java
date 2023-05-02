package edu.wpi.teamc.dao;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.map.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportCSV {
  static DBConnection dbConnection = new DBConnection(CApp.getWpiDB());
  static Connection connection;

  /** displays related import functions */
  public void importAllDisplaysCSV(String CSVfilepathAlert, String CSVfilepathSignage) {
    try {
      nukeDisplaysDatabase();
      connection = dbConnection.getConnection();
      importAlertCSV(CSVfilepathAlert);
      importSignageCSV(CSVfilepathSignage);
      connection.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void importSignageCSV(String CSVfilepath)
      throws SQLException, FileNotFoundException {
    String query =
        "INSERT INTO displays.\"Signage\" (macadd,devicename,date,locationname,direction) VALUES (?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeSignageDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, values[0]);
        stmt.setString(2, values[1]);
        stmt.setDate(3, Date.valueOf(values[2]));
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importAlertCSV(String CSVfilepath) throws SQLException, FileNotFoundException {
    String query =
        "INSERT INTO displays.\"Alert\" (id,title,description,type,startdate,enddate) VALUES (?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeAlertDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setTimestamp(5, Timestamp.valueOf(values[4]));
        stmt.setTimestamp(6, Timestamp.valueOf(values[5]));
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  /** service request related import functions */
  public static void importAllRequestsCSV(
      String CSVfilepathConference,
      String CSVfilepathFlower,
      String CSVfilepathFurniture,
      String CSVfilepathGiftBasket,
      String CSVfilepathMeal,
      String CSVfilepathOfficeSupply)
      throws SQLException {
    try {
      nukeServiceRequestsDatabase();
      connection = dbConnection.getConnection();
      importConferenceRequestCSV(CSVfilepathConference);
      importFlowerRequestCSV(CSVfilepathFlower);
      importFurnitureRequestCSV(CSVfilepathFurniture);
      importMealRequestCSV(CSVfilepathMeal);
      importOfficeSupplyRequestCSV(CSVfilepathOfficeSupply);
      connection.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void importConferenceRequestCSV(String CSVfilepath)
      throws SQLException, FileNotFoundException {
    String query =
        "INSERT INTO \"ServiceRequests\".\"conferenceRoomRequest\" (requestid, requester, roomname, status, additionalnotes, starttime, endtime, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeConferenceRequestDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importFlowerRequestCSV(String CSVfilepath) {
    String query =
        "INSERT INTO \"ServiceRequests\".\"flowerRequest\" (requestid, requester, roomname, status, additionalnotes,eta, flower, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeFlowerRequestDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importFurnitureRequestCSV(String CSVfilepath) {
    String query =
        "INSERT INTO \"ServiceRequests\".\"furnitureDeliveryRequest\" (requestid, requester, roomname, status, additionalnotes,furnitureType, eta, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeFurnitureDeliveryRequestDatabase();

      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importMealRequestCSV(String CSVfilepath) {
    String query =
        "INSERT INTO \"ServiceRequests\".\"mealRequest\" (requestid, requester, status, additionalnotes,meal, eta, roomname, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeMealRequestDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importOfficeSupplyRequestCSV(String CSVfilepath) {
    String query =
        "INSERT INTO \"ServiceRequests\".\"officeSupplyRequest\" (requestid, requester, status, additionalnotes,officesupplytype, eta, roomname, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeOfficeSupplyRequestDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importGiftBasketRequestCSV(String CSVfilepath) {
    String query =
        "INSERT INTO \"ServiceRequests\".\"giftBasketRequest\" (requestid, requester, status, additionalnotes, giftbasket, eta, roomname, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      connection = new DBConnection(CApp.getWpiDB()).getConnection();
      nukeGiftBasketRequestDatabase();
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);
        stmt.setString(8, values[7]);
        stmt.executeUpdate();
        stmt.close();
      }
      connection.close();
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }
  /** Employee related import functions */
  public static void importEmployeeCSV(String CSVfilepathLogin, String CSVfilepathUser)
      throws SQLException {
    dbConnection = new DBConnection(CApp.getWpiDB());
    connection = dbConnection.getConnection();
    nukeUserDatabase();
    importLoginCSV(CSVfilepathLogin);
    importEmployeeUserCSV(CSVfilepathUser);
    connection.close();
  }

  public static void importEmployeeUserCSV(String CSVfilepath) {
    //    dbConnection = new DBConnection(CApp.getWpiDB());
    //    connection = dbConnection.getConnection();
    String query =
        "INSERT INTO users.employee (id,username,name,department,position) VALUES (?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.executeUpdate();
        stmt.close();
        //        connection.close();
      }
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importLoginCSV(String CSVfilepath) {
    //    dbConnection = new DBConnection(CApp.getWpiDB());
    //    connection = dbConnection.getConnection();
    String query =
        "INSERT INTO users.login (username,password,permissions,salt,otp,email) VALUES (?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, values[0]);
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.executeUpdate();
        stmt.close();
        //        connection.close();
      }
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void importPatientCSV(String CSVfilepath) {
    dbConnection = new DBConnection(CApp.getWpiDB());
    connection = dbConnection.getConnection();
    nukePatientDatabase();
    String query =
        "INSERT INTO users.patient (id,name,checkin,checkout,phone,room,activetext) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (BufferedReader br = new BufferedReader(new FileReader(CSVfilepath))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, Integer.valueOf(values[0]));
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setString(4, values[3]);
        stmt.setString(5, values[4]);
        stmt.setString(6, values[5]);
        stmt.setString(7, values[6]);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
      }
    } catch (IOException | SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  /** Map related import functions */
  public static Boolean importMapCSV(
      String CSVfilepathNode,
      String CSVfilepathEdge,
      String CSVfilepathMove,
      String CSVfilepathLocationName) {

    try {
      connection = dbConnection.getConnection();
      // clear database
      nukeMapDatabase();
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

  public static void importNodeCSV(String CSVfilepath) {
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
          //          String nodeStatus = matcher.group(6);
          Node node = new Node(nodeID, xCoord, yCoord, floor, building, NODE_STATUS.OPEN);
          importNodeRow(node);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void importNodeRow(Node orm) {
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODE + " VALUES (?,?,?,?,?,?);";

      PreparedStatement ps = connection.prepareStatement(queryInsertNodesDB);

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
  }

  private static void importLocationNameCSV(String CSVfilepath) {
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

  public static LocationName importLocationRow(LocationName orm) {
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

  private static void importMoveCSV(String CSVfilepath) {
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

  public static Move importMoveRow(Move orm) {
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

  private static void importEdgeCSV(String CSVfilepath) {
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

  public static Edge importEdgeRow(Edge orm) {
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

  /** nuke functions */
  public void nukeDisplaysDatabase() {
    try {

      // table names
      String Alert = "displays.\"Alert\"";
      String Signage = "displays.\"Signage\"";
      // queries
      String queryDeleteAlert = "DELETE FROM " + Alert + ";";
      String queryDeleteSignages = "DELETE FROM " + Signage + ";";

      PreparedStatement psDeleteAlert = connection.prepareStatement(queryDeleteAlert);
      PreparedStatement psDeleteSignages = connection.prepareStatement(queryDeleteSignages);
      psDeleteSignages.executeUpdate();
      psDeleteAlert.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeAlertDatabase() {
    try {

      // table names
      String Alert = "displays.\"Alert\"";
      // queries
      String queryDeleteAlert = "DELETE FROM " + Alert + ";";

      PreparedStatement psDeleteAlert = connection.prepareStatement(queryDeleteAlert);
      psDeleteAlert.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeSignageDatabase() {
    try {
      // table names
      String Signage = "displays.\"Signage\"";
      // queries
      String queryDeleteSignages = "DELETE FROM " + Signage + ";";

      PreparedStatement psDeleteSignages = connection.prepareStatement(queryDeleteSignages);
      psDeleteSignages.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeUserDatabase() {
    try {
      //      connection = dbConnection.getConnection();

      // table names
      String Login = "users.login";
      String Employee = "users.employee";
      // queries
      String queryDeleteLogins = "DELETE FROM " + Login + ";";
      String queryDeleteEmployees = "DELETE FROM " + Employee + ";";

      PreparedStatement psDeleteLogins = connection.prepareStatement(queryDeleteLogins);
      PreparedStatement psDeleteEmployees = connection.prepareStatement(queryDeleteEmployees);

      psDeleteEmployees.executeUpdate();
      psDeleteLogins.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukePatientDatabase() {
    try {
      connection = dbConnection.getConnection();

      // table names
      String Patient = "users.patint";
      // queries
      String queryDeletePatients = "DELETE FROM " + Patient + ";";

      PreparedStatement psDeletePatients = connection.prepareStatement(queryDeletePatients);

      psDeletePatients.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeMapDatabase() {
    try {
      connection = dbConnection.getConnection();
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

  public static void nukeConferenceRequestDatabase() {
    try {
      //      connection = dbConnection.getConnection();
      String Conference = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      String queryDeleteConferenceRequests = "DELETE FROM " + Conference + ";";
      PreparedStatement psDeleteConferenceRequests =
          connection.prepareStatement(queryDeleteConferenceRequests);
      psDeleteConferenceRequests.executeUpdate();
      //      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeFlowerRequestDatabase() {
    try {
      String Flower = "\"ServiceRequests\".\"flowerRequest\"";
      String queryDeleteFlowerRequests = "DELETE FROM " + Flower + ";";
      PreparedStatement psDeleteFlowerRequests =
          connection.prepareStatement(queryDeleteFlowerRequests);
      psDeleteFlowerRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeFurnitureDeliveryRequestDatabase() {
    try {
      String furnitureDelivery = "\"ServiceRequests\".\"furnitureDeliveryRequest\"";
      String queryDeleteFurnitureDeliveryRequests = "DELETE FROM " + furnitureDelivery + ";";
      PreparedStatement psDeleteFurnitureDeliveryRequests =
          connection.prepareStatement(queryDeleteFurnitureDeliveryRequests);
      psDeleteFurnitureDeliveryRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeMealRequestDatabase() {
    try {
      String mealRequest = "\"ServiceRequests\".\"mealRequest\"";
      String queryDeleteMealRequests = "DELETE FROM " + mealRequest + ";";
      PreparedStatement psDeleteMealRequests = connection.prepareStatement(queryDeleteMealRequests);
      psDeleteMealRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeOfficeSupplyRequestDatabase() {
    try {
      String officeSupply = "\"ServiceRequests\".\"officeSupplyRequest\"";
      String queryDeleteOfficeSupplyRequests = "DELETE FROM " + officeSupply + ";";
      PreparedStatement psDeleteOfficeSupplyRequests =
          connection.prepareStatement(queryDeleteOfficeSupplyRequests);
      psDeleteOfficeSupplyRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeGiftBasketRequestDatabase() {
    try {
      String giftBasket = "\"ServiceRequests\".\"giftBasketRequest\"";
      String queryDeleteGiftBasketRequests = "DELETE FROM " + giftBasket + ";";
      PreparedStatement psDeleteGiftBasketRequests =
          connection.prepareStatement(queryDeleteGiftBasketRequests);
      psDeleteGiftBasketRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void nukeServiceRequestsDatabase() {
    try {
      connection = dbConnection.getConnection();
      // table names
      String Conference = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      String Flower = "\"ServiceRequests\".\"flowerRequest\"";
      String furnitureDelivery = "\"ServiceRequests\".\"furnitureDeliveryRequest\"";
      String mealRequest = "\"ServiceRequests\".\"mealRequest\"";
      String officeSupply = "\"ServiceRequests\".\"officeSupplyRequest\"";

      // queries
      String queryDeleteConferenceRequests = "DELETE FROM " + Conference + ";";
      String queryDeleteFlowerRequests = "DELETE FROM " + Flower + ";";
      String queryDeleteFurnitureDeliveryRequests = "DELETE FROM " + furnitureDelivery + ";";
      String queryDeleteMealRequests = "DELETE FROM " + mealRequest + ";";
      String queryDeleteOfficeSupplyRequests = "DELETE FROM " + officeSupply + ";";

      PreparedStatement psDeleteConferenceRequests =
          connection.prepareStatement(queryDeleteConferenceRequests);
      PreparedStatement psDeleteFlowerRequests =
          connection.prepareStatement(queryDeleteFlowerRequests);
      PreparedStatement psDeleteFurnitureDeliveryRequests =
          connection.prepareStatement(queryDeleteFurnitureDeliveryRequests);
      PreparedStatement psDeleteMealRequests = connection.prepareStatement(queryDeleteMealRequests);
      PreparedStatement psDeleteOfficeSupplyRequests =
          connection.prepareStatement(queryDeleteOfficeSupplyRequests);

      psDeleteConferenceRequests.executeUpdate();
      psDeleteFlowerRequests.executeUpdate();
      psDeleteFurnitureDeliveryRequests.executeUpdate();
      psDeleteMealRequests.executeUpdate();
      psDeleteOfficeSupplyRequests.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Date returnDate(String dateString) {
    // function to convert to yyyy-mm-dd
    SimpleDateFormat[] formats =
        new SimpleDateFormat[] {
          new SimpleDateFormat("d/M/yyyy"),
          new SimpleDateFormat("dd/M/yyyy"),
          new SimpleDateFormat("dd/MM/yyyy"),
          new SimpleDateFormat("yyyy-MM-dd")
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
