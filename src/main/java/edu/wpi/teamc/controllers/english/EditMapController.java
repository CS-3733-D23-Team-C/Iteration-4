package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.controllers.english.MapHelpers.HandleMapModes;
import edu.wpi.teamc.controllers.english.MapHelpers.MapModeSaver;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.tableview2.FilteredTableView;

public class EditMapController {
  public Group group;
  double mouseX;
  double mouseY;
  public Image image =
      new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());

  /*
  Notes:
  * implement import and export buttons and get rid of navigation actions
  * implement a submit button that submits all edits at once ****** DONE (just need database functions for finding node based on ID)
      * maybe store edits and their indices in an array and iterate over that array after the submit
      * button is pressed to update the move list from the database
  * draw edges over map page for pathfinding (currently PathfindingIt1)
      * implement A* on this map
      * have list of nodes you can pick from?
      * be able to select nodes using listeners?
      node id and longname to modify and shortname
      longname, shortname, nodeid, nodetype, xcoord, ycoord, floor, building
   */

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<TableRow> historyTable;
  @FXML private FilteredTableView<TableRow> edgeTable;
  @FXML TableView<TableRow> otherTable;
  @FXML TableColumn<TableRow, String> ColumnOne;
  @FXML TableColumn<TableRow, String> ColumnTwo;
  @FXML TableColumn<TableRow, String> ColumnThree;
  @FXML TableColumn<TableRow, String> ColumnFour;
  @FXML TableColumn<TableRow, String> ColumnFive;
  @FXML TableColumn<TableRow, String> ColumnSix;
  @FXML TableColumn<TableRow, String> ColumnSeven;
  @FXML TableColumn<TableRow, String> ColumnEight;
  @FXML TableColumn<TableRow, String> ColumnNine;
  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton floorButton;
  @FXML MFXButton modeButton;
  @FXML VBox importMenu;

  @FXML VBox exportMenu;

  Group mapNodes = new Group();
  Group mapEdges = new Group();
  Group movingNode = new Group();
  Group movingText = new Group();
  Group mapText = new Group();
  private Desktop desktop = Desktop.getDesktop();
  Node initialNodeClicked;
  int initXCoord = 0;
  int initYCoord = 0;

  private String filePath;
  @FXML private Label testText;

  NodeDao InodeDao = new NodeDao();
  EdgeDao IedgeDao = new EdgeDao();
  LocationDao IlocationDao = new LocationDao();
  MoveDao ImoveDao = new MoveDao();

  @FXML private Button goHome;
  int XCoord = 0;
  int YCoord = 0;
  String xCoord_temp = "";
  String yCoord_temp = "";
  String nodeID_temp = "";
  String nodeType_temp = "";
  String iD;
  String building = "";
  String floor = "1";
  List<Node> n_toAdd = new ArrayList<Node>();
  List<Node> n_toModify_newNode = new ArrayList<Node>();
  List<String> n_toModify_oldID = new ArrayList<String>();
  List<String> n_toRemove = new ArrayList<String>();
  List<Node> oldNameToAdd = new ArrayList<Node>();
  List<LocationName> newNameToAdd = new ArrayList<LocationName>();
  List<Move> moveNamesToAdd = new ArrayList<Move>();

  List<String> oldNameToModify = new ArrayList<String>();
  List<LocationName> newNameToModify = new ArrayList<LocationName>();
  List<String> nameToRemove = new ArrayList<String>();
  List<String> idList_r = new ArrayList<String>();
  String iD_r = "";
  String removeName = "";
  List<Move> moveNamesToRemove = new ArrayList<Move>();
  List<Node> listNodeToRemove = new ArrayList<Node>();

  // ORM lists
  List<Node> nodeList = new ArrayList<Node>();
  List<Edge> edgeList = new ArrayList<Edge>();
  List<LocationName> locationNameList = new ArrayList<LocationName>();
  // hash maps
  HashMap<Integer, Move> nodeIDtoMove = new HashMap<Integer, Move>();
  HashMap<String, LocationName> longNametoLocationName = new HashMap<String, LocationName>();
  HashMap<Integer, Node> nodeIDToNode = new HashMap<Integer, Node>();
  //  HashMap<Integer,String> nodeIDToFloor = new HashMap<Integer, String>();

  List<Move> moveList = new ArrayList<Move>();
  // List of nodes by floor
  List<Node> Floor1 = new ArrayList<Node>();
  List<Node> Floor2 = new ArrayList<Node>();
  List<Node> Floor3 = new ArrayList<Node>();
  List<Node> FloorG = new ArrayList<Node>();
  List<Node> FloorL1 = new ArrayList<Node>();
  List<Node> FloorL2 = new ArrayList<Node>();
  // List of edges by floor
  List<Edge> Floor1Edges = new ArrayList<Edge>();
  List<Edge> Floor2Edges = new ArrayList<Edge>();
  List<Edge> Floor3Edges = new ArrayList<Edge>();
  List<Edge> FloorL1Edges = new ArrayList<Edge>();
  List<Edge> FloorL2Edges = new ArrayList<Edge>();

  String sNameInput_temp;
  String lNameInput_temp;
  String oldName_temp;
  String nodeIDinput_temp;
  StackPane stackPane = new StackPane();
  Boolean nodeClicked = false;
  Node currNodeClicked;
  MFXButton tempSave = new MFXButton();
  Node draggedNode;
  Node nodeToDrag;
  Boolean firstPass = true;
  //  Node initialNodeClicked;
  Node newNodeTemp;
  String currNodeLongname = "";
  String currNodeShortname = "";
  String currNodeType = "";
  Boolean movingNodeClicked = false;
  HandleMapModes mapMode;
  Boolean addClicked = false;
  Boolean modifyClicked = false;
  Boolean lockMap = false;
  Boolean dragModeOn = false;
  MapModeSaver mapModeSaver = new MapModeSaver();
  @FXML HBox checkAndX_HBox;

  @FXML HBox checkAndX_HBox1;
  @FXML MFXButton check_button;
  @FXML MFXButton x_button;
  ImageView imageView;

  //  Boolean

  /** Method run when controller is initialized */
  public void initialize() {

    Image image = new Image(Main.class.getResource("./views/Images/FirstFloor.png").toString());
    imageView = new ImageView(image); // was ImageView imageView
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);

    group.getChildren().addAll(mapNodes, mapText, movingNode, movingText); // nodes
    //    group.getChildren().add(mapText); // shortnames of nodes
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    mapMode = HandleMapModes.SELECT;
    checkAndX_HBox.setMouseTransparent(true);
    checkAndX_HBox1.setMouseTransparent(true);
    //    group.getChildren().add(stackPane);

    group.setOnMouseClicked(
        e -> {
          //          System.out.print(lockMap);
          mouseX = e.getX();
          mouseY = e.getY();
          //          System.out.println(mouseX + "  " + mouseY);
          //          if(addClicked) //dont need this bc when add is clicked will update mapMode
          // to
          // Add, need to set that up

          if ((Objects.equals(mapMode.getMapMode(), "Add")) && !lockMap && !nodeClicked) {
            lockMap = true;
            //            System.out.println(lockMap);
            addNodeByMouseLoc((int) mouseX, (int) mouseY);
          } // bring up node add popup

          if (nodeClicked && !lockMap) {
            if (Objects.equals(mapMode.getMapMode(), "Add")) { // to add a location name to a node
              lockMap = true;
              addMenu();
            } // bring up location name add popup
            else if (Objects.equals(mapMode.getMapMode(), "Modify")) {
              lockMap = true;
              modifyMenu();
            } // bring up modify popup
            else if (Objects.equals(mapMode.getMapMode(), "Remove")) {
              lockMap = true;
              removeMenu();
            } // bring up remove popup
            // if modify, first popup asks if you want to modify node by drag or by entering?
            // also if modify pop up asks if you want to modify node or name
            else if (Objects.equals(mapMode.getMapMode(), "Move")) {
              lockMap = true;
              //moveMenu();
            }
          }
        });

    //    enum Test { //Look at observers from lecture to see if those will help here, need to be
    // set mode externally and call an instance of the HandleMapNodes object to check which mode,
    // mode will determine action of mouse action event where listeners for that must be set up here
    // in initialize
    //      SELECT,
    //      ADD,
    //      MODIFY,
    //      REMOVE;
    //    }

    loadDatabase();
    loadNodeIDToNode();
    sortNodes();
    sortEdges();
    placeEdges("1");
    placeNodes("1");
  } // end initialize

  // load hashmap of nodeID to floor
  private void loadNodeIDToNode() {
    for (Node node : nodeList) {
      nodeIDToNode.put(node.getNodeID(), node);
    }
  }
  // see if both nodes are on the same floor
  private boolean sameFloor(int nodeID1, int nodeID2) {
    String node1Floor = nodeIDToNode.get(nodeID1).getFloor();
    String node2Floor = nodeIDToNode.get(nodeID2).getFloor();
    //    System.out.println(node1Floor + "    " + node2Floor);
    if (node1Floor.equals(node2Floor)) {
      return true;
    }
    return false;
  }

  private void placeEdges(String floor) {
    group.getChildren().remove(mapEdges);
    mapEdges = new Group();
    switch (floor) {
      case "1":
        for (Edge edge : Floor1Edges) {
          createMapEdges(edge);
        }
        break;
      case "2":
        for (Edge edge : Floor2Edges) {
          createMapEdges(edge);
        }
        break;
      case "3":
        for (Edge edge : Floor3Edges) {
          createMapEdges(edge);
        }
        break;
      case "L1":
        for (Edge edge : FloorL1Edges) {
          createMapEdges(edge);
        }
        break;
      case "L2":
        for (Edge edge : FloorL2Edges) {
          createMapEdges(edge);
        }
        break;
    }
    group.getChildren().add(mapEdges);
    mapEdges.toFront();
  }

  public void createMapEdges(Edge edge) {
    Line line = new Line();
    line.setStartX(nodeIDToNode.get(edge.getStartNode()).getXCoord());
    line.setStartY(nodeIDToNode.get(edge.getStartNode()).getYCoord());
    line.setEndX(nodeIDToNode.get(edge.getEndNode()).getXCoord());
    line.setEndY(nodeIDToNode.get(edge.getEndNode()).getYCoord());
    line.setStrokeWidth(5);
    line.setStroke(Paint.valueOf("021335"));
    mapEdges.getChildren().add(line);
  }

  private void sortEdges() {
    // look at floor list and find all edges that have both nodes in that floor list
    Floor1Edges.clear();
    Floor2Edges.clear();
    Floor3Edges.clear();
    FloorL1Edges.clear();
    FloorL2Edges.clear();

    for (Edge edge : edgeList) {
      String floor = nodeIDToNode.get(edge.getStartNode()).getFloor();
      String floor2 = nodeIDToNode.get(edge.getEndNode()).getFloor();
      //      System.out.println(floor + "   " + floor2);
      // see if both nodes are in the floor list
      sameFloor(edge.getStartNode(), edge.getEndNode());
      if (sameFloor(edge.getStartNode(), edge.getEndNode())
          && (nodeIDToNode.get(edge.getStartNode()).getFloor().equals("1"))) {
        Floor1Edges.add(edge);
      } else if (sameFloor(edge.getStartNode(), edge.getEndNode())
          && nodeIDToNode.get(edge.getStartNode()).getFloor().equals("2")) {
        Floor2Edges.add(edge);
      } else if (sameFloor(edge.getStartNode(), edge.getEndNode())
          && nodeIDToNode.get(edge.getStartNode()).getFloor().equals("3")) {
        Floor3Edges.add(edge);
      } else if (sameFloor(edge.getStartNode(), edge.getEndNode())
          && nodeIDToNode.get(edge.getStartNode()).getFloor().equals("L1")) {
        FloorL1Edges.add(edge);
      } else if (sameFloor(edge.getStartNode(), edge.getEndNode())
          && nodeIDToNode.get(edge.getStartNode()).getFloor().equals("L2")) {
        FloorL2Edges.add(edge);
      }
    }
  }

  // load database
  public void loadDatabase() {
    nodeList = new NodeDao().fetchAllObjects();
    edgeList = new EdgeDao().fetchAllObjects();
    locationNameList = new LocationDao().fetchAllObjects();
    moveList = new MoveDao().fetchAllObjects();

    for (Move move : moveList) {
      try {
        move.getLongName();
      } catch (NullPointerException e) {
        move.setLongName("ERROR");
      }
      nodeIDtoMove.put(move.getNodeID(), move);
    }
    for (LocationName locationName : locationNameList) {
      longNametoLocationName.put(locationName.getLongName(), locationName);
    }
    longNametoLocationName.put("ERROR", new LocationName("ERROR", "ERROR", "ERROR"));
  }
  // comparators
  class NodeComparator implements Comparator<Node> {
    public int compare(Node node1, Node node2) {
      int group1 = getGroupNumber(node1.getFloor());
      int group2 = getGroupNumber(node2.getFloor());
      return Integer.compare(group1, group2);
    }
  }

  private int getGroupNumber(String floor) {
    int group = 0;
    if (floor.startsWith("L")) {
      group = Integer.parseInt(floor.substring(1)) + 2;
    } else {
      group = Integer.parseInt(floor);
    }
    return group;
  }

  private int getFloorNumber(String floor) {
    if (floor.startsWith("L")) {
      return Integer.parseInt(floor.substring(1)) + 2;
    } else {
      return Integer.parseInt(floor);
    }
  }

  public void changeMapMode(ActionEvent event) {
    if (!dragModeOn) {
      modeButton = (MFXButton) event.getTarget();
      if (Objects.equals(modeButton.getId(), "Select")) {
        mapMode = HandleMapModes.SELECT;
      } else if (Objects.equals(modeButton.getId(), "Add")) {
        mapMode = HandleMapModes.ADD;
      } else if (Objects.equals(modeButton.getId(), "Modify")) {
        mapMode = HandleMapModes.MODIFY;
      } else if (Objects.equals(modeButton.getId(), "Remove")) {
        mapMode = HandleMapModes.REMOVE;
      } else if (Objects.equals(modeButton.getId(), "move")) {
        mapMode = HandleMapModes.MOVE;
      }
    }
  }

  public void changeFloor(ActionEvent event) {
    floorButton = (MFXButton) event.getTarget();

    if (Objects.equals(floorButton.getId(), "FL1")) {
      image = new Image(Main.class.getResource("./views/Images/FirstFloor.png").toString());
      floor = "1";
    } else if (Objects.equals(floorButton.getId(), "FL2")) {
      image = new Image(Main.class.getResource("./views/Images/SecondFloor.png").toString());
      floor = "2";
    } else if (Objects.equals(floorButton.getId(), "FL3")) {
      image = new Image(Main.class.getResource("./views/Images/ThirdFloor.png").toString());
      floor = "3";
      //    } else if (Objects.equals(floorButton.getId(), "FLG")) {
      //      image = new
      // Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
      //      floor = "G";
    } else if (Objects.equals(floorButton.getId(), "FLB1")) {
      image = new Image(Main.class.getResource("./views/Images/B1.png").toString());
      floor = "L1";
    } else if (Objects.equals(floorButton.getId(), "FLB2")) {
      image = new Image(Main.class.getResource("./views/Images/B2.png").toString());
      floor = "L2";
    }
    group.getChildren().removeAll(mapNodes, mapText, imageView);
    group.getChildren().remove(mapNodes);
    group.getChildren().remove(mapText);
    //    stackPane.getChildren().remove(mapNodes);

    imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    mapText = new Group();
    group.getChildren().addAll(imageView, mapNodes, mapText);
    //    stackPane.getChildren().add(mapNodes);
    //    group.getChildren().add(mapNodes);
    //    group.getChildren().add(mapText);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    placeEdges(floor);
    placeNodes(floor);
  }

  public void comparatorSortNode() {}

  public void sortNodes() {
    Floor1.clear();
    Floor2.clear();
    Floor3.clear();
    FloorG.clear();
    FloorL1.clear();
    FloorL2.clear();
    for (Node node : nodeList) {
      if (node.getFloor().equals("1")) {
        Floor1.add(node);
      }
      if (node.getFloor().equals("2")) {
        Floor2.add(node);
      }
      if (node.getFloor().equals("3")) {
        Floor3.add(node);
      }
      if (node.getFloor().equals("G")) {
        FloorG.add(node);
      }
      if (node.getFloor().equals("L1")) {
        FloorL1.add(node);
      }
      if (node.getFloor().equals("L2")) {
        FloorL2.add(node);
      }
    }
  }

  public void placeNodes(String floor) {
    switch (floor) {
      case "1":
        for (int i = 0; i < Floor1.size(); i++) {
          int nodeID = Floor1.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor1.get(i), shortName, nodeType, longName);
        }
        break;
      case "2":
        for (int i = 0; i < Floor2.size(); i++) {
          int nodeID = Floor2.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor2.get(i), shortName, nodeType, longName);
        }
        break;
      case "3":
        for (int i = 0; i < Floor3.size(); i++) {
          int nodeID = Floor3.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor3.get(i), shortName, nodeType, longName);
        }
        break;
      case "G":
        for (int i = 0; i < FloorG.size(); i++) {
          int nodeID = FloorG.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorG.get(i), shortName, nodeType, longName);
        }
        break;
      case "L1":
        for (int i = 0; i < FloorL1.size(); i++) {
          int nodeID = FloorL1.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorL1.get(i), shortName, nodeType, longName);
        }
        break;
      case "L2":
        for (int i = 0; i < FloorL2.size(); i++) {
          int nodeID = FloorL2.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorL2.get(i), shortName, nodeType, longName);
        }
    }
    //    stackPane.toFront();
    mapNodes.toFront();
    mapText.toFront();
    //    mapNodes.getChildren().s
  }

  public void createMapNodes(Node node, String shortname, String nodeType, String longName) {
    Circle newCircle = new Circle();
    Text text = new Text();
    //    TextArea text = new TextArea();
    if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
      //      text = new TextArea(shortname);
      text = new Text(shortname);
      //      text.setX();
      //      Tooltip nodeName = new Tooltip(shortname);
      //      nodeName.setShowDelay(Duration.ZERO);
      //      nodeName.setShowDuration(Duration.hours(2));
      //      Tooltip.install(newCircle, nodeName);
    }
    newCircle.setRadius(6);
    newCircle.setCenterX(node.getXCoord());
    newCircle.setCenterY(node.getYCoord());
    //    text.setLocation(node.getXCoord() + 10, node.getYCoord() - 10);

    //    text.(node.getXCoord() + 10);
    //    text.snapPositionY(node.getYCoord() - 10);
    text.setX(node.getXCoord() + 5);
    text.setY(node.getYCoord() - 5);
    //    text.setStroke(Paint.valueOf("#FFFFFF"));
    //    text.setFill(Paint.valueOf("#CD8003"));
    //    text.setBackground(Color.getColor("#13DAF7"));
    //    text.setStyle("-fx-background-color: black; -fx-text-fill: white");

    //    if (Objects.equals(mapMode.getMapMode(), "Modify_drag")) {
    //      newCircle.setId(String.valueOf(0));
    //      newCircle.setStroke(Paint.valueOf("#13DAF7"));
    //      newCircle.setFill(Paint.valueOf("#13DAF7"));
    //      newCircle.setVisible(true);
    //      text.setVisible(true);
    //    }
    newCircle.setId(String.valueOf(node.getNodeID()));
    newCircle.setStroke(Paint.valueOf("#13DAF7"));
    newCircle.setFill(Paint.valueOf("#13DAF7"));
    newCircle.setVisible(true);
    text.setVisible(true);
    //    if (Objects.equals(mapMode.getMapMode(), "Modify_drag")) {
    //      newCircle.setOnMousePressed
    //    }
    newCircle.setOnMouseEntered(
        e -> {
          newCircle.setStroke(Paint.valueOf("#C51919"));
        });

    newCircle.setOnMousePressed( // was set on mouse clicked
        e -> {
          nodeClicked = true; // clicked on a node
          currNodeClicked = node;
          currNodeLongname = longName;
          currNodeShortname = shortname;
          currNodeType = nodeType;

          if (!(Objects.equals(mapMode.getMapMode(), "Modify_drag"))) {
            newCircle.setFill(Paint.valueOf("#45a37f"));
          } else if (Objects.equals(node.getNodeID(), mapModeSaver.getNodeID())) {
            newCircle.setFill(Paint.valueOf("#45a37f"));
          }

          if ((Objects.equals(mapMode.getMapMode(), "Modify_drag"))
              && !mapModeSaver.getDraggingNodeCreated()) {
            movingNodeClicked = true;
          }
          //          System.out.println("circle clicked");
        });
    newCircle.setOnMouseExited(
        e -> {
          if (!(Objects.equals(mapMode.getMapMode(), "Modify_drag"))) {
            newCircle.setFill(Paint.valueOf("#13DAF7"));
            newCircle.setStroke(Paint.valueOf("#13DAF7"));
          } else if (!(Objects.equals(node.getNodeID(), mapModeSaver.getNodeID()))) {
            newCircle.setStroke(Paint.valueOf("13DAF7"));
          }
        });
    //    newCircle.setOnMouseDragEntered(
    //        e -> {
    //          mouseDragged = true;
    //        });

    if (Objects.equals(mapMode.getMapMode(), "Modify_drag")) {
      //      newCircle.setOnMousePressed( //////////////
      //          event -> {
      //            movingNodeClicked = true;
      //          });

      System.out.println("GOT HERE");
      movingNode.getChildren().add(newCircle);
      movingText.getChildren().add(text);
    } else {
      mapNodes.getChildren().add(newCircle);
      mapText.getChildren().add(text);
    }
  }

  public void createMovingMapNode(
      Node node,
      int nodeID,
      String shortname,
      String nodeType,
      String longName,
      int x,
      int y) { // updated nodeID input
    Circle newCircle = new Circle();
    Text text = new Text();
    //    TextArea text = new TextArea();
    if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
      //      text = new TextArea(shortname);
      text = new Text(shortname);
      //      text.setX();
      //      Tooltip nodeName = new Tooltip(shortname);
      //      nodeName.setShowDelay(Duration.ZERO);
      //      nodeName.setShowDuration(Duration.hours(2));
      //      Tooltip.install(newCircle, nodeName);
    }
    newCircle.setRadius(6);
    newCircle.setCenterX(x);
    newCircle.setCenterY(y);
    //    text.setLocation(node.getXCoord() + 10, node.getYCoord() - 10);

    //    text.(node.getXCoord() + 10);
    //    text.snapPositionY(node.getYCoord() - 10);
    text.setX(x + 5);
    text.setY(y - 5);
    //    text.setStroke(Paint.valueOf("#FFFFFF"));
    //    text.setFill(Paint.valueOf("#CD8003"));
    //    text.setBackground(Color.getColor("#13DAF7"));
    //    text.setStyle("-fx-background-color: black; -fx-text-fill: white");

    //    if (Objects.equals(mapMode.getMapMode(), "Modify_drag")) {
    //      newCircle.setId(String.valueOf(0));
    //      newCircle.setStroke(Paint.valueOf("#13DAF7"));
    //      newCircle.setFill(Paint.valueOf("#13DAF7"));
    //      newCircle.setVisible(true);
    //      text.setVisible(true);
    //    }
    newCircle.setId(String.valueOf(nodeID));
    newCircle.setStroke(Paint.valueOf("#45a37f"));
    newCircle.setFill(Paint.valueOf("#8745a3"));
    newCircle.setVisible(true);
    text.setVisible(true);
    newCircle.setOnMousePressed(
        e -> {
          nodeClicked = true; // clicked on a node
          //          currNodeClicked = node; //took this out, is it still needed?*************
          currNodeLongname = longName;
          currNodeShortname = shortname;
          currNodeType = nodeType;
          movingNodeClicked = true;
          if (Objects.equals(node.getNodeID(), mapModeSaver.getNodeID())) {
            newCircle.setFill(Paint.valueOf("#45a37f"));
          }
          //          System.out.println("circle clicked");
        });
    //    newCircle.setOnMouseDragEntered(
    //        e -> {
    //          mouseDragged = true;
    //        });

    System.out.println("GOT HERE");
    movingNode.getChildren().add(newCircle);
    movingText.getChildren().add(text);
  }

  public void addNodeByMouseLoc(int x, int y) {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Add Node Using Mouse Location");
    Text building = new Text("Input name of building the node will be in");
    MFXTextField b_input = new MFXTextField();
    MFXButton addButton = new MFXButton("Submit Add");
    vBox.getChildren().addAll(headerText, building, b_input, addButton);

    // set styles
    headerText.getStyleClass().add("Header");
    building.getStyleClass().add("Text");
    b_input.getStyleClass().add("MFXtextIn");
    addButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // add input boxes styles
    //    nodeTypeInput.setPrefWidth(120);

    //    vBox.getChildren() //vBox does not work bc usage with it sucks with spacing
    //            .addAll(building, b_input, addButton);
    //
    //    // set vBox location
    //    vBox.setLayoutX(50);
    //    vBox.setLayoutY(20);
    //    vBox.setSpacing(15);

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    building.setLayoutX(lay_x);
    building.setLayoutY(lay_y + 35);
    b_input.setLayoutX(lay_x);
    b_input.setLayoutY(lay_y + 35);
    addButton.setLayoutX(lay_x);
    addButton.setLayoutY(lay_y + 95);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, building, b_input, addButton);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 410, 225);
    scene
        .getStylesheets()
        .add(Main.class.getResource("./views/Stylesheets/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Node Window");
    stage.setAlwaysOnTop(true);
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    addButton.setOnMouseClicked(
        buttonEvent -> {
          // make new node
          Node newNode = new Node(x, y, floor, b_input.getText());
          NodeDao nodeDao = new NodeDao();
          b_input.clear();
          //          MoveDao moveDao = new MoveDao();

          // add to database and history page
          MapHistoryDao mapHistory = new MapHistoryDao();
          nodeDao.addRow(newNode);
          mapHistory.addRow(
              new MapHistory(
                  "ADD",
                  String.valueOf(newNode.getNodeID()),
                  "node",
                  new Timestamp(System.currentTimeMillis())));

          // Paint node
          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);
          loadDatabase();
          sortNodes();
          placeNodes(floor);

          // close menu
          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
  }

  public void modifyMenu() {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Select Modification Method");
    Text modify_1 = new Text("Modify node by text input?");
    Text modify_2 = new Text("Modify node by dragging on map?");
    Text modify_3 = new Text("Modify location name of node?");
    MFXButton byText = new MFXButton("By Text");
    MFXButton byDrag = new MFXButton("By Drag");
    MFXButton editName = new MFXButton("Edit Name");

    vBox.getChildren().addAll(headerText, modify_1, byText, modify_2, byDrag, modify_3, editName);

    // set styles
    headerText.getStyleClass().add("Header");
    modify_1.getStyleClass().add("Text");
    modify_2.getStyleClass().add("Text");
    modify_3.getStyleClass().add("Text");
    byText.getStyleClass().add("MFXbutton");
    byDrag.getStyleClass().add("MFXbutton");
    editName.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // add input boxes styles
    //    nodeTypeInput.setPrefWidth(120);

    //    vBox.getChildren() //vBox does not work bc usage with it sucks with spacing
    //            .addAll(building, b_input, addButton);
    //
    //    // set vBox location
    //    vBox.setLayoutX(50);
    //    vBox.setLayoutY(20);
    //    vBox.setSpacing(15);

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    modify_1.setLayoutX(lay_x);
    modify_1.setLayoutY(lay_y + 35);
    byText.setLayoutX(lay_x);
    byText.setLayoutY(lay_y + 50);
    modify_2.setLayoutX(lay_x);
    modify_2.setLayoutY(lay_y + 120);
    byDrag.setLayoutX(lay_x);
    byDrag.setLayoutY(lay_y + 135);
    modify_3.setLayoutX(lay_x);
    modify_3.setLayoutY(lay_y + 205);
    editName.setLayoutX(lay_x);
    editName.setLayoutY(lay_y + 220);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, modify_1, byText, modify_2, byDrag, modify_3, editName);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 350, 330);
    scene
        .getStylesheets()
        .add(Main.class.getResource("./views/Stylesheets/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Modify Window");
    stage.setAlwaysOnTop(true);
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    byText.setOnMouseClicked(
        event -> {
          stage.close();
          modifyNodeByInput();
        });
    byDrag.setOnMouseClicked(
        event -> {
          stage.close();
          mapMode = HandleMapModes.MODIFY_DRAG;
          initialNodeClicked = currNodeClicked;
          mapModeSaver.setNodeID(currNodeClicked.getNodeID());
          mapModeSaver.setLocationName(
              new LocationName(currNodeLongname, currNodeShortname, currNodeType));
          initXCoord = currNodeClicked.getXCoord();
          initYCoord = currNodeClicked.getYCoord();
          modifyByDrag();
        });
    editName.setOnMouseClicked(
        event -> {
          stage.close();
          modifyName();
        });
  }

  public void modifyNodeByInput() {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();

    Text headerText = new Text("Modify Node Location");
    Text xCoord_t = new Text("Input new Xcoord");
    Text yCoord_t = new Text("Input new YCoord");
    MFXTextField xCoord_input = new MFXTextField();
    MFXTextField yCoord_input = new MFXTextField();

    MFXButton submitModify = new MFXButton("Modify");
    submitModify.setPrefSize(100, 35);
    submitModify.setMinSize(100, 35);

    //    vBox.getChildren().addAll(headerText, xCoord_t, xCoord_input, yCoord_t, yCoord_input,
    // submitModify);
    //    vBox.setSpacing(20);

    // set styles
    headerText.getStyleClass().add("Header");
    xCoord_t.getStyleClass().add("Text");
    yCoord_t.getStyleClass().add("Text");
    xCoord_input.getStyleClass().add("MFXtextIn");
    yCoord_input.getStyleClass().add("MFXtextIn");
    submitModify.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 40;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    xCoord_t.setLayoutX(lay_x);
    xCoord_t.setLayoutY(lay_y + 35);
    xCoord_input.setLayoutX(lay_x);
    xCoord_input.setLayoutY(lay_y + 35);
    yCoord_t.setLayoutX(lay_x);
    yCoord_t.setLayoutY(lay_y + 105);
    yCoord_input.setLayoutX(lay_x);
    yCoord_input.setLayoutY(lay_y + 105);
    submitModify.setLayoutX(lay_x);
    submitModify.setLayoutY(lay_y + 165);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane
        .getChildren()
        .addAll(headerText, xCoord_t, xCoord_input, yCoord_t, yCoord_input, submitModify);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 290, 290);
    scene
        .getStylesheets()
        .add(Main.class.getResource("./views/Stylesheets/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Modify Node Window");
    stage.setAlwaysOnTop(true);
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    submitModify.setOnMouseClicked(
        buttonEvent -> {

          // take inputted text and clear
          xCoord_temp = xCoord_input.getText();
          xCoord_input.clear();
          yCoord_temp = yCoord_input.getText();
          yCoord_input.clear();

          // make new node
          Node newNode =
              new Node(
                  currNodeClicked.getNodeID(),
                  Integer.valueOf(xCoord_temp),
                  Integer.valueOf(yCoord_temp),
                  floor,
                  building);

          // Add node to database
          NodeDao nodeDao = new NodeDao();
          MapHistoryDao mapHistoryDao = new MapHistoryDao();
          nodeDao.updateRow(currNodeClicked.getNodeID(), newNode);
          mapHistoryDao.addRow(
              new MapHistory(
                  "UPDATE",
                  String.valueOf(newNode.getNodeID()),
                  "node",
                  new Timestamp(System.currentTimeMillis())));

          // Paint node
          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);
          loadDatabase();
          loadNodeIDToNode();
          sortNodes();
          sortEdges();
          placeEdges(floor);
          placeNodes(floor);

          // close menu
          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
  }

  public void removeMenu() { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text remove_1 = new Text("Remove Node?");
    Text remove_2 = new Text("Remove Node Location Name");
    MFXButton removeNode = new MFXButton("Remove Node");
    MFXButton removeName = new MFXButton("Remove Name");

    vBox.getChildren().addAll(remove_1, removeNode, remove_2, removeName);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().add(vBox);
    Insets insets = new Insets(0, 0, 0, 200);
    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 650, 500);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Remove Window");
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    removeNode.setOnMouseClicked(
        event -> {
          MoveDao moveDao = new MoveDao();
          NodeDao nodeDao = new NodeDao();
          moveDao.deleteRow(currNodeClicked.getNodeID());
          nodeDao.deleteRow(currNodeClicked.getNodeID());

          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);
          loadDatabase();
          loadNodeIDToNode();
          sortNodes();
          sortEdges();
          placeEdges(floor);
          placeNodes(floor);

          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
    removeName.setOnMouseClicked(
        event -> {
          MoveDao moveDao = new MoveDao();
          LocationDao locationDao = new LocationDao();
          long currentTime = System.currentTimeMillis();
          Date currentDate = new Date(currentTime);
          Move move = new Move(currNodeClicked.getNodeID(), currNodeLongname, currentDate);
          moveDao.deleteRow(move);
          locationDao.deleteRow(currNodeLongname);

          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);
          loadDatabase();
          sortNodes();
          placeNodes(floor);

          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
  }

  public void addMenu() { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Add Location Name to Node");
    Text nodeType = new Text("Input New Node Type");
    Text SName = new Text("Input New Shortname");
    Text LName = new Text("Input New Longname");

    MFXTextField nodeTypeInput = new MFXTextField();
    MFXTextField sNameInput = new MFXTextField();
    MFXTextField lNameInput = new MFXTextField();

    MFXButton addName = new MFXButton("Submit");
    VBox v1 = new VBox();
    VBox v2 = new VBox();
    VBox v3 = new VBox();

    // set styles
    headerText.getStyleClass().add("Header");
    nodeType.getStyleClass().add("Text");
    SName.getStyleClass().add("Text");
    LName.getStyleClass().add("Text");
    nodeTypeInput.getStyleClass().add("MFXtextIn");
    sNameInput.getStyleClass().add("MFXtextIn");
    lNameInput.getStyleClass().add("MFXtextIn");
    addName.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // add input boxes styles
    //    nodeTypeInput.setPrefWidth(120);

    vBox.getChildren() // vBox does not work bc usage with it sucks with spacing
        .addAll(headerText, nodeType, nodeTypeInput, SName, sNameInput, LName, lNameInput, addName);

    // set vBox location
    vBox.setLayoutX(50);
    vBox.setLayoutY(20);
    vBox.setSpacing(15);

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    nodeType.setLayoutX(lay_x);
    nodeType.setLayoutY(lay_y + 35);
    nodeTypeInput.setLayoutX(lay_x);
    nodeTypeInput.setLayoutY(lay_y + 35);
    SName.setLayoutX(lay_x);
    SName.setLayoutY(lay_y + 110);
    sNameInput.setLayoutX(lay_x);
    sNameInput.setLayoutY(lay_y + 110);
    LName.setLayoutX(lay_x);
    LName.setLayoutY(lay_y + 185);
    lNameInput.setLayoutX(lay_x);
    lNameInput.setLayoutY(lay_y + 185);
    addName.setLayoutX(lay_x);
    addName.setLayoutY(lay_y + 250);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane
        .getChildren()
        .addAll(
            headerText,
            nodeType,
            nodeTypeInput,
            SName,
            sNameInput,
            LName,
            lNameInput,
            addName); // was add vBox
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 370, 360);

    // Set Scene styleClass
    //    scene.setFill(Paint.valueOf("#02143b"));
    //    scene.set

    //    File stylesheet = new
    // File((Main.class.getResource("./views/Stylesheets/MapEditorPopUps.css").toString()));
    scene
        .getStylesheets()
        .add(Main.class.getResource("./views/Stylesheets/MapEditorPopUps.css").toString());

    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Location Name Window");
    stage.setAlwaysOnTop(true);

    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    addName.setOnMouseClicked(
        event -> {
          LocationDao locationDao = new LocationDao();
          MoveDao moveDao = new MoveDao();
          LocationName locationName =
              new LocationName(lNameInput.getText(), sNameInput.getText(), nodeTypeInput.getText());

          long currentTime = System.currentTimeMillis();
          Date currentDate = new Date(currentTime);
          Move move = new Move(currNodeClicked.getNodeID(), lNameInput.getText(), currentDate);
          locationDao.addRow(locationName);
          moveDao.addRow(move);

          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);
          loadDatabase();
          sortNodes();
          placeNodes(floor);

          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
  }

  public void modifyName() { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();

    Text nodeType = new Text("Input new Node Type");
    Text LName = new Text("Input new Longname");
    Text SName = new Text("Input new Shortname"); // need current longname of current node

    MFXTextField nodeTypeInput = new MFXTextField();
    MFXTextField lNameInput = new MFXTextField();
    MFXTextField sNameInput = new MFXTextField();

    MFXButton modifyName = new MFXButton("Modify Name");

    vBox.getChildren()
        .addAll(nodeType, nodeTypeInput, SName, sNameInput, LName, lNameInput, modifyName);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().add(vBox);
    Insets insets = new Insets(0, 0, 0, 200);
    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 650, 500);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Location Name Window");
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
        });

    modifyName.setOnMouseClicked(
        event -> {
          LocationDao locationDao = new LocationDao();

          // If nodeType entered is not equal to 4 characters, assign the nodeType as HALL
          String nodeType_t = nodeTypeInput.getText();
          if (!(nodeType_t.length() == 4)) { // Fix later
            nodeType_t = "HALL";
          }

          // Add to LocationName and Move Tables
          LocationName locationName =
              new LocationName(lNameInput.getText(), sNameInput.getText(), nodeType_t);
          locationDao.updateRow(currNodeLongname, locationName);

          group.getChildren().removeAll(mapNodes, mapText);
          mapNodes = new Group();
          mapText = new Group();
          group.getChildren().addAll(mapNodes, mapText);

          loadDatabase();
          sortNodes();
          placeNodes(floor);

          stage.close();
          nodeClicked = false;
          lockMap = false;
        });
  }

  public void modifyByDrag() { // make this a pop up window instead of a whole new scene?
    //    mapGPane.setGestureEnabled(false);
    checkAndX_HBox.setVisible(true);
    checkAndX_HBox1.setVisible(true);
    checkAndX_HBox.setMouseTransparent(false);
    checkAndX_HBox1.setMouseTransparent(false);
    Node helperNode1 = new Node(0, 5, 5, "test", "test");
    Node helperNode2 = new Node(20000, 5, 5, "test", "test");
    //    final Node initialNodeClicked = currNodeClicked;
    nodeToDrag = currNodeClicked;
    draggedNode = currNodeClicked;
    draggedNode.setNodeID(0);
    //    if (firstPass) {
    //      initialNodeClicked = currNodeClicked;
    //    }
    //    firstPass = false;
    AtomicBoolean onChosenNode = new AtomicBoolean(true);
    //    mapGPane.getOnMouseDragged();
    //    ObjectProperty<EventHandler<? super MouseEvent>> eventHandlerDrag =
    // group.onMouseDraggedProperty();
    EventHandler<? super MouseEvent> eventHandlerDrag = group.getOnMouseDragged();
    //    EventHandler<? super MouseEvent> eventHandlerPress = group.getOnMousePressed();
    EventHandler<? super MouseEvent> eventHandlerRel = group.getOnMouseReleased();
    //    EventHandler<? super MouseEvent> eventHandlerDrag_Node = group.getOnMouseDragged();
    NodeDao nodeDao = new NodeDao();
    int eventX = 0;
    int eventY = 0;

    //    group.setOnMousePressed(
    //        event -> {
    //          mapGPane.setGestureEnabled(false);
    //          //          if (Objects.equals(currNodeClicked.getNodeID(), nodeToDrag.getNodeID()))
    // {
    //          //            onChosenNode.set(true);
    //          //            mapGPane.setGestureEnabled(false);
    //          //          }
    //        });

    //    group.setOnMousePressed(event -> {
    //      if(movingNodeClicked) {
    //
    //      }
    //    })
    // cant figure out how to have drag functionality not start off and then to have it return when
    // not dragging a node, placed back when mouse is over node again

    // update node as drag occurs
    group.setOnMouseDragged( // setOnMouseDragged
        dragEvent -> {
          // lock gesture pane to drag node
          //          mapGPane.setGestureEnabled(false);
          if (dragEvent.isAltDown()) {
            mapGPane.setGestureEnabled(true);
          } else {
            mapGPane.setGestureEnabled(false);
            //            System.out.println(currNodeClicked.getNodeID() + "  " +
            // nodeToDrag.getNodeID());
            //            if ((Objects.equals(currNodeClicked.getNodeID(), nodeToDrag.getNodeID()))
            // || Objects.equals(currNodeClicked.getNodeID(), 0)) {
            // //was currNodeClicked.getNodeID(); //testNode
            if (movingNodeClicked) { // remove if want to click anywhere and move mouse to update
              mapModeSaver.setDraggingNodeCreated(true);
              // moving node
              //                        mapGPane.setGestureEnabled(false);

              // make new node
              //            Node newNode = ////////////////////////////////////
              //                new Node(
              //                    nodeToDrag.getNodeID(),
              //                    (int) dragEvent.getX(),
              //                    (int) dragEvent.getY(),
              //                    floor,
              //                    nodeToDrag.getBuilding());
              //            newNodeTemp = newNode; ////////////////////////////

              //            nodeToDrag.setXCoord((int) dragEvent.getX());
              //            nodeToDrag.setYCoord((int) dragEvent.getY());
              //
              //              draggedNode.setXCoord((int) dragEvent.getX());
              //              draggedNode.setYCoord((int) dragEvent.getY());
              mapModeSaver.setEventCoords((int) dragEvent.getX(), (int) dragEvent.getY());
              //              eventX = (int) dragEvent.getX();
              //              eventY = (int) dragEvent.getY();

              // Add node to database
              //            nodeDao.updateRow(nodeToDrag.getNodeID(), newNode); //////////////////

              // Paint node
              //            group ///////////////////////////////////////////
              //                .getChildren()
              //                .removeAll(
              //                    mapNodes,
              //                    mapText); // highlight last spot in one color, dragging spot in
              // another
              //            mapNodes = new Group();
              //            mapText = new Group();
              //            group.getChildren().addAll(mapNodes, mapText);
              //            loadDatabase();
              //            sortNodes(); ////////////////////////////////
              group.getChildren().removeAll(movingNode, movingText);
              movingNode = new Group();
              movingText = new Group();
              group.getChildren().addAll(movingNode, movingText);
              createMovingMapNode(
                  currNodeClicked,
                  0,
                  currNodeShortname,
                  currNodeType,
                  currNodeLongname,
                  (int) dragEvent.getX(),
                  (int) dragEvent.getY());
              movingNode.setVisible(true);
              movingNode.toFront();
              movingText.toFront();

              //            placeNodes(floor);
            }
          }
          //            else {
          //              group.onMouseDraeventHandlerDrag;
          ////              group.setOnMouseDragged(eventHandlerDrag);
          //            }
        });
    // unlock gesture pane if not clicking on and dragging node
    //    group.setOnMouseDragReleased(
    //        event -> {
    //          mapGPane.setGestureEnabled(true);
    //        });
    group.setOnMouseReleased(
        event -> {
          mapGPane.setGestureEnabled(true);
          currNodeClicked = helperNode2; // necessary still?
          movingNodeClicked =
              false; // remove if want to move mouse anywhere, click and drag and node to follow
          //          group.setOnMouseDragged(eventHandlerDrag);
          //          onChosenNode.set(false);
        });

    // exit conditions
    check_button.setOnMouseClicked(
        event -> { // maybe when this is pressed ask if you want to modify now or later? set move
          // for future here
          currNodeClicked = helperNode2;
          MapHistoryDao mapHistoryDao = new MapHistoryDao();
          mapHistoryDao.addRow(
              new MapHistory(
                  "UPDATE",
                  String.valueOf(mapModeSaver.getNodeID()),
                  "node",
                  new Timestamp(System.currentTimeMillis())));

          Node newNode =
              new Node(
                  mapModeSaver.getNodeID(),
                  mapModeSaver.getEventX(),
                  mapModeSaver.getEventY(),
                  floor,
                  nodeToDrag.getBuilding());

          System.out.println(
              mapModeSaver.getEventX()
                  + "    "
                  + draggedNode.getXCoord()
                  + "      "
                  + initialNodeClicked.getXCoord());

          //          newNodeTemp = newNode;

          //          NodeDao nodeDao3 = new NodeDao();
          nodeDao.updateRow(mapModeSaver.getNodeID(), newNode); // ////////////////

          // Paint node
          group
              .getChildren()
              .removeAll(
                  mapNodes,
                  mapText,
                  movingNode,
                  movingText); // highlight last spot in one color, dragging spot in
          // another
          mapNodes = new Group();
          mapText = new Group();
          movingNode = new Group();
          movingText = new Group();
          group.getChildren().addAll(mapNodes, mapText, movingNode, movingText);

          mapMode = HandleMapModes.MODIFY;

          dragModeOn = false;
          lockMap = false;
          checkAndX_HBox.setVisible(false);
          checkAndX_HBox1.setVisible(false);
          checkAndX_HBox.setMouseTransparent(true);
          checkAndX_HBox1.setMouseTransparent(true);
          nodeClicked = false;
          mapGPane.setGestureEnabled(true);
          group.setOnMouseDragged(eventHandlerDrag);
          group.setOnMouseReleased(eventHandlerRel);
          firstPass = true;
          mapModeSaver.setDraggingNodeCreated(false);

          loadDatabase();
          loadNodeIDToNode();
          sortNodes();
          sortEdges();
          placeEdges(floor);
          placeNodes(floor);

          currNodeClicked =
              helperNode1; // ensures clicking on the map again won't try to cause modify to run
          // again
        });

    x_button.setOnMouseClicked(
        event -> { // set tooltip describing check and exit buttons
          // Add node to database
          NodeDao nodeDao2 = new NodeDao();
          nodeDao2.updateRow(initialNodeClicked.getNodeID(), initialNodeClicked);
          System.out.println(
              currNodeClicked.getXCoord()
                  + "   "
                  + nodeToDrag.getXCoord()
                  + "   "
                  + initialNodeClicked.getXCoord()
                  + "   "
                  + draggedNode.getXCoord());

          // Paint node
          // Paint node
          group
              .getChildren()
              .removeAll(
                  mapNodes,
                  mapText,
                  movingNode,
                  movingText); // highlight last spot in one color, dragging spot in
          // another
          mapNodes = new Group();
          mapText = new Group();
          movingNode = new Group();
          movingText = new Group();
          group.getChildren().addAll(mapNodes, mapText, movingNode, movingText);
          mapMode = HandleMapModes.MODIFY;
          dragModeOn = false;
          lockMap = false;
          checkAndX_HBox.setVisible(false);
          checkAndX_HBox1.setVisible(false);
          checkAndX_HBox.setMouseTransparent(true);
          checkAndX_HBox1.setMouseTransparent(true);
          nodeClicked = false;
          mapGPane.setGestureEnabled(true);
          group.setOnMouseDragged(eventHandlerDrag);
          group.setOnMouseReleased(eventHandlerRel);
          firstPass = true;
          mapModeSaver.setDraggingNodeCreated(false);

          loadDatabase();
          sortNodes();
          //          placeEdges(floor);
          placeNodes(floor);

          currNodeClicked = helperNode1;
        });
  }

  //  public void resetAndSetFloorIndicator(MFXButton button) {
  //    button.setBackground(Background.fill(Paint.valueOf("#32CD32")));
  //    tempSave.setBackground(Background.fill(DEFAULT_BG));
  //    tempSave = button;
  //  }

  //    })

  public void showNodeMenu(ActionEvent event) {
    BorderPane borderPane = new BorderPane();
    HBox hBox = new HBox();

    // modify
    VBox modifyBox = new VBox(); // modify
    Text nodeID_M = new Text("Input ID of Node to Be Modified");
    Text xCoord_t = new Text("Input new Xcoord");
    Text yCoord_t = new Text("Input new YCoord");
    MFXTextField nodeIDText = new MFXTextField();
    MFXTextField xCoord_text = new MFXTextField();
    MFXTextField yCoord_text = new MFXTextField();
    MFXButton submitModify = new MFXButton("Modify");
    submitModify.setPrefSize(100, 35);
    submitModify.setMinSize(100, 35);
    modifyBox
        .getChildren()
        .addAll(nodeID_M, nodeIDText, xCoord_t, xCoord_text, yCoord_t, yCoord_text, submitModify);
    modifyBox.setSpacing(20);

    // remove
    VBox removeBox = new VBox();
    Text nodeID_R = new Text("Input ID of Node to be Removed");
    MFXTextField nodeID_RText = new MFXTextField();
    MFXButton submitRemove = new MFXButton("Remove");
    submitRemove.setPrefSize(100, 35);
    submitRemove.setMinSize(100, 35);
    removeBox.getChildren().addAll(nodeID_R, nodeID_RText, submitRemove);
    removeBox.setSpacing(20);

    // add
    VBox addBox = new VBox();
    Text XCoordText = new Text("Input X Coordinate of Node to be Added");
    Text YCoordText = new Text("Input Y Coordinate of Node to be Added");
    Text BuildingText = new Text("Input Building Name of Node to be Added");
    MFXTextField inputXCoord = new MFXTextField();
    MFXTextField inputYCoord = new MFXTextField();
    MFXTextField inputBuilding = new MFXTextField(); // need floor as well
    MFXButton submitNode = new MFXButton("Add");
    submitNode.setId("submitNode");
    submitNode.setPrefSize(100, 35);
    submitNode.setMinSize(100, 35);
    addBox
        .getChildren()
        .addAll(
            XCoordText,
            inputXCoord,
            YCoordText,
            inputYCoord,
            BuildingText,
            inputBuilding,
            submitNode);
    addBox.setSpacing(20);
    hBox.getChildren().addAll(addBox, modifyBox, removeBox);
    hBox.setSpacing(20);

    MFXButton submitNodeEdits = new MFXButton("Update Map");
    submitNodeEdits.setPrefSize(150, 40);

    VBox masterBox = new VBox();
    masterBox.getChildren().addAll(hBox, submitNodeEdits);
    masterBox.setSpacing(20);
    masterBox.setAlignment(Pos.CENTER);

    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().add(masterBox);
    Insets insets = new Insets(0, 0, 0, 200);
    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);

    //    borderPane.setPadding(insets);
    //    addBox.relocate(0, 0);
    Scene scene = new Scene(borderPane, 650, 500);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Node Window");

    stage.show();
    // Add
    submitNode.setOnMouseClicked(
        buttonEvent -> {
          xCoord_temp = inputXCoord.getText();
          inputXCoord.clear();
          yCoord_temp = inputYCoord.getText();
          inputYCoord.clear();
          //          iD = inputID.getText();
          building = inputBuilding.getText(); // maybe set automatically later
          inputBuilding.clear();
          Node newNode =
              new Node(Integer.valueOf(xCoord_temp), Integer.valueOf(yCoord_temp), floor, building);
          //          NodeDao nodeDao = new NodeDao();
          n_toAdd.add(newNode);
          //          nodeDao.addRow(newNode); to iterate over in submit method
          //          placeNodes(
          //              floor); // later implement an update map button that updates all changes
          // made at once
          // so user can submit multiple at a time
          //          System.out.println("printed the new node");
        });
    // Modify
    submitModify.setOnMouseClicked(
        buttonEvent -> {
          nodeID_temp = nodeIDText.getText();
          nodeIDText.clear();
          xCoord_temp = xCoord_text.getText();
          xCoord_text.clear();
          yCoord_temp = yCoord_text.getText();
          yCoord_text.clear();
          //          iD = inputID.getText();
          //              building = inputBuilding.getText(); // maybe set automatically later
          Node newNode =
              new Node(
                  Integer.valueOf(nodeID_temp),
                  Integer.valueOf(xCoord_temp),
                  Integer.valueOf(yCoord_temp),
                  floor,
                  building);

          n_toModify_newNode.add(newNode);
          n_toModify_oldID.add(nodeID_temp);

          //          System.out.println("modified the node");
        });
    // Remove
    submitRemove.setOnMouseClicked(
        buttonEvent -> {
          iD = nodeID_RText.getText();
          nodeID_RText.clear();
          n_toRemove.add(iD);
          //              placeNodes(
          //                      floor); // later implement an update map button that updates all
          // changes made at once
          // so
          // user can submit multiple at a tim

          //          System.out.println("removed the node");
        });
    // Submit
    submitNodeEdits.setOnMouseClicked(
        buttonEvent -> {
          NodeDao nodeDao = new NodeDao();
          MoveDao moveDao = new MoveDao();

          MapHistoryDao mapHistory = new MapHistoryDao();
          // Add loop
          for (Node currNode : n_toAdd) {
            nodeDao.addRow(currNode);
            mapHistory.addRow(
                new MapHistory(
                    "ADD",
                    String.valueOf(currNode.getNodeID()),
                    "node",
                    new Timestamp(System.currentTimeMillis())));
          }
          // Modify loop
          for (int i = 0; i < n_toModify_oldID.size(); i++) {
            //            NodeDao nodeDao = new NodeDao();
            Node currNode = n_toModify_newNode.get(i);
            String oldID = n_toModify_oldID.get(i);
            int oldId_int = Integer.valueOf(oldID);
            nodeDao.updateRow(Integer.valueOf(oldID), currNode);
            mapHistory.addRow(
                new MapHistory(
                    "UPDATE",
                    String.valueOf(currNode.getNodeID()),
                    "node",
                    new Timestamp(System.currentTimeMillis())));
          }
          // Remove loop
          for (String currID : n_toRemove) {
            moveDao.deleteRow(Integer.valueOf(currID));
            nodeDao.deleteRow(Integer.valueOf(currID));
            // mapHistory.addRow(new MapHistory("UPDATE", String.valueOf(currNode.getNodeID()),
            // "node", new Timestamp(System.currentTimeMillis())));
          }
          // a new floor assignment relating to the currently viewed floor
          group.getChildren().remove(mapNodes);
          mapNodes = new Group();
          group.getChildren().add(mapNodes);
          loadDatabase();
          sortNodes();
          placeNodes(floor);
          // Delete node
          stage
              .close(); // no need to close when switching floors bc any new one submitted with have
        });
  }

  public void showNameMenu(ActionEvent event) {
    BorderPane borderPane = new BorderPane();
    HBox hBox = new HBox();

    // modify
    VBox modifyBox = new VBox(); // modify
    Text nodeType = new Text("Input new Node Type");
    Text longName = new Text("Input new Longname");
    Text shortName = new Text("Input new Shortname");
    Text oldName = new Text("Input Longname of Name to be Modified");
    MFXTextField nodeTypeText = new MFXTextField();
    MFXTextField shortName_t = new MFXTextField();
    MFXTextField longName_t = new MFXTextField();
    MFXTextField oldName_t = new MFXTextField();

    MFXButton submitModify = new MFXButton("Modify");
    submitModify.setPrefSize(100, 35);
    submitModify.setMinSize(100, 35);
    modifyBox
        .getChildren()
        .addAll(
            oldName,
            oldName_t,
            nodeType,
            nodeTypeText,
            shortName,
            shortName_t,
            longName,
            longName_t,
            submitModify);
    modifyBox.setSpacing(20);

    // remove
    VBox removeBox = new VBox();
    Text nodeID_R = new Text("Input Longname of name to be Removed");
    Text nodeID_N = new Text("Input NodeID of node with longname to remove");
    MFXTextField nameToBeRemoved = new MFXTextField();
    MFXTextField iDToBeRemoved = new MFXTextField();
    MFXButton submitRemove = new MFXButton("Remove");
    submitRemove.setPrefSize(100, 35);
    submitRemove.setMinSize(100, 35);
    removeBox
        .getChildren()
        .addAll(nodeID_R, nameToBeRemoved, nodeID_N, iDToBeRemoved, submitRemove);
    removeBox.setSpacing(20);

    // add
    VBox addBox = new VBox();
    //      Text XCoordText = new Text("Input X Coordinate");
    //      Text YCoordText = new Text("Input Y Coordinate");
    Text nodeType_t = new Text("Input Node Type");
    Text SName = new Text("Input New Shortname");
    Text LName = new Text("Input New Longname");
    Text nodeID = new Text("Input NodeID of node to add name to");

    //      Text BuildingText = new Text("Input Building Name");
    MFXTextField nodeTypeInput = new MFXTextField();
    MFXTextField sNameInput = new MFXTextField();
    MFXTextField lNameInput = new MFXTextField();
    MFXTextField nodeIDinput = new MFXTextField();
    MFXButton submitNode = new MFXButton("Add");
    submitNode.setId("submitNode");
    //    submitNode.setText("Submit Node");
    submitNode.setPrefSize(100, 35);
    submitNode.setMinSize(100, 35);
    //    inputXCoord.setPrefSize(30, 30);
    //    inputXCoord.setBorderGap(20);
    addBox
        .getChildren()
        .addAll(
            nodeID,
            nodeIDinput,
            nodeType_t,
            nodeTypeInput,
            SName,
            sNameInput,
            LName,
            lNameInput,
            submitNode);
    addBox.setSpacing(20);

    hBox.getChildren().addAll(addBox, modifyBox, removeBox);
    hBox.setSpacing(20);

    MFXButton submitNodeEdits = new MFXButton("Update Table");
    submitNodeEdits.setPrefSize(150, 40);

    VBox masterBox = new VBox();
    masterBox.getChildren().addAll(hBox, submitNodeEdits);
    masterBox.setSpacing(20);
    masterBox.setAlignment(Pos.CENTER);

    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().add(masterBox);
    Insets insets = new Insets(0, 0, 0, 200);
    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    //    borderPane.setAlignment(aPane, Pos.CENTER);

    addBox.relocate(0, 0);
    Scene scene = new Scene(borderPane, 600, 600);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Node Window");

    stage.show();
    //    MoveDao moveDao1 = new MoveDao(); //////////were replaced in remove below
    //    LocationDao locationDao1 = new LocationDao();

    // Add
    submitNode.setOnMouseClicked(
        buttonEvent -> {
          nodeType_temp = nodeTypeInput.getText();
          sNameInput_temp = sNameInput.getText();
          lNameInput_temp = lNameInput.getText();
          nodeIDinput_temp = nodeIDinput.getText();

          LocationName locationName =
              new LocationName(lNameInput_temp, sNameInput_temp, nodeType_temp);

          long currentTime = System.currentTimeMillis();
          Date currentDate = new Date(currentTime);
          Move move = new Move(Integer.valueOf(nodeIDinput_temp), lNameInput_temp, currentDate);

          moveNamesToAdd.add(move);
          newNameToAdd.add(locationName);
          nodeTypeInput.clear();
          sNameInput.clear();
          lNameInput.clear();
          nodeIDinput.clear();

          //          System.out.println("printed the new node");
        });

    // Modify
    submitModify.setOnMouseClicked(
        buttonEvent -> {
          nodeType_temp = nodeTypeText.getText();
          sNameInput_temp = shortName_t.getText();
          lNameInput_temp = longName_t.getText();
          oldName_temp = oldName_t.getText();
          //          System.out.println(nodeType_temp + "   " + nodeType_temp.length());
          if (!(nodeType_temp.length() == 4)) { // Fix later
            nodeType_temp = "HALL";
          }
          LocationName locationName =
              new LocationName(lNameInput_temp, sNameInput_temp, nodeType_temp);

          newNameToModify.add(locationName);
          oldNameToModify.add(oldName_temp);
          nodeTypeText.clear();
          shortName_t.clear();
          longName_t.clear();
          oldName_t.clear();
          //          System.out.println("modified the name");
        });
    // remove
    submitRemove.setOnMouseClicked(
        buttonEvent -> {
          removeName = nameToBeRemoved.getText();
          nameToRemove.add(removeName);
          iD_r = iDToBeRemoved.getText();
          idList_r.add(iD_r);

          nameToBeRemoved.clear();
          //          System.out.println("removed the node");
        });

    submitNodeEdits.setOnMouseClicked(
        buttonEvent -> {
          LocationDao locationDao = new LocationDao();
          MoveDao moveDao = new MoveDao();

          // Add
          for (int i = 0; i < newNameToAdd.size(); i++) {

            LocationName currName = newNameToAdd.get(i);
            Move currMove = moveNamesToAdd.get(i);
            locationDao.addRow(currName);
            moveDao.addRow(currMove);
          }
          // Modify
          for (int i = 0; i < oldNameToModify.size(); i++) {
            String currOldName = oldNameToModify.get(i);
            LocationName currNewName = newNameToModify.get(i);
            locationDao.updateRow(currOldName, currNewName);

            ///// METHOD TO REPLACE NAME OF NODE AND INPUT TO TABLE
          }
          // Remove
          for (int i = 0; i < nameToRemove.size(); i++) {

            long currentTime = System.currentTimeMillis();
            Date currentDate = new Date(currentTime);
            String currName = nameToRemove.get(i);
            String iD = idList_r.get(i);
            Move move = new Move(Integer.valueOf(iD), currName, currentDate);
            moveDao.deleteRow(move);
            locationDao.deleteRow(currName);
            //// METHOD TO FIND NODE IN DAO AND REMOVE IT BASED ON ID
            //                nodeDao.deleteRow(currID); ////NEED TO MAKE WORK WITH NODE ID ONLY AS
            // SUPPLIED
          }
          group.getChildren().remove(mapNodes);
          mapNodes = new Group();
          group.getChildren().add(mapNodes);
          sortNodes();
          placeNodes(floor);
          stage.close();
        });
  }

  private String[] selectedFilePaths = new String[4];

  @FXML
  void getImportMenu(ActionEvent event) {
    selectedFilePaths[0] = null;
    selectedFilePaths[1] = null;
    selectedFilePaths[2] = null;
    selectedFilePaths[3] = null;
    importMenu.setVisible(true);
    exportMenu.setVisible(false);
  }

  @FXML
  void getImportCancel(ActionEvent event) {
    importMenu.setVisible(false);
    selectedFilePaths[0] = null;
    selectedFilePaths[1] = null;
    selectedFilePaths[2] = null;
    selectedFilePaths[3] = null;
  }

  @FXML
  void getImportNodes(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String nodeHeader = "nodeID,xcoord,ycoord,floor,building";
        String importedHeader = br.readLine();
        //        System.out.println(importedHeader);
        // check if file header matches Node header format
        if (importedHeader.equals(nodeHeader)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          selectedFilePaths[0] = filePath;
          // TODO if it does, add file to file list for mass import
          System.out.println("import works");
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match Node header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  void getImportEdges(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String edgeHeader = "startNode,endNode";
        String importedHeader = br.readLine();
        // check if file header matches Edge header format
        if (importedHeader.equals(edgeHeader)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          selectedFilePaths[1] = filePath;
          // TODO if it does, add file to file list for mass import
          System.out.println("import works");
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match Edge header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  void getImportLocationNames(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String locationNameHeader = "longName,shortName,nodeType";
        String importedHeader = br.readLine();
        // check if file header matches Edge header format
        if (importedHeader.equals(locationNameHeader)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          selectedFilePaths[2] = filePath;
          // TODO if it does, add file to file list for mass import
          System.out.println("import works");
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match Location name header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  void getImportMove(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String moveHeader = "nodeID,longName,date";
        String importedHeader = br.readLine();
        // check if file header matches Edge header format
        if (importedHeader.equals(moveHeader)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          // TODO if it does, add file to file list for mass import
          selectedFilePaths[3] = filePath;
          System.out.println("import works");
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match move header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  void getImportSubmit(ActionEvent event) {
    importMenu.setVisible(false);
    String nodesFilePath = selectedFilePaths[0];
    String edgesFilePath = selectedFilePaths[1];
    String moveFilePath = selectedFilePaths[2];
    String locationNamesFilePath = selectedFilePaths[3];
    ImportCSV.importAllCSV(nodesFilePath, edgesFilePath, moveFilePath, locationNamesFilePath);
    selectedFilePaths[0] = null;
    selectedFilePaths[1] = null;
    selectedFilePaths[2] = null;
    selectedFilePaths[3] = null;
  }

  @FXML
  void getExportMenu(ActionEvent event) {
    importMenu.setVisible(false);
    exportMenu.setVisible(true);
  }

  @FXML
  void getExportBack(ActionEvent event) {
    exportMenu.setVisible(false);
  }

  @FXML
  void getExportNodes(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      InodeDao.exportCSV(filePath);
      testText.setText(filePath);
    }
  }

  @FXML
  void getExportEdges(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      IedgeDao.exportCSV(filePath);
      testText.setText(filePath);
    }
  }

  @FXML
  void getExportLocationNames(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      IlocationDao.exportCSV(filePath);
      testText.setText(filePath);
    }
  }

  @FXML
  void getExportMove(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      ImoveDao.exportCSV(filePath);
      testText.setText(filePath);
    }
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  public String getText(ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    inputBox.clear();
    return inputtedText;
  }

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.);
  //  }
  /////////////////////////////////////////////
  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }
  //
  //  @FXML
  //  void getPathfindingPage(ActionEvent event) {
  //    Navigation.navigate(Screen.PATHFINDING_PAGE);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}

/* Notes
 * make it so when you click on a floor the button for that floor is a different color than the rest
 * when in drag mode to modify, have a red x and green check on the screen somewhere to cancel move of node (x) or exit the drag mode (check)
 * still need to paint edges on the map
 * go over with ian what is required for new move component
 *  * idea is to implement another mode on the map to enter move mode and can click on a node and submit a move for it and a date for that move to be implemented
 *  * separate button to enter this mode appears if in admin
 * if stair or elevator, bypass the floor number attribute and print on every floor
 *
 * ***** If modifying or deleting a node that is a stair or elevator, need to modify or delete relating nodes accordingly, check for relating nodes using longname of elevator
 */

// NEED TO MAKE EXCEPTION CASES FOR IF TRYING TO ADD A NAME TO A NODE THAT ALREADY HAS A NAME
