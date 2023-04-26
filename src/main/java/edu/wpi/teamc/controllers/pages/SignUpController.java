package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PatientUser;
import edu.wpi.teamc.dao.users.PatientUserDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {

  @FXML private Button backButton;

  @FXML private TextField name;

  @FXML private TextField phoneNumber;

  @FXML private Button submitButton;

  @FXML
  void getBack(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {
    // modify an existing patient
    IDao<PatientUser, Integer> dao = new PatientUserDao();
    PatientUserDao pdao = new PatientUserDao();
    PatientUser patient1 =
        pdao.fetchPatient(
            name.getText(), phoneNumber.getText()); // why did I need to make this static????
    PatientUser patient2 =
        new PatientUser(
            patient1.getId(),
            patient1.getName(),
            patient1.getIn(),
            patient1.getOut(),
            patient1.getPhone(),
            patient1.getRoom(),
            true);
    dao.updateRow(patient1, patient2);

    Navigation.navigate(Screen.CONGRATS_PAGE); // maybe make this different
  }
}
