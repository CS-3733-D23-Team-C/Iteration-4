package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.dao.displays.signage.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class EditSignageController {
  String buttonColor = "-fx-background-color: white; -fx-text-fill: #02143B;";
  String selectedButtonColor = "-fx-background-color: #FCC201; -fx-text-fill: #02143B;";
  @FXML private Text AdminHome_Title;

  @FXML private Button add;

  @FXML private DatePicker date;

  @FXML private Button delete;

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

  @FXML private Button submit;

  @FXML private FilteredTableView<Sign> table1;

  @FXML private FilteredTableView<SignVersion> table2;
  @FXML private TableColumn signIDCol;
  @FXML private TableColumn signNameCol;
  @FXML private TableColumn signDateCol;

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
    if (selected != null) {
      signSystem.removeSignVersion(selected);
      this.loadSignTable();
    }
  }

  @FXML
  void getEditSelect(ActionEvent event) {
    add.setStyle(buttonColor);
    editSelect.setStyle(selectedButtonColor);
    table1.getSelectionModel().clearSelection();
    table2.getSelectionModel().clearSelection();
    edit = true;
    this.setAllNull();
    signID.setEditable(false);
    signName.setEditable(false);
    autofillMac.setVisible(false);
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
          this.loadSignTable();
        }
      }
    } else {
      Sign newSign = new Sign();
      newSign.setMacadd(signID.getText());
      newSign.setDevicename(signName.getText());
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
