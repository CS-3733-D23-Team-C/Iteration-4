package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ConferenceController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;
  @FXML private TextField nameBox;
  @FXML private TextField specialRequest;
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getChoice0() {
    menuButton.setText("--Please Conference Room--");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Conference A1");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Conference A2");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Conference A3");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Conference A4");
  }

  @FXML
  void getSubmit(ActionEvent event) {
    LocalDate start = startTime.getValue();
    LocalDate end = endTime.getValue();
    String name = nameBox.getText();
    String room = menuButton.getText();
    String notes = specialRequest.getText();
    STATUS status = STATUS.COMPLETE;
    ConferenceRoomRequest req =
        new ConferenceRoomRequest(
            0,
            new Requester(0, name),
            new ConferenceRoom(room, room, false),
            start.toString(),
            end.toString(),
            notes,
            status);

    IDao dao = new ConferenceRoomRequestDAO();
    dao.addRow(req);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear() {
    clear.setOnMouseClicked(event -> Navigation.navigate(Screen.CONFERENCE));
  }

  @FXML
  void getMenuButton() {}

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getConferenceHistory(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE_HISTORY);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}

  @FXML
  void getPathfindingPage(ActionEvent event) {}
}
