package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.SMSHelper;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.dao.displays.AlertDao;
import edu.wpi.teamc.dao.users.PatientUserDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AlertController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;
  @FXML AnchorPane assignEmployeeAnchor;

  @FXML private MenuItem choice1;
  @FXML private MenuItem choice2;

  @FXML private MenuButton alertType;
  @FXML private TextField alertTitle;
  @FXML private TextArea alertDescription;
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {
    LocalDateTime start = LocalDateTime.from((startTime.getValue()));
    LocalDateTime end = LocalDateTime.from((endTime.getValue()));
    String title = alertTitle.getText();
    String description = alertDescription.getText();
    String type = alertType.getText();
    Alert alert =
        new Alert(title, description, type, Timestamp.valueOf(start), Timestamp.valueOf(end));

    IDao<Alert, Integer> dao = new AlertDao();
    PatientUserDao pdao = new PatientUserDao();
    dao.addRow(alert);

    // fetch all the objects in patient database and if the activeText column is true
    List<String> phoneNums = pdao.listActivePhone();
    for (int i = 0; i < phoneNums.size(); i++) {
      SMSHelper.sendSMS(phoneNums.get(i), description);
    }

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void setChoice1(ActionEvent event) {
    alertType.setText(choice1.getText());
  }

  @FXML
  void setChoice2(ActionEvent event) {
    alertType.setText(choice2.getText());
  }

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
  }
}
