package edu.wpi.teamc.controllers.english;

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

public class ConferenceHistoryController {

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
  @FXML TableView<TableRow> otherTable;
  @FXML TableColumn<TableRow, String> ColumnOne;
  @FXML TableColumn<TableRow, String> NameColumn;
  @FXML TableColumn<TableRow, String> ColumnTwo;
  @FXML TableColumn<TableRow, String> ColumnThree;
  @FXML TableColumn<TableRow, String> ColumnFour;
  @FXML TableColumn<TableRow, String> ColumnFive;
  @FXML TableColumn<TableRow, String> ColumnSix;

  ObservableList<TableRowController> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s1"));
    NameColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s2"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s3"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s4"));
    ColumnFour.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s5"));
    ColumnFive.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s6"));
    ColumnSix.setCellValueFactory(new PropertyValueFactory<TableRow, String>("s7"));
    //    ColumnOne.setText("ID");
    //    ColumnTwo.setText("Status");
    //    ColumnThree.setText("Start");
    //    ColumnFour.setText("End");
    //   ColumnFive.setText("Info");
    //    ColumnSix.setText("Room");
    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnFour.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnFive.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnSix.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    NameColumn.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    // get conference room table
    /*historyTable
    .getItems()
    .setAll(convertToObservableList(Cdb.getTable("ServiceRequests", "conferenceRoom")));*/
    System.out.println("did it");
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

  public ObservableList<TableRowController> convertToObservableList(List<List<String>> rowList) {
    String requestID;
    String reqname;
    String status;
    String startTime;
    String endTime;
    String additionalInfo;
    String roomName;
    for (List<String> rl : rowList) {
      for (String s : rl) {
        System.out.println(s);
      }

      requestID = rl.get(0);
      reqname = rl.get(1);
      status = rl.get(2);
      startTime = rl.get(3);
      endTime = rl.get(4);
      additionalInfo = rl.get(5);
      roomName = rl.get(6);
      rows.add(
          new TableRowController(
              requestID, reqname, status, startTime, endTime, additionalInfo, roomName));
    }
    return rows;
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
