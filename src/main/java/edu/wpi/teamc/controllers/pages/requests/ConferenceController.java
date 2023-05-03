package edu.wpi.teamc.controllers.pages.requests;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

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
import java.util.ArrayList;
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
import javafx.scene.text.Text;
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
  public void initialize() throws Exception {
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
    setLanguage();
  }

  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {
    if (language_choice == 0) {
      holder = CApp.Conference_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Conference_Chinese_list;
    }

    Title.setText(holder.get(0));
    Box1.setText(holder.get(1));
    Box2.setText(holder.get(2));
    Box3.setText(holder.get(3));
    Box4.setText(holder.get(4));
    Box5.setText(holder.get(5));
    nameBox.setPromptText(holder.get(6));
    roomMenu.setPromptText(holder.get(7));
    startTime.setPromptText(holder.get(8));
    endTime.setPromptText(holder.get(9));
    specialRequest.setPromptText(holder.get(10));
    employeeName.setPromptText(holder.get(11));
    Submit.setText(holder.get(12));
    Clear.setText(holder.get(13));
    Cancel.setText(holder.get(14));

    //        notEnglish = true;
  }

  @FXML private Text Title;
  @FXML private TextField Box1;
  @FXML private TextField Box2;
  @FXML private TextField Box3;
  @FXML private TextField Box4;
  @FXML private TextField Box5;
  @FXML private MFXButton Submit;
  @FXML private MFXButton Clear;
  @FXML private MFXButton Cancel;
}
