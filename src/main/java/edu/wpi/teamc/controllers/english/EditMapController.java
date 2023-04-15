package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.controllers.english.MapHelpers.HandleMapModes;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.*;
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
  @FXML MFXButton FLG;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton floorButton;
  @FXML MFXButton modeButton;
  Group mapNodes = new Group();
  Group mapText = new Group();
  private Desktop desktop = Desktop.getDesktop();
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
  String floor = "G";
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
  HashMap<Integer, Move> nodeIDtoMove = new HashMap<Integer, Move>();
  HashMap<String, LocationName> longNametoLocationName = new HashMap<String, LocationName>();

  List<Move> moveList = new ArrayList<Move>();
  List<Node> Floor1 = new ArrayList<Node>();
  List<Node> Floor2 = new ArrayList<Node>();
  List<Node> Floor3 = new ArrayList<Node>();
  List<Node> FloorG = new ArrayList<Node>();
  List<Node> FloorL1 = new ArrayList<Node>();
  List<Node> FloorL2 = new ArrayList<Node>();

  //  List<String> Floor1Name = new ArrayList<String>();
  //  List<String> Floor2Name = new ArrayList<String>();
  //  List<String> Floor3Name = new ArrayList<String>();
  //  List<String> FloorGName = new ArrayList<String>();
  //  List<String> FloorL1Name = new ArrayList<String>();
  //  List<String> FloorL2Name = new ArrayList<String>();

  String sNameInput_temp;
  String lNameInput_temp;
  String oldName_temp;
  String nodeIDinput_temp;
  StackPane stackPane = new StackPane();
  Boolean nodeClicked = false;
  HandleMapModes mapMode;
  Boolean addClicked = false;
  Boolean modifyClicked = false;
  //  Boolean

  /** Method run when controller is initialized */
  public void initialize() {

    Image image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);

    group.getChildren().add(mapNodes);
    group.getChildren().add(mapText);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    mapMode = HandleMapModes.SELECT;
    //    group.getChildren().add(stackPane);

    group.setOnMouseClicked(
        e -> {
          mouseX = e.getX();
          mouseY = e.getY();
          System.out.println(mouseX + "  " + mouseY);
          //          if(addClicked) //dont need this bc when add is clicked will update mapMode to
          // Add, need to set that up

          if (Objects.equals(mapMode.getMapMode(), "Add")) {
            addNodeByMouseLoc((int) mouseX, (int) mouseY);
          } // bring up node add popup

          if (nodeClicked) {
            if (Objects.equals(mapMode.getMapMode(), "Add")) {
            } // bring up location name add popup
            else if (Objects.equals(mapMode.getMapMode(), "Modify")) {
            } // bring up modify popup
            else if (Objects.equals(mapMode.getMapMode(), "Remove")) {
            } // bring up remove popup
            // if modify, first popup asks if you want to modify node by drag or by entering?
            // also if modify pop up asks if you want to modify node or name
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
    sortNodes();
    placeNodes("G");
  } // end initialize
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
    modeButton = (MFXButton) event.getTarget();
    if (Objects.equals(modeButton.getId(), "Select")) {
      mapMode = HandleMapModes.SELECT;
    } else if (Objects.equals(modeButton.getId(), "Add")) {
      mapMode = HandleMapModes.ADD;
    } else if (Objects.equals(modeButton.getId(), "Modify")) {
      mapMode = HandleMapModes.MODIFY;
    } else if (Objects.equals(modeButton.getId(), "Remove")) {
      mapMode = HandleMapModes.REMOVE;
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
    group.getChildren().removeAll();
    group.getChildren().remove(mapNodes);
    group.getChildren().remove(mapText);
    //    stackPane.getChildren().remove(mapNodes);
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    mapText = new Group();
    group.getChildren().add(imageView);
    //    stackPane.getChildren().add(mapNodes);
    group.getChildren().add(mapNodes);
    group.getChildren().add(mapText);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
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
            nodeIDtoMove.put(nodeID, new Move(nodeID, "ERROR", new Date(100)));
          }
          longName = nodeIDtoMove.get(nodeID).getLongName();
          String shortName = longNametoLocationName.get(longName).getShortName();
          String nodeType = longNametoLocationName.get(longName).getNodeType();
          createMapNodes(FloorL2.get(i), shortName, nodeType);
        }
    }
    //    stackPane.toFront();
    mapNodes.toFront();
    mapText.toFront();
    //    mapNodes.getChildren().s
  }

  public void createMapNodes(Node node, String shortname, String nodeType) {
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
    newCircle.setId(String.valueOf(node.getNodeID()));
    newCircle.setStroke(Paint.valueOf("#13DAF7"));
    newCircle.setFill(Paint.valueOf("#13DAF7"));
    newCircle.setVisible(true);
    text.setVisible(true);
    newCircle.setOnMouseClicked(
        e -> {
          nodeClicked = true; // clicked on a node
          System.out.println("circle clicked");
        });
    //    newCircle.setOnMouseDragEntered(
    //        e -> {
    //          mouseDragged = true;
    //        });

    mapNodes.getChildren().add(newCircle);
    mapText.getChildren().add(text);
  }

  public void addNodeByMouseLoc(int x, int y) {
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text building = new Text("Input name of building the node will be in");
    MFXTextField b_input = new MFXTextField();
    MFXButton addButton = new MFXButton("Submit Add");
    vBox.getChildren().addAll(building, b_input, addButton);

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
    stage.setTitle("Add Node Window");
    stage.show();

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
          group.getChildren().remove(mapNodes);
          mapNodes = new Group();
          group.getChildren().add(mapNodes);
          loadDatabase();
          sortNodes();
          placeNodes(floor);

          // close menu
          stage.close();
        });
  }

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
          System.out.println("printed the new node");
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
          //          NodeDao oldDao = new NodeDao();
          //              Node oldNode = oldDao.getNode(nodeID_temp); ////*******Need to add this
          // getter method
          //          NodeDao nodeDao = new NodeDao();
          n_toModify_newNode.add(newNode);
          n_toModify_oldID.add(nodeID_temp);
          //              nodeDao.updateRow(newNode, oldNode); //////////********
          //              placeNodes(
          //                      floor); // later implement an update map button that updates all
          // changes made at once
          // so
          // user can submit multiple at a time
          System.out.println("modified the node");
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

          System.out.println("removed the node");
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

          System.out.println("printed the new node");
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
          System.out.println("modified the name");
        });
    // remove
    submitRemove.setOnMouseClicked(
        buttonEvent -> {
          removeName = nameToBeRemoved.getText();
          nameToRemove.add(removeName);
          iD_r = iDToBeRemoved.getText();
          idList_r.add(iD_r);

          nameToBeRemoved.clear();
          System.out.println("removed the node");
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

  @FXML
  void getImportNodes(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        desktop.open(file);
        filePath = file.getAbsolutePath();
        InodeDao.importCSV(filePath);
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
        desktop.open(file);
        filePath = file.getAbsolutePath();
        IedgeDao.importCSV(filePath);
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
        desktop.open(file);
        filePath = file.getAbsolutePath();
        IlocationDao.importCSV(filePath);
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
        desktop.open(file);
        filePath = file.getAbsolutePath();
        ImoveDao.importCSV(filePath);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
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
