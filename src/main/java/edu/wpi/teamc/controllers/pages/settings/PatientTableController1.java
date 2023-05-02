package edu.wpi.teamc.controllers.pages.settings;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientTableController1 {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private TableView<PatientUser> patientTable;

  @FXML TableColumn<PatientUser, Integer> id;
  @FXML TableColumn<PatientUser, String> name;
  @FXML TableColumn<PatientUser, String> checkin;
  @FXML TableColumn<PatientUser, String> checkout;
  @FXML TableColumn<PatientUser, String> phone;
  @FXML TableColumn<PatientUser, String> room;
  @FXML TableColumn<PatientUser, String> activetext;

  @FXML TextField idField;
  @FXML TextField nameField;
  @FXML TextField checkinField;
  @FXML TextField checkoutField;
  @FXML TextField phoneField;
  @FXML TextField roomField;
  @FXML TextField activetextField;

  @FXML Button clearButton;
  @FXML Button updateButton;
  @FXML Button deleteButton;
  @FXML Button addButton;

  ObservableList<PatientUser> rows = FXCollections.observableArrayList();

  HospitalSystem hospitalSystem = new HospitalSystem();

  /** Method run when controller is initialized */
  public void initialize() {
    id.setCellValueFactory(new PropertyValueFactory<PatientUser, Integer>("id"));
    name.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("name"));
    checkin.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("name"));
    checkout.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("department"));
    phone.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("position"));
    room.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("position"));
    activetext.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("position"));

    id.setText("ID");
    name.setText("Name");
    checkin.setText("Check In time");
    checkout.setText("Check Out time");
    phone.setText("Phone Number");
    room.setText("Room");
    activetext.setText("Active");
    //
    deleteButton
        .disableProperty()
        .bind(Bindings.isEmpty(patientTable.getSelectionModel().getSelectedItems()));
    updateButton
        .disableProperty()
        .bind(Bindings.isEmpty(patientTable.getSelectionModel().getSelectedItems()));

    List<PatientUser> list = (List<PatientUser>) HospitalSystem.fetchAllObjects(new PatientUser());
    rows.addAll(list);
    patientTable.getItems().setAll(rows);

    patientTable.setOnMouseClicked(
        event -> {
          updateEmpView();
        });
    clearButton.setOnMouseClicked(
        event -> {
          clearEmpView();
        });
  }

  private void loadEmployees() {
    List<PatientUser> list = (List<PatientUser>) HospitalSystem.fetchAllObjects(new PatientUser());
    patientTable.getItems().removeAll();
    patientTable.getItems().setAll(list);
  }

  /*private PatientUser getEmployeeUser() {
    EmployeeUser employeeUser = null;
    try {
      int id = Integer.parseInt(idField.getText());
      String username = usernameField.getText();
      String name = nameField.getText();
      String department = departmentField.getText();
      String position = positionField.getText();
      employeeUser = new EmployeeUser(id, username, name, department, position);
    } catch (NumberFormatException e) {
      return null;
    }
    return employeeUser;
  }*/

  private void updateEmpView() {
    PatientUser patientUser = patientTable.getSelectionModel().getSelectedItem();
    setPatientView(patientUser);
  }

  private void clearEmpView() {
    idField.setText("");
    nameField.setText("");
    checkinField.setText("");
    checkoutField.setText("");
    phoneField.setText("");
    roomField.setText("");
    activetextField.setText("");
  }

  public void setPatientView(PatientUser selected) {
    idField.setText(Integer.toString(selected.getId()));
    nameField.setText(selected.getName());
    checkinField.setText(String.valueOf(selected.getIn()));
    checkoutField.setText(String.valueOf(selected.getOut()));
    phoneField.setText(selected.getPhone());
    roomField.setText(selected.getRoom());
    activetextField.setText(String.valueOf(selected.isActiveText()));
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }
}
