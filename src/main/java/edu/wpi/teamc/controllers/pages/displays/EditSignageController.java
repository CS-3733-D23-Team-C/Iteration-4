package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.signage.SignEntry;
import edu.wpi.teamc.dao.users.IUser;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableView;

public class EditSignageController {
  String buttonColor = "-fx-background-color: white; -fx-text-fill: #02143B;";
  String selectedButtonColor = "-fx-background-color: #FCC201; -fx-text-fill: #02143B;";
  @FXML private Button addTab;

  @FXML private Label dataText;

  @FXML private SearchableComboBox<?> direction1;

  @FXML private SearchableComboBox<?> direction2;

  @FXML private SearchableComboBox<?> direction3;

  @FXML private SearchableComboBox<?> direction4;

  @FXML private SearchableComboBox<?> direction5;

  @FXML private Button editSelectedTab;

  @FXML private SearchableComboBox location1;

  @FXML private SearchableComboBox location2;

  @FXML private SearchableComboBox location3;

  @FXML private SearchableComboBox location4;

  @FXML private SearchableComboBox location5;

  @FXML private FilteredTableView<SignEntry> moveTable;

  @FXML private TextField selectedSignID;

  @FXML private TextField signNameField;

  @FXML private FilteredTableView<SignEntry> signTable;
  @FXML TableColumn Column1;
  @FXML TableColumn Column2;

  @FXML
  void getAdd(ActionEvent event) {}

  @FXML
  void getEditSelect(ActionEvent event) {}

  @FXML
  void initailize() {
    this.getAdd(null);
    ObservableList<SignEntry> rows = FXCollections.observableArrayList();
    Column1.setCellValueFactory(new PropertyValueFactory<SignEntry, Integer>("id"));
    Column2.setCellValueFactory(new PropertyValueFactory<SignEntry, IUser>("requester"));
    Column1.setText("ID");
    Column2.setText("Sign Name");
    List<SignEntry> signs =
        (List<SignEntry>) HospitalSystem.fetchAllObjects( new SignEntry());
   for (SignEntry sign : signs) {
      rows.add(sign);
    }

    signTable.setItems(rows);
  }

  @FXML
  void getSubmit(ActionEvent event) {}
}
