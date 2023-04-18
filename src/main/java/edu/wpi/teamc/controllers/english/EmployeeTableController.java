package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import org.controlsfx.control.tableview2.FilteredTableView;

public class EmployeeTableController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  //    @FXML private Button testButton;
  //    @FXML private TextField inputBox;
  @FXML private FilteredTableView<EmployeeUser> employeeTable;
  //    @FXML
  //    TableView<Move> otherTable;
  @FXML TableColumn<EmployeeUser, Integer> id;
  @FXML TableColumn<EmployeeUser, String> username;
  @FXML TableColumn<EmployeeUser, String> name;
  @FXML TableColumn<EmployeeUser, String> department;
  @FXML TableColumn<EmployeeUser, String> position;
  @FXML Button clearButton;
  @FXML Button addButton;
  @FXML Button deleteButton;
  @FXML Button updateButton;
  @FXML TextField idField;
  @FXML TextField usernameField;
  @FXML TextField nameField;
  @FXML TextField departmentField;
  @FXML TextField positionField;

  @FXML MFXFilterComboBox<EmployeeUser> employeeID;
  @FXML MFXFilterComboBox<LocationName> locationName;
  @FXML DatePicker date;

  ObservableList<EmployeeUser> rows = FXCollections.observableArrayList();

  //    @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    id.setCellValueFactory(new PropertyValueFactory<EmployeeUser, Integer>("id"));
    username.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("userName"));
    name.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("name"));
    department.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("department"));
    position.setCellValueFactory(new PropertyValueFactory<EmployeeUser, String>("position"));

    //    id.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("id"));
    //    username.setCellValueFactory(new PropertyValueFactory<TableRow, String>("userName"));
    //    name.setCellValueFactory(new PropertyValueFactory<TableRow, String>("name"));
    //    department.setCellValueFactory(new PropertyValueFactory<TableRow, String>("department"));
    //    position.setCellValueFactory(new PropertyValueFactory<TableRow, String>("position"));

    //    id.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    username.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    name.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    department.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    position.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    id.setText("ID");
    username.setText("Username");
    name.setText("Name");
    department.setText("Department");
    position.setText("Position");

    List<EmployeeUser> list =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    rows.addAll(list);
    employeeTable.getItems().setAll(rows);
    employeeTable.setEditable(true);

    //        List<EmployeeUser> employeeUsers = (List<EmployeeUser>)
    // HospitalSystem.fetchAllObjects(new EmployeeUser());
    //        employeeID.setItems(FXCollections.observableArrayList(employeeUsers));

    employeeTable.setOnMouseClicked(
        event -> {
          updateEmployeeView();
        });
    clearButton.setOnAction(
        event -> {
          clearView();
        });
    addButton.setOnAction(
        event -> {
          addEmployee();
        });
  }

  private void addEmployee() {
    int id = Integer.valueOf(idField.getText());
    String username = usernameField.getText();
    String name = nameField.getText();
    String department = departmentField.getText();
    String position = positionField.getText();
    EmployeeUser employeeUser = new EmployeeUser(id, username, name, department, position);
    HospitalSystem.addRow(employeeUser);
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  private void updateEmployeeView() {
    EmployeeUser selected = employeeTable.getSelectionModel().getSelectedItem();
    setEmployeeView(selected);
  }

  private void setEmployeeView(EmployeeUser selected) {
    idField.setText(Integer.toString(selected.getId()));
    usernameField.setText(selected.getUserName());
    nameField.setText(selected.getName());
    departmentField.setText(selected.getDepartment());
    positionField.setText(selected.getPosition());
  }

  private void clearView() {
    idField.setText("");
    usernameField.setText("");
    nameField.setText("");
    departmentField.setText("");
    positionField.setText("");
  }
}
