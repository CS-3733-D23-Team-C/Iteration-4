package edu.wpi.teamc.controllers.pages.history;

// import edu.wpi.teamc.map.Move;
import edu.wpi.teamc.dao.map.MapHistory;
import edu.wpi.teamc.dao.map.MapHistoryDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Timestamp;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.tableview2.FilteredTableView;

public class MapHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<MapHistory> historyTable;
  @FXML TableView<MapHistory> otherTable;
  @FXML TableColumn<MapHistory, Integer> ColumnOne;
  @FXML TableColumn<MapHistory, String> ColumnTwo;
  @FXML TableColumn<MapHistory, String> ColumnThree;
  @FXML TableColumn<MapHistory, String> ColumnFour;
  @FXML TableColumn<MapHistory, Timestamp> ColumnFive;

  ObservableList<MapHistory> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<MapHistory, Integer>("id"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<MapHistory, String>("action"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<MapHistory, String>("nodepk"));
    ColumnFour.setCellValueFactory(new PropertyValueFactory<MapHistory, String>("table"));
    ColumnFive.setCellValueFactory(new PropertyValueFactory<MapHistory, Timestamp>("timestamp"));
    ColumnOne.setText("History ID");
    ColumnTwo.setText("Action");
    ColumnThree.setText("Primary Key");
    ColumnFour.setText("Table");
    ColumnFive.setText("Timestamp");
    //    //    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //
    MapHistoryDao dao = new MapHistoryDao();
    List<MapHistory> list = dao.fetchAllObjects();
    rows.addAll(list);
    historyTable.getItems().setAll(rows);
    //
    //    System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
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
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
