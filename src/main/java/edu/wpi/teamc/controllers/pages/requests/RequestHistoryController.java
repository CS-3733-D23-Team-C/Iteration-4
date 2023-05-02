package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.ImportCSV;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.IUser;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class RequestHistoryController {

  public SearchableComboBox filterByStatusField;
  String buttonColor = "-fx-background-color: white; -fx-text-fill: #02143B;";
  String selectedButtonColor = "-fx-background-color: #FCC201; -fx-text-fill: #02143B;";

  @FXML private Button conference;
  @FXML private Button flower;
  @FXML private Button meal;
  @FXML private Button furniture;
  @FXML private Button officeSupply;

  @FXML private Button giftBasket;

  @FXML private TableView historyTable;
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

  @FXML MFXButton exportButton;
  @FXML MFXButton importButton;
  private Desktop desktop = Desktop.getDesktop();
  private String filePath;

  @FXML SearchableComboBox filterByEmployeeField;

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

  RequestSystem requestSystem = new RequestSystem(new ArrayList<>());

  /** Method run when controller is initialized */
  public void initialize() {
    this.getConference();
    List<EmployeeUser> employeeList =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    assignedtoField.getItems().addAll(FXCollections.observableArrayList(employeeList));
    filterByEmployeeField.getItems().addAll(FXCollections.observableArrayList(employeeList));

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
          if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Request");
            alert.setHeaderText(
                "Are you sure you want to delete this request: " + selected.getRequestID() + "?");
            alert.setContentText("This action cannot be undone.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
              HospitalSystem.deleteRow((IOrm) selected);
              getSwitch(selected);
            }
          } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No request selected");
            alert.setContentText("Please select a request to delete.");
            alert.showAndWait();
          }
        });

    List<STATUS> statusList = Arrays.stream(STATUS.values()).toList();
    filterByStatusField.setItems(FXCollections.observableArrayList(statusList));

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

    //    historyTable.getStyleClass().add("table-view");
    //    historyTable.getStyleClass().add("column-header-background");
    //    historyTable.getStyleClass().add("corner");
    //
    //    historyTable.setStyle("-fx-opacity: 0; -fx-background-color: #02143B;  -fx-text-fill:
    // white;");
    //    historyTable.getRowHeader()
    //        .setStyle("-fx-opacity: 1; -fx-background-color: red; -fx-text-fill: white;");
    //    Column1.setStyle("-fx-opacity: 0.5; -fx-background-color: #02143B; -fx-text-fill:
    // white;");
    //    Column2.setStyle("-fx-opacity: 1; -fx-background-color: #02143B; -fx-text-fill: white;");
    //    Column3.setStyle("-fx-opacity: 0.5; -fx-background-color: #02143B; -fx-text-fill:
    // white;");
    //    Column4.setStyle("-fx-opacity: 1; -fx-background-color: #02143B; -fx-text-fill: white;");
    //    Column5.setStyle("-fx-opacity: 0.5; -fx-background-color: #02143B; -fx-text-fill:
    // white;");
    //    Column6.setStyle("-fx-opacity: 1; -fx-background-color: #02143B; -fx-text-fill: white;");
    //    Column7.setStyle("-fx-opacity: 0.5; -fx-background-color: #02143B; -fx-text-fill:
    // white;");
    //    Column8.setStyle("-fx-opacity: 1; -fx-background-color: #02143B; -fx-text-fill: white;");
    historyTable
        .getStylesheets()
        .add(Main.class.getResource("views/pages/requests/RequestHistory.css").toString());
  }

  public void filterView() {
    historyTable.getItems().removeAll();
    String assingedTo = null;
    try {
      assingedTo = filterByEmployeeField.getSelectionModel().getSelectedItem().toString();
    } catch (Exception e) {
      assingedTo = null;
    }
    STATUS status = null;
    try {
      status = STATUS.valueOf(filterByStatusField.getSelectionModel().getSelectedItem().toString());
    } catch (Exception e) {
      status = null;
    }
    List<AbsServiceRequest> filteredList = requestSystem.filterRequest(assingedTo, status);
    historyTable.setItems(FXCollections.observableArrayList(filteredList));
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
    } else if (selected instanceof GiftBasketRequest) {
      this.getGiftBasket();
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
    List<ConferenceRoomRequest> list = dao.fetchAllObjects();
    for (ConferenceRoomRequest ConferenceRoomRequest : list) {
      rows.add(ConferenceRoomRequest);
    }
    requestSystem.setRequests(new ArrayList<>(list));
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
    requestSystem.setRequests(new ArrayList<>(list));
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
    requestSystem.setRequests(new ArrayList<>(list));
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
    requestSystem.setRequests(new ArrayList<>(list));
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
    requestSystem.setRequests(new ArrayList<>(list));
    historyTable.setItems(rows);
  }

  @FXML
  public void getGiftBasket() {
    this.resetColor();
    this.clearCurrentSelection();
    giftBasket.setStyle(selectedButtonColor);
    selectedRequest = new GiftBasketRequest();
    ObservableList<GiftBasketRequest> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, Integer>("requestID"));
    Column2.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, IUser>("requester"));
    Column3.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, String>("roomName"));
    Column4.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, String>("gifttype"));
    Column5.setCellValueFactory(
        new PropertyValueFactory<GiftBasketRequest, String>("additionalNotes"));
    Column6.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, STATUS>("status"));
    Column7.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, String>("eta"));
    Column8.setCellValueFactory(new PropertyValueFactory<GiftBasketRequest, String>("assignedto"));
    Column1.setText("ID");
    Column2.setText("Requester");
    Column3.setText("Room Name");
    Column4.setText("Gift");
    Column5.setText("Additional Notes");
    Column6.setText("Status");
    Column7.setText("ETA");
    Column8.setText("Assigned To");
    GiftBasketDAO dao = new GiftBasketDAO();
    List<GiftBasketRequest> list = dao.fetchAllObjects();
    for (GiftBasketRequest r : list) {
      rows.add(r);
    }
    requestSystem.setRequests(new ArrayList<>(list));
    historyTable.setItems(rows);
  }

  private void resetColor() {
    conference.setStyle(buttonColor);
    flower.setStyle(buttonColor);
    meal.setStyle(buttonColor);
    furniture.setStyle(buttonColor);
    officeSupply.setStyle(buttonColor);
    giftBasket.setStyle(buttonColor);
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
      } else if (selectedRequest instanceof GiftBasketRequest) {
        GiftBasketDAO giftBasketDAO = new GiftBasketDAO();
        giftBasketDAO.exportCSV(filePath);
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
      ImportCSV.importFlowerRequestCSV(filePath);
      this.getFlower();
    } else if (selectedRequest instanceof FurnitureDeliveryRequest) {
      ImportCSV.importFurnitureRequestCSV(filePath);
      this.getFurniture();
    } else if (selectedRequest instanceof MealRequest) {
      ImportCSV.importMealRequestCSV(filePath);
      this.getMeal();
    } else if (selectedRequest instanceof OfficeSuppliesRequest) {
      ImportCSV.importOfficeSupplyRequestCSV(filePath);
      this.getOfficeSupply();
    } else if (selectedRequest instanceof GiftBasketRequest) {
      ImportCSV.importGiftBasketRequestCSV(filePath);
      this.getGiftBasket();
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

  @FXML
  public void getApplyFilter(ActionEvent event) {
    this.filterView();
  }

  @FXML
  public void getClearFilter(ActionEvent event) {
    getSwitch(selectedRequest);
    this.filterByStatusField.getSelectionModel().select(null);
    this.filterByEmployeeField.getSelectionModel().select(null);
  }
}
