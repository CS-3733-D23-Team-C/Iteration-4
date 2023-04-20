package edu.wpi.teamc.controllers.pages.history;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
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
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class FurnitureHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<FurnitureDeliveryRequest> historyTable;
  @FXML TableColumn<FurnitureDeliveryRequest, Integer> ColumnOne;
  @FXML TableColumn<FurnitureDeliveryRequest, IUser> ColumnTwo;
  @FXML TableColumn<FurnitureDeliveryRequest, String> ColumnThree;
  @FXML TableColumn<FurnitureDeliveryRequest, String> ColumnFour;
  @FXML TableColumn<FurnitureDeliveryRequest, String> ColumnFive;
  @FXML TableColumn<FurnitureDeliveryRequest, STATUS> ColumnSix;
  @FXML TableColumn<FurnitureDeliveryRequest, String> ColumnSeven;
  @FXML TableColumn<FurnitureDeliveryRequest, String> ColumnEight;

  @FXML Button clearButton;
  @FXML TextField idField;
  @FXML MenuButton statusField;
  @FXML SearchableComboBox assignedtoField;
  @FXML DatePicker etaField;

  @FXML Button updateButton;
  @FXML Button deleteButton;

  @FXML
  void status1(ActionEvent event) {
    statusField.setText("PENDING");
  }

  @FXML
  void status2(ActionEvent event) {
    statusField.setText("IN_PROGRESS");
  }

  @FXML
  void status3(ActionEvent event) {
    statusField.setText("COMPLETE");
  }

  @FXML
  void status4(ActionEvent event) {
    statusField.setText("CANCELLED");
  }

  ObservableList<FurnitureDeliveryRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, Integer>("requestID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, IUser>("requester"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("roomName"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("furnitureType"));
    ColumnFive.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("additionalNotes"));
    ColumnSix.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, STATUS>("status"));
    ColumnSeven.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("eta"));
    ColumnEight.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("assignedto"));
    ColumnOne.setText("requestID");
    ColumnTwo.setText("Requester");
    ColumnThree.setText("Room Name");
    ColumnFour.setText("Furniture");
    ColumnFive.setText("Additional Notes");
    ColumnSix.setText("Status");
    ColumnSeven.setText("ETA");
    ColumnEight.setText("Assigned To");

    // get conference room table
    FurnitureDeliveryRequestDAO dao = new FurnitureDeliveryRequestDAO();
    List<FurnitureDeliveryRequest> list = dao.fetchAllObjects();
    for (FurnitureDeliveryRequest r : list) {
      rows.add(r);
    }
    historyTable.setItems(rows);

    List<EmployeeUser> employeeList =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    assignedtoField.getItems().addAll(FXCollections.observableArrayList(employeeList));

    System.out.println("did it");

    historyTable.setOnMouseClicked(
        event -> {
          updateCurrentSelection();
        });
    clearButton.setOnAction(
        event -> {
          idField.clear();
          statusField.setText("");
          assignedtoField.getSelectionModel().clearSelection();
          etaField.getEditor().clear();
        });

    updateButton.setOnMouseClicked(
        event -> {
          //   ConferenceRoomRequestDAO dao2 = new ConferenceRoomRequestDAO();
          FurnitureDeliveryRequest selected = historyTable.getSelectionModel().getSelectedItem();
          selected.setAssignedto(assignedtoField.getSelectionModel().getSelectedItem().toString());
          selected.setStatus(STATUS.valueOf(statusField.getText()));
          selected.setEta(etaField.getEditor().getText());
          HospitalSystem.updateRow(selected);
          loadRequests();
        });

    deleteButton.setOnMouseClicked(
        event -> {
          FurnitureDeliveryRequest selected = historyTable.getSelectionModel().getSelectedItem();
          HospitalSystem.deleteRow(selected);
          rows.remove(selected);
          loadRequests();
        });
  }

  private void loadRequests() {
    List<FurnitureDeliveryRequest> list =
        (List<FurnitureDeliveryRequest>)
            HospitalSystem.fetchAllObjects(new FurnitureDeliveryRequest());
    historyTable.getItems().removeAll();
    historyTable.getItems().setAll(list);
  }

  private void updateCurrentSelection() {
    FurnitureDeliveryRequest selected = historyTable.getSelectionModel().getSelectedItem();
    idField.setText(String.valueOf(selected.getRequestID()));
    statusField.setText(selected.getStatus().toString());
    assignedtoField.getSelectionModel().select(selected.getAssignedto());
    etaField.getEditor().setText(selected.getEta());
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
