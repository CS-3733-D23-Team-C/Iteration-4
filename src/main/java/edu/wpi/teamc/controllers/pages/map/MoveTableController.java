package edu.wpi.teamc.controllers.pages.map;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.Move;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

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
  @FXML private TableView<Move> historyTable;
  @FXML TableView<Move> otherTable;
  @FXML TableColumn<Move, Integer> ColumnOne;
  @FXML TableColumn<Move, String> ColumnTwo;
  @FXML TableColumn<Move, Date> ColumnThree;
  @FXML SearchableComboBox<Node> nodeID;
  @FXML SearchableComboBox<LocationName> locationName;
  @FXML DatePicker date;

  ObservableList<Move> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<Move, Integer>("nodeID"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<Move, Date>("date"));
    ColumnOne.setText("Node ID");
    ColumnTwo.setText("Long Name");
    ColumnThree.setText("Date");

    //    //    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    //    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    List<Move> list = (List<Move>) HospitalSystem.fetchAllObjects(new Move());
    rows.addAll(list);
    historyTable.getItems().setAll(rows);

    List<Node> nodes = (List<Node>) HospitalSystem.fetchAllObjects(new Node());
    nodeID.setItems(FXCollections.observableArrayList(nodes));
    List<LocationName> locationNames =
        (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
    locationName.setItems(FXCollections.observableArrayList(locationNames));
    historyTable
        .getStylesheets()
        .add(Main.class.getResource("views/pages/requests/RequestHistory.css").toString());
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
    if (!(move == null)) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete Move");
      alert.setHeaderText(
          "Are you sure you want to delete this move: "
              + move.getNodeID()
              + " | "
              + move.getDate()
              + " ?");
      alert.setContentText("This action cannot be undone.");
      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        HospitalSystem.deleteRow(move);
        rows.remove(move);
        historyTable.getItems().setAll(rows);
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("No Move Selected");
      alert.setContentText("Please select a move row on the table to delete.");
      alert.showAndWait();
    }
  }
}
