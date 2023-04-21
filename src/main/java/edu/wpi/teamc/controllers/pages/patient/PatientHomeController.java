package edu.wpi.teamc.controllers.pages.patient;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class PatientHomeController {

    private String filePath;
    private Desktop desktop = Desktop.getDesktop();
    // @FXML private AdminMenuController adminMenuController;

    @FXML
    private HTMLEditor patientWeather;
    @FXML private ImageView English_flag;
    @FXML private ImageView Spanish_flag;
    @FXML private Text PatientHome_Title;
    @FXML private javafx.scene.control.Button logoutButton;
    @FXML private Button exitButton;
    @FXML private MenuButton ServiceRequest;
    @FXML private MenuButton NavigationButton;
    @FXML private MenuButton Settings;
    @FXML private MenuButton Help;
}
