package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import edu.wpi.teamc.dao.users.login.Login;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class LoginTableController {

  @FXML private HBox bottomPane;

  @FXML private MenuItem admin;

  @FXML private MenuItem staff;

  @FXML private TableColumn<?, ?> department;

  @FXML private TableColumn<?, ?> id;

  @FXML private TableColumn<?, ?> name;

  @FXML private TextField passwordField;

  @FXML private MenuButton permission;

  @FXML private TableColumn<?, ?> position;

  //  @FXML private TableColumn<?, ?> username;

  @FXML private TextField usernameField;

  ///////////
  @FXML private FilteredTableView<Login> loginTable;

  @FXML TableColumn<Login, String> username;
  @FXML TableColumn<Login, String> email;
  @FXML TableColumn<Login, String> permissions;
  @FXML TableColumn<Login, Boolean> twoFactor;

  @FXML Button clearButton;
  @FXML Button updateButton;
  @FXML Button deleteButton;
  @FXML Button addButton;

  Login login = null;

  ObservableList<Login> rows = FXCollections.observableArrayList();

  HospitalSystem hospitalSystem = new HospitalSystem();

  public void initialize() {
    username.setCellValueFactory(new PropertyValueFactory<Login, String>("username"));
    email.setCellValueFactory(new PropertyValueFactory<Login, String>("email"));
    permissions.setCellValueFactory(new PropertyValueFactory<Login, String>("permissions"));
    twoFactor.setCellValueFactory(new PropertyValueFactory<Login, Boolean>("OTPEnabled"));

    username.setText("Username");
    email.setText("Email");
    permissions.setText("Permissions");
    twoFactor.setText("Two factor");
    //
    deleteButton
        .disableProperty()
        .bind(Bindings.isEmpty(loginTable.getSelectionModel().getSelectedItems()));
    updateButton
        .disableProperty()
        .bind(Bindings.isEmpty(loginTable.getSelectionModel().getSelectedItems()));

    List<Login> list = (List<Login>) HospitalSystem.fetchAllObjects(new Login());
    rows.addAll(list);
    loginTable.getItems().setAll(rows);

    loginTable.setOnMouseClicked(
        event -> {
          updateLoginView();
        });
    clearButton.setOnMouseClicked(
        event -> {
          clearLoginView();
        });
    addButton.setOnMouseClicked(
        event -> {
          hospitalSystem.addRow(getLogin());
          loadLogins();
        });
    deleteButton.setOnMouseClicked(
        event -> {
          hospitalSystem.deleteRow(getLogin());
          loadLogins();
        });
    updateButton.setOnMouseClicked(
        event -> {
          hospitalSystem.updateRow(getLogin());
          loadLogins();
        });
    permission.setOnAction(
        event -> {
          String selected = event.getTarget().toString();
          System.out.println("selected");
          permission.setText(selected);
        });
  }

  private void loadLogins() {
    List<Login> list = (List<Login>) HospitalSystem.fetchAllObjects(new Login());
    loginTable.getItems().removeAll();
    loginTable.getItems().setAll(list);
  }

  private Login getLogin() {
    //    Login login = null;
    try {
      String username = usernameField.getText();
      String password = passwordField.getText();
      PERMISSIONS permissions = PERMISSIONS.valueOf(permission.getText());
      login = new Login(username, password, permissions);
    } catch (NumberFormatException e) {
      return null;
    }
    return login;
  }

  private void updateLoginView() {
    Login login = loginTable.getSelectionModel().getSelectedItem();
    setLoginView(login);
  }

  @FXML
  private void getAdmin(ActionEvent event) {
    permission.setText(admin.getText());
  }

  @FXML
  private void getStaff(ActionEvent event) {
    permission.setText(staff.getText());
  }

  private void clearLoginView() {
    usernameField.setText("");
    passwordField.setText("");
    permission.setText("");
  }

  public void setLoginView(Login selected) {
    usernameField.setText(selected.getUsername());
    passwordField.setText(selected.getHashedPassword());
    permission.setText(selected.getPermissions().toString());
  }

  @FXML
  void getPermission(ActionEvent event) {}
}
