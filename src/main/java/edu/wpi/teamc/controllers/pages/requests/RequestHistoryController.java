package edu.wpi.teamc.controllers.pages.requests;

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

public class RequestHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  String buttonColor = "-fx-background-color: white; -fx-text-fill: #02143B;";
  String selectedButtonColor = "-fx-background-color: #FCC201; -fx-text-fill: #02143B;";
  @FXML private Button conference;
  @FXML private Button flower;
  @FXML private Button meal;
  @FXML private Button furniture;
  @FXML private Button officeSupply;

  @FXML private FilteredTableView historyTable;
  @FXML TableColumn Column1;
  @FXML TableColumn Column2;
  @FXML TableColumn Column3;
  @FXML TableColumn Column4;
  @FXML TableColumn Column5;
  @FXML TableColumn Column6;
  @FXML TableColumn Column7;
  @FXML TableColumn Column8;

  @FXML Button clearButton;
  @FXML TextField idField;
  @FXML MenuButton statusField;
  @FXML SearchableComboBox assignedtoField;
  @FXML DatePicker etaField;

  @FXML Button updateButton;
  @FXML Button deleteButton;

  IRequest selectedRequest = null;

  @FXML
  void statusPending(ActionEvent event) {
    statusField.setText("PENDING");
  }

  @FXML
  void statusInProgress(ActionEvent event) {
    statusField.setText("IN_PROGRESS");
  }

  @FXML
  void statusComplete(ActionEvent event) {
    statusField.setText("COMPLETE");
  }

  @FXML
  void statusCancelled(ActionEvent event) {
    statusField.setText("CANCELLED");
  }

  /** Method run when controller is initialized */
  public void initialize() {
    this.getConference(new ActionEvent());

    List<EmployeeUser> employeeList =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    assignedtoField.getItems().addAll(FXCollections.observableArrayList(employeeList));

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
          ConferenceRoomRequest selected =
              (ConferenceRoomRequest) historyTable.getSelectionModel().getSelectedItem();
          selected.setAssignedto(assignedtoField.getSelectionModel().getSelectedItem().toString());
          selected.setStatus(STATUS.valueOf(statusField.getText()));
          HospitalSystem.updateRow(selected);
          getConference(new ActionEvent());
        });

    deleteButton.setOnMouseClicked(
        event -> {
          ConferenceRoomRequest selected =
              (ConferenceRoomRequest) historyTable.getSelectionModel().getSelectedItem();
          HospitalSystem.deleteRow(selected);
          getConference(new ActionEvent());
        });
  }

  @FXML
  private void getConference(ActionEvent event) {
    selectedRequest = new ConferenceRoomRequest();
    ObservableList<ConferenceRoomRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, Integer>("requestID"));
    Column2.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, IUser>("requester"));
    Column3.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, ConferenceRoom>("conferenceRoom"));
    Column4.setCellValueFactory(new PropertyValueFactory<ConferenceRoomRequest, STATUS>("status"));
    Column5.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("startTime"));
    Column7.setCellValueFactory(new PropertyValueFactory<ConferenceRoomRequest, String>("endTime"));
    Column8.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("assignedto"));
    Column1.setText("requestID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Status");
    Column5.setText("Additional Notes");
    Column6.setText("Start time");
    Column7.setText("End time");
    Column8.setText("Assigned To");
    ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
    List<ConferenceRoomRequest> list = (List<ConferenceRoomRequest>) dao.fetchAllObjects();
    for (ConferenceRoomRequest ConferenceRoomRequest : list) {
      rows.add(ConferenceRoomRequest);
    }
    historyTable.getItems().removeAll();
    historyTable.setItems(rows);
  }

  @FXML
  private void getFlower(ActionEvent event) {
    selectedRequest = new FlowerDeliveryRequest();
    ObservableList<FlowerDeliveryRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, Integer>("requestID"));
    Column2.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, IUser>("requester"));
    Column3.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("roomName"));
    Column4.setCellValueFactory(new PropertyValueFactory<FlowerDeliveryRequest, String>("flower"));
    Column5.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(new PropertyValueFactory<FlowerDeliveryRequest, STATUS>("status"));
    Column7.setCellValueFactory(new PropertyValueFactory<FlowerDeliveryRequest, String>("eta"));
    Column8.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("assignedto"));
    Column1.setText("requestID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Flower");
    Column5.setText("Additional Notes");
    Column6.setText("Status");
    Column7.setText("ETA");
    Column8.setText("Assigned To");
    FlowerDeliveryRequestDAO dao = new FlowerDeliveryRequestDAO();
    List<FlowerDeliveryRequest> list = dao.fetchAllObjects();
    for (FlowerDeliveryRequest r : list) {
      rows.add(r);
    }
    historyTable.getItems().removeAll();
    historyTable.setItems(rows);
  }

  private void resetColor() {
    conference.setStyle(buttonColor);
    flower.setStyle(buttonColor);
    meal.setStyle(buttonColor);
    furniture.setStyle(buttonColor);
    officeSupply.setStyle(buttonColor);
  }

  private void updateCurrentSelection() {
    IRequest selected = (IRequest) historyTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      idField.setText(Integer.toString(selected.getRequestID()));
      statusField.setText(selected.getStatus().toString());
      assignedtoField.getSelectionModel().select(selected.getAssignedto());
    }
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }
}
