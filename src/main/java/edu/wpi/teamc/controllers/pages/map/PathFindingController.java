package edu.wpi.teamc.controllers.pages.map;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.controllers.pages.map.MapHelpers.TextDirectionsHelper;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.graph.AlgoSingleton;
import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.ToggleSwitch;

public class PathFindingController {
  @FXML MFXButton nextFloor;
  @FXML MFXButton prevFloor;
  @FXML MenuButton algChoice;
  @FXML SearchableComboBox<String> startChoice;
  @FXML SearchableComboBox<String> endChoice;
  @FXML ToggleSwitch locToggle;
  @FXML DatePicker pickDate;
  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton submit;
  @FXML MFXButton floorButton;
  private MFXButton tempSave;
  private final Paint DEFAULT_BG = Paint.valueOf("#bebebe");
  private Group mapNodes = new Group();
  private Group edges = new Group();
  private Group mapText = new Group();
  private String floor = "1";
  private List<Move> moveList = new ArrayList<Move>();
  private List<Node> Floor1 = new ArrayList<Node>();
  private List<Node> Floor2 = new ArrayList<Node>();
  private List<Node> Floor3 = new ArrayList<Node>();
  private List<Node> FloorL1 = new ArrayList<Node>();
  private List<Node> FloorL2 = new ArrayList<Node>();
  private List<Node> nodeList = new ArrayList<Node>();
  private List<LocationName> locationNameList = new ArrayList<LocationName>();
  private HashMap<Integer, Move> nodeIDtoMove = new HashMap<Integer, Move>();
  private HashMap<String, LocationName> longNameToLocationName = new HashMap<>();
  private LinkedList<List<GraphNode>> splitPath = new LinkedList<>();
  private int pathLoc = 0;
  private GraphNode src;
  private GraphNode dest;
  private boolean toggleStatus;
  public Group group;
  public Image image =
      new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());

  public PathFindingController() throws IOException {}

  /** Method run when controller is initialized */
  public void initialize() {
    submit.setDisable(true);
    tempSave = FL1;
    Image image = this.image;
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);
    group.getChildren().add(mapNodes);
    StackPane pane = new StackPane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);

    Point2D centrePoint = new Point2D(1100, 400);
    mapGPane.centreOn(centrePoint);
    mapGPane.zoomTo(0.4, mapGPane.targetPointAtViewportCentre());

    group.getChildren().add(pane);

    loadDatabase();
    sortNodes();
    addLocationsToSelect();

    nextFloor.setDisable(true);
    prevFloor.setDisable(true);
    toggleStatus = false;
  }

  public void sortNodes() {
    Floor1.clear();
    Floor2.clear();
    Floor3.clear();
    FloorL1.clear();
    FloorL2.clear();
    for (Node node : nodeList) {
      if (node.getFloor().equals("1")) {
        Floor1.add(node);
      } else if (node.getFloor().equals("2")) {
        Floor2.add(node);
      } else if (node.getFloor().equals("3")) {
        Floor3.add(node);
      } else if (node.getFloor().equals("L1")) {
        FloorL1.add(node);
      } else if (node.getFloor().equals("L2")) {
        FloorL2.add(node);
      }
    }
  }

  // load database
  public void loadDatabase() {
    nodeList = new NodeDao().fetchAllObjects();
    locationNameList = new LocationNameDao().fetchAllObjects();
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
      longNameToLocationName.put(locationName.getLongName(), locationName);
    }
    longNameToLocationName.put("ERROR", new LocationName("ERROR", "ERROR", "ERROR"));
  }

  public void addLocationsToSelect() {
    ObservableList<String> locNames = FXCollections.observableArrayList();
    Pattern pattern = Pattern.compile("\\bhallway\\b|\\bhall\\b", Pattern.CASE_INSENSITIVE);
    Matcher matcher;

    for (Move move : moveList) {
      matcher = pattern.matcher(move.getLongName());

      if (!matcher.find()) {
        if (!locNames.contains(move.getLongName())) {
          locNames.add(move.getLongName());
        }
      }
    }

    Collections.sort(locNames);
    startChoice.setItems(locNames);
    endChoice.setItems(locNames);
  }

  public void resetGroupVar() {
    group.getChildren().clear();
    group.getChildren().remove(mapNodes);
    group.getChildren().remove(edges);
    group.getChildren().remove(mapText);
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    edges = new Group();
    mapText = new Group();
    group.getChildren().add(imageView);
    group.getChildren().add(edges);
    group.getChildren().add(mapNodes);
    group.getChildren().add(mapText);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
  }

  public void placeText(String floor) {
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
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
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
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
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
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(Floor3.get(i), shortName, nodeType);
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
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(FloorL2.get(i), shortName, nodeType);
        }
    }
    mapNodes.toFront();
    mapText.toFront();
  }

  public void changeFloor(ActionEvent event) throws IOException {
    floorButton = (MFXButton) event.getTarget();

    if (Objects.equals(floorButton.getId(), "FL1")) {
      image = new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());
      floor = "1";
    } else if (Objects.equals(floorButton.getId(), "FL2")) {
      image = new Image(Main.class.getResource("views/images/SecondFloor.png").openStream());
      floor = "2";
    } else if (Objects.equals(floorButton.getId(), "FL3")) {
      image = new Image(Main.class.getResource("views/images/ThirdFloor.png").openStream());
      floor = "3";
    } else if (Objects.equals(floorButton.getId(), "FLB1")) {
      image = new Image(Main.class.getResource("views/images/B1.png").openStream());
      floor = "L1";
    } else if (Objects.equals(floorButton.getId(), "FLB2")) {
      image = new Image(Main.class.getResource("views/images/B2.png").openStream());
      floor = "L2";
    }
    resetGroupVar();

    if (toggleStatus) {
      placeText(floor);
    }
  }

  public void resetAndSetFloorIndicator(MFXButton button) {
    button.setBackground(Background.fill(Paint.valueOf("#32CD32")));
    tempSave.setBackground(Background.fill(DEFAULT_BG));
    tempSave = button;
  }

  public void changeFloorFromString(String floor) throws IOException {
    if (floor.equals("1")) {
      image = new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());
      this.floor = "1";
      resetAndSetFloorIndicator(FL1);
    } else if (floor.equals("2")) {
      image = new Image(Main.class.getResource("views/images/SecondFloor.png").openStream());
      this.floor = "2";
      resetAndSetFloorIndicator(FL2);
    } else if (floor.equals("3")) {
      image = new Image(Main.class.getResource("views/images/ThirdFloor.png").openStream());
      this.floor = "3";
      resetAndSetFloorIndicator(FL3);
    } else if (floor.equals("L1")) {
      image = new Image(Main.class.getResource("views/images/B1.png").openStream());
      this.floor = "L1";
      resetAndSetFloorIndicator(FLB1);
    } else if (floor.equals("L2")) {
      image = new Image(Main.class.getResource("views/images/B2.png").openStream());
      this.floor = "L2";
      resetAndSetFloorIndicator(FLB2);
    }
    resetGroupVar();

    if (toggleStatus) {
      placeText(floor);
    }
  }

  public void createMapNodes(Node node, String shortname, String nodeType) {
    Text text = new Text();

    if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
      text = new Text(shortname);
      text.setX(node.getXCoord() + 5);
      text.setY(node.getYCoord() - 5);
      text.setVisible(true);
      mapText.getChildren().add(text);
    }
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

  public void placeDestCircle(GraphNode node) {
    Circle circ = new Circle();
    circ.setCenterX(node.getXCoord());
    circ.setCenterY(node.getYCoord());
    circ.setRadius(15);
    circ.setFill(Paint.valueOf("#4CAF50"));
    circ.setStroke(Paint.valueOf("#021335"));
    circ.setVisible(true);
    mapNodes.getChildren().add(circ);
  }

  public void placeSrcCircle(GraphNode node) {
    Circle circ2 = new Circle();
    circ2.setCenterX(node.getXCoord());
    circ2.setCenterY(node.getYCoord());
    circ2.setRadius(15);
    circ2.setFill(Paint.valueOf("#021335"));
    circ2.setStroke(Paint.valueOf("#021335"));
    circ2.setVisible(true);
    mapNodes.getChildren().add(circ2);

    Point2D centrePoint = new Point2D(node.getXCoord(), node.getYCoord());
    mapGPane.centreOn(centrePoint);
    mapGPane.zoomTo(0.4, mapGPane.targetPointAtViewportCentre());
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
      temp.setStroke(Paint.valueOf("021335"));
      edges.getChildren().add(temp);
    }

    placeSrcCircle(splitPath.get(pathLoc).get(0));
    placeDestCircle(splitPath.get(pathLoc).get(splitPath.get(pathLoc).size() - 1));
  }

  @FXML
  void getSubmit(ActionEvent event) throws IOException {
    nextFloor.setDisable(false);
    prevFloor.setDisable(true);
    edges.getChildren().clear();
    mapNodes.getChildren().clear();

    String startName = startChoice.getValue();
    String endName = endChoice.getValue();
    LocalDate date = pickDate.getValue();

    if (date == null) {
      date = LocalDate.now();
    }

    String dateString = date.toString();
    Graph graph = new Graph(AlgoSingleton.INSTANCE.getType());
    graph.syncWithDB(dateString);

    int srcN = graph.getNodeIDfromLongName(startName);
    int destN = graph.getNodeIDfromLongName(endName);

    src = graph.getNode(srcN);
    dest = graph.getNode(destN);
    changeFloorFromString(src.getFloor());

    List<GraphNode> path = graph.getPathway(src, dest);
    breakPathIntoFloors(path);
    drawEdges();

    if (splitPath.size() == 1) {
      nextFloor.setDisable(true);
      prevFloor.setDisable(true);
    }

    edges.toFront();

    TextDirectionsHelper textHelper = new TextDirectionsHelper();
    LinkedList<String> textDirections = textHelper.textDirections(path);

    for (String s : textDirections) {
      System.out.println(s);
    }
  }

  @FXML
  void getNextFloor(ActionEvent event) throws IOException {
    pathLoc++;
    edges.getChildren().clear();

    if (splitPath.get(pathLoc).size() > 1) {
      changeFloorFromString(splitPath.get(pathLoc).get(1).getFloor());
    } else {
      changeFloorFromString(splitPath.get(pathLoc).get(0).getFloor());
    }
    prevFloor.setDisable(false);

    if (pathLoc == splitPath.size() - 1) {
      nextFloor.setDisable(true);
    }

    drawEdges();
    edges.toFront();
  }

  @FXML
  void getPrevFloor(ActionEvent event) throws IOException {
    nextFloor.setDisable(false);
    pathLoc--;
    edges.getChildren().clear();

    if (splitPath.get(pathLoc).size() > 1) {
      changeFloorFromString(splitPath.get(pathLoc).get(1).getFloor());
    } else {
      changeFloorFromString(splitPath.get(pathLoc).get(0).getFloor());
    }

    if (pathLoc == 0) {
      prevFloor.setDisable(true);
    }

    drawEdges();
    edges.toFront();
  }

  @FXML
  void getChoiceAStar(ActionEvent event) {
    algChoice.setText("A*");
    AlgoSingleton.INSTANCE.setType(algChoice.getText());
    activateSubmit();
  }

  @FXML
  void getChoiceBFS(ActionEvent event) {
    algChoice.setText("BFS");
    AlgoSingleton.INSTANCE.setType(algChoice.getText());
    activateSubmit();
  }

  @FXML
  void getChoiceDFS(ActionEvent event) {
    algChoice.setText("DFS");
    AlgoSingleton.INSTANCE.setType(algChoice.getText());
    activateSubmit();
  }

  @FXML
  void getChoiceDijkstra(ActionEvent event) {
    algChoice.setText("Dijkstra");
    AlgoSingleton.INSTANCE.setType(algChoice.getText());
    activateSubmit();
  }

  @FXML
  void doLocToggle() {
    toggleStatus = !toggleStatus;

    if (toggleStatus) {
      group.getChildren().remove(mapText);
      group.getChildren().add(mapText);
      placeText(floor);
    } else {
      group.getChildren().remove(mapText);
      mapText = new Group();
    }
  }

  @FXML
  void getStartChoice(ActionEvent event) {}

  @FXML
  void getEndChoice(ActionEvent event) {}

  void activateSubmit() {
    submit.setDisable(false);
  }
}
