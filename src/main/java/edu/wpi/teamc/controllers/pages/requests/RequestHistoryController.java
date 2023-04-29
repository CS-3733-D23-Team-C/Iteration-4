package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.ImportCSV;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.IUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class RequestHistoryController extends AbsServiceRequest {

  public RequestHistoryController() {
    super();
  }

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
  @FXML SearchableComboBox filterByStatusField;

  @FXML MFXButton exportButton;
  @FXML MFXButton importButton;
  private Desktop desktop = Desktop.getDesktop();
  private String filePath;

  @FXML SearchableComboBox filterByEmployeeField;
  @FXML Button clearButtonFilter;

  int incrTest = 0;
  String currTable;

  int incrTest2 = 0;
  EmployeeUser currEmployee;
  ;

  IRequest selectedRequest = null;
  EmployeeUser selectedEmployee = null;
  STATUS status2;

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
    ;

    List<STATUS> statusList = new ArrayList<STATUS>();
    statusList.add(STATUS.COMPLETE);
    statusList.add(STATUS.CANCELLED);
    statusList.add(STATUS.IN_PROGRESS);
    statusList.add(STATUS.PENDING);
    filterByStatusField.setItems(FXCollections.observableArrayList(statusList));

    filterByStatusField.setOnAction(
        event -> {
          //          STATUS selected = STATUS.PENDING;
          if (incrTest == 2) {
            status2 = (STATUS) filterByStatusField.getValue();
            filterView(status2);
            incrTest = -1;
          }
          incrTest++;
        });

    importButton.setOnMouseClicked(
        event -> {
          getImportMenu();
        });
    exportButton.setOnMouseClicked(
        event -> {
          try {
            getExportMenu();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    filterByEmployeeField.getItems().addAll(FXCollections.observableArrayList(employeeList));
    filterByEmployeeField.setOnAction(
        event -> {
          if (incrTest2 == 2) {
            currEmployee = (EmployeeUser) filterByEmployeeField.getValue();
            assignedToFilterView(currEmployee);
            incrTest2 = -1;
          }
          incrTest2++;
        });

    //    clearButtonFilter.setOnAction(
    //        event -> {
    //          filterByEmployeeField.getSelectionModel().clearSelection();
    //          filterByStatusField.getSelectionModel().clearSelection();
    //        });
  }

  public void assignedToFilterView(EmployeeUser employee) {
    if (selectedRequest instanceof ConferenceRoomRequest) {
      List<ConferenceRoomRequest> currentView =
          (List<ConferenceRoomRequest>) HospitalSystem.fetchAllObjects(new ConferenceRoomRequest());
      List<ConferenceRoomRequest> filteredList = filterRequestConferenceEmp(currentView, employee);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof FlowerDeliveryRequest) {
      List<FlowerDeliveryRequest> currentView =
          (List<FlowerDeliveryRequest>) HospitalSystem.fetchAllObjects(new FlowerDeliveryRequest());
      List<FlowerDeliveryRequest> filteredList = filterRequestFlowerEmp(currentView, employee);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof MealRequest) {
      List<MealRequest> currentView =
          (List<MealRequest>) HospitalSystem.fetchAllObjects(new MealRequest());
      List<MealRequest> filteredList = filterRequestMealEmp(currentView, employee);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof FurnitureDeliveryRequest) {
      List<FurnitureDeliveryRequest> currentView =
          (List<FurnitureDeliveryRequest>)
              HospitalSystem.fetchAllObjects(new FurnitureDeliveryRequest());
      List<FurnitureDeliveryRequest> filteredList =
          filterRequestFurnitureEmp(currentView, employee);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof OfficeSuppliesRequest) {
      List<OfficeSuppliesRequest> currentView =
          (List<OfficeSuppliesRequest>) HospitalSystem.fetchAllObjects(new OfficeSuppliesRequest());
      List<OfficeSuppliesRequest> filteredList =
          filterRequestOfficeSuppliesEmp(currentView, employee);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    }
  }

  @FXML
  public void filterView(STATUS selected) {

    //    historyTable.getItems().stream().filter((IRequest)item -> item.getStatus)
    if (selectedRequest instanceof ConferenceRoomRequest) {
      List<ConferenceRoomRequest> currentView =
          (List<ConferenceRoomRequest>) HospitalSystem.fetchAllObjects(new ConferenceRoomRequest());
      List<ConferenceRoomRequest> filteredList = filterRequestConference(currentView, selected);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof FlowerDeliveryRequest) {
      List<FlowerDeliveryRequest> currentView =
          (List<FlowerDeliveryRequest>) HospitalSystem.fetchAllObjects(new FlowerDeliveryRequest());
      List<FlowerDeliveryRequest> filteredList = filterRequestFlower(currentView, selected);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof MealRequest) {
      List<MealRequest> currentView =
          (List<MealRequest>) HospitalSystem.fetchAllObjects(new MealRequest());
      List<MealRequest> filteredList = filterRequestMeal(currentView, selected);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof FurnitureDeliveryRequest) {
      List<FurnitureDeliveryRequest> currentView =
          (List<FurnitureDeliveryRequest>)
              HospitalSystem.fetchAllObjects(new FurnitureDeliveryRequest());
      List<FurnitureDeliveryRequest> filteredList = filterRequestFurniture(currentView, selected);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    } else if (selectedRequest instanceof OfficeSuppliesRequest) {
      List<OfficeSuppliesRequest> currentView =
          (List<OfficeSuppliesRequest>) HospitalSystem.fetchAllObjects(new OfficeSuppliesRequest());
      List<OfficeSuppliesRequest> filteredList = filterRequestOfficeSupplies(currentView, selected);
      ObservableList<IRequest> rows = FXCollections.observableArrayList();
      rows.addAll(filteredList);
      historyTable.setItems(rows);
    }
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
    currTable = "Conf";
    this.resetColor();
    this.clearCurrentSelection();
    clearFilters();
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
    clearFilters();
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
    clearFilters();
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
    clearFilters();
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
    clearFilters();
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

  //  @FXML
  //  private void getExportMenu() {}
  // TODO
  @FXML
  void getExportMenu() throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    fileChooser.setInitialFileName("Export_Request");
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
      String filePath = file.getAbsolutePath();
      if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
        filePath += ".csv"; // append ".csv" to the file path
      }
      if (selectedRequest instanceof ConferenceRoomRequest) {
        ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
        conferenceRoomRequestDAO.exportCSV(filePath);
      } else if (selectedRequest instanceof FlowerDeliveryRequest) {
        FlowerDeliveryRequestDAO flowerDeliveryRequestDAO = new FlowerDeliveryRequestDAO();
        flowerDeliveryRequestDAO.exportCSV(filePath);
      } else if (selectedRequest instanceof FurnitureDeliveryRequest) {
        FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO = new FurnitureDeliveryRequestDAO();
        furnitureDeliveryRequestDAO.exportCSV(filePath);
      } else if (selectedRequest instanceof MealRequest) {
        MealRequestDAO mealRequestDAO = new MealRequestDAO();
        mealRequestDAO.exportCSV(filePath);
      } else if (selectedRequest instanceof OfficeSuppliesRequest) {
        OfficeSuppliesRequestDAO officeSuppliesRequestDAO = new OfficeSuppliesRequestDAO();
        officeSuppliesRequestDAO.exportCSV(filePath);
      }
    }
  }

  @SneakyThrows
  @FXML
  private void getImportMenu() {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    desktop.open(file);
    filePath = file.getAbsolutePath();
    if (selectedRequest instanceof ConferenceRoomRequest) {
      //      ImportCSV.nukeConferenceRequestDatabase();
      ImportCSV.importConferenceRequestCSV(filePath);
      System.out.println("IMPORT_WORKS");
      this.getConference();
    } else if (selectedRequest instanceof FlowerDeliveryRequest) {
      ImportCSV.importflowerRequestCSV(filePath);
      this.getFlower();
    } else if (selectedRequest instanceof FurnitureDeliveryRequest) {
      ImportCSV.importfurnitureDeliveryRequest(filePath);
      this.getFurniture();
    } else if (selectedRequest instanceof MealRequest) {
      ImportCSV.importMealRequest(filePath);
      this.getMeal();
    } else if (selectedRequest instanceof OfficeSuppliesRequest) {
      ImportCSV.importOfficeSupplyRequest(filePath);
      this.getOfficeSupply();
    }
    // TODO
  }

  private void updateCurrentSelection() {
    IRequest selected = (IRequest) historyTable.getSelectionModel().getSelectedItem();
    // currTable = selected;
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

  private void clearFilters() {
    filterByEmployeeField.getSelectionModel().select(null);
    filterByStatusField.getSelectionModel().select(null);
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }
}
