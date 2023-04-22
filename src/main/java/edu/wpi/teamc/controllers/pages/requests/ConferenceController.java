package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

public class ConferenceController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  //  @FXML public Image image = new Image("edu/wpi/teamc/resources/images/ConferenceRoom.png");

  @FXML private MFXButton clear;

  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;
  @FXML private MenuItem choice5;

  @FXML private MenuItem choice6;

  @FXML private MenuItem choice7;

  @FXML private MenuItem choice8;

  @FXML private SearchableComboBox roomMenu;
  @FXML private TextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;

  @FXML private SearchableComboBox employeeName;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  //  @FXML
  //  void getChoice0() {
  //    roomMenu.setText("--Please Conference Room--");
  //  }

  // These 4 choices(1-4) are for the conference room
  //  @FXML
  //  void getChoice1() {
  //    roomMenu.setText("Conference A1");
  //  }
  //
  //  @FXML
  //  void getChoice2() {
  //    roomMenu.setText("Conference A2");
  //  }
  //
  //  @FXML
  //  void getChoice3() {
  //    roomMenu.setText("Conference A3");
  //  }
  //
  //  @FXML
  //  void getChoice4() {
  //    roomMenu.setText("Conference A4");
  //  }

  // These 4 choices(5-8) are for the employee name
  //  @FXML
  //  void getChoice5() {
  //    employeeName.setText(choice5.getText());
  //  }
  //
  //  @FXML
  //  void getChoice6() {
  //    employeeName.setText(choice6.getText());
  //  }
  //
  //  @FXML
  //  void getChoice7() {
  //    employeeName.setText(choice7.getText());
  //  }
  //
  //  @FXML
  //  void getChoice8() {
  //    employeeName.setText(choice8.getText());
  //  }

  @FXML
  void getSubmit(ActionEvent event) {

    LocalDate start = startTime.getValue();
    LocalDate end = endTime.getValue();
    String name = nameBox.getText();

    //    String room = roomMenu.getText();
    String notes = specialRequest.getText();
    STATUS status = STATUS.COMPLETE;
    ConferenceRoomRequest req =
        new ConferenceRoomRequest(
            new PatientUser(name),
            new ConferenceRoom(
                roomMenu.getValue().toString(), roomMenu.getValue().toString(), false),
            notes,
            start.toString(),
            end.toString(),
            status);
    if (!(employeeName == null)) {
      try {
        req.setAssignedto(employeeName.getValue().toString());
      } catch (Exception e) {
        System.out.println("No employee selected");
        req.setAssignedto(null);
      }
    }

    IDao<ConferenceRoomRequest, Integer> dao = new ConferenceRoomRequestDAO();
    dao.addRow(req);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  //  @FXML
  //  void getImage(ActionEvent event) throws IOException {
  //    image = new
  // Image(Main.class.getResource("views/Images/ConferenceRoom/conference_room_1.png").openStream());
  //  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    if (!CApp.getAdminLoginCheck()) {
      assignEmployeeAnchor.setMouseTransparent(true);
      assignEmployeeAnchor.setOpacity(0);
    }
    List<LocationName> locationNames =
        (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
    roomMenu.setItems(FXCollections.observableArrayList(locationNames));

    List<EmployeeUser> employeeUsers =
        (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
    employeeName.setItems(FXCollections.observableArrayList(employeeUsers));
  }
}
