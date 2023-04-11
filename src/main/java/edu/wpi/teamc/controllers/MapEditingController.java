package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.map.Edge;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.dao.map.NodeDao;
import edu.wpi.teamc.mapHelpers.CoordinatePasser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.tableview2.FilteredTableView;

public class MapEditingController {
  public Group group;
  public Image image =
      new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());

  /*
  Notes:
  * implement import and export buttons and get rid of navigation actions
  * implement a submit button that submits all edits at once
      * maybe store edits and their indices in an array and iterate over that array after the submit
      * button is pressed to update the move list from the database
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
  CoordinatePasser cordPasser = new CoordinatePasser();

  //  @FXML TableColumn<TableRow, String> ColumnOne1;
  //  @FXML TableColumn<TableRow, String> ColumnTwo1;
  //  @FXML TableColumn<TableRow, String> ColumnThree1;
  //  @FXML TableView<TableRow> testTable;
  ObservableList<TableRow> rows = FXCollections.observableArrayList();
  ObservableList<TableRow> rowsEdge = FXCollections.observableArrayList();

  @FXML private Button goHome;
  int XCoord = 0;
  int YCoord = 0;
  String xCoord;
  String yCoord;
  String iD;
  String building;
  String floor = "G";
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

    //    mapGPane.setOnMouseDragged(
    //        mouseEvent -> {
    //          setMouseCoordinates(mouseEvent);
    //        });
    //    mapGPane.setOnMouseDragReleased(
    //        mouseEvent -> {
    //          setMouseCoordinates(mouseEvent);
    //        });
  }

  //  public void setMouseCoordinates(MouseEvent e) {
  //    Affine invMatrix = null;
  //    try {
  //      invMatrix = mapGPane.getAffine().createInverse();
  //    } catch (NonInvertibleTransformException nonInvertibleTransformException) {
  //      //      nonInvertibleTransformException.printStackTrace();
  //    }
  //    Point2D realPoint = invMatrix.transform(e.getX(), e.getY());
  //
  //    double x = (realPoint.getX()) + mapGPane.getLayoutX();
  //    double y = (realPoint.getY()) + mapGPane.getLayoutY();
  //    cordPasser.setXcoord(x);
  //    cordPasser.setYCoord(y);
  //  }

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
  } // initialize end

  public void placeNodes(String floor) {
    NodeDao nodeDao = new NodeDao();
    nodeDao
        .fetchAllObjects()
        .forEach(
            TBP_node -> {
              if (Objects.equals(TBP_node.getFloor(), floor)) {
                createMapNodes(TBP_node);
              }
            });
    mapNodes.toFront();
  }
  //  public void placeNodes(String floor, Node node) {
  //
  //  }

  public void createMapNodes(Node node) {
    String shortname = new NodeDao().getShortName(node.getNodeID());
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
  //    public EventHandler<javafx.event.ActionEvent> submitNewNode(){
  //
  //    }
  //  public void submitNewNode() {
  //    submitNode.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  //
  //
  //  }
  public void showNodeMenu(ActionEvent event) {
    BorderPane borderPane = new BorderPane();
    HBox hBox = new HBox();

    VBox middleBox = new VBox(); // modify
    Text nodeID_M = new Text();
    Text xCoord = new Text();
    xCoord.setText("Input new Xcoord");
    Text yCoord = new Text();
    yCoord.setText("Input new YCoord");
    MFXTextField nodeIDText = new MFXTextField();
    MFXTextField xCoord_text = new MFXTextField();
    MFXTextField yCoord_text = new MFXTextField();
    MFXButton submitModification = new MFXButton();

    VBox rightBox = new VBox();
    Text nodeID_R = new Text();
    MFXButton submitRemove = new MFXButton();

    VBox textBoxes = new VBox();
    Text XCoordText = new Text();
    Text YCoordText = new Text();
    //    Text IDText = new Text();
    Text BuildingText = new Text();
    //    TextArea SubmitText = new TextArea();
    XCoordText.setText("Input X Coordinate");
    YCoordText.setText("Input Y Coordinate");
    //    IDText.setText("Input Node ID");
    BuildingText.setText("Input Building Name");

    MFXTextField inputXCoord = new MFXTextField();
    MFXTextField inputYCoord = new MFXTextField();
    //    MFXTextField inputID = new MFXTextField();
    MFXTextField inputBuilding = new MFXTextField(); // need floor as well
    MFXButton submitNode = new MFXButton();
    submitNode.setId("submitNode");
    //    submitNode.setOnAction(submitNewNode(submitNode.onActionProperty()));

    submitNode.setText("Submit Node");
    submitNode.setPrefSize(100, 35);
    submitNode.setMinSize(100, 35);
    //    inputXCoord.setPrefSize(30, 30);
    //    inputXCoord.setBorderGap(20);
    textBoxes
        .getChildren()
        .addAll(
            XCoordText,
            inputXCoord,
            YCoordText,
            inputYCoord,
            BuildingText,
            inputBuilding,
            submitNode);
    //    textBoxes.setSpacing(5);
    //    textBoxes.setAlignment(Pos.CENTER);
    //    textBoxes.relocate(0,0);
    borderPane.getChildren().add(textBoxes);
    textBoxes.relocate(0, 0);
    Scene scene = new Scene(borderPane, 600, 400);
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add Node Window");

    stage.show();

    submitNode.setOnMouseClicked(
        buttonEvent -> {
          xCoord = inputXCoord.getText();
          yCoord = inputYCoord.getText();
          //          iD = inputID.getText();
          building = inputBuilding.getText(); // maybe set automatically later
          Node newNode =
              new Node(Integer.valueOf(xCoord), Integer.valueOf(yCoord), floor, building);
          NodeDao nodeDao = new NodeDao();
          nodeDao.addRow(newNode);
          placeNodes(
              floor); // later implement an update map button that updates all changes made at once
          // so
          // user can submit multiple at a time
          System.out.println("printed the new node");
        });
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public ObservableList<TableRow> gettableRows(
      List<LocationName> locationNameList, List<Edge> edgeList, List<Node> nodeList) {
    String nodeID;
    String longName;
    String date;
    LocationName currLocNameList;
    Edge currEdgeList;
    Node currNodeList;
    //    String startNode;
    //    String endNode;
    String nodeType;
    String xCoord;
    String yCoord;
    String floorNum;
    String building;
    String shortName;
    int index = -1;
    for (int i = 0; i < locationNameList.size(); i++) {
      currLocNameList = locationNameList.get(i);
      currEdgeList = edgeList.get(i);
      currNodeList = nodeList.get(i);

      //      nodeID = currLocNameList.get;
      longName = currLocNameList.getLongName();
      shortName = currLocNameList.getShortName();
      nodeType = currLocNameList.getNodeType();
      //      startNode = currEdgeList.getStartNode().getNodeID();
      //      endNode = currEdgeList.getEndNode().getNodeID();
      nodeID = String.valueOf(currNodeList.getNodeID());
      xCoord = String.valueOf(currNodeList.getXCoord());
      yCoord = String.valueOf(currNodeList.getYCoord());
      floorNum = currNodeList.getFloor();
      building = currNodeList.getBuilding();
      index++;
      rows.add(new TableRow(nodeID, xCoord, yCoord, floorNum, building, longName, nodeType, index));
    }
    return rows;
  }

  public ObservableList<TableRow> gettableRowsEdge(List<Edge> edgeList) {
    Edge currEdgeList;
    String startNode;
    String endNode;
    int index = -1;
    for (int i = 0; i < edgeList.size(); i++) {
      startNode = String.valueOf(edgeList.get(i).getStartNode());
      endNode = String.valueOf(edgeList.get(i).getEndNode());
      index++;
      rowsEdge.add(new TableRow(startNode, endNode, index));
    }
    return rowsEdge;
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
}
