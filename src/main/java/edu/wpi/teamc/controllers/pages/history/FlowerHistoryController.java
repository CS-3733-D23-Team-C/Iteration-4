package edu.wpi.teamc.controllers.pages.history;

import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.IUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.tableview2.FilteredTableView;

public class FlowerHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<FlowerDeliveryRequest> historyTable;
  @FXML TableColumn<FlowerDeliveryRequest, Integer> ColumnOne;
  @FXML TableColumn<FlowerDeliveryRequest, IUser> ColumnTwo;
  @FXML TableColumn<FlowerDeliveryRequest, String> ColumnThree;
  @FXML TableColumn<FlowerDeliveryRequest, String> ColumnFour;
  @FXML TableColumn<FlowerDeliveryRequest, String> ColumnFive;
  @FXML TableColumn<FlowerDeliveryRequest, STATUS> ColumnSix;
  @FXML TableColumn<FlowerDeliveryRequest, String> ColumnSeven;
  @FXML TableColumn<FlowerDeliveryRequest, String> ColumnEight;
  ObservableList<FlowerDeliveryRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, Integer>("requestID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, IUser>("requester"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("roomName"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("flower"));
    ColumnFive.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("additionalNotes"));
    ColumnSix.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, STATUS>("status"));
    ColumnSeven.setCellValueFactory(new PropertyValueFactory<FlowerDeliveryRequest, String>("eta"));
    ColumnEight.setCellValueFactory(
        new PropertyValueFactory<FlowerDeliveryRequest, String>("assignedto"));
    ColumnOne.setText("requestID");
    ColumnTwo.setText("Requester");
    ColumnThree.setText("Room Name");
    ColumnFour.setText("Flower");
    ColumnFive.setText("Additional Notes");
    ColumnSix.setText("Status");
    ColumnSeven.setText("ETA");
    ColumnEight.setText("Assigned To");
    //    ColumnOne.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnTwo.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnThree.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFour.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFive.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnSix.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    // get conference room table
    FlowerDeliveryRequestDAO dao = new FlowerDeliveryRequestDAO();
    List<FlowerDeliveryRequest> list = dao.fetchAllObjects();
    for (FlowerDeliveryRequest r : list) {
      rows.add(r);
    }
    historyTable.setItems(rows);
    System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    inputBox.clear();
    return inputtedText;
  }

  public void getMapPage(ActionEvent event) {}
}
