package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.graph.AlgoSingleton;
import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphNode;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javax.swing.*;
import net.kurobako.gesturefx.GesturePane;

public class PathFindingController {
  public Group group;
  public Image image =
      new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
  @FXML MFXButton backButton;
  @FXML MFXButton nextFloor;
  @FXML MFXButton prevFloor;
  @FXML MenuButton algChoice;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLG;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton floorButton;
  Group mapNodes = new Group();
  Group edges = new Group();
  @FXML MFXTextField startNodeId;
  @FXML MFXTextField endNodeId;
  @FXML MFXButton submit;
  @FXML private Button goHome;
  String floor = "G";
  List<Move> moveList = new ArrayList<Move>();
  List<Node> Floor1 = new ArrayList<Node>();
  List<Node> Floor2 = new ArrayList<Node>();
  List<Node> Floor3 = new ArrayList<Node>();
  List<Node> FloorG = new ArrayList<Node>();
  List<Node> FloorL1 = new ArrayList<Node>();
  List<Node> FloorL2 = new ArrayList<Node>();
  List<Node> nodeList = new ArrayList<Node>();
  List<Edge> edgeList = new ArrayList<Edge>();
  List<LocationName> locationNameList = new ArrayList<LocationName>();
  HashMap<Integer, Move> nodeIDtoMove = new HashMap<Integer, Move>();
  HashMap<String, LocationName> longNametoLocationName = new HashMap<String, LocationName>();
  private LinkedList<List<GraphNode>> splitPath = new LinkedList<>();
  private int pathLoc = 0;
  private GraphNode src;
  private GraphNode dest;

  /** Method run when controller is initialized */
  public void initialize() {

    //    File file = new File();
    Image image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);
    group.getChildren().add(mapNodes);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);

    loadDatabase();
    sortNodes();
    // placeNodes("G");
  }

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

  public void resetGroupVar() {
    group.getChildren().clear();
    group.getChildren().remove(mapNodes);
    group.getChildren().remove(edges);
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    edges = new Group();
    group.getChildren().add(imageView);
    group.getChildren().add(edges);
    group.getChildren().add(mapNodes);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
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
    } else if (Objects.equals(floorButton.getId(), "FLG")) {
      image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
      floor = "G";
    } else if (Objects.equals(floorButton.getId(), "FLB1")) {
      image = new Image(Main.class.getResource("./views/Images/B1.png").toString());
      floor = "L1";
    } else if (Objects.equals(floorButton.getId(), "FLB2")) {
      image = new Image(Main.class.getResource("./views/Images/B2.png").toString());
      floor = "L2";
    }
    resetGroupVar();
    // placeNodes(floor);
  }

  public void changeFloorFromString(String floor) {
    if (floor.equals("1")) {
      image = new Image(Main.class.getResource("./views/Images/FirstFloor.png").toString());
    } else if (floor.equals("2")) {
      image = new Image(Main.class.getResource("./views/Images/SecondFloor.png").toString());
    } else if (floor.equals("3")) {
      image = new Image(Main.class.getResource("./views/Images/ThirdFloor.png").toString());
    } else if (floor.equals("G")) {
      image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
    } else if (floor.equals("L1")) {
      image = new Image(Main.class.getResource("./views/Images/B1.png").toString());
    } else if (floor.equals("L2")) {
      image = new Image(Main.class.getResource("./views/Images/B2.png").toString());
    }
    resetGroupVar();
    // placeNodes(floor);
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor1.get(i), shortName, nodeType);
        }
        break;
      case "2":
        for (int i = 0; i < Floor2.size(); i++) {
          int nodeID = Floor2.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor2.get(i), shortName, nodeType);
        }
        break;
      case "3":
        for (int i = 0; i < Floor3.size(); i++) {
          int nodeID = Floor3.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(Floor3.get(i), shortName, nodeType);
        }
        break;
      case "G":
        for (int i = 0; i < FloorG.size(); i++) {
          int nodeID = FloorG.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorG.get(i), shortName, nodeType);
        }
        break;
      case "L1":
        for (int i = 0; i < FloorL1.size(); i++) {
          int nodeID = FloorL1.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorL1.get(i), shortName, nodeType);
        }
        break;
      case "L2":
        for (int i = 0; i < FloorL2.size(); i++) {
          int nodeID = FloorL2.get(i).getNodeID();
          String longName;
          try {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } catch (NullPointerException e) {
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new java.sql.Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorL2.get(i), shortName, nodeType);
        }
    }
    mapNodes.toFront();
  }

  public void createMapNodes(Node node, String shortname, String nodeType) {
    Circle newCircle = new Circle();
    if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
      Tooltip nodeName = new Tooltip(shortname);
      nodeName.setShowDelay(Duration.ZERO);
      nodeName.setShowDuration(Duration.hours(2));
      Tooltip.install(newCircle, nodeName);
    }
    newCircle.setRadius(10);
    newCircle.setCenterX(node.getXCoord());
    newCircle.setCenterY(node.getYCoord());
    newCircle.setId(String.valueOf(node.getNodeID()));
    newCircle.setStroke(Paint.valueOf("#000000"));
    newCircle.setFill(Paint.valueOf("#000000"));
    newCircle.setVisible(true);
    mapNodes.getChildren().add(newCircle);
  }

  public void breakPathIntoFloors(List<GraphNode> path) {
    int index;
    int pathSize = path.size();
    splitPath.clear();
    pathLoc = 0;

    for (int i = 0; i < pathSize - 1; i++) {
      index = i;

      while (i < pathSize - 1 && path.get(index).getFloor().equals(path.get(i + 1).getFloor())) {
        i++;
      }

      splitPath.add(path.subList(index, i + 1));
    }

    // edge case where the second to last location in path is traveling through an elevator
    if (!path.get(pathSize - 1).getFloor().equals(path.get(pathSize - 2).getFloor())) {
      splitPath.add(path.subList(pathSize - 2, pathSize));
    }
  }

  public void placeDestCircle() {
    Circle circ = new Circle();
    circ.setCenterX(dest.getXCoord());
    circ.setCenterY(dest.getYCoord());
    circ.setRadius(20);
    circ.setFill(Paint.valueOf("#32CD32"));
    circ.setStroke(Paint.valueOf("#FF0000"));
    circ.setVisible(true);
    mapNodes.getChildren().add(circ);
  }

  public void placeSrcCircle() {
    Circle circ2 = new Circle();
    circ2.setCenterX(src.getXCoord());
    circ2.setCenterY(src.getYCoord());
    circ2.setRadius(20);
    circ2.setFill(Paint.valueOf("#FF0000"));
    circ2.setStroke(Paint.valueOf("#32CD32"));
    circ2.setVisible(true);
    mapNodes.getChildren().add(circ2);
  }

  public void drawEdges() {
    for (int i = 0; i < splitPath.get(pathLoc).size() - 1; i++) {
      Line temp =
          new Line(
              splitPath.get(pathLoc).get(i).getXCoord(),
              splitPath.get(pathLoc).get(i).getYCoord(),
              splitPath.get(pathLoc).get(i + 1).getXCoord(),
              splitPath.get(pathLoc).get(i + 1).getYCoord());
      temp.setStrokeWidth(12);
      temp.setVisible(true);
      temp.setStroke(Paint.valueOf("FF0000"));
      edges.getChildren().add(temp);
    }
  }

  @FXML
  void getSubmit(ActionEvent event) {
    nextFloor.setDisable(false);
    prevFloor.setDisable(true);
    edges.getChildren().clear();
    mapNodes.getChildren().clear();
    String startNode = startNodeId.getText();
    String endNode = endNodeId.getText();

    // TODO : add drop down algo selection
    AlgoSingleton.INSTANCE.setType(algChoice.getText());
    Graph graph = new Graph(AlgoSingleton.INSTANCE.getType());
    graph.syncWithDB();

    src = graph.getNode(Integer.valueOf(startNode));
    dest = graph.getNode(Integer.valueOf(endNode));
    changeFloorFromString(src.getFloor());

    List<GraphNode> path = graph.getPathway(src, dest);
    breakPathIntoFloors(path);
    // TODO : remember that if a broken up path has a list of size 1, just considerate it an
    // stair/elevator

    placeSrcCircle();

    for (int i = 0; i < splitPath.get(0).size() - 1; i++) {
      Line temp =
          new Line(
              splitPath.get(0).get(i).getXCoord(),
              splitPath.get(0).get(i).getYCoord(),
              splitPath.get(0).get(i + 1).getXCoord(),
              splitPath.get(0).get(i + 1).getYCoord());
      temp.setStrokeWidth(12);
      temp.setVisible(true);
      temp.setStroke(Paint.valueOf("FF0000"));
      edges.getChildren().add(temp);
    }

    if (splitPath.size() == 1) {
      placeDestCircle();
      nextFloor.setDisable(true);
      prevFloor.setDisable(true);
    }

    edges.toFront();
  }

  @FXML
  void getNextFloor(ActionEvent event) {
    pathLoc++;
    edges.getChildren().clear();
    changeFloorFromString(splitPath.get(pathLoc).get(1).getFloor());
    prevFloor.setDisable(false);

    if (pathLoc == splitPath.size() - 1) {
      placeDestCircle();
      nextFloor.setDisable(true);
    }

    drawEdges();
    edges.toFront();
  }

  @FXML
  void getPrevFloor(ActionEvent event) {
    nextFloor.setDisable(false);
    pathLoc--;

    edges.getChildren().clear();
    changeFloorFromString(splitPath.get(pathLoc).get(1).getFloor());

    if (pathLoc == 0) {
      placeSrcCircle();
      prevFloor.setDisable(true);
    }

    drawEdges();
    edges.toFront();
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
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getChoiceAStar(ActionEvent event) {
    algChoice.setText("A*");
  }

  @FXML
  void getChoiceBFS(ActionEvent event) {
    algChoice.setText("BFS");
  }

  @FXML
  void getChoiceDFS(ActionEvent event) {
    algChoice.setText("DFS");
  }
}
