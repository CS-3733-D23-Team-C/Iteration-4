package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.requests.ConferenceRoom;
import edu.wpi.teamc.dao.requests.ConferenceRoomRequest;
import edu.wpi.teamc.dao.requests.ConferenceRoomRequestDAO;
import edu.wpi.teamc.dao.requests.STATUS;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

public class ConferenceController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

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

  //  @FXML private ImageView image;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
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
                Platform.runLater(
                    new Runnable() {
                      @Override
                      public void run() {
                        Navigation.navigate(Screen.CONGRATS_PAGE);
                      }
                    });
              }
            });
    thread.start();
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    Thread thread =
        new Thread(
            () -> {
              if (!CApp.getAdminLoginCheck()) {
                assignEmployeeAnchor.setMouseTransparent(true);
                assignEmployeeAnchor.setOpacity(0);
              }
              List<LocationName> locationNames =
                  (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
              locationNames.removeIf(
                  locationName ->
                      !locationName.getNodeType().equals("CONF")); // remove non-conference
              roomMenu.setItems(FXCollections.observableArrayList(locationNames));

              List<EmployeeUser> employeeUsers =
                  (List<EmployeeUser>) HospitalSystem.fetchAllObjects(new EmployeeUser());
              employeeName.setItems(FXCollections.observableArrayList(employeeUsers));
            });
    thread.start();
  }
}
