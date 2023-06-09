package edu.wpi.teamc.controllers.pages.map;

import com.fazecast.jSerialComm.SerialPort;
import edu.wpi.teamc.Main;
import edu.wpi.teamc.controllers.pages.map.MapHelpers.ArrowHelper;
import edu.wpi.teamc.controllers.pages.map.MapHelpers.TextDirectionsHelper;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.graph.AlgoSingleton;
import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
  @FXML ToggleSwitch stairToggle;
  @FXML DatePicker pickDate;
  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton submit;
  @FXML MFXButton textDir;
  @FXML MFXButton qrCode;
  @FXML MFXButton sendTextDir;
  @FXML MFXButton floorButton;
  //  @FXML MFXTextField message;
  @FXML MFXButton robotButton;
  int numNodesSent = 0;
  int totalNodesSent = 0;
  @FXML TextArea message;
  private MFXButton tempSave;
  private static final Paint DEFAULT_BG = Paint.valueOf("#bebebe");
  private static final int SIZE_FACTOR = 18;
  private Group mapNodes = new Group();
  private Group edges = new Group();
  private Group mapText = new Group();
  private String floor = "1";
  private List<Move> moveList = new ArrayList<>();
  private final List<Node> Floor1 = new ArrayList<>();
  private final List<Node> Floor2 = new ArrayList<>();
  private final List<Node> Floor3 = new ArrayList<>();
  private final List<Node> FloorL1 = new ArrayList<>();
  private final List<Node> FloorL2 = new ArrayList<>();
  private List<Node> nodeList = new ArrayList<>();
  private final HashMap<Integer, Move> nodeIDtoMove = new HashMap<>();
  private final HashMap<String, LocationName> longNameToLocationName = new HashMap<>();
  private final LinkedList<List<GraphNode>> splitPath = new LinkedList<>();
  private int pathLoc = 0;
  private GraphNode src;
  private GraphNode dest;
  private boolean toggleStatus;
  private boolean stairStatus;
  public Group group;
  public Image image =
      new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());
  private Graph graph;
  private List<GraphNode> path;

  public PathFindingController() throws IOException {}

  /** Method run when controller is initialized */
  public void initialize() {
    submit.setDisable(true);
    textDir.setDisable(true);
    qrCode.setDisable(true);
    robotButton.setDisable(true);
    message.setEditable(true);
    startChoice.setValue("75 Lobby");
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
    robotButton.setOnAction(
        event -> {
          sendToRobotMethod4(path);
        });

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
    stairStatus = false;
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
    List<LocationName> locationNameList = new LocationNameDao().fetchAllObjects();
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
  }

  public void addLocationsToSelect() {
    ObservableList<String> locNames = FXCollections.observableArrayList();
    Pattern pattern = Pattern.compile("\\bhallway\\b|\\bhall\\b", Pattern.CASE_INSENSITIVE);
    Matcher matcher;

    for (Move move : moveList) {
      String longName = move.getLongName();
      matcher = pattern.matcher(longName);

      if (!matcher.find()) {
        if (!locNames.contains(longName)) {
          locNames.add(longName);
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
      case "1" -> {
        for (int i = 0; i < Floor1.size(); i++) {
          int nodeID = Floor1.get(i).getNodeID();
          String longName;
          if (graph == null) {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } else {
            longName = graph.getLongNameFromNodeID(nodeID);
          }
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(Floor1.get(i), shortName, nodeType);
        }
      }
      case "2" -> {
        for (int i = 0; i < Floor2.size(); i++) {
          int nodeID = Floor2.get(i).getNodeID();
          String longName;
          if (graph == null) {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } else {
            longName = graph.getLongNameFromNodeID(nodeID);
          }
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(Floor2.get(i), shortName, nodeType);
        }
      }
      case "3" -> {
        for (int i = 0; i < Floor3.size(); i++) {
          int nodeID = Floor3.get(i).getNodeID();
          String longName;
          if (graph == null) {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } else {
            longName = graph.getLongNameFromNodeID(nodeID);
          }
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(Floor3.get(i), shortName, nodeType);
        }
      }
      case "L1" -> {
        for (int i = 0; i < FloorL1.size(); i++) {
          int nodeID = FloorL1.get(i).getNodeID();
          String longName;
          if (graph == null) {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } else {
            longName = graph.getLongNameFromNodeID(nodeID);
          }
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(FloorL1.get(i), shortName, nodeType);
        }
      }
      case "L2" -> {
        for (int i = 0; i < FloorL2.size(); i++) {
          int nodeID = FloorL2.get(i).getNodeID();
          String longName;
          if (graph == null) {
            longName = nodeIDtoMove.get(nodeID).getLongName();
          } else {
            longName = graph.getLongNameFromNodeID(nodeID);
          }
          String shortName = longNameToLocationName.get(longName).getShortName();
          String nodeType = longNameToLocationName.get(longName).getNodeType();
          createMapNodes(FloorL2.get(i), shortName, nodeType);
        }
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
    button.setBackground(Background.fill(Paint.valueOf("#EAB334")));
    if (tempSave != null) {
      tempSave.setBackground(Background.fill(DEFAULT_BG));
    }
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

  public void placeDestCircle(GraphNode node, String paintVal) {
    Circle circ = new Circle();
    circ.setCenterX(node.getXCoord());
    circ.setCenterY(node.getYCoord());
    circ.setRadius(15);
    circ.setFill(Paint.valueOf(paintVal));
    circ.setStroke(Paint.valueOf("#021335"));
    circ.setVisible(true);

    mapNodes.getChildren().add(circ);
  }

  public void placeStartShape(GraphNode node) {
    int x = node.getXCoord();
    int y = node.getYCoord();

    Path p = new Path();

    MoveTo moveTo = new MoveTo(x - SIZE_FACTOR, y);
    LineTo line1 = new LineTo(x, y - SIZE_FACTOR);
    LineTo line2 = new LineTo(x + SIZE_FACTOR, y);
    LineTo line3 = new LineTo(x, y + SIZE_FACTOR);
    LineTo line4 = new LineTo(x - SIZE_FACTOR, y);

    p.getElements().add(moveTo);
    p.getElements().addAll(line1, line2, line3, line4);
    p.setFill(Paint.valueOf("#EAB334"));
    p.setVisible(true);
    mapNodes.getChildren().add(p);
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

  public void placeArrow(GraphNode node, GraphNode nextNode) {
    ArrowHelper arr = new ArrowHelper();
    Group temp = arr.drawArrow(node, nextNode);
    mapNodes.getChildren().add(temp);
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

    if (pathLoc != 0) {
      placeSrcCircle(splitPath.get(pathLoc).get(0));
    } else {
      placeStartShape(splitPath.get(pathLoc).get(0));
    }

    GraphNode destNode = splitPath.get(pathLoc).get(splitPath.get(pathLoc).size() - 1);
    if (!destNode.equals(dest)) {
      placeDestCircle(destNode, "#EAB334");

      GraphNode nextNode = splitPath.get(pathLoc + 1).get(splitPath.get(pathLoc + 1).size() - 1);
      placeArrow(destNode, nextNode);
    } else {
      placeDestCircle(destNode, "#4CAF50");
    }
  }

  @FXML
  void getSubmit(ActionEvent event) throws IOException {
    message.setText("");
    nextFloor.setDisable(false);
    textDir.setDisable(false);
    robotButton.setDisable(false);
    qrCode.setDisable(false);
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
    graph = new Graph(AlgoSingleton.INSTANCE.getType(), stairStatus);
    graph.syncWithDB(dateString);

    int srcN = graph.getNodeIDfromLongName(startName);
    int destN = graph.getNodeIDfromLongName(endName);

    src = graph.getNode(srcN);
    dest = graph.getNode(destN);
    changeFloorFromString(src.getFloor());

    path = graph.getPathway(src, dest);
    breakPathIntoFloors(path);
    drawEdges();

    if (splitPath.size() == 1) {
      nextFloor.setDisable(true);
      prevFloor.setDisable(true);
    }

    String combo = "";
    String s = graph.checkRecentMoves(true, srcN, date);
    String s2 = graph.checkRecentMoves(false, destN, date);

    if (!s.isEmpty()) {
      combo += s + "\n";
    }
    if (!s2.isEmpty()) {
      combo += s2 + "\n";
    }

    String s3 = graph.checkUpcomingMoves(startName, true, srcN, date);
    String s4 = graph.checkUpcomingMoves(endName, false, destN, date);

    if (!s3.isEmpty()) {
      combo += s3 + "\n";
    }
    if (!s4.isEmpty()) {
      combo += s4 + "\n";
    }

    message.setFont(new Font(24));
    message.setText(combo);
    mapNodes.toFront();
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
    mapNodes.toFront();
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
    mapNodes.toFront();
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
  void doStairToggle() {
    stairStatus = !stairStatus;
  }

  @FXML
  void getStartChoice(ActionEvent event) {}

  @FXML
  void getEndChoice(ActionEvent event) {}

  @FXML
  void getQR(ActionEvent event) {
    TextDirectionsHelper textHelper = new TextDirectionsHelper();
    LocalDate date = pickDate.getValue();
    if (date == null) {
      date = LocalDate.now();
    }
    Image tempImage = SwingFXUtils.toFXImage(textHelper.buildImage(path, graph, date), null);

    BorderPane borderPane = new BorderPane();
    VBox vbox = new VBox();
    ImageView view = new ImageView();
    view.setImage(tempImage);

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);

    vbox.getChildren().add(view);

    // Set and show screen

    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 390, 390);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    borderPane.getStyleClass().add("scenePane");
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Scan to View Text Directions");
    stage.show();
    stage.setAlwaysOnTop(true);
  }

  @FXML
  void getTextDirections(ActionEvent event) {
    String fullPath = "";
    BorderPane borderPane = new BorderPane();
    VBox vbox = new VBox();

    TextDirectionsHelper textHelper = new TextDirectionsHelper();
    LinkedList<String> directions = textHelper.textDirections(path, graph);

    for (String s : directions) {
      if (!s.startsWith("D")) {
        String[] split = s.split("~");

        if (split[1].startsWith("Go s") || split[1].startsWith("?") || split[1].startsWith("?")) {
          fullPath += split[1] + ": To ";
        } else {
          fullPath += split[1] + ": At ";
        }

        fullPath += split[2] + "; Distance: " + split[0] + "ft\n";
      } else {
        fullPath += "\n" + s + ":\n\n";
      }
    }

    TextArea textField = new TextArea();
    textField.setMinHeight(300);
    textField.setMinWidth(150);
    textField.setEditable(false);
    textField.setFont(new Font(18));
    textField.setText(fullPath);

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);
    // MFXButton textButton = new MFXButton("Get Directions through Text");
    // textButton.setLayoutX(lay_x);
    // textButton.setLayoutX(lay_y);
    vbox.getChildren().add(textField);
    // Set and show screen

    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    // Button phoneB = new Button("Send Directions to My Phone Number");
    // aPane.getChildren().add(phoneB);
    Scene scene = new Scene(borderPane, 800, 390);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    borderPane.getStyleClass().add("scenePane");
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Text Directions");
    stage.setAlwaysOnTop(true);
    stage.show();

    // SMSHelper.sendSMS("");
  }

  public void sendToRobot(List<GraphNode> currentPath) {
    SerialPort[] ports = SerialPort.getCommPorts();
    //        List<Node> currentPath = new ArrayList<Node>();
    System.out.println(Arrays.toString(ports));
    System.out.println("Got ports");

    if (ports.length != 0) {
      System.out.println(ports[0]);
      ports[2].setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
      ports[2].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 16); // Blocking write

      if (ports[2].openPort(16)) {
        int numMessages = currentPath.size();
        byte[] bytes = {
          (byte) (numMessages / 1000 % 10),
          (byte) (numMessages / 100 % 10),
          (byte) (numMessages / 10 % 10),
          (byte) (numMessages % 10),
          10
        };
        ports[2].writeBytes(bytes, bytes.length);
        System.out.println(numMessages + " numMessages sent");
      }

      for (GraphNode node : currentPath) {
        System.out.println(node.getNodeID());
        if (ports[2].isOpen() || ports[2].openPort(16)) {
          //            System.out.println("Port opened successfully");
          int x = node.getXCoord();
          int y = node.getYCoord();
          byte[] bytes = {
            (byte) (x / 1000 % 10), (byte) (x / 100 % 10), (byte) (x / 10 % 10), (byte) (x % 10), 10
          };
          ports[2].writeBytes(bytes, bytes.length);
          // not sending the the following byte, only sending the numMessages and x
          byte[] bytes2 = {
            (byte) (y / 1000 % 10), (byte) (y / 100 % 10), (byte) (y / 10 % 10), (byte) (y % 10), 10
          };
          ports[2].writeBytes(bytes2, bytes2.length);
          //          ports[2].readBytes(bytes, 2);
          System.out.println(bytes.toString() + "sent");
          System.out.println(bytes2.toString() + "sent");
        } else {
          System.out.println("Failed to open port");
          System.out.println(ports[2].getLastErrorCode());
        }
      }

      ports[2].closePort();
      System.out.println("Port closed");
    }
  }

  public void sendToRobotMethod2(List<GraphNode> currentPath) {
    SerialPort[] ports = SerialPort.getCommPorts();
    //        List<Node> currentPath = new ArrayList<Node>();
    System.out.println(Arrays.toString(ports));
    System.out.println("Got ports");

    if (ports.length != 0) {
      //      System.out.println(ports[2]);
      ports[2].setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
      ports[2].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 16); // Blocking write

      //      if (ports[2].openPort(16)) {
      //        int numMessages = currentPath.size();
      //        byte[] bytes = {
      //                (byte) (numMessages / 1000 % 10),
      //                (byte) (numMessages / 100 % 10),
      //                (byte) (numMessages / 10 % 10),
      //                (byte) (numMessages % 10),
      //                10
      //        };
      //        ports[2].writeBytes(bytes, bytes.length);
      //        System.out.println(numMessages + " numMessages sent");
      //      }
      ports[2].openPort(16);

      while (numNodesSent < 1 && totalNodesSent < currentPath.size()) {
        System.out.println(currentPath.get(totalNodesSent).getNodeID());
        if (ports[2].isOpen() || ports[2].openPort(16)) {
          System.out.println("Port opened successfully");
          int x = currentPath.get(totalNodesSent).getXCoord();
          System.out.println("x coord: " + x);
          int y = currentPath.get(totalNodesSent).getYCoord();
          System.out.println("y coord: " + y);
          byte[] bytes = {
            (byte) (x / 1000 % 10), (byte) (x / 100 % 10), (byte) (x / 10 % 10), (byte) (x % 10), 10
          };
          ports[2].writeBytes(bytes, bytes.length);
          // not sending the the following byte, only sending the numMessages and x
          byte[] bytes2 = {
            (byte) (y / 1000 % 10), (byte) (y / 100 % 10), (byte) (y / 10 % 10), (byte) (y % 10), 10
          };
          ports[2].writeBytes(bytes2, bytes2.length);
          System.out.println(bytes.toString() + "sent");
          System.out.println(bytes2.toString() + "sent");
        } else {
          System.out.println("Failed to open port");
          System.out.println(ports[2].getLastErrorCode());
        }

        ports[2].closePort();
        System.out.println("Port closed");
        numNodesSent++;
        totalNodesSent++;
      }
      numNodesSent = 0;
      if (totalNodesSent >= currentPath.size()) {
        System.out.println("Robot has reached the final node in the path");
        totalNodesSent = 0;
      }

      //      for (GraphNode node : currentPath) {
      //        System.out.println(node.getNodeID());
      //        if (ports[2].isOpen() || ports[2].openPort(16)) {
      //          //            System.out.println("Port opened successfully");
      //          int x = node.getXCoord();
      //          int y = node.getYCoord();
      //          byte[] bytes = {
      //                  (byte) (x / 1000 % 10), (byte) (x / 100 % 10), (byte) (x / 10 % 10),
      // (byte) (x % 10), 10
      //          };
      //          ports[2].writeBytes(bytes, bytes.length);
      //          // not sending the the following byte, only sending the numMessages and x
      //          byte[] bytes2 = {
      //                  (byte) (y / 1000 % 10), (byte) (y / 100 % 10), (byte) (y / 10 % 10),
      // (byte) (y % 10), 10
      //          };
      //          ports[2].writeBytes(bytes2, bytes2.length);
      //          System.out.println(bytes.toString() + "sent");
      //          System.out.println(bytes2.toString() + "sent");
      //        } else {
      //          System.out.println("Failed to open port");
      //          System.out.println(ports[2].getLastErrorCode());
      //        }
      //      }
      //
      //      ports[2].closePort();
      //      System.out.println("Port closed");
      //    }
    }
  }

  public void sendToRobotMethod3(List<GraphNode> currentPath) {
    SerialPort[] ports = SerialPort.getCommPorts();
    //        List<Node> currentPath = new ArrayList<Node>();
    System.out.println(Arrays.toString(ports));
    System.out.println("Got ports");

    if (ports.length != 0) {
      System.out.println(ports[2]);
      ports[2].setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
      ports[2].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 16); // Blocking write

      if (ports[2].openPort(16)) {
        int numMessages = currentPath.size();
        byte[] bytes = {
          (byte) (numMessages / 1000 % 10),
          (byte) (numMessages / 100 % 10),
          (byte) (numMessages / 10 % 10),
          (byte) (numMessages % 10),
          10
        };
        ports[2].writeBytes(bytes, bytes.length);
        System.out.println(numMessages + " numMessages sent");
        ports[2].closePort();
        System.out.println("Message Port Closed");
      }

      //      ports[2].openPort(16);
      for (GraphNode node : currentPath) {
        if (ports[2].openPort(16)) {
          //          System.out.println(currentPath.get(totalNodesSent).getNodeID());
          if (ports[2].isOpen() || ports[2].openPort(16)) {
            System.out.println("Port opened successfully");
            int x = node.getXCoord();
            System.out.println("x coord: " + x);
            int y = node.getYCoord();
            System.out.println("y coord: " + y);
            byte[] bytes = {
              (byte) (x / 1000 % 10),
              (byte) (x / 100 % 10),
              (byte) (x / 10 % 10),
              (byte) (x % 10),
              10
            };
            ports[2].writeBytes(bytes, bytes.length);
            // not sending the the following byte, only sending the numMessages and x
            byte[] bytes2 = {
              (byte) (y / 1000 % 10),
              (byte) (y / 100 % 10),
              (byte) (y / 10 % 10),
              (byte) (y % 10),
              10
            };
            ports[2].writeBytes(bytes2, bytes2.length);
            System.out.println(bytes.toString() + "sent");
            System.out.println(bytes2.toString() + "sent");
          } else {
            System.out.println("Failed to open port");
            System.out.println(ports[2].getLastErrorCode());
          }

          ports[2].closePort();
          System.out.println("Port closed");
          numNodesSent++;
          totalNodesSent++;
        }
        //        numNodesSent = 0;
        if (totalNodesSent >= currentPath.size()) {
          if (ports[2].openPort(16)) {
            System.out.println("final port opening for end call");
            byte[] bytes3 = {9, 9, 9, 9, 10};
            byte[] bytes4 = {9, 9, 9, 9, 10};
            ports[2].writeBytes(bytes3, bytes3.length);
            ports[2].writeBytes(bytes4, bytes4.length);
            System.out.println(bytes3.toString() + "sent");
            System.out.println(bytes4.toString() + "sent");
          }
          System.out.println("Robot has reached the final node in the path");
          ports[2].closePort();
          System.out.println("Final port Closing");
          totalNodesSent = 0;
        }
      }
    }
  }

  public void sendToRobotMethod4(List<GraphNode> currentPath) {
    SerialPort[] ports = SerialPort.getCommPorts();
    //        List<Node> currentPath = new ArrayList<Node>();
    System.out.println(Arrays.toString(ports));
    System.out.println("Got ports");

    if (ports.length != 0) {
      System.out.println(ports[2]);
      ports[2].setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
      ports[2].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 16, 16); // Blocking write

      if (ports[2].openPort(16)) {
        int numMessages = currentPath.size();
        byte[] bytes = {
          (byte) (numMessages / 1000 % 10),
          (byte) (numMessages / 100 % 10),
          (byte) (numMessages / 10 % 10),
          (byte) (numMessages % 10),
          10
        };
        ports[2].writeBytes(bytes, bytes.length);
        System.out.println(numMessages + " numMessages sent");
        ports[2].closePort();
        System.out.println("Message Port Closed");
      }

      //      ports[2].openPort(16);
      byte[] inputByte = {0};
      byte[] inputByte2 = {0};
      int reading = 0;
      int reading2 = -1;
      if (ports[2].openPort()) {
        System.out.println("port opening for reading");
        reading = ports[2].readBytes(inputByte, 1);
        ports[2].flushIOBuffers();
        System.out.println("Reading is: " + reading);
        ports[2].closePort();
      }

      // if read a 1
      boolean block = true;
      while (block) {

        if (reading == 1) {
          if (ports[2].openPort(16)) {
            if (ports[2].isOpen() || ports[2].openPort(16)) {
              System.out.println("Port opened successfully");
              int x = currentPath.get(0).getXCoord();
              System.out.println("x coord: " + x);
              int y = currentPath.get(0).getYCoord();
              System.out.println("y coord: " + y);
              byte[] bytes = {
                (byte) (x / 1000 % 10),
                (byte) (x / 100 % 10),
                (byte) (x / 10 % 10),
                (byte) (x % 10),
                10
              };
              ports[2].writeBytes(bytes, bytes.length);
              // not sending the the following byte, only sending the numMessages and x
              byte[] bytes2 = {
                (byte) (y / 1000 % 10),
                (byte) (y / 100 % 10),
                (byte) (y / 10 % 10),
                (byte) (y % 10),
                10
              };
              ports[2].writeBytes(bytes2, bytes2.length);
              System.out.println(bytes.toString() + "sent");
              System.out.println(bytes2.toString() + "sent");
            } else {
              System.out.println("Failed to open port");
              System.out.println(ports[2].getLastErrorCode());
            }
            //            reading = ports[2].readBytes(inputByte, 1);
            //            System.out.println(reading);
            ports[2].closePort();
            System.out.println("Port closed after initial node sent");
            numNodesSent++;
            reading = -1;
          }
          //          if (ports[2].openPort(16)) {
          //            reading2 = ports[2].readBytes(inputByte2, 1);
          //            reading = reading2 + 2;
          //            System.out.println("Reading 2 is: " + reading2);
          //            ports[2].closePort();
          //          }
        }
        if (ports[2].openPort(16) && !(reading2 == 0)) {
          reading2 = ports[2].readBytes(inputByte2, 1);
          //          reading = reading2 + 2;
          System.out.println("Reading 2 is: " + reading2);
          ports[2].flushIOBuffers();
          ports[2].closePort();
        }
        if (reading2 == 0 && numNodesSent >= currentPath.size()) {
          if (ports[2].openPort()) {
            System.out.println("reading final was read");

            byte[] bytes3 = {(byte) 9, (byte) 9, (byte) 9, (byte) 9, 10};
            ports[2].writeBytes(bytes3, bytes3.length);
            ports[2].writeBytes(bytes3, bytes3.length);
            ports[2].closePort();
            System.out.println("Final port closed hoorah");
            block = false;
            numNodesSent = 0;
          }
        } else if (reading2 == 0) {
          if (ports[2].openPort()) {
            System.out.print("reading 2 was read");
            if (ports[2].openPort(16)) {
              if (ports[2].isOpen() || ports[2].openPort(16)) {
                System.out.println("Port opened successfully");
                int x = currentPath.get(numNodesSent).getXCoord();
                System.out.println("x coord: " + x);
                int y = currentPath.get(numNodesSent).getYCoord();
                System.out.println("y coord: " + y);
                byte[] bytes = {
                  (byte) (x / 1000 % 10),
                  (byte) (x / 100 % 10),
                  (byte) (x / 10 % 10),
                  (byte) (x % 10),
                  10
                };
                ports[2].writeBytes(bytes, bytes.length);
                // not sending the the following byte, only sending the numMessages and x
                byte[] bytes2 = {
                  (byte) (y / 1000 % 10),
                  (byte) (y / 100 % 10),
                  (byte) (y / 10 % 10),
                  (byte) (y % 10),
                  10
                };
                ports[2].writeBytes(bytes2, bytes2.length);
                System.out.println(bytes.toString() + "sent");
                System.out.println(bytes2.toString() + "sent");
              } else {
                System.out.println("Failed to open port");
                System.out.println(ports[2].getLastErrorCode());
              }
              //            reading = ports[2].readBytes(inputByte, 1);
              //            System.out.println(reading);
              ports[2].closePort();
              System.out.println("Port closed after initial node sent");
              numNodesSent++;
            }

            //            byte[] bytes3 = {(byte) 9, (byte) 9, (byte) 9, (byte) 9, 10};
            //            ports[2].writeBytes(bytes3, bytes3.length);
            //            ports[2].writeBytes(bytes3, bytes3.length);
            //            ports[2].closePort();
            //            System.out.println("Final port closed hoorah");
            //            block = false;
            reading2 = -1;
          }
        }
      }

      //      for (GraphNode node : currentPath) {
      //        if (ports[2].openPort(16)) {
      //          //          System.out.println(currentPath.get(totalNodesSent).getNodeID());
      //          if (ports[2].isOpen() || ports[2].openPort(16)) {
      //            System.out.println("Port opened successfully");
      //            int x = node.getXCoord();
      //            System.out.println("x coord: " + x);
      //            int y = node.getYCoord();
      //            System.out.println("y coord: " + y);
      //            byte[] bytes = {
      //              (byte) (x / 1000 % 10),
      //              (byte) (x / 100 % 10),
      //              (byte) (x / 10 % 10),
      //              (byte) (x % 10),
      //              10
      //            };
      //            ports[2].writeBytes(bytes, bytes.length);
      //            // not sending the the following byte, only sending the numMessages and x
      //            byte[] bytes2 = {
      //              (byte) (y / 1000 % 10),
      //              (byte) (y / 100 % 10),
      //              (byte) (y / 10 % 10),
      //              (byte) (y % 10),
      //              10
      //            };
      //            ports[2].writeBytes(bytes2, bytes2.length);
      //            System.out.println(bytes.toString() + "sent");
      //            System.out.println(bytes2.toString() + "sent");
      //          } else {
      //            System.out.println("Failed to open port");
      //            System.out.println(ports[2].getLastErrorCode());
      //          }
      //
      //          ports[2].closePort();
      //          System.out.println("Port closed");
      //          numNodesSent++;
      //          totalNodesSent++;
      //        }
      //        //        numNodesSent = 0;
      //        if (totalNodesSent >= currentPath.size()) {
      //          if (ports[2].openPort(16)) {
      //            System.out.println("final port opening for end call");
      //            byte[] bytes3 = {9, 9, 9, 9, 10};
      //            byte[] bytes4 = {9, 9, 9, 9, 10};
      //            ports[2].writeBytes(bytes3, bytes3.length);
      //            ports[2].writeBytes(bytes4, bytes4.length);
      //            System.out.println(bytes3.toString() + "sent");
      //            System.out.println(bytes4.toString() + "sent");
      //          }
      //          System.out.println("Robot has reached the final node in the path");
      //          ports[2].closePort();
      //          System.out.println("Final port Closing");
      //          totalNodesSent = 0;
      //        }
      //      }
    }
  }

  void activateSubmit() {
    submit.setDisable(false);
  }

  public void getSendTextDirections(ActionEvent actionEvent) {
    String phone = sendTextDir.getText();
  }
}
