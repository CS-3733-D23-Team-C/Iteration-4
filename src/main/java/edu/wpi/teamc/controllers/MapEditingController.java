package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.map.Edge;
import edu.wpi.teamc.map.LocationName;
import edu.wpi.teamc.map.Node;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.controlsfx.control.tableview2.FilteredTableView;

public class MapEditingController {
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

  //  @FXML TableColumn<TableRow, String> ColumnOne1;
  //  @FXML TableColumn<TableRow, String> ColumnTwo1;
  //  @FXML TableColumn<TableRow, String> ColumnThree1;
  //  @FXML TableView<TableRow> testTable;
  ObservableList<TableRow> rows = FXCollections.observableArrayList();
  ObservableList<TableRow> rowsEdge = FXCollections.observableArrayList();

  @FXML private Button goHome;
  //  List<Node> databaseNodeList = new ArrayList<Node>();
  //  List<Edge> databaseEdgeList = new ArrayList<Edge>();
  //  List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
  //  List<Move> databaseMoveList = new ArrayList<Move>();

  /** Method run when controller is initialized */
  public void initialize() {
    // Allows cells to be identifiable
    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s1"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s2"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s3"));
    ColumnFour.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s4"));
    ColumnFive.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s5"));
    ColumnSix.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s6"));
    ColumnSeven.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s7"));

    ColumnEight.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s1"));
    ColumnNine.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s2"));

    // Allows cells to be editable
    //    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnFour.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnFive.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnSix.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnSeven.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnEight.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnNine.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    historyTable.setEditable(true);
    edgeTable.setEditable(true);
    edgeTable.getItems().setAll(gettableRowsEdge(Cdb.databaseEdgeList));
    //    historyTable.getOnMouseClicked();
    //    historyTable.getEditingCell();
    //    testTable.setEditable(true);
    //    ColumnOne.setEditable(true);
    //    ColumnTwo.setEditable(true);
    //    ColumnThree.setEditable(true);

    //    testTable.getItems().setAll(gettableRows(Cdb.databaseMoveList));
    Cdb.loadDatabaseTables(
        Cdb.databaseNodeList,
        Cdb.databaseEdgeList,
        Cdb.databaseLocationNameList,
        Cdb.databaseMoveList);
    historyTable
        .getItems()
        .setAll(
            gettableRows(
                Cdb.databaseLocationNameList,
                Cdb.databaseEdgeList,
                Cdb.databaseNodeList)); // Need to change from move list to whatever we actually
    // want here

    // here to be used to update databaseMoveList, needs to be implemented still
    //    List<Move> moveList = Cdb.databaseMoveList;

    // Allows cells to be edited and their edits to be saved and displayed in the table. If we do
    // not want the nodeID to be editable, delete this call for columnOne

    //    ColumnOne.setOnEditCommit(
    //        event -> {
    //          TableRow rowData = event.getRowValue();
    //          rowData.setNodeID(event.getNewValue());
    //        });

    // index is used to find the point in the databaseMoveList the updated node is from. Can
    // therefore
    // use the index to only edit that specific node in the databaseMoveList (instead of checking
    // for
    // nodeID matching because, for this, you would have to look within the nodeID of each and every
    // move object and find the matching one. This way you can just look for the matching index and
    // replace the proper move object at that index)

    ColumnTwo.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS2(event.getNewValue());
          Cdb.syncNodeDB(
              new Node(
                  rowData.getS1(),
                  Integer.valueOf(rowData.getS2()),
                  Integer.valueOf(rowData.getS3()),
                  rowData.getS4(),
                  rowData.getS5()),
              "update");
          //              String updatedNode = rowData.getNodeID();
          //              String updatedLongName = rowData.getLongName();
          //              System.out.print(updatedNode + " " + updatedLongName);

          int index = rowData.getI();
          //              System.out.println("/n this is the index: " + index);
        });
    ColumnThree.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS3(event.getNewValue());
          Cdb.syncNodeDB(
              new Node(
                  rowData.getS1(),
                  Integer.valueOf(rowData.getS2()),
                  Integer.valueOf(rowData.getS3()),
                  rowData.getS4(),
                  rowData.getS5()),
              "update");
          //              String updatedNode = rowData.getNodeID();
          //              String updatedDate = rowData.getDate();
          //              System.out.print(updatedNode + " " + updatedDate);
          //          System.out.print(
          //              rowData.getNodeID() + " " + rowData.getIndex() + " " +
          // rowData.getYCoord());

          int index = rowData.getI();
        });
    ColumnFour.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS4(event.getNewValue());
          Cdb.syncNodeDB(
              new Node(
                  rowData.getS1(),
                  Integer.valueOf(rowData.getS2()),
                  Integer.valueOf(rowData.getS3()),
                  rowData.getS4(),
                  rowData.getS5()),
              "update");
          //              String updatedNode = rowData.getNodeID();
          //              String updatedLongName = rowData.getLongName();
          //              System.out.print(updatedNode + " " + updatedLongName);

          int index = rowData.getI();
          //              System.out.println("/n this is the index: " + index);
        });
    ColumnFive.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS5(event.getNewValue());
          Cdb.syncNodeDB(
              new Node(
                  rowData.getS1(),
                  Integer.valueOf(rowData.getS2()),
                  Integer.valueOf(rowData.getS3()),
                  rowData.getS4(),
                  rowData.getS5()),
              "update");

          int index = rowData.getI();
        });
    ColumnSix.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS6(event.getNewValue());

          int index = rowData.getI();
        });
    ColumnSeven.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setS7(event.getNewValue());

          int index = rowData.getI();
        });

    ColumnEight.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          Cdb.syncEdgeDBUpdate(
              new Edge(
                  new Node(rowData.getS1(), 0, 0, "", ""), new Node(rowData.getS2(), 0, 0, "", "")),
              new Edge(
                  new Node(event.getNewValue(), 0, 0, "", ""),
                  new Node(rowData.getS2(), 0, 0, "", "")));
          rowData.setS1(event.getNewValue());
          int index = rowData.getI();
        });

    ColumnNine.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();

          Cdb.syncEdgeDBUpdate(
              new Edge(
                  new Node(rowData.getS1(), 0, 0, "", ""), new Node(rowData.getS2(), 0, 0, "", "")),
              new Edge(
                  new Node(rowData.getS1(), 0, 0, "", ""),
                  new Node(event.getNewValue(), 0, 0, "", "")));
          rowData.setS2(event.getNewValue());
          int index = rowData.getI();
        });

    //    ObservableList<TableColumn<TableRow, ?>> nodeIDList = ColumnOne.getColumns();
    //    TableColumn newColumn = nodeIDList.get(1);
    //    TableRow newTableRow = newColumn.getTableView();
    // use "indexOf()" to get index of nodeID in list from database and compare to saved nodeID from
    // edited cell above?
    // System.out.println("before");
    //    historyTable.getItems()
    // System.out.println(historyTable.getItems());
    // System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  //  public void dispTable(List<Move> moveList) {
  //    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("nodeID"));
  //    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("longName"));
  //    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("date"));
  //    //    testTable.getItems().setAll(gettableRows(moveList));
  //    historyTable.getItems().setAll(gettableRows(moveList));
  //    //    ColumnOne.setEditable(true);
  //    //    ColumnTwo.setEditable(true);
  //    //    ColumnThree.setEditable(true);
  //
  //    System.out.println("did it");
  //  }

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
      nodeID = currNodeList.getNodeID();
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
      startNode = edgeList.get(i).getStartNode().getNodeID();
      endNode = edgeList.get(i).getEndNode().getNodeID();
      index++;
      rowsEdge.add(new TableRow(startNode, endNode, index));
    }
    return rowsEdge;
  }

  public String getText(javafx.event.ActionEvent actionEvent) {
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
