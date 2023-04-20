package edu.wpi.teamc.controllers.pages.history;

import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.IUser;
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
import org.controlsfx.control.tableview2.FilteredTableView;

public class OfficeSupplyHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<OfficeSuppliesRequest> historyTable;
  @FXML TableColumn<OfficeSuppliesRequest, Integer> ColumnOne;
  @FXML TableColumn<OfficeSuppliesRequest, IUser> ColumnTwo;
  @FXML TableColumn<OfficeSuppliesRequest, String> ColumnThree;
  @FXML TableColumn<OfficeSuppliesRequest, String> ColumnFour;
  @FXML TableColumn<OfficeSuppliesRequest, String> ColumnFive;
  @FXML TableColumn<OfficeSuppliesRequest, STATUS> ColumnSix;
  @FXML TableColumn<OfficeSuppliesRequest, String> ColumnSeven;
  @FXML TableColumn<OfficeSuppliesRequest, String> ColumnEight;

  ObservableList<OfficeSuppliesRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, Integer>("requestID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, IUser>("requester"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("roomName"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("officesupplytype"));
    ColumnFive.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("additionalNotes"));
    ColumnSix.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, STATUS>("status"));
    ColumnSeven.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesRequest, String>("eta"));
    ColumnEight.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("assignedto"));
    ColumnOne.setText("requestID");
    ColumnTwo.setText("Requester");
    ColumnThree.setText("Room Name");
    ColumnFour.setText("Supply");
    ColumnFive.setText("Additional Notes");
    ColumnSix.setText("Status");
    ColumnSeven.setText("ETA");
    ColumnEight.setText("Assigned To");
    //    ColumnOne.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnTwo.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnThree.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFour.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFive.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnSix.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    // get conference room table
    OfficeSuppliesRequestDAO dao = new OfficeSuppliesRequestDAO();
    List<OfficeSuppliesRequest> list = dao.fetchAllObjects();
    for (OfficeSuppliesRequest r : list) {
      rows.add(r);
    }
    historyTable.setItems(rows);
    System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    inputBox.clear();
    return inputtedText;
  }

  public void getMapPage(ActionEvent event) {}
}
