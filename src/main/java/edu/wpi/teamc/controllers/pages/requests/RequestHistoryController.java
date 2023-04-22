package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.IUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

import java.util.List;

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
    this.getConference();

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
          IRequest selected = (IRequest) historyTable.getSelectionModel().getSelectedItem();
          selected.setAssignedto(assignedtoField.getSelectionModel().getSelectedItem().toString());
          selected.setStatus(STATUS.valueOf(statusField.getText()));
          HospitalSystem.updateRow((IOrm) selected);
          getSwitch(selected);
        });

    deleteButton.setOnMouseClicked(
        event -> {
          IRequest selected = (IRequest) historyTable.getSelectionModel().getSelectedItem();
          HospitalSystem.deleteRow((IOrm) selected);
          getSwitch(selected);
        });
  }

  @FXML
  private void getSwitch(IRequest selected) {
    if (selected instanceof ConferenceRoomRequest) {
      this.getConference();
    } else if (selected instanceof FlowerDeliveryRequest) {
      this.getFlower();
    } else if (selected instanceof MealRequest) {
      this.getMeal();
    } else if (selected instanceof FurnitureDeliveryRequest) {
      this.getFurniture();
    } else if (selected instanceof OfficeSuppliesRequest) {
      this.getOfficeSupply();
    }
  }

  @FXML
  private void getConference() {
    this.resetColor();
    this.clearCurrentSelection();
    conference.setStyle(selectedButtonColor);
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
    Column1.setText("ID");
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
  private void getFlower() {
    this.resetColor();
    this.clearCurrentSelection();
    flower.setStyle(selectedButtonColor);
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
    Column1.setText("ID");
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

  @FXML
  private void getMeal() {
    this.resetColor();
    this.clearCurrentSelection();
    meal.setStyle(selectedButtonColor);
    selectedRequest = new MealRequest();
    ObservableList<MealRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(new PropertyValueFactory<MealRequest, Integer>("requestID"));
    Column2.setCellValueFactory(new PropertyValueFactory<MealRequest, IUser>("requester"));
    Column3.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("roomName"));
    Column4.setCellValueFactory(new PropertyValueFactory<MealRequest, Meal>("meal"));
    Column5.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(new PropertyValueFactory<MealRequest, STATUS>("status"));
    Column7.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("eta"));
    Column8.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("assignedto"));
    Column1.setText("ID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Meal");
    Column5.setText("Additional Notes");
    Column6.setText("Status");
    Column7.setText("ETA");
    Column8.setText("Assigned To");
    List<MealRequest> list = (List<MealRequest>) HospitalSystem.fetchAllObjects(new MealRequest());
    for (MealRequest r : list) {
      rows.add(r);
    }
    historyTable.getItems().removeAll();
    historyTable.setItems(rows);
  }

  @FXML
  private void getFurniture() {
    this.resetColor();
    this.clearCurrentSelection();
    furniture.setStyle(selectedButtonColor);
    selectedRequest = new FurnitureDeliveryRequest();
    ObservableList<FurnitureDeliveryRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, Integer>("requestID"));
    Column2.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, IUser>("requester"));
    Column3.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("roomName"));
    Column4.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("furnituretype"));
    Column5.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, STATUS>("status"));
    Column7.setCellValueFactory(new PropertyValueFactory<FurnitureDeliveryRequest, String>("eta"));
    Column8.setCellValueFactory(
        new PropertyValueFactory<FurnitureDeliveryRequest, String>("assignedto"));
    Column1.setText("ID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Furniture");
    Column5.setText("Additional Notes");
    Column6.setText("Status");
    Column7.setText("ETA");
    Column8.setText("Assigned To");
    List<FurnitureDeliveryRequest> list =
        (List<FurnitureDeliveryRequest>)
            HospitalSystem.fetchAllObjects(new FurnitureDeliveryRequest());
    for (FurnitureDeliveryRequest r : list) {
      rows.add(r);
    }
    historyTable.getItems().removeAll();
    historyTable.setItems(rows);
  }

  @FXML
  public void getOfficeSupply() {
    this.resetColor();
    this.clearCurrentSelection();
    officeSupply.setStyle(selectedButtonColor);
    selectedRequest = new OfficeSuppliesRequest();
    ObservableList<OfficeSuppliesRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, Integer>("requestID"));
    Column2.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, IUser>("requester"));
    Column3.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("roomName"));
    Column4.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("officesupplytype"));
    Column5.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesRequest, STATUS>("status"));
    Column7.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesRequest, String>("eta"));
    Column8.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesRequest, String>("assignedto"));
    Column1.setText("ID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Supply");
    Column5.setText("Additional Notes");
    Column6.setText("Status");
    Column7.setText("ETA");
    Column8.setText("Assigned To");
    OfficeSuppliesRequestDAO dao = new OfficeSuppliesRequestDAO();
    List<OfficeSuppliesRequest> list = dao.fetchAllObjects();
    for (OfficeSuppliesRequest r : list) {
      rows.add(r);
    }
    historyTable.setItems(rows);
  }

  private void resetColor() {
    conference.setStyle(buttonColor);
    flower.setStyle(buttonColor);
    meal.setStyle(buttonColor);
    furniture.setStyle(buttonColor);
    officeSupply.setStyle(buttonColor);
  }

  @FXML
  private void getExportMenu() {}
  // TODO
  @FXML
  private void getImportMenu() {
    // TODO
  }

  private void updateCurrentSelection() {
    IRequest selected = (IRequest) historyTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      idField.setText(Integer.toString(selected.getRequestID()));
      statusField.setText(selected.getStatus().toString());
      assignedtoField.getSelectionModel().select(selected.getAssignedto());
    }
  }

  private void clearCurrentSelection() {
    idField.setText(null);
    statusField.setText(null);
    assignedtoField.getSelectionModel().select(null);
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }
}
