package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.Move;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.tableview2.FilteredTableView;

public class MoveTableController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<Move> historyTable;
  @FXML TableView<Move> otherTable;
  @FXML TableColumn<Move, Integer> ColumnOne;
  @FXML TableColumn<Move, String> ColumnTwo;
  @FXML TableColumn<Move, Date> ColumnThree;
  @FXML MFXFilterComboBox<Node> nodeID;
  @FXML MFXFilterComboBox<LocationName> locationName;
  @FXML DatePicker date;

  ObservableList<Move> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<Move, Integer>("id"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<Move, Date>("date"));
    ColumnOne.setText("Node ID");
    ColumnTwo.setText("Long Name");
    ColumnThree.setText("Date");

    //    //    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    ObservableList<Move> list = (ObservableList<Move>) HospitalSystem.fetchAllObjects(new Move(-1, "", null));
    rows.addAll(list);
    historyTable.getItems().setAll(rows);

    List<Node> nodes = (List<Node>) HospitalSystem.fetchAllObjects(new Node());
    nodeID.setItems(FXCollections.observableArrayList(nodes));
    List<LocationName> locationNames =
        (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
    locationName.setItems(FXCollections.observableArrayList(locationNames));
    //
    //    System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

    public void getAdd(ActionEvent event) {
        Move move = new Move();
        move.setNodeID(nodeID.getValue().getNodeID());
        move.setLongName(locationName.getValue().getLongName());
        move.setDate(Date.valueOf(date.getValue()));
        HospitalSystem.addRow(move);
        rows.add(move);
        historyTable.getItems().setAll(rows);
    }

  public void getDelete(ActionEvent event) {
    Move move = historyTable.getSelectionModel().getSelectedItem();
    HospitalSystem.deleteRow(move);
    rows.remove(move);
    historyTable.getItems().setAll(rows);
  }
}
