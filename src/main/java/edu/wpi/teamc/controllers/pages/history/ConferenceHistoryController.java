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

public class ConferenceHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private FilteredTableView<ConferenceRoomRequest> historyTable;
  @FXML TableColumn<ConferenceRoomRequest, Integer> ColumnOne;
  @FXML TableColumn<ConferenceRoomRequest, IUser> ColumnTwo;
  @FXML TableColumn<ConferenceRoomRequest, ConferenceRoom> ColumnThree;
  @FXML TableColumn<ConferenceRoomRequest, STATUS> ColumnFour;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnFive;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnSix;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnSeven;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnEight;

  @FXML Button clearButton;
  @FXML TextField idField;
  @FXML MenuButton statusField;
  @FXML SearchableComboBox assignedtoField;
  @FXML DatePicker etaField;

  @FXML Button updateButton;
  @FXML Button deleteButton;

  ObservableList<ConferenceRoomRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

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

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, Integer>("requestID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, IUser>("requester"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, ConferenceRoom>("conferenceRoom"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, STATUS>("status"));
    ColumnFive.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("additionalNotes"));
    ColumnSix.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("startTime"));
    ColumnSeven.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("endTime"));
    ColumnEight.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("assignedto"));
    ColumnOne.setText("requestID");
    ColumnTwo.setText("Requester");
    ColumnThree.setText("Room Name");
    ColumnFour.setText("Status");
    ColumnFive.setText("Additional Notes");
    ColumnSix.setText("Start time");
    ColumnSeven.setText("End time");
    ColumnEight.setText("Assigned To");
    //    ColumnOne.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnTwo.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnThree.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFour.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFive.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnSix.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    // get conference room table
    ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
    List<ConferenceRoomRequest> list = dao.fetchAllObjects();
    for (ConferenceRoomRequest ConferenceRoomRequest : list) {
      rows.add(ConferenceRoomRequest);
    }
    historyTable.setItems(rows);

    List<EmployeeUser> employeeList =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    assignedtoField.getItems().addAll(FXCollections.observableArrayList(employeeList));

    //  System.out.println("did it");

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
          ConferenceRoomRequest selected = historyTable.getSelectionModel().getSelectedItem();
          selected.setAssignedto(assignedtoField.getSelectionModel().getSelectedItem().toString());
          selected.setStatus(STATUS.valueOf(statusField.getText()));
          HospitalSystem.updateRow(selected);
          loadRequests();
        });

    deleteButton.setOnMouseClicked(
        event -> {
          ConferenceRoomRequest selected = historyTable.getSelectionModel().getSelectedItem();
          HospitalSystem.deleteRow(selected);
          rows.remove(selected);
          loadRequests();
        });
  }

  private void loadRequests() {
    List<ConferenceRoomRequest> list =
        (List<ConferenceRoomRequest>) HospitalSystem.fetchAllObjects(new ConferenceRoomRequest());
    historyTable.getItems().removeAll();
    historyTable.getItems().setAll(list);
  }

  private void updateCurrentSelection() {
    ConferenceRoomRequest selected = historyTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      idField.setText(Integer.toString(selected.getRequestID()));
      statusField.setText(selected.getStatus().toString());
      assignedtoField.getSelectionModel().select(selected.getAssignedto());
    }
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  //  public String getText(javafx.event.ActionEvent actionEvent) {
  //    String inputtedText;
  //    inputtedText = inputBox.getText();
  //    inputBox.clear();
  //    return inputtedText;
  //  }

}