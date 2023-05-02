package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.displays.signage.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class EditSignageController {
  String buttonColor = "-fx-background-color: white; -fx-text-fill: #02143B;";
  String selectedButtonColor = "-fx-background-color: #FCC201; -fx-text-fill: #02143B;";

  @FXML private Button add;

  @FXML private DatePicker date;

  @FXML private SearchableComboBox<String> direction1;

  @FXML private SearchableComboBox<String> direction2;

  @FXML private SearchableComboBox<String> direction3;

  @FXML private SearchableComboBox<String> direction4;

  @FXML private SearchableComboBox<String> direction5;

  @FXML private SearchableComboBox<String> direction6;

  @FXML private Button editSelect;

  @FXML private TextField location1;

  @FXML private TextField location2;

  @FXML private TextField location3;

  @FXML private TextField location4;

  @FXML private TextField location5;

  @FXML private TextField location6;

  @FXML private TextField signID;

  @FXML private TextField signName;

  @FXML private TableView<Sign> table1;

  @FXML private TableView<SignVersion> table2;
  @FXML private TableColumn signIDCol;
  @FXML private TableColumn signNameCol;
  @FXML private TableColumn signDateCol;
  @FXML private Hyperlink editMac;
  @FXML private Hyperlink editDeviceName;

  @FXML Hyperlink autofillMac;

  SignSystem signSystem;

  public void initialize() {
    this.loadSignTable();

    direction1
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));
    direction2
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));
    direction3
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));
    direction4
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));
    direction5
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));
    direction6
        .getItems()
        .addAll(FXCollections.observableArrayList(DIRECTION.getDirectionStrings()));

    table1.setOnMouseClicked(
        event -> {
          if (edit) {
            updateCurrentSelectionSign();
          }
        });

    table2.setOnMouseClicked(
        event -> {
          if (edit) {
            updateCurrentSelectionVersion();
          }
        });
    table1
        .getStylesheets()
        .add(Main.class.getResource("views/pages/requests/RequestHistory.css").toString());
    table2
        .getStylesheets()
        .add(Main.class.getResource("views/pages/requests/RequestHistory.css").toString());
  }

  private void loadSignTable() {
    this.getEditSelect(null);
    signSystem = new SignSystem();
    ObservableList<Sign> rows = FXCollections.observableArrayList();
    signIDCol.setCellValueFactory(new PropertyValueFactory<Sign, String>("macadd"));
    signNameCol.setCellValueFactory(new PropertyValueFactory<Sign, String>("devicename"));
    signIDCol.setText("Mac Address");
    signNameCol.setText("Device Name");
    signDateCol.setText("Date");
    HashMap<String, Sign> signs = signSystem.getSigns();
    for (String key : signs.keySet()) {
      rows.add(signs.get(key));
    }
    table1.setItems(rows);
  }

  boolean edit = false;

  private void setAllNull() {
    signID.setText(null);
    signName.setText(null);
    table2.setItems(null);
    date.setValue(null);
    direction1.setValue(null);
    direction2.setValue(null);
    direction3.setValue(null);
    direction4.setValue(null);
    direction5.setValue(null);
    direction6.setValue(null);
    location1.setText(null);
    location2.setText(null);
    location3.setText(null);
    location4.setText(null);
    location5.setText(null);
    location6.setText(null);
  }

  private void updateCurrentSelectionSign() {
    Sign selected = table1.getSelectionModel().getSelectedItem();
    this.setAllNull();

    if (selected != null) {
      signID.setText(selected.getMacadd());
      signName.setText(selected.getDevicename());
      ObservableList<SignVersion> rows = FXCollections.observableArrayList();
      signDateCol.setCellValueFactory(new PropertyValueFactory<SignVersion, Date>("date"));
      HashMap<Date, SignVersion> signVersions = selected.getSignVersions();
      for (Date key : signVersions.keySet()) {
        rows.add(signVersions.get(key));
      }
      table2.setItems(rows);
    }
  }

  private void updateCurrentSelectionVersion() {
    SignVersion selected = table2.getSelectionModel().getSelectedItem();
    direction1.setValue(null);
    direction2.setValue(null);
    direction3.setValue(null);
    direction4.setValue(null);
    direction5.setValue(null);
    direction6.setValue(null);
    location1.setText(null);
    location2.setText(null);
    location3.setText(null);
    location4.setText(null);
    location5.setText(null);
    location6.setText(null);
    if (selected != null) {
      date.setValue(selected.getDate().toLocalDate());
      ArrayList<SignEntry> selectedEntries = (ArrayList<SignEntry>) selected.getSignEntries();
      try {
        direction1.setValue(selectedEntries.get(0).getDirection().toString());
        direction2.setValue(selectedEntries.get(1).getDirection().toString());
        direction3.setValue(selectedEntries.get(2).getDirection().toString());
        direction4.setValue(selectedEntries.get(3).getDirection().toString());
        direction5.setValue(selectedEntries.get(4).getDirection().toString());
        direction6.setValue(selectedEntries.get(5).getDirection().toString());
      } catch (Exception e) {
        // System.out.println("Less than 6 entries");
      }
      try {
        location1.setText(selectedEntries.get(0).getLocationname());
        location2.setText(selectedEntries.get(1).getLocationname());
        location3.setText(selectedEntries.get(2).getLocationname());
        location4.setText(selectedEntries.get(3).getLocationname());
        location5.setText(selectedEntries.get(4).getLocationname());
        location6.setText(selectedEntries.get(5).getLocationname());
      } catch (Exception e) {
        // System.out.println("Less than 6 entries");
      }
    }
  }

  @FXML
  void getAdd(ActionEvent event) {
    add.setStyle(selectedButtonColor);
    editSelect.setStyle(buttonColor);
    edit = false;
    this.setAllNull();
    signID.setEditable(true);
    signName.setEditable(true);
    autofillMac.setVisible(true);
    autofillMac.setText("Autofill Current Device Mac Address");
    editMac.setVisible(false);
    editDeviceName.setVisible(false);
  }

  @FXML
  void getAutofillMac(ActionEvent event) {
    signID.setText(SignEntry.getCurrentDeviceMacAddress());
  }

  @FXML
  void getDate(ActionEvent event) {}

  @FXML
  void getDelete(ActionEvent event) {
    SignVersion selected = table2.getSelectionModel().getSelectedItem();
    String macadd = selected.getSignEntries().get(0).getMacadd();
    if (selected != null) {
      signSystem.removeSignVersion(selected);
      if (signSystem.getSigns().containsKey(macadd)) {
        this.updateCurrentSelectionSign();
      } else {
        this.loadSignTable();
      }
    }
  }

  @FXML
  void getEditSelect(ActionEvent event) {
    add.setStyle(buttonColor);
    editSelect.setStyle(selectedButtonColor);
    table2.getSelectionModel().clearSelection();
    autofillMac.setText("");
    edit = true;
    this.setAllNull();
    signID.setEditable(false);
    signName.setEditable(false);
    autofillMac.setVisible(false);
    editMac.setVisible(true);
    editDeviceName.setVisible(true);
  }

  @FXML
  void getEditMac(ActionEvent event) {
    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Enter New Mac Address");
    TextField newMac = new TextField(signID.getText());
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    Hyperlink hyperlink = new Hyperlink("Autofill Current Device Mac Address");
    HBox newMachbox = new HBox();
    VBox vbox = new VBox();

    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");

    hyperlink.setStyle(" -fx-font-size: 10px; -fx-padding-left: 10px; -fx-text-fill: FFFFFF");
    hyperlink.setOnAction(
        e -> {
          newMac.setText(SignEntry.getCurrentDeviceMacAddress());
        });

    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });
    confirmButton.setOnAction(
        e -> {
          if (newMac.getText() != null && !newMac.getText().equals("")) {
            signSystem.updateMacAddress(signID.getText(), newMac.getText());
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
            this.loadSignTable();
          }
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);
    newMachbox.getChildren().addAll(newMac, hyperlink);

    vbox.getChildren().addAll(prompt, newMachbox, confirmButton, cancelButton);

    // Set and show screen

    if (signID.getText() != null && !signID.getText().equals("")) {
      AnchorPane aPane = new AnchorPane();
      aPane.getChildren().addAll(vbox);
      borderPane.getChildren().add(aPane);
      Scene scene = new Scene(borderPane, 450, 225);
      scene
          .getStylesheets()
          .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
      borderPane.relocate(0, 0);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setTitle("Edit Mac Address");
      stage.show();
      stage.setAlwaysOnTop(true);
    }
  }

  @FXML
  void getEditDeviceName(ActionEvent event) {
    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Enter New Device Name");
    TextField newName = new TextField(signName.getText());
    Text oldMac = new Text("Mac Address Selected: " + signID.getText());
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    HBox newNamehbox = new HBox();
    VBox vbox = new VBox();
    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");
    oldMac.setStyle(" -fx-font-size: 11px; -fx-padding-left: 10px;  -fx-fill: FFFFFF;");
    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });
    confirmButton.setOnAction(
        e -> {
          if (newName.getText() != null && !newName.getText().equals("")) {
            signSystem.updateDeviceName(signID.getText(), newName.getText());
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
            this.loadSignTable();
          }
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);
    newNamehbox.getChildren().addAll(newName);

    vbox.getChildren().addAll(prompt, oldMac, newNamehbox, confirmButton, cancelButton);

    // Set and show screen

    if (signID.getText() != null && !signID.getText().equals("")) {
      AnchorPane aPane = new AnchorPane();
      aPane.getChildren().addAll(vbox);
      borderPane.getChildren().add(aPane);
      Scene scene = new Scene(borderPane, 400, 250);
      scene
          .getStylesheets()
          .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
      borderPane.relocate(0, 0);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setTitle("Edit Device Name");
      stage.show();
      stage.setAlwaysOnTop(true);
    }
  }

  @FXML
  void getSubmit(ActionEvent event) {
    if (edit) {
      SignVersion selected = table2.getSelectionModel().getSelectedItem();
      Sign selectedSign = table1.getSelectionModel().getSelectedItem();
      Date currdate = Date.valueOf(date.getValue());
      if (selected != null) {
        SignVersion newVersion = new SignVersion();
        newVersion.setDate(currdate);
        try {
          if (direction1.getValue() != null
              && location1.getText() != null
              && DIRECTION.valueOf(direction1.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    currdate,
                    location1.getText(),
                    DIRECTION.valueOf(direction1.getValue())));
          }
        } catch (NullPointerException e) {
        }
        try {
          if (direction2.getValue() != null
              && location2.getText() != null
              && DIRECTION.valueOf(direction2.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    currdate,
                    location2.getText(),
                    DIRECTION.valueOf(direction2.getValue())));
          }
        } catch (NullPointerException e) {
        }
        try {
          if (direction3.getValue() != null
              && location3.getText() != null
              && DIRECTION.valueOf(direction3.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    currdate,
                    location3.getText(),
                    DIRECTION.valueOf(direction3.getValue())));
          }
        } catch (NullPointerException e) {
        }

        try {
          if (direction4.getValue() != null
              && location4.getText() != null
              && DIRECTION.valueOf(direction4.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    Date.valueOf(date.getValue()),
                    location4.getText(),
                    DIRECTION.valueOf(direction4.getValue())));
          }

        } catch (NullPointerException e) {
        }
        try {
          if (direction5.getValue() != null
              && location5.getText() != null
              && DIRECTION.valueOf(direction5.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    Date.valueOf(date.getValue()),
                    location5.getText(),
                    DIRECTION.valueOf(direction5.getValue())));
          }
        } catch (NullPointerException e) {
        }
        try {
          if (direction6.getValue() != null
              && location6.getText() != null
              && DIRECTION.valueOf(direction6.getValue()).toString() != null) {
            newVersion.addSignEntry(
                new SignEntry(
                    selectedSign.getMacadd(),
                    selectedSign.getDevicename(),
                    Date.valueOf(date.getValue()),
                    location6.getText(),
                    DIRECTION.valueOf(direction6.getValue())));
          }
        } catch (NullPointerException e) {
        }
        if (newVersion.getSignEntries().size() > 0) {
          if (newVersion.getDate().equals(selected.getDate())) {
            signSystem.removeSignVersion(selected);
          }
          signSystem.addSignVersion(newVersion);
          int selectedIndex = table1.getSelectionModel().getSelectedIndex();
          this.getEditSelect(event);
          this.loadSignTable();
          table1.getSelectionModel().select(selectedIndex);
          this.updateCurrentSelectionSign();
        }
      }
    } else {
      Sign newSign = new Sign();
      newSign.setMacadd(signID.getText());
      newSign.setDevicename(signName.getText());
      if (signSystem.getSigns().containsKey(signID.getText())) {
        newSign = signSystem.getSigns().get(signID.getText());
      }
      SignVersion newVersion = new SignVersion();
      newVersion.setDate(Date.valueOf(date.getValue()));
      try {
        if (direction1.getValue() != null
            && location1.getText() != null
            && DIRECTION.valueOf(direction1.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location1.getText(),
                  DIRECTION.valueOf(direction1.getValue())));
        }
      } catch (NullPointerException e) {

      }
      try {
        if (direction2.getValue() != null
            && location2.getText() != null
            && DIRECTION.valueOf(direction2.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location2.getText(),
                  DIRECTION.valueOf(direction2.getValue())));
        }
      } catch (NullPointerException e) {
      }
      try {
        if (direction3.getValue() != null
            && location3.getText() != null
            && DIRECTION.valueOf(direction3.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location3.getText(),
                  DIRECTION.valueOf(direction3.getValue())));
        }
      } catch (NullPointerException e) {
      }
      try {
        if (direction4.getValue() != null
            && location4.getText() != null
            && DIRECTION.valueOf(direction4.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location4.getText(),
                  DIRECTION.valueOf(direction4.getValue())));
        }
      } catch (NullPointerException e) {
      }
      try {
        if (direction5.getValue() != null
            && location5.getText() != null
            && DIRECTION.valueOf(direction5.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location5.getText(),
                  DIRECTION.valueOf(direction5.getValue())));
        }
      } catch (NullPointerException e) {

      }
      try {
        if (direction6.getValue() != null
            && location6.getText() != null
            && DIRECTION.valueOf(direction6.getValue()).toString() != null) {
          newVersion.addSignEntry(
              new SignEntry(
                  newSign.getMacadd(),
                  newSign.getDevicename(),
                  Date.valueOf(date.getValue()),
                  location6.getText(),
                  DIRECTION.valueOf(direction6.getValue())));
        }
      } catch (NullPointerException e) {
      }
      if (newVersion.getSignEntries().size() > 0) {
        signSystem.addSignVersion(newVersion);
        this.loadSignTable();
      }
    }
  }
}
