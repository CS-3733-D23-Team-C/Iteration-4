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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientTableController {

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
  @FXML TableColumn<PatientUser, String> activeText;
  @FXML TextField idField;
  @FXML TextField nameField;
  @FXML TextField checkInField;
  @FXML TextField checkOutField;
  @FXML TextField phoneField;
  @FXML TextField roomField;
  @FXML TextField activeTextField;

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
    checkin.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("checkin"));
    checkout.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("checkout"));
    phone.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("phone"));
    room.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("room"));
    activeText.setCellValueFactory(new PropertyValueFactory<PatientUser, String>("activetext"));

    id.setText("ID");
    name.setText("Name");
    checkin.setText("Check In");
    checkout.setText("Check Out");
    phone.setText("Phone");
    room.setText("Room");
    activeText.setText("Active Text");

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
    addButton.setOnMouseClicked(
        event -> {
          hospitalSystem.addRow(getPatientUser());
          loadPatients();
        });
    deleteButton.setOnMouseClicked(
        event -> {
          hospitalSystem.deleteRow(getPatientUser());
          loadPatients();
        });
    updateButton.setOnMouseClicked(
        event -> {
          hospitalSystem.deleteRow(getPatientUser());
          hospitalSystem.addRow(getPatientUser());
          hospitalSystem.updateRow(getPatientUser());
          loadPatients();
        });
  }

  private void loadPatients() {
    List<PatientUser> list = (List<PatientUser>) HospitalSystem.fetchAllObjects(new PatientUser());
    patientTable.getItems().removeAll();
    patientTable.getItems().setAll(list);
  }

  private PatientUser getPatientUser() {
    PatientUser patientUser = null;
    try {
      int id = Integer.parseInt(idField.getText());

      String name = nameField.getText();
      String checkin = checkInField.getText();
      String checkout = checkOutField.getText();
      String phone = phoneField.getText();
      String room = roomField.getText();
      String activeText = activeTextField.getText();
      // for when check in and check out are strings they just get recorded as null

      patientUser = new PatientUser(id, name, checkin, checkout, phone, room, activeText);
    } catch (NumberFormatException e) {
      return null;
    }
    return patientUser;
  }

  private void updateEmpView() {
    PatientUser patientUser = patientTable.getSelectionModel().getSelectedItem();
    setPatientView(patientUser);
  }

  private void clearEmpView() {
    idField.setText("");
    nameField.setText("");
    checkInField.setText("");
    checkOutField.setText("");
    phoneField.setText("");
    roomField.setText("");
    activeTextField.setText("");
  }

  public void setPatientView(PatientUser selected) {
    idField.setText(Integer.toString(selected.getId()));
    nameField.setText(selected.getName());
    checkInField.setText(String.valueOf(selected.getIn()));
    checkOutField.setText(String.valueOf(selected.getOut()));
    phoneField.setText(selected.getPhone());
    roomField.setText(selected.getRoom());
    activeTextField.setText(String.valueOf(selected.isActiveText()));
  }

  public void getGoHome() {
    Navigation.navigate(Screen.HOME);
  }
}
