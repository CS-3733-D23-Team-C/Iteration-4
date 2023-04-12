package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.map.LocationDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.dao.map.NodeDao;
import edu.wpi.teamc.mapHelpers.CoordinatePasser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.tableview2.FilteredTableView;

public class EditMapController {
  public Group group;
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
  Group mapNodes = new Group();

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
  List<String> newNameToAdd = new ArrayList<String>();

  List<String> oldNameToModify = new ArrayList<String>();
  List<LocationName> newNameToModify = new ArrayList<LocationName>();
  List<String> nameToRemove = new ArrayList<String>();

  List<Node> Floor1 = new ArrayList<Node>();
  List<Node> Floor2 = new ArrayList<Node>();
  List<Node> Floor3 = new ArrayList<Node>();
  List<Node> FloorG = new ArrayList<Node>();
  List<Node> FloorL1 = new ArrayList<Node>();
  List<Node> FloorL2 = new ArrayList<Node>();

  List<String> Floor1Name = new ArrayList<String>();
  List<String> Floor2Name = new ArrayList<String>();
  List<String> Floor3Name = new ArrayList<String>();
  List<String> FloorGName = new ArrayList<String>();
  List<String> FloorL1Name = new ArrayList<String>();
  List<String> FloorL2Name = new ArrayList<String>();

  String sNameInput_temp;
  String lNameInput_temp;
  String oldName_temp;

  //  List<Node> databaseNodeList = new ArrayList<Node>();
  //  List<Edge> databaseEdgeList = new ArrayList<Edge>();
  //  List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
  //  List<Move> databaseMoveList = new ArrayList<Move>();

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
    placeNodes("G");
    sortNodes();
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
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    group.getChildren().add(imageView);
    group.getChildren().add(mapNodes);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    placeNodes(floor);
  }

  public void sortNodes() {
    NodeDao nodeDao = new NodeDao();
    nodeDao
        .fetchAllObjects()
        .forEach(
            TBP_node -> {
              if (Objects.equals(TBP_node.getFloor(), "1")) {
                Floor1.add(TBP_node);
                Floor1Name.add(nodeDao.getShortName(TBP_node.getNodeID()));
              } else if (Objects.equals(TBP_node.getFloor(), "2")) {
                Floor2.add(TBP_node);
                Floor2Name.add(nodeDao.getShortName(TBP_node.getNodeID()));
              } else if (Objects.equals(TBP_node.getFloor(), "3")) {
                Floor3.add(TBP_node);
                Floor3Name.add(nodeDao.getShortName(TBP_node.getNodeID()));
              } else if (Objects.equals(TBP_node.getFloor(), "G")) {
                FloorG.add(TBP_node);
                FloorGName.add(nodeDao.getShortName(TBP_node.getNodeID()));
              } else if (Objects.equals(TBP_node.getFloor(), "L1")) {
                FloorL1.add(TBP_node);
                FloorL1Name.add(nodeDao.getShortName(TBP_node.getNodeID()));
              } else if (Objects.equals(TBP_node.getFloor(), "L2")) {
                FloorL2.add(TBP_node);
                FloorL2Name.add(nodeDao.getShortName(TBP_node.getNodeID()));
              }
            });
  }

  public void placeNodes(String floor) {
    switch (floor) {
      case "1":
        for (int i = 0; i < Floor1.size(); i++) {
          createMapNodes(Floor1.get(i), Floor1Name.get(i));
        }
        break;
      case "2":
        for (int i = 0; i < Floor2.size(); i++) {
          createMapNodes(Floor2.get(i), Floor2Name.get(i));
        }
        break;
      case "3":
        for (int i = 0; i < Floor3.size(); i++) {
          createMapNodes(Floor3.get(i), Floor3Name.get(i));
        }
        break;
      case "G":
        for (int i = 0; i < FloorG.size(); i++) {
          createMapNodes(FloorG.get(i), FloorGName.get(i));
        }
        break;
      case "L1":
        for (int i = 0; i < FloorL1.size(); i++) {
          createMapNodes(FloorL1.get(i), FloorL1Name.get(i));
        }
        break;
      case "L2":
        for (int i = 0; i < FloorL2.size(); i++) {
          createMapNodes(FloorL2.get(i), FloorL2Name.get(i));
        }
    }
    mapNodes.toFront();
  }

  public void createMapNodes(Node node, String shortname) {
    Circle newCircle = new Circle();
    if (!shortname.equals("")) {
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
    //    submitNode.setText("Submit Node");
    submitNode.setPrefSize(100, 35);
    submitNode.setMinSize(100, 35);
    //    inputXCoord.setPrefSize(30, 30);
    //    inputXCoord.setBorderGap(20);
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
    //    textBoxes.setSpacing(5);
    //    textBoxes.setAlignment(Pos.CENTER);
    //    textBoxes.relocate(0,0);
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
          // user can submit multiple at a time
          System.out.println("removed the node");
        });
    // Submit
    submitNodeEdits.setOnMouseClicked(
        buttonEvent -> {
          NodeDao nodeDao = new NodeDao();

          // Add loop
          for (Node currNode : n_toAdd) {
            nodeDao.addRow(currNode);
          }
          // Modify loop
          for (int i = 0; i < n_toModify_oldID.size(); i++) {
            //            NodeDao nodeDao = new NodeDao();
            Node currNode = n_toModify_newNode.get(i);
            String oldID = n_toModify_oldID.get(i);
            int oldId_int = Integer.valueOf(oldID);
            nodeDao.updateRow(Integer.valueOf(oldID), currNode);
            //                nodeDao.getNodeFromID() ////////NEED TO CREATE THIS METHOD
            ///// REPLACE NODE METHOD
          }
          // Remove loop
          for (String currID : n_toRemove) {

            //                nodeDao.deleteRow(currID); ////NEED TO MAKE WORK WITH NODE ID ONLY AS
            // SUPPLIED
          }
          stage
              .close(); // no need to close when switching floors bc any new one submitted with have
          // a new floor assignment relating to the currently viewed floor
          group.getChildren().remove(mapNodes);
          mapNodes.getChildren().clear();
          group.getChildren().add(mapNodes);
          placeNodes(floor);
          // Delete node

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
    Text nodeID_R = new Text("Input ID of Node to be Removed");
    MFXTextField nodeID_RText = new MFXTextField();
    MFXButton submitRemove = new MFXButton("Remove");
    submitRemove.setPrefSize(100, 35);
    submitRemove.setMinSize(100, 35);
    removeBox.getChildren().addAll(nodeID_R, nodeID_RText, submitRemove);
    removeBox.setSpacing(20);

    // add
    VBox addBox = new VBox();
    //      Text XCoordText = new Text("Input X Coordinate");
    //      Text YCoordText = new Text("Input Y Coordinate");
    Text nodeID = new Text("Input NodeID");
    Text SName = new Text("Input New Shortname");
    Text LName = new Text("Input New Longname");

    //      Text BuildingText = new Text("Input Building Name");
    MFXTextField nodeIDinput = new MFXTextField();
    MFXTextField sNameInput = new MFXTextField();
    MFXTextField lNameInput = new MFXTextField();
    MFXButton submitNode = new MFXButton("Add");
    submitNode.setId("submitNode");
    //    submitNode.setText("Submit Node");
    submitNode.setPrefSize(100, 35);
    submitNode.setMinSize(100, 35);
    //    inputXCoord.setPrefSize(30, 30);
    //    inputXCoord.setBorderGap(20);
    addBox
        .getChildren()
        .addAll(nodeID, nodeIDinput, SName, sNameInput, LName, lNameInput, submitNode);
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

    // Add
    submitNode.setOnMouseClicked(
        buttonEvent -> {
          nodeID_temp = nodeIDinput.getText();
          sNameInput_temp = sNameInput.getText();
          lNameInput_temp = lNameInput.getText();
          //          *********FIND NODE USING NODE ID
          Node newNode = // /////////// get rid of this when find old node or make node using old x,
              // y, and building found
              new Node(Integer.valueOf(xCoord_temp), Integer.valueOf(yCoord_temp), floor, building);
          //          NodeDao nodeDao = new NodeDao();
          oldNameToAdd.add(newNode); // ///////////INSTEAD HAVE THIS BE THE OLD NODE FOUND USING ID
          newNameToAdd.add(nodeID_temp);
          //          nodeDao.addRow(newNode); to iterate over in submit method
          //          placeNodes(
          //              floor); // later implement an update map button that updates all changes
          // made at
          //   once
          // so user can submit multiple at a time
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
          iD = nodeID_RText.getText();
          nameToRemove.add(iD);
          //              placeNodes(
          //                      floor); // later implement an update map button that updates all
          // changes
          //   made at once
          // so
          // user can submit multiple at a time
          System.out.println("removed the node");
        });
    submitNodeEdits.setOnMouseClicked(
        buttonEvent -> {
          LocationDao locationDao = new LocationDao();

          // Add
          for (int i = 0; i < newNameToAdd.size(); i++) {
            Node currNode = oldNameToAdd.get(i);
            String currId = newNameToAdd.get(i);
            //            nodeDao.addRow(currNode); ///USE ADD NAME FUNCTION
          }
          // Modify
          for (int i = 0; i < oldNameToModify.size(); i++) {
            String currOldName = oldNameToModify.get(i);
            LocationName currNewName = newNameToModify.get(i);
            locationDao.updateRow(currOldName, currNewName);

            ///// METHOD TO REPLACE NAME OF NODE AND INPUT TO TABLE
          }
          for (String currID : nameToRemove) {
            //// METHOD TO FIND NODE IN DAO AND REMOVE IT BASED ON ID
            //                nodeDao.deleteRow(currID); ////NEED TO MAKE WORK WITH NODE ID ONLY AS
            // SUPPLIED
          }
          placeNodes(floor);
        });
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
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
  /////////////////////////////////////////////

}
