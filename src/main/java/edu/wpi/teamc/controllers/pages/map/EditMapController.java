package edu.wpi.teamc.controllers.pages.map;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.controllers.pages.map.MapHelpers.*;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.ImportCSV;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.ToggleSwitch;

public class EditMapController {
  public Group group;
  public MFXButton Add;
  public MFXButton Modify;
  public MFXButton Remove;
  public MFXButton Move;
  public MFXButton Edges;
  double mouseX;
  double mouseY;
  public Image image =
      new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());

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

  public EditMapController() throws IOException {}

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton selectButton;
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
  @FXML Text edgeMadeText;

  NodeDao InodeDao = new NodeDao();
  EdgeDao IedgeDao = new EdgeDao();
  LocationNameDao IlocationDao = new LocationNameDao();
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
  MakeEdgesHelper edgesHelper = new MakeEdgesHelper(0);

  NodeResetterHelper nodeResetterHelper = new NodeResetterHelper();
  LineResetterHelper lineResetterHelper = new LineResetterHelper();
  ModeResetterHelper modeResetterHelper = new ModeResetterHelper();
  FloorResetterHelper floorResetterHelper = new FloorResetterHelper();
  AlignModeHelper alignModeHelper = new AlignModeHelper();
  CloseModeHelper closeModeHelper = new CloseModeHelper();

  // ORM lists
  List<Node> nodeList = new ArrayList<Node>();
  List<NodeStatus> nodeStatusList = new ArrayList<NodeStatus>();

  List<Edge> edgeList = new ArrayList<Edge>();
  List<LocationName> locationNameList = new ArrayList<LocationName>();
  // hash maps
  HashMap<Integer, Move> nodeIDtoMove = new HashMap<>();
  HashMap<String, LocationName> longNametoLocationName = new HashMap<String, LocationName>();
  HashMap<Integer, Node> nodeIDToNode = new HashMap<Integer, Node>();
  HashMap<Integer, NODE_STATUS> nodeIDtoNodeStatus = new HashMap<Integer, NODE_STATUS>();
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
  Boolean edgeClicked = false;
  Edge clickedEdge;
  Line lineClicked;
  Boolean alignMode = true;
  Boolean closeMode = false;
  Node currNodeClicked;
  Circle currCircleClicked;
  Circle tempSave;
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

  /*
  Iteration 3 newly added
   */
  @FXML ComboBox filterBox;
  @FXML Text shortToggleText;
  @FXML ToggleSwitch shortToggle;
  @FXML Text confToggleText;
  @FXML ToggleSwitch confToggle;
  @FXML Text hallToggleText;
  @FXML ToggleSwitch hallToggle;
  @FXML Text elevToggleText;
  @FXML ToggleSwitch elevToggle;
  @FXML VBox toggleBox;
  @FXML ToggleSwitch edgeToggle;

  @FXML Text edgeToggleText;
  Boolean shortShown = true;
  Boolean edgeShown = true;
  Boolean alignVert = false;
  Boolean alignHoriz = false;
  Boolean confShown = true;
  Boolean elevShown = true;
  Boolean hallShown = true;
  MoveHelper moveHelper = new MoveHelper();
  Boolean secondNodeClicked = false;

  @FXML AnchorPane basePane;
  @FXML AnchorPane backgroundPane;
  @FXML AnchorPane headerPane;
  @FXML VBox floorBox;
  @FXML VBox importBox;
  @FXML VBox modeBox;
  @FXML AnchorPane mapBackgroundPane;
  @FXML MFXButton importButton;
  @FXML MFXButton exportButton;
  //  EventHandler<MouseEvent> initGroupOnMouseClicked = new EventHandler();
  EventHandler<? super MouseEvent> initGroupOnMouseClicked;
  @FXML MFXButton Align;

  //  Boolean

  // Notes: Fix bug that you can click on an edge and exit and its still highlighted
  // click bug that you can click on a node during move and then click anywhere on the map to bring
  // up the move menu

  /* Bugs to fix
  When go into remove mode, if click on edge, only edge menu. If click on node, get both edge and node menu. Should only get node menu.
   */

  /** Method run when controller is initialized */
  public void initialize() {

    backgroundPane.getStyleClass().add("backgroundPane");
    headerPane.getStyleClass().add("headerPane");
    floorBox.getStyleClass().add("mapBox");
    importBox.getStyleClass().add("mapBox");
    modeBox.getStyleClass().add("mapBox");
    mapBackgroundPane.getStyleClass().add("mapBackgroundPane");

    Image image = this.image;
    imageView = new ImageView(image); // was ImageView imageView
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);
    Point2D centrePoint = new Point2D(1100, 400);
    mapGPane.centreOn(centrePoint);
    mapGPane.zoomTo(0.5, mapGPane.targetPointAtViewportCentre());

    // Make and display toggle buttons ************
    //    MFXToggleButton shortnameToggle = new MFXToggleButton();
    //    MFXToggleButton confToggle = new MFXToggleButton();
    //    MFXToggleButton hallToggle = new MFXToggleButton();
    //    MFXToggleButton elevToggle = new MFXToggleButton();
    //    List<MFXToggleButton> toggleButtons = new ArrayList<MFXToggleButton>();
    //
    //    toggleButtons.add(shortnameToggle);
    //    toggleButtons.add(confToggle);
    //    toggleButtons.add(hallToggle);
    //    toggleButtons.add(elevToggle);

    //    filterBox.setItems(FXCollections.observableArrayList(toggleButtons));
    // Does not work *********

    backgroundPane.getStyleClass().add("backgroundPane");
    headerPane.getStyleClass().add("headerPane");
    floorBox.getStyleClass().add("mapBox");
    importBox.getStyleClass().add("mapBox");
    modeBox.getStyleClass().add("mapBox");
    mapBackgroundPane.getStyleClass().add("mapBackgroundPane");

    shortToggleText.getStyleClass().add("toggleText");
    confToggleText.getStyleClass().add("toggleText");
    elevToggleText.getStyleClass().add("toggleText");
    hallToggleText.getStyleClass().add("toggleText");
    edgeToggleText.getStyleClass().add("toggleText");

    shortToggle.getStyleClass().add("toggleButton");
    confToggle.getStyleClass().add("toggleButton");
    elevToggle.getStyleClass().add("toggleButton");
    hallToggle.getStyleClass().add("toggleButton");
    edgeToggle.getStyleClass().add("toggleButton");
    toggleBox.getStyleClass().add("mapBox");

    //    toggleBox
    //        .getStylesheets()
    //        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    basePane
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());

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
    selectButton.setBackground(Background.fill(Paint.valueOf("#EAB334")));
    selectButton.setStyle(
        "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12; -fx-background-color: #EAB334");
    modeResetterHelper.setButton(selectButton);
    String defaultButtonStyle = FL1.getStyle();
    importButton.setStyle(defaultButtonStyle);
    exportButton.setStyle(defaultButtonStyle);
    FL1.setBackground(Background.fill(Paint.valueOf("#EAB334")));
    FL1.setStyle(
        "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12; -fx-background-color: #EAB334");
    floorResetterHelper.setButton(FL1);

    // Set tooltips
    Tooltip alignTip =
        new Tooltip(
            "Align Mode: Click on multiple nodes. Then, press the green check-mark\n to determine if you want to align nodes vertically or horizontally. Finally, click a location on the map\nto align the nodes about that location.");
    Align.setTooltip(alignTip);
    alignTip.setShowDuration(Duration.hours(3));
    alignTip.setShowDelay(Duration.millis(0));

    Tooltip addTip =
        new Tooltip(
            "Add Mode: Click anywhere on the map to add a new node to the floor. \nOr, click on a node to add a location name to a node");
    Add.setTooltip(addTip);
    addTip.setShowDuration(Duration.hours(3));
    addTip.setShowDelay(Duration.millis(0));

    Tooltip modifyTip =
        new Tooltip(
            "Modify Mode: Click on a node to be prompted with four options.\n1: Modify the location of a node by text input.\n2: Modify the location of a node by dragging the node on the map with the cursor.\n3: Modify the location name of the node.\n4: Enter Close Mode. Close Mode allows you to select multiple nodes and close them so \npathfinding can no longer use those nodes. Closed nodes can also be opened in this mode.");
    Modify.setTooltip(modifyTip);
    modifyTip.setShowDuration(Duration.hours(3));
    modifyTip.setShowDelay(Duration.millis(0));

    Tooltip removeTip =
        new Tooltip(
            "Remove Mode: Click on a node to remove either the node or just\nthe node's location name. Click on an edge to remove the edge.");
    Remove.setTooltip(removeTip);
    removeTip.setShowDuration(Duration.hours(3));
    removeTip.setShowDelay(Duration.millis(0));

    edgeToggle.setOnMouseClicked(
        e -> {
          if (edgeShown) {
            //        group.getChildren().remove(mapEdges);
            mapEdges.setVisible(false);
            edgeShown = false;
          } else {
            edgeShown = true;
            mapEdges.setVisible(true);
            //            mapEdges.toFront();
            //            mapNodes.toFront();
          }
        });

    shortToggle.setOnMouseClicked(
        e -> {
          System.out.println("toggle Clicked");
          if (shortShown) {
            shortShown = false;
            //            mapText = new Group();
            //            placeNodes(floor);
            mapText.setVisible(false);
            if (dragModeOn) {
              movingText.setVisible(shortShown);
            }
          } else {
            shortShown = true;
            mapText.setVisible(true);
            //            placeNodes(floor); /////Not sure if this is necessary
            if (dragModeOn) {
              movingText.setVisible(shortShown);
              //              createMovingMapNode()
            }
          }
        });
    confToggle.setOnMouseClicked(
        e -> {
          if (confShown) {
            confShown = false;
            mapNodes.getChildren().clear();
            mapText.getChildren().clear();
            placeNodes(floor);
          } else {
            confShown = true;
            placeNodes(floor);
          }
        });
    hallToggle.setOnMouseClicked(
        e -> {
          if (hallShown) {
            hallShown = false;
            mapNodes.getChildren().clear();
            mapText.getChildren().clear();
            placeNodes(floor);
          } else {
            hallShown = true;
            placeNodes(floor);
          }
        });
    elevToggle.setOnMouseClicked(
        e -> {
          if (elevShown) {
            elevShown = false;
            mapNodes.getChildren().clear();
            mapText.getChildren().clear();
            placeNodes(floor);
          } else {
            elevShown = true;
            placeNodes(floor);
          }
        });

    //    group.getChildren().add(stackPane);

    group.setOnMouseClicked(
        e -> {
          //          System.out.print(lockMap);
          mouseX = e.getX();
          mouseY = e.getY();
          System.out.println(mouseX + "  " + mouseY);
          //          if(addClicked) //dont need this bc when add is clicked will update mapMode
          // to
          // Add, need to set that up

          if ((Objects.equals(mapMode.getMapMode(), "Add")) && !lockMap && !nodeClicked) {
            lockMap = true;
            //            System.out.println(lockMap);
            try {
              addNodeByMouseLoc((int) mouseX, (int) mouseY);
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }
          } // bring up node add popup
          if (Objects.equals(mapMode.getMapMode(), "Select")) {
            // do Nothing
          }
          if (Objects.equals(mapMode.getMapMode(), "Align") && !lockMap) {
            System.out.println("why am i here");
            lockMap = true;
            alignNodes();
          }
          if (edgeClicked && !nodeClicked) {
            if (Objects.equals(mapMode.getMapMode(), "Remove")) {
              lineClicked.setFill(Paint.valueOf("#EAB334"));
              removeEdges();
            }
          }

          if (nodeClicked && !lockMap) {
            if (Objects.equals(mapMode.getMapMode(), "Add")) { // to add a location name to a node
              lockMap = true;
              try {
                addMenu();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            } // bring up location name add popup
            else if (Objects.equals(mapMode.getMapMode(), "Modify")) {
              lockMap = true;
              try {
                modifyMenu();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            } // bring up modify popup
            else if (Objects.equals(mapMode.getMapMode(), "Remove")) {
              lockMap = true;
              try {
                removeMenu();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            } // bring up remove popup
            else if (Objects.equals(mapMode.getMapMode(), "Move")) {
              if (moveHelper.getNodesClicked() == 1) {
                lockMap = true;
                currCircleClicked.setFill(Paint.valueOf("#CB02D7"));
                currCircleClicked.setStroke(Paint.valueOf("#020275"));
                moveMenu();
              } else if (moveHelper.getNodesClicked() == 0) {
                moveHelper.setNodesClicked(1);
                moveHelper.setCircle(currCircleClicked);
                moveHelper.setNode(currNodeClicked);
                currCircleClicked.setFill(Paint.valueOf("#FA8B02"));
                currCircleClicked.setStroke(Paint.valueOf("#020275"));
                nodeClicked = false;
              }
            } else if (Objects.equals(mapMode.getMapMode(), "Make_edges")) {
              lockMap = true;
              createEdgesForNodes();
              edgeMadeText.setVisible(true);
            }
            //            } else if (Objects.equals(mapMode.getMapMode(), "Align")) {
            //              lockMap = true;
            //              alignNodes();
            //            }
          }
          //          if (Objects.equals((MFXToggleButton) e.getTarget(), shortnameToggle)) {
          //            if (shortShown) {
          //              shortShown = false;
          //              placeNodes(floor);
          //            } else {
          //              shortShown = true;
          //              placeNodes(floor);
          //            }
          //          }
          ;
        });

    //    initGroupOnMouseClicked = group.getOnMouseClicked();
    initGroupOnMouseClicked = group.getOnMouseClicked();

    loadDatabase();
    loadNodeIDToNode();
    loadNodeIDToNodeStatus();
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
  // load hashmap of nodeID to nodeStatus
  private void loadNodeIDToNodeStatus() {
    //    for (NodeStatus nodeStatus : nodeStatusList) {
    //      nodeIDtoNodeStatus.put(nodeStatus.getNodeID(), nodeStatus.getStatus());
    //    }
  }

  // see if both nodes are on the same floor
  private boolean sameFloor(int nodeID1, int nodeID2) {
    String node1Floor = nodeIDToNode.get(nodeID1).getFloor();
    String node2Floor = nodeIDToNode.get(nodeID2).getFloor();
    if (node1Floor.equals(node2Floor)) {
      return true;
    }
    return false;
  }

  public void removeEdges() {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    Text headerText = new Text("Remove Edge?");
    MFXButton confirm = new MFXButton("Confirm");
    MFXButton cancel = new MFXButton("Cancel");

    // set styles
    headerText.getStyleClass().add("Header");
    confirm.getStyleClass().add("MFXbutton");
    cancel.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    confirm.setLayoutX(lay_x);
    confirm.setLayoutY(lay_y + 30);
    cancel.setLayoutX(lay_x);
    cancel.setLayoutY(lay_y + 90);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, confirm, cancel);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 290, 220);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Remove Node Window");
    stage.setAlwaysOnTop(true);
    stage.show();
    EdgeDao edgeDao = new EdgeDao();

    stage.setOnCloseRequest(
        event -> {
          lineClicked.setStroke(Paint.valueOf("#021335"));
          edgeClicked = false;
          stage.close();
          lockMap = false;
        });
    confirm.setOnMouseClicked(
        e -> {
          edgeDao.deleteRow(clickedEdge);
          loadDatabase();
          loadNodeIDToNode();
          sortEdges();
          placeEdges(floor);
          mapNodes.toFront();
          edgeClicked = false;
          stage.close();
        });

    cancel.setOnMouseClicked(
        e -> {
          lineClicked.setStroke(Paint.valueOf("#021335"));
          edgeClicked = false;
          stage.close();
        });
  }

  public void createEdgesForNodes() {
    //    Node secondNode;
    //    Circle secondCircle;
    EdgeDao edgeDao = new EdgeDao();
    if (nodeClicked && (edgesHelper.getNodesClicked() == 0)) {
      System.out.println("first node");
      edgesHelper.setNode(currNodeClicked);
      currCircleClicked.setFill(Paint.valueOf("#EAB334"));
      edgesHelper.setCircle(currCircleClicked);
      edgesHelper.setNodesClicked(1);
      //      System.out.println()

      nodeClicked = false;
      lockMap = false;
    }
    if (nodeClicked && (edgesHelper.getNodesClicked() == 1)) {
      currCircleClicked.setFill(Paint.valueOf("#EAB334"));
      System.out.println("second node");
      //      secondNode = currNodeClicked;
      //      secondCircle = currCircleClicked;
      Edge edge = new Edge(edgesHelper.getNode().getNodeID(), currNodeClicked.getNodeID());
      edgeDao.addRow(edge);
      loadDatabase();
      loadNodeIDToNode();
      sortEdges();
      placeEdges(floor);
      mapNodes.toFront();
      nodeClicked = false;
      edgesHelper.setNodesClicked(0);
      lockMap = false;
      currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
      edgesHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
      edgeMadeText.setText(
          "New Edge: " + edgesHelper.getNode().getNodeID() + " to " + currNodeClicked.getNodeID());
    }
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
    line.setStroke(Paint.valueOf("#021335"));
    line.setOnMouseClicked(
        e -> {
          System.out.println("edge clicked");
          clickedEdge = edge;
          lineClicked = line;
          edgeClicked = true;
          resetAndSetLine(line);
        });
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
    // nodeStatusList = new NodeStatusDao().fetchAllObjects();
    edgeList = new EdgeDao().fetchAllObjects();
    locationNameList = new LocationNameDao().fetchAllObjects();
    moveList = new MoveDao().fetchAllObjects();
    java.sql.Date currentDate = new Date(System.currentTimeMillis());

    for (Move move : moveList) {
      try {
        move.getLongName();
      } catch (NullPointerException e) {
        move.setLongName("ERROR");
      }
      if (nodeIDtoMove.get(move.getNodeID()) == null) {
        nodeIDtoMove.put(move.getNodeID(), move);
      } else {
        Move inHashMap = nodeIDtoMove.get(move.getNodeID());
        if (currentDate.compareTo(move.getDate()) >= 0) {
          if (move.getDate().compareTo(inHashMap.getDate()) >= 0) {
            nodeIDtoMove.put(move.getNodeID(), move);
          }
        }
      }
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
      if (Objects.equals(modeButton.getId(), "selectButton")) {
        mapMode = HandleMapModes.SELECT;
        resetAndSetModes(modeButton);
        System.out.println("select mode");
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
      } else if (Objects.equals(modeButton.getId(), "Add")) {
        mapMode = HandleMapModes.ADD;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
      } else if (Objects.equals(modeButton.getId(), "Modify")) {
        mapMode = HandleMapModes.MODIFY;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
      } else if (Objects.equals(modeButton.getId(), "Remove")) {
        mapMode = HandleMapModes.REMOVE;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
        System.out.println("Removing");
      } else if (Objects.equals(modeButton.getId(), "Move")) {
        mapMode = HandleMapModes.MOVE;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
        System.out.println("Moving");
      } else if (Objects.equals(modeButton.getId(), "Edges")) {
        mapMode = HandleMapModes.MAKE_EDGES;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
        System.out.println("Making Edges");
      } else if (Objects.equals(modeButton.getId(), "Align")) {
        mapMode = HandleMapModes.ALIGN;
        resetAndSetModes(modeButton);
        edgeMadeText.setText("");
        edgeMadeText.setVisible(false);
      }
    }
  }

  public void changeFloor(ActionEvent event) throws IOException {
    floorButton = (MFXButton) event.getTarget();

    if (Objects.equals(floorButton.getId(), "FL1")) {
      image = new Image(Main.class.getResource("views/images/FirstFloor.png").openStream());
      floor = "1";
      resetAndSetFloorIndicators(floorButton);
    } else if (Objects.equals(floorButton.getId(), "FL2")) {
      image = new Image(Main.class.getResource("views/images/SecondFloor.png").openStream());
      floor = "2";
      resetAndSetFloorIndicators(floorButton);
    } else if (Objects.equals(floorButton.getId(), "FL3")) {
      image = new Image(Main.class.getResource("views/images/ThirdFloor.png").openStream());
      floor = "3";
      resetAndSetFloorIndicators(floorButton);
    } else if (Objects.equals(floorButton.getId(), "FLB1")) {
      image = new Image(Main.class.getResource("views/images/B1.png").openStream());
      floor = "L1";
      resetAndSetFloorIndicators(floorButton);
    } else if (Objects.equals(floorButton.getId(), "FLB2")) {
      image = new Image(Main.class.getResource("views/images/B2.png").openStream());
      floor = "L2";
      resetAndSetFloorIndicators(floorButton);
    }
    group.getChildren().removeAll(mapNodes, mapText, imageView);
    group.getChildren().remove(mapNodes);
    group.getChildren().remove(mapText);

    imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    mapText = new Group();
    group.getChildren().addAll(imageView, mapNodes, mapText);

    //    resetAndSetModes(floorButton);

    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    placeEdges(floor);
    placeNodes(floor);
    if (!edgeShown) {
      mapEdges.setVisible(false);
    }
    if (!shortShown) {
      mapText.setVisible(false);
    }
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
          createMapNode(Floor1.get(i), shortName, nodeType, longName);
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
          createMapNode(Floor2.get(i), shortName, nodeType, longName);
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
          createMapNode(Floor3.get(i), shortName, nodeType, longName);
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
          createMapNode(FloorG.get(i), shortName, nodeType, longName);
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
          createMapNode(FloorL1.get(i), shortName, nodeType, longName);
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
          createMapNode(FloorL2.get(i), shortName, nodeType, longName);
        }
    }
    mapNodes.toFront();
    mapText.toFront();
  }

  public void createMapNode(Node node, String shortname, String nodeType, String longName) {
    Circle newCircle = new Circle();
    Text text = new Text();
    //    System.out.println("creating nodes");

    //    if (shortShown) {
    if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
      text = new Text(shortname);
    }
    //    }
    Tooltip tooltip =
        new Tooltip(
            "Node ID: "
                + node.getNodeID()
                + "\n xCoord: "
                + node.getXCoord()
                + "\n yCoord"
                + node.getYCoord()
                + "\n Node Type: "
                + nodeType);

    newCircle.setRadius(6);
    newCircle.setCenterX(node.getXCoord());
    newCircle.setCenterY(node.getYCoord());

    text.setX(node.getXCoord() + 5);
    text.setY(node.getYCoord() - 5);

    newCircle.setId(String.valueOf(node.getNodeID()));
    Tooltip.install(newCircle, tooltip);
    tooltip.setShowDuration(Duration.hours(3));
    tooltip.setShowDelay(Duration.millis(0));

    // load hashmap of status versus nodeID
    NODE_STATUS status = node.getStatus();

    // set color of nodes based on if they are closed or open
    if (status.equals(NODE_STATUS.OPEN)) {
      newCircle.setStroke(Paint.valueOf("#13DAF7"));
      newCircle.setFill(Paint.valueOf("#13DAF7"));
    } else {
      newCircle.setStroke(Paint.valueOf("#FE2300"));
      newCircle.setFill(Paint.valueOf("#FE2300"));
    }

    newCircle.setVisible(false);
    text.setVisible(false);

    newCircle.setOnMouseEntered(
        e -> {
          newCircle.setStroke(Paint.valueOf("#1338B3"));
        });

    newCircle.setOnMousePressed( // was set on mouse clicked
        e -> {
          currCircleClicked = newCircle;
          nodeClicked = true; // clicked on a node
          currNodeClicked = node;
          currNodeLongname = longName;
          currNodeShortname = shortname;
          currNodeType = nodeType;

          if (!(Objects.equals(mapMode.getMapMode(), "Modify_drag"))
              && !(Objects.equals(mapMode.getMapMode(), "Align"))
              && !(Objects.equals(mapMode.getMapMode(), "Move"))
              && status.equals(NODE_STATUS.OPEN)
              && !(Objects.equals(mapMode.getMapMode(), "Close"))) {
            resetAndSetCircle(newCircle);

            //            newCircle.setFill(Paint.valueOf("#45a37f"));
          } else if (Objects.equals(node.getNodeID(), mapModeSaver.getNodeID())
              && !(Objects.equals(mapMode.getMapMode(), "Move"))
              && status.equals(NODE_STATUS.OPEN)) {
            newCircle.setFill(Paint.valueOf("#45a37f"));
          }

          if ((Objects.equals(mapMode.getMapMode(), "Modify_drag"))
              && !mapModeSaver.getDraggingNodeCreated()) {
            movingNodeClicked = true;
          }
        });

    newCircle.setOnMouseExited(
        e -> {
          if (!nodeClicked
              && !Objects.equals(mapMode.getMapMode(), "Close")
              && !Objects.equals(mapMode.getMapMode(), "Align")
              && status.equals(NODE_STATUS.OPEN)
              && !Objects.equals(mapMode.getMapMode(), "Move")) {
            newCircle.setStroke(Paint.valueOf("#13DAF7"));
          }
        });

    if (Objects.equals(mapMode.getMapMode(), "Modify_drag")) {

      System.out.println("GOT HERE");
      movingNode.getChildren().add(newCircle);
      movingText.getChildren().add(text);
      //      }
    } else {
      if (confShown && hallShown && elevShown) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      } else if (!confShown && hallShown && elevShown && !(Objects.equals(nodeType, "CONF"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
        System.out.println("HEEEEERRREEEE");
      } else if (!hallShown && confShown && elevShown && !(Objects.equals(nodeType, "HALL"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
        //        System.out.println("Halls should be toggled");
      } else if (!elevShown && hallShown && confShown && !(Objects.equals(nodeType, "ELEV"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      } else if (!confShown
          && !hallShown
          && elevShown
          && !(Objects.equals(nodeType, "CONF"))
          && !(Objects.equals(nodeType, "HALL"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      } else if (!confShown
          && !elevShown
          && hallShown
          && !(Objects.equals(nodeType, "CONF"))
          && !(Objects.equals(nodeType, "ELEV"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      } else if (!elevShown
          && !hallShown
          && confShown
          && !(Objects.equals(nodeType, "ELEV"))
          && !(Objects.equals(nodeType, "HALL"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      } else if (!(Objects.equals(nodeType, "CONF"))
          && !(Objects.equals(nodeType, "ELEV"))
          && !(Objects.equals(nodeType, "HALL"))) {
        mapNodes.getChildren().add(newCircle);
        mapText.getChildren().add(text);
        newCircle.setVisible(true);
        text.setVisible(true);
      }
      //      }
    }
  }

  //  public void toggleShort(ActionEvent event) {
  //    if (shortShown) {
  //      shortShown = false;
  //      placeNodes(floor);
  //    } else {
  //      shortShown = true;
  //      placeNodes(floor);
  //    }
  //  }

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
    if (shortShown) {
      if (!nodeType.equals("HALL") && !nodeType.equals("ERROR")) {
        text = new Text(shortname);
      }
    }
    newCircle.setRadius(6);
    newCircle.setCenterX(x);
    newCircle.setCenterY(y);

    text.setX(x + 5);
    text.setY(y - 5);

    newCircle.setId(String.valueOf(nodeID));
    newCircle.setStroke(Paint.valueOf("#45a37f"));
    newCircle.setFill(Paint.valueOf("#8745a3"));
    newCircle.setVisible(true);
    text.setVisible(true);
    newCircle.setOnMousePressed(
        e -> {
          nodeClicked = true; // clicked on a node
          currNodeLongname = longName;
          currNodeShortname = shortname;
          currNodeType = nodeType;
          movingNodeClicked = true;
          if (Objects.equals(node.getNodeID(), mapModeSaver.getNodeID())) {
            newCircle.setFill(Paint.valueOf("#45a37f"));
          }
        });

    System.out.println("GOT HERE");
    movingNode.getChildren().add(newCircle);
    movingText.getChildren().add(text);
  }

  public void resetAndSetCircle(Circle circle) {
    circle.setFill(Paint.valueOf("#EAB334"));
    nodeResetterHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
    nodeResetterHelper.getCircle().setStroke(Paint.valueOf("#13DAF7"));
    nodeResetterHelper.setCircle(circle);
    //    tempSave.setFill(Paint.valueOf("#13DAF7"));
    //    tempSave = circle;
  }

  public void resetAndSetLine(Line line) {
    line.setStroke(Paint.valueOf("#EAB334"));
    lineResetterHelper.getLine().setStroke(Paint.valueOf("#021335"));
    lineResetterHelper.setLine(line);
  }

  public void resetAndSetModes(MFXButton button) {
    button.setButtonType(ButtonType.FLAT);
    button.setDepthLevel(DepthLevel.LEVEL2);
    button.setRippleAnimateBackground(true);
    button.setStyle(
        "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12; -fx-background-color: #EAB334");
    //    button.setBackground(Background.fill(Paint.valueOf("#EAB334")));

    modeResetterHelper.getButton().setBackground(Background.fill(Paint.valueOf("#FFFFFF")));
    modeResetterHelper.getButton().setRippleAnimateBackground(true);
    //    modeResetterHelper.getButton().setDepthLevel(DepthLevel.LEVEL4);
    modeResetterHelper.getButton().setButtonType(ButtonType.FLAT);
    modeResetterHelper.getButton().setDepthLevel(DepthLevel.LEVEL2);
    modeResetterHelper
        .getButton()
        .setStyle(
            "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12");
    modeResetterHelper.setButton(button);
    //    tempSave.setFill(Paint.valueOf("#13DAF7"));
    //    tempSave = circle;
  }

  public void resetAndSetFloorIndicators(MFXButton button) {
    button.setBackground(Background.fill(Paint.valueOf("#EAB334")));
    button.setRippleAnimateBackground(true);
    button.setButtonType(ButtonType.FLAT);
    button.setDepthLevel(DepthLevel.LEVEL2);
    button.setStyle(
        "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12; -fx-background-color: #EAB334");
    floorResetterHelper.getButton().setBackground(Background.fill(Paint.valueOf("#FFFFFF")));
    floorResetterHelper.getButton().setRippleAnimateBackground(true);
    floorResetterHelper.getButton().setButtonType(ButtonType.FLAT);
    floorResetterHelper
        .getButton()
        .setStyle(
            "-fx-background-radius: 2; -fx-pref-height: 23.2; -fx-font-weight: bold; -fx-set-pref-width: 70; -fx-font-family: Arial; -fx-font-size: 12");
    floorResetterHelper.setButton(button);
    //    tempSave.setFill(Paint.valueOf("#13DAF7"));
    //    tempSave = circle;
  }

  public void addNodeByMouseLoc(int x, int y) throws IOException {
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
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
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

  public void modifyMenu() throws IOException {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Select Modification Method");
    Text modify_1 = new Text("Modify node by text input?");
    Text modify_2 = new Text("Modify node by dragging on map?");
    Text modify_3 = new Text("Modify location name of node?");

    Text modify_4 =
        new Text("Enter close node mode?"); // /////////// Add this changing to "Open node?" if
    // currNodeClicked = closed
    MFXButton byText = new MFXButton("By Text");
    MFXButton byDrag = new MFXButton("By Drag");
    MFXButton editName = new MFXButton("Edit Name");
    MFXButton closeNodeButton = new MFXButton("Close Mode");

    vBox.getChildren()
        .addAll(
            headerText,
            modify_1,
            byText,
            modify_2,
            byDrag,
            modify_3,
            editName,
            modify_4,
            closeNodeButton);

    // set styles
    headerText.getStyleClass().add("Header");
    modify_1.getStyleClass().add("Text");
    modify_2.getStyleClass().add("Text");
    modify_3.getStyleClass().add("Text");
    modify_4.getStyleClass().add("Text");
    byText.getStyleClass().add("MFXbutton");
    byDrag.getStyleClass().add("MFXbutton");
    editName.getStyleClass().add("MFXbutton");
    closeNodeButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

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
    modify_4.setLayoutX(lay_x);
    modify_4.setLayoutY(lay_y + 290);
    closeNodeButton.setLayoutX(lay_x);
    closeNodeButton.setLayoutY(lay_y + 305);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane
        .getChildren()
        .addAll(
            headerText,
            modify_1,
            byText,
            modify_2,
            byDrag,
            modify_3,
            editName,
            modify_4,
            closeNodeButton);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 350, 415);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
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
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }
        });

    byText.setOnMouseClicked(
        event -> {
          stage.close();
          try {
            modifyNodeByInput();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
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
          try {
            modifyName();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    closeNodeButton.setOnMouseClicked(
        event -> {
          stage.close();
          mapMode = HandleMapModes.CLOSE;
          closeMode();
        });
  }

  public void closeMode() {
    nodeClicked = false;
    closeMode = true;
    NodeStatusDao nodeStatusDao = new NodeStatusDao();
    currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
    currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
    group.setOnMouseClicked(
        e -> {
          if (nodeClicked) {
            checkAndX_HBox.setVisible(true);
            checkAndX_HBox1.setVisible(true);
            checkAndX_HBox.setMouseTransparent(false);
            checkAndX_HBox1.setMouseTransparent(false);
            closeModeHelper.addToList(currNodeClicked, currCircleClicked);
            if (Objects.equals(currNodeClicked.getStatus(), NODE_STATUS.CLOSED)) {
              currCircleClicked.setFill(Paint.valueOf("#14FF03"));
              currCircleClicked.setStroke(Paint.valueOf("#14FF03"));
            } else {
              currCircleClicked.setFill(Paint.valueOf("#FE2300"));
              currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            }
            nodeClicked = false;
          }
        });
    check_button.setOnMouseClicked(
        e -> {
          for (Node node : closeModeHelper.getToClose()) {
            if (Objects.equals(node.getStatus(), NODE_STATUS.OPEN)) {
              node.setStatus(NODE_STATUS.CLOSED);
              HospitalSystem.updateRow(node);
            } else {
              node.setStatus(NODE_STATUS.OPEN);
              HospitalSystem.updateRow(node);
            }
          }
          loadNodeIDToNode();
          loadDatabase();
          loadNodeIDToNodeStatus();
          sortEdges();
          sortNodes();
          placeEdges(floor);
          placeNodes(floor);
          checkAndX_HBox.setVisible(false);
          checkAndX_HBox1.setVisible(false);
          checkAndX_HBox.setMouseTransparent(true);
          checkAndX_HBox1.setMouseTransparent(true);
          mapMode = HandleMapModes.MODIFY;
          lockMap = false;
          nodeClicked = false;
          group.setOnMouseClicked(initGroupOnMouseClicked);
        });
    x_button.setOnMouseClicked(
        xEvent -> {
          for (Circle circle : closeModeHelper.getCircToClose()) {
            circle.setFill(Paint.valueOf("#13DAF7"));
            circle.setStroke(Paint.valueOf("#13DAF7"));
          }
          checkAndX_HBox.setVisible(false);
          checkAndX_HBox1.setVisible(false);
          checkAndX_HBox.setMouseTransparent(true);
          checkAndX_HBox1.setMouseTransparent(true);
          mapMode = HandleMapModes.MODIFY;
          lockMap = false;
          nodeClicked = false;
          group.setOnMouseClicked(initGroupOnMouseClicked);
        });
  }

  public void modifyNodeByInput() throws IOException {
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
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
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
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }
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

  public void removeMenu()
      throws IOException { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Remove Selection Menu");
    Text remove_1 = new Text("Remove Node?");
    Text remove_2 = new Text("Remove Node Location Name?");
    MFXButton removeNode = new MFXButton("Remove Node");
    MFXButton removeName = new MFXButton("Remove Name");

    // set styles
    headerText.getStyleClass().add("Header");
    remove_1.getStyleClass().add("Text");
    remove_2.getStyleClass().add("Text");
    removeNode.getStyleClass().add("MFXbutton");
    removeName.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 40;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    remove_1.setLayoutX(lay_x);
    remove_1.setLayoutY(lay_y + 35);
    removeNode.setLayoutX(lay_x);
    removeNode.setLayoutY(lay_y + 50);
    remove_2.setLayoutX(lay_x);
    remove_2.setLayoutY(lay_y + 120);
    removeName.setLayoutX(lay_x);
    removeName.setLayoutY(lay_y + 135);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, remove_1, removeNode, remove_2, removeName);

    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 325, 260);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
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
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }
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
          LocationNameDao locationDao = new LocationNameDao();
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

  public void moveMenu() { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    //    VBox vBox = new VBox();
    //    Text remove_1 = new Text("move Node?");
    //    Text remove_2 = new Text("move Node Location Name");
    //    MFXButton moveNode = new MFXButton("move Node");
    //    MFXButton moveName = new MFXButton("move Name");
    Text headerText = new Text("Select Move Method");
    Text moveByNodeID = new Text("Node ID to move to");
    Text moveByLocationName = new Text("Name of location to move to");
    MFXButton byNode = new MFXButton("By node ID");
    MFXButton byLocationName = new MFXButton("By location name");

    Text dateText = new Text("Select a Date for the Move to Occur");
    DatePicker datePicker = new DatePicker();
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");

    confirmButton.getStyleClass().add("MFXbutton");
    datePicker.getStyleClass().add("DatePicker");
    cancelButton.getStyleClass().add("MFXbutton");
    dateText.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    dateText.setLayoutX(lay_x);
    dateText.setLayoutY(lay_y);
    datePicker.setLayoutX(lay_x);
    datePicker.setLayoutY(lay_y + 25);
    confirmButton.setLayoutX(lay_x);
    confirmButton.setLayoutY(lay_y + 70);
    cancelButton.setLayoutX(lay_x);
    cancelButton.setLayoutY(lay_y + 120);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(dateText, datePicker, confirmButton, cancelButton);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 450, 225);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Move Window");
    stage.show();
    stage.setAlwaysOnTop(true);

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
          //          secondNodeClicked = false;
          moveHelper.setNodesClicked(0);

          // load hashmap of status versus nodeID
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }

          // load hashmap of status versus nodeID
          NODE_STATUS status_helper = moveHelper.getNode().getStatus();

          // set color of nodes based on if they are closed or open
          if (status_helper.equals(NODE_STATUS.OPEN)) {
            moveHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
            moveHelper.getCircle().setStroke(Paint.valueOf("#13DAF7"));
          } else {
            moveHelper.getCircle().setStroke(Paint.valueOf("#13DAF7"));
            moveHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
          }
        });

    MoveDao moveDao = new MoveDao();

    confirmButton.setOnMouseClicked(
        event -> {
          Move move = new Move();
          move.setNodeID(moveHelper.getNode().getNodeID());
          move.setLongName(currNodeLongname);
          move.setDate(Date.valueOf(datePicker.getValue()));
          HospitalSystem.addRow(move);

          //        secondNodeClicked = false;
          moveHelper.setNodesClicked(0);
          lockMap = false;
          nodeClicked = false;
          currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
          currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          moveHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
          moveHelper.getCircle().setStroke(Paint.valueOf("#13DAF7"));
          stage.close();
        });

    cancelButton.setOnMouseClicked(
        event -> {
          lockMap = false;
          nodeClicked = false;
          moveHelper.setNodesClicked(0);
          currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
          currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          moveHelper.getCircle().setFill(Paint.valueOf("#13DAF7"));
          moveHelper.getCircle().setStroke(Paint.valueOf("#13DAF7"));
          stage.close();
        });

    // TODO fix
    //    byNode.setOnMouseClicked(
    //        event -> {
    //          //          MoveDao moveDao = new MoveDao();
    //          NodeDao nodeDao = new NodeDao();
    //          moveDao.deleteRow(currNodeClicked.getNodeID());
    //          nodeDao.deleteRow(currNodeClicked.getNodeID());
    //
    //          group.getChildren().removeAll(mapNodes, mapText);
    //          mapNodes = new Group();
    //          mapText = new Group();
    //          group.getChildren().addAll(mapNodes, mapText);
    //          loadDatabase();
    //          loadNodeIDToNode();
    //          sortNodes();
    //          sortEdges();
    //          placeEdges(floor);
    //          placeNodes(floor);
    //
    //          stage.close();
    //          nodeClicked = false;
    //          lockMap = false;
    //        });
    //    // TODO fix
    //    byLocationName.setOnMouseClicked(
    //        event -> {
    //          //          MoveDao moveDao = new MoveDao();
    //          LocationNameDao locationDao = new LocationNameDao();
    //          long currentTime = System.currentTimeMillis();
    //          Date currentDate = new Date(currentTime);
    //          Move move = new Move(currNodeClicked.getNodeID(), currNodeLongname, currentDate);
    //          moveDao.deleteRow(move);
    //          locationDao.deleteRow(currNodeLongname);
    //
    //          group.getChildren().removeAll(mapNodes, mapText);
    //          mapNodes = new Group();
    //          mapText = new Group();
    //          group.getChildren().addAll(mapNodes, mapText);
    //          loadDatabase();
    //          sortNodes();
    //          placeNodes(floor);
    //
    //          stage.close();
    //          nodeClicked = false;
    //          lockMap = false;
    //        });
  }

  public void addMenu()
      throws IOException { // make this a pop up window instead of a whole new scene?
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
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());

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
          // load hashmap of status versus nodeID
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }
        });

    addName.setOnMouseClicked(
        event -> {
          LocationNameDao locationNameDao = new LocationNameDao();
          MoveDao moveDao = new MoveDao();
          LocationName locationName =
              new LocationName(lNameInput.getText(), sNameInput.getText(), nodeTypeInput.getText());

          long currentTime = System.currentTimeMillis();
          Date currentDate = new Date(currentTime);
          Move move = new Move(currNodeClicked.getNodeID(), lNameInput.getText(), currentDate);
          locationNameDao.addRow(locationName);
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

  public void modifyName()
      throws IOException { // make this a pop up window instead of a whole new scene?
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();

    Text headerText = new Text("Modify Location Name");
    Text nodeType = new Text("Input new Node Type");
    Text LName = new Text("Input new Longname");
    Text SName = new Text("Input new Shortname"); // need current longname of current node

    MFXTextField nodeTypeInput = new MFXTextField();
    MFXTextField lNameInput = new MFXTextField();
    MFXTextField sNameInput = new MFXTextField();

    MFXButton modifyName = new MFXButton("Modify Name");

    vBox.getChildren()
        .addAll(
            headerText, nodeType, nodeTypeInput, SName, sNameInput, LName, lNameInput, modifyName);

    // set styles
    headerText.getStyleClass().add("Header");
    nodeType.getStyleClass().add("Text");
    LName.getStyleClass().add("Text");
    SName.getStyleClass().add("Text");
    nodeTypeInput.getStyleClass().add("MFXtextIn");
    lNameInput.getStyleClass().add("MFXtextIn");
    sNameInput.getStyleClass().add("MFXtextIn");
    modifyName.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 40;
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
    modifyName.setLayoutX(lay_x);
    modifyName.setLayoutY(lay_y + 250);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane
        .getChildren()
        .addAll(
            headerText, nodeType, nodeTypeInput, SName, sNameInput, LName, lNameInput, modifyName);

    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 295, 360);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    stage.setScene(scene);
    stage.setTitle("Add Location Name Window");
    stage.setAlwaysOnTop(true);
    stage.show();

    // When stage closed with inherit x, will unlock map and understand a node is no longer selected
    stage.setOnCloseRequest(
        event -> {
          lockMap = false;
          nodeClicked = false;
          NODE_STATUS status = currNodeClicked.getStatus();

          // set color of nodes based on if they are closed or open
          if (status.equals(NODE_STATUS.OPEN)) {
            currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
            currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
          } else {
            currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
            currCircleClicked.setFill(Paint.valueOf("#FE2300"));
          }
        });

    modifyName.setOnMouseClicked(
        event -> {
          LocationNameDao locationNameDao = new LocationNameDao();

          // If nodeType entered is not equal to 4 characters, assign the nodeType as HALL
          String nodeType_t = nodeTypeInput.getText();
          if (!(nodeType_t.length() == 4)) { // Fix later
            nodeType_t = "HALL";
          }

          // Add to LocationName and Move Tables
          LocationName locationName =
              new LocationName(lNameInput.getText(), sNameInput.getText(), nodeType_t);
          locationNameDao.updateRow(currNodeLongname, locationName);

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

  public void alignNodes() {
    //    checkAndX_HBox.setVisible(true);
    //    checkAndX_HBox1.setVisible(true);
    //    checkAndX_HBox.setMouseTransparent(false);
    //    checkAndX_HBox1.setMouseTransparent(false);
    alignModeHelper.addToList(currNodeClicked, currCircleClicked);
    currCircleClicked.setFill(Paint.valueOf("#EAB334"));
    //    checkAndX_HBox.setVisible(true);
    //    checkAndX_HBox1.setVisible(true);
    //    checkAndX_HBox.setMouseTransparent(false);
    //    checkAndX_HBox1.setMouseTransparent(false);
    nodeClicked = false;
    alignMode = true;
    initGroupOnMouseClicked = group.getOnMouseClicked();
    group.setOnMouseClicked(
        e -> {
          if (e.isAltDown()) {
            BorderPane borderPane = new BorderPane();

            // Stuff to show on pop up
            Text headerText = new Text("Undo Alignment?");
            MFXButton confirm = new MFXButton("Confirm");
            MFXButton cancel = new MFXButton("Cancel");

            // set styles
            headerText.getStyleClass().add("Header");
            confirm.getStyleClass().add("MFXbutton");
            cancel.getStyleClass().add("MFXbutton");
            borderPane.getStyleClass().add("scenePane");

            // set object locations
            int lay_x = 45;
            int lay_y = 40;
            headerText.setLayoutX(lay_x);
            headerText.setLayoutY(lay_y);
            confirm.setLayoutX(lay_x);
            confirm.setLayoutY(lay_y + 30);
            cancel.setLayoutX(lay_x);
            cancel.setLayoutY(lay_y + 90);

            // Set and show screen
            AnchorPane aPane = new AnchorPane();
            aPane.getChildren().addAll(headerText, confirm, cancel);
            //    Insets insets = new Insets(0, 0, 0, 200);
            //    aPane.setPadding(insets);
            borderPane.getChildren().add(aPane);
            Scene scene = new Scene(borderPane, 290, 220);
            scene
                .getStylesheets()
                .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
            borderPane.relocate(0, 0);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Undo Alignment Window");
            stage.setAlwaysOnTop(true);
            stage.show();
            EdgeDao edgeDao = new EdgeDao();

            stage.setOnCloseRequest(
                event -> {
                  stage.close();
                  lockMap = false;
                  group.setOnMouseClicked(initGroupOnMouseClicked);
                });
            confirm.setOnMouseClicked(
                event -> {
                  NodeDao nodeDao = new NodeDao();
                  for (Node node : alignModeHelper.getLastAlignedNode()) {
                    nodeDao.updateRow(node.getNodeID(), node);
                    System.out.println(node.getXCoord() + " " + node.getYCoord());
                  }

                  alignModeHelper.getLastAlignedNode().clear();
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
                  lockMap = false;
                  stage.close();
                  group.setOnMouseClicked(initGroupOnMouseClicked);
                });
            cancel.setOnMouseClicked(
                event -> {
                  stage.close();
                  group.setOnMouseClicked(initGroupOnMouseClicked);
                });
          } else if (nodeClicked) {
            checkAndX_HBox.setVisible(true);
            checkAndX_HBox1.setVisible(true);
            checkAndX_HBox.setMouseTransparent(false);
            checkAndX_HBox1.setMouseTransparent(false);
            alignModeHelper.addToList(currNodeClicked, currCircleClicked);
            currCircleClicked.setFill(Paint.valueOf("#EAB334"));
            nodeClicked = false;
          }
        });
    check_button.setOnMouseClicked(
        e -> {
          alignVert = false;
          alignHoriz = false;
          BorderPane borderPane = new BorderPane();

          // Stuff to show on pop up
          Text headerText = new Text("Choose a method of aligning the nodes selected. Then, click");
          Text headerText2 = new Text("anywhere on the map to align the nodes about that point.");
          //      Text remove_1 = new Text("Remove Node?");
          //      Text remove_2 = new Text("Remove Node Location Name?");
          MFXButton vertical = new MFXButton("Align Vertically");
          MFXButton horizontal = new MFXButton("Align Horizontally");

          // set styles
          headerText.getStyleClass().add("Header2");
          headerText2.getStyleClass().add("Header2");
          //      remove_1.getStyleClass().add("Text");
          //      remove_2.getStyleClass().add("Text");
          vertical.getStyleClass().add("MFXbutton");
          horizontal.getStyleClass().add("MFXbutton");
          borderPane.getStyleClass().add("scenePane");

          // set object locations
          int lay_x = 35;
          int lay_y = 40;
          headerText.setLayoutX(lay_x);
          headerText.setLayoutY(lay_y);
          headerText2.setLayoutX(lay_x);
          headerText2.setLayoutY(lay_y + 26);
          vertical.setLayoutX(lay_x + 50);
          vertical.setLayoutY(lay_y + 55);
          horizontal.setLayoutX(lay_x + 350);
          horizontal.setLayoutY(lay_y + 55);

          // Set and show screen
          AnchorPane aPane = new AnchorPane();
          aPane.getChildren().addAll(headerText, headerText2, vertical, horizontal);

          borderPane.getChildren().add(aPane);
          Scene scene = new Scene(borderPane, 670, 190);
          scene
              .getStylesheets()
              .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
          borderPane.relocate(0, 0);
          Stage stage = new Stage();
          stage.setScene(scene);
          stage.setTitle("Align Window");
          stage.setAlwaysOnTop(true);
          stage.show();

          // When stage closed with inherit x, will unlock map and understand a node is no longer
          // selected
          stage.setOnCloseRequest(
              event -> {
                lockMap = false;
                nodeClicked = false;
                group.setOnMouseClicked(initGroupOnMouseClicked);
                NODE_STATUS status = currNodeClicked.getStatus();

                // set color of nodes based on if they are closed or open
                if (status.equals(NODE_STATUS.OPEN)) {
                  currCircleClicked.setFill(Paint.valueOf("#13DAF7"));
                  currCircleClicked.setStroke(Paint.valueOf("#13DAF7"));
                } else {
                  currCircleClicked.setStroke(Paint.valueOf("#FE2300"));
                  currCircleClicked.setFill(Paint.valueOf("#FE2300"));
                }
                int i = 0;

                for (Node node : alignModeHelper.getToAlign()) {
                  status = node.getStatus();
                  if (status.equals(NODE_STATUS.OPEN)) {
                    alignModeHelper.getCircToAlign().get(i).setFill(Paint.valueOf("#13DAF7"));
                    alignModeHelper.getCircToAlign().get(i).setStroke(Paint.valueOf("#13DAF7"));
                  } else {
                    alignModeHelper.getCircToAlign().get(i).setStroke(Paint.valueOf("#FE2300"));
                    alignModeHelper.getCircToAlign().get(i).setFill(Paint.valueOf("#FE2300"));
                  }
                  i++;
                }
                i = 0;
                checkAndX_HBox.setVisible(false);
                checkAndX_HBox1.setVisible(false);
                checkAndX_HBox.setMouseTransparent(true);
                checkAndX_HBox1.setMouseTransparent(true);
              });

          vertical.setOnMouseClicked(
              vEvent -> {
                alignVert = true;
                stage.close();
                checkAndX_HBox.setVisible(false);
                checkAndX_HBox1.setVisible(false);
                checkAndX_HBox.setMouseTransparent(true);
                checkAndX_HBox1.setMouseTransparent(true);
                findAlignCenter();
              });
          horizontal.setOnMouseClicked(
              hEvent -> {
                alignHoriz = true;
                stage.close();
                checkAndX_HBox.setVisible(false);
                checkAndX_HBox1.setVisible(false);
                checkAndX_HBox.setMouseTransparent(true);
                checkAndX_HBox1.setMouseTransparent(true);
                findAlignCenter();
              });
        });
    x_button.setOnMouseClicked(
        xEvent -> {
          for (Circle circle : alignModeHelper.getCircToAlign()) {
            circle.setFill(Paint.valueOf("#13DAF7"));
          }
          checkAndX_HBox.setVisible(false);
          checkAndX_HBox1.setVisible(false);
          checkAndX_HBox.setMouseTransparent(true);
          checkAndX_HBox1.setMouseTransparent(true);
          //          mapMode = HandleMapModes.SELECT;
          ////          alignMode = false;
          lockMap = false;
          group.setOnMouseClicked(initGroupOnMouseClicked);
          //          nodeClicked = false;
        });
  }

  public void findAlignCenter() {
    //    int alignX = 0;
    //    int alignY = 0;
    group.setOnMouseClicked(
        e -> {
          if (alignVert) {
            //            alignModeHelper.setAlignX((int) e.getX());
            alignVertically((int) e.getX());
          }
          if (alignHoriz) {
            //            alignModeHelper.setAlignY((int) e.getY());
            alignHorizontally((int) e.getY());
          }
        });
  }

  public void alignVertically(int x) {
    NodeDao nodeDao = new NodeDao();
    alignModeHelper.setLastAlignedCirc(alignModeHelper.getCircToAlign());
    alignModeHelper.setLastAlignedNode(alignModeHelper.getToAlign());
    for (Node node : alignModeHelper.getToAlign()) {
      //      node.setXCoord(alignModeHelper.getAlignX());
      node.setXCoord(x);
      nodeDao.updateRow(node.getNodeID(), node);
    }
    alignModeHelper.getToAlign().clear();
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
    group.setOnMouseClicked(initGroupOnMouseClicked);
    lockMap = false;
    //    alignNodes();
  }

  public void alignHorizontally(int y) {
    NodeDao nodeDao = new NodeDao();
    alignModeHelper.setLastAlignedCirc(alignModeHelper.getCircToAlign());
    alignModeHelper.setLastAlignedNode(alignModeHelper.getToAlign());
    for (Node node : alignModeHelper.getToAlign()) {
      //      node.setYCoord(alignModeHelper.getAlignY());
      node.setYCoord(y);
      nodeDao.updateRow(node.getNodeID(), node);
    }
    alignModeHelper.getToAlign().clear();
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
    group.setOnMouseClicked(initGroupOnMouseClicked);
    lockMap = false;
    //    alignNodes();
  }

  public void modifyByDrag() { // make this a pop up window instead of a whole new scene?
    checkAndX_HBox.setVisible(true);
    checkAndX_HBox1.setVisible(true);
    checkAndX_HBox.setMouseTransparent(false);
    checkAndX_HBox1.setMouseTransparent(false);
    Node helperNode1 = new Node(0, 5, 5, "test", "test");
    Node helperNode2 = new Node(20000, 5, 5, "test", "test");
    nodeToDrag = currNodeClicked;
    draggedNode = currNodeClicked;
    draggedNode.setNodeID(0);

    EventHandler<? super MouseEvent> eventHandlerDrag = group.getOnMouseDragged();
    EventHandler<? super MouseEvent> eventHandlerRel = group.getOnMouseReleased();
    NodeDao nodeDao = new NodeDao();

    // update node as drag occurs
    group.setOnMouseDragged( // setOnMouseDragged
        dragEvent -> {
          if (dragEvent.isAltDown()) {
            mapGPane.setGestureEnabled(true);
          } else {
            mapGPane.setGestureEnabled(false);

            if (movingNodeClicked) { // remove if want to click anywhere and move mouse to update
              mapModeSaver.setDraggingNodeCreated(true);
              mapModeSaver.setEventCoords((int) dragEvent.getX(), (int) dragEvent.getY());

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
              movingText.setVisible(shortShown);
            }
          }
        });

    group.setOnMouseReleased(
        event -> {
          mapGPane.setGestureEnabled(true);
          currNodeClicked = helperNode2; // necessary still?
          movingNodeClicked =
              false; // remove if want to move mouse anywhere, click and drag and node to follow
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
          group.getChildren().remove(mapText);
          mapText.setVisible(shortShown);
          group.getChildren().add(mapText);

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
        String nodeHeader = "nodeID,xcoord,ycoord,floorNum,building,status";
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
    ImportCSV.importMapCSV(nodesFilePath, edgesFilePath, moveFilePath, locationNamesFilePath);
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

  //  public String getText(ActionEvent actionEvent) {
  //    String inputtedText;
  //    inputtedText = inputBox.getText();
  //    inputBox.clear();
  //    return inputtedText;
  //  }

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

/*
Close method for submitting a nodes status as closed is working, more to add to it such as opening the node if it is clicked on and closed, making a notification with it, and sending to pathfinding to be ignored, and doing something with edges here as well
 */
