package edu.wpi.teamc.controllers.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class LoginTableController {

  @FXML private Button addButton;

  @FXML private HBox bottomPane;

  @FXML private Button clearButton;

  @FXML private Button deleteButton;

  @FXML private TableColumn<?, ?> department;

  @FXML private FilteredTableView<?> employeeTable;

  @FXML private TableColumn<?, ?> id;

  @FXML private TableColumn<?, ?> name;

  @FXML private TextField passwordField;

  @FXML private MenuButton permission;

  @FXML private TableColumn<?, ?> position;

  @FXML private Button updateButton;

  @FXML private TableColumn<?, ?> username;

  @FXML private TextField usernameField;

  @FXML
  void getPermission(ActionEvent event) {}
}
