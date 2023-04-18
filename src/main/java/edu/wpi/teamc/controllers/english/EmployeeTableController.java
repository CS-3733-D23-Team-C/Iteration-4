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
  @FXML private FilteredTableView<EmployeeUser> historyTable;
  //    @FXML
  //    TableView<Move> otherTable;
  @FXML TableColumn<EmployeeUser, Integer> id;
  @FXML TableColumn<EmployeeUser, String> username;
  @FXML TableColumn<EmployeeUser, String> name;
  @FXML TableColumn<EmployeeUser, String> department;
  @FXML TableColumn<EmployeeUser, String> position;
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

    id.setText("ID");
    username.setText("Username");
    name.setText("Name");
    department.setText("Department");
    position.setText("Position");

    List<EmployeeUser> list =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    rows.addAll(list);
    historyTable.getItems().setAll(rows);

    //        List<EmployeeUser> employeeUsers = (List<EmployeeUser>)
    // HospitalSystem.fetchAllObjects(new EmployeeUser());
    //        employeeID.setItems(FXCollections.observableArrayList(employeeUsers));

  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public void getAdd(ActionEvent event) {
    //          Move move = new Move();
    //          move.setNodeID(nodeID.getValue().getNodeID());
    //          move.setLongName(locationName.getValue().getLongName());
    //          move.setDate(Date.valueOf(date.getValue()));
    //          HospitalSystem.addRow(move);
    //          rows.add(move);
    //          historyTable.getItems().setAll(rows);
  }

  public void getDelete(ActionEvent event) {
    //          Move move = historyTable.getSelectionModel().getSelectedItem();
    //          HospitalSystem.deleteRow(move);
    //          rows.remove(move);
    //          historyTable.getItems().setAll(rows);
  }
}
