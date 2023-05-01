package edu.wpi.teamc.controllers.pages.settings;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import edu.wpi.teamc.dao.users.login.Login;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class LoginTableController {

  //  @FXML private TableColumn<?, ?> username;

  @FXML private TextField usernameField;
  @FXML private TextField permissionField;

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
    twoFactor.setText("MFA Enabled");
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
          updateSelectedLogin();
        });
    clearButton.setOnMouseClicked(
        event -> {
          clearLoginView();
        });

    deleteButton.setOnMouseClicked(
        event -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Delete Login");
          alert.setHeaderText("Are you sure you want to delete this login?");
          alert.setContentText("This action cannot be undone.");
          alert.showAndWait();
          if (alert.getResult() == ButtonType.OK) {
            hospitalSystem.deleteRow(this.updateSelectedLogin());
            loadLogins();
          }
        });
    updateButton.setOnMouseClicked(
        event -> {
          this.getUpdatePopup(this.updateSelectedLogin());
        });

    addButton.setOnMouseClicked(
        event -> {
          this.getAddPopup();
        });
  }

  private void loadLogins() {
    List<Login> list = (List<Login>) HospitalSystem.fetchAllObjects(new Login());
    loginTable.getItems().removeAll();
    loginTable.getItems().setAll(list);
  }

  private Login updateSelectedLogin() {
    Login login = loginTable.getSelectionModel().getSelectedItem();
    setLoginView(login);
    return login;
  }

  private void clearLoginView() {
    usernameField.setText("");
    permissionField.setText("");
  }

  public void setLoginView(Login selected) {
    usernameField.setText(selected.getUsername());
    permissionField.setText(selected.getPermissions().toString());
  }

  @FXML
  public void getAddPopup() {
    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Add a new login");
    Label usernameLabel = new Label("Username");
    Label passwordLabel = new Label("Password");
    Label emailLabel = new Label("Email");
    Label permissionsLabel = new Label("Permissions");
    TextField username = new TextField();
    TextField password = new TextField();
    TextField email = new TextField();
    SearchableComboBox<PERMISSIONS> permissions = new SearchableComboBox();
    permissions.getItems().addAll(PERMISSIONS.values());
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    VBox vbox = new VBox();

    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");
    usernameLabel.setLabelFor(username);
    passwordLabel.setLabelFor(password);
    emailLabel.setLabelFor(email);
    permissionsLabel.setLabelFor(permissions);
    usernameLabel.getStyleClass().add("whiteLabel");
    passwordLabel.getStyleClass().add("whiteLabel");
    emailLabel.getStyleClass().add("whiteLabel");
    permissionsLabel.getStyleClass().add("whiteLabel");

    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });
    confirmButton.setOnAction(
        e -> {
          if (username.getText() != null
              && !username.getText().equals("")
              && !password.equals("")
              && !email.equals("")
              && permissions.getValue() != null) {
            Login login = new Login(username.getText(), password.getText(), permissions.getValue());
            login.setEmail(email.getText());
            hospitalSystem.addRow(login);
            this.loadLogins();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
          } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
          }
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);

    vbox.getChildren()
        .addAll(
            prompt,
            usernameLabel,
            username,
            emailLabel,
            email,
            passwordLabel,
            password,
            permissionsLabel,
            permissions,
            confirmButton,
            cancelButton);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 300, 450);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Add new Login");
    stage.show();
  }

  public void getUpdatePopup(Login selectedLogin) {
    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Update login");
    Label usernameLabel = new Label("Username");
    Label passwordLabel = new Label("Password (leave blank to keep current)");
    Label emailLabel = new Label("Email");
    Label permissionsLabel = new Label("Permissions");
    TextField username = new TextField();
    TextField password = new TextField();
    TextField email = new TextField();
    SearchableComboBox<PERMISSIONS> permissions = new SearchableComboBox();
    permissions.getItems().addAll(PERMISSIONS.values());
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    VBox vbox = new VBox();

    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");
    usernameLabel.setLabelFor(username);
    passwordLabel.setLabelFor(password);
    emailLabel.setLabelFor(email);
    permissionsLabel.setLabelFor(permissions);
    usernameLabel.getStyleClass().add("whiteLabel");
    passwordLabel.getStyleClass().add("whiteLabel");
    emailLabel.getStyleClass().add("whiteLabel");
    permissionsLabel.getStyleClass().add("whiteLabel");

    username.setText(selectedLogin.getUsername());
    username.setEditable(false);
    password.setText("");
    email.setText(selectedLogin.getEmail());
    permissions.setValue(selectedLogin.getPermissions());

    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });
    confirmButton.setOnAction(
        e -> {
          if (username.getText() != null
              && !username.getText().equals("")
              && permissions.getValue() != null) {

            selectedLogin.setEmail(email.getText());
            selectedLogin.setPermissions(permissions.getValue());
            if (!password.getText().equals("")) {
              selectedLogin.setPassword(password.getText());
            }
            hospitalSystem.updateRow(selectedLogin);
            this.loadLogins();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
          } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
          }
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);

    vbox.getChildren()
        .addAll(
            prompt,
            usernameLabel,
            username,
            emailLabel,
            email,
            passwordLabel,
            password,
            permissionsLabel,
            permissions,
            confirmButton,
            cancelButton);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 300, 450);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Update Login");
    stage.show();
  }
}
