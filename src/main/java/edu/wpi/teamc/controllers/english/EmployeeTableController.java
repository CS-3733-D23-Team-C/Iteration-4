package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.Login;
import edu.wpi.teamc.dao.users.PERMISSIONS;
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

public class EmployeeTableController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private FilteredTableView<EmployeeUser> employeeTable;

  @FXML TableColumn<EmployeeUser, Integer> id;
  @FXML TableColumn<EmployeeUser, String> username;
  @FXML TableColumn<EmployeeUser, String> name;
  @FXML TableColumn<EmployeeUser, String> department;
  @FXML TableColumn<EmployeeUser, String> position;
  @FXML TextField idField;
  @FXML TextField usernameField;
  @FXML TextField nameField;
  @FXML TextField departmentField;
  @FXML TextField positionField;

  @FXML Button clearButton;
  @FXML Button updateButton;
  @FXML Button deleteButton;
  @FXML Button addButton;

  ObservableList<EmployeeUser> rows = FXCollections.observableArrayList();

  HospitalSystem hospitalSystem = new HospitalSystem();

  /** Method run when controller is initialized */
  public void initialize() {
    id.setCellValueFactory(new PropertyValueFactory<EmployeeUser, Integer>("id"));
    username.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("username"));
    name.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("name"));
    department.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("department"));
    position.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("position"));

    id.setText("ID");
    username.setText("Username");
    name.setText("Name");
    department.setText("Department");
    position.setText("Position");

    List<EmployeeUser> list =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    rows.addAll(list);
    employeeTable.getItems().setAll(rows);

    employeeTable.setOnMouseClicked(
        event -> {
          updateEmpView();
        });
    clearButton.setOnMouseClicked(
        event -> {
          clearEmpView();
        });
    addButton.setOnMouseClicked(
        event -> {
          hospitalSystem.addRow(
              new Login(getEmployeeUser().getUserName(), "staff", PERMISSIONS.STAFF));
          hospitalSystem.addRow(getEmployeeUser());
          loadEmployees();
        });
    deleteButton.setOnMouseClicked(
        event -> {
          hospitalSystem.deleteRow(getEmployeeUser());
          loadEmployees();
        });
    updateButton.setOnMouseClicked(
        event -> {
          hospitalSystem.deleteRow(
              new Login(getEmployeeUser().getUserName(), "staff", PERMISSIONS.STAFF));
          hospitalSystem.addRow(
              new Login(getEmployeeUser().getUserName(), "staff", PERMISSIONS.STAFF));
          hospitalSystem.updateRow(getEmployeeUser());
          loadEmployees();
        });
  }

  private void loadEmployees() {
    List<EmployeeUser> list =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    employeeTable.getItems().removeAll();
    employeeTable.getItems().setAll(list);
  }

  private EmployeeUser getEmployeeUser() {
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
  }

  private void updateEmpView() {
    EmployeeUser employeeUser = employeeTable.getSelectionModel().getSelectedItem();
    setEmployeeView(employeeUser);
  }

  private void clearEmpView() {
    idField.setText("");
    usernameField.setText("");
    nameField.setText("");
    departmentField.setText("");
    positionField.setText("");
  }

  public void setEmployeeView(EmployeeUser selected) {
    idField.setText(Integer.toString(selected.getId()));
    usernameField.setText(selected.getUserName());
    nameField.setText(selected.getName());
    departmentField.setText(selected.getDepartment());
    positionField.setText(selected.getPosition());
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }
}
