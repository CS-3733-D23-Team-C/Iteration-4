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

public class MealHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<MealRequest> historyTable;
  @FXML TableColumn<MealRequest, Integer> ColumnOne;
  @FXML TableColumn<MealRequest, IUser> ColumnTwo;
  @FXML TableColumn<MealRequest, STATUS> ColumnThree;
  @FXML TableColumn<MealRequest, String> ColumnFour;
  @FXML TableColumn<MealRequest, Meal> ColumnFive;
  @FXML TableColumn<MealRequest, String> ColumnSix;
  @FXML TableColumn<MealRequest, String> ColumnSeven;

  ObservableList<MealRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<MealRequest, Integer>("requestID"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<MealRequest, IUser>("requester"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<MealRequest, STATUS>("status"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<MealRequest, String>("additionalNotes"));
    ColumnFive.setCellValueFactory(new PropertyValueFactory<MealRequest, Meal>("meal"));
    ColumnSix.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("eta"));
    ColumnSeven.setCellValueFactory(new PropertyValueFactory<MealRequest, String>("assignedto"));
    ColumnOne.setText("requestID");
    ColumnTwo.setText("Requester");
    ColumnThree.setText("Status");
    ColumnFour.setText("Additional Notes");
    ColumnFive.setText("Meal");
    ColumnSix.setText("ETA");
    ColumnSeven.setText("Assigned To");
    //    ColumnOne.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnTwo.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnThree.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFour.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnFive.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    //    ColumnSix.setCellFactory(TextFieldTableCell.<MealRequest>forTableColumn());
    // get conference room table

    MealRequestDAO dao = new MealRequestDAO();
    List<MealRequest> mealRequests = dao.fetchAllObjects();
    for (MealRequest mealRequest : mealRequests) {
      rows.add(mealRequest);
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
}
