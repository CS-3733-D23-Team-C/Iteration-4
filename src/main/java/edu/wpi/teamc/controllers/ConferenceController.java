package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ConferenceController {

    @FXML
    private MFXButton serviceRequest;


    @FXML
    public void getService(ActionEvent event) {
    }

    @FXML
    public void getLogout(ActionEvent actionEvent) {
        Navigation.navigate(Screen.HOME);
    }

    @FXML
    public void getExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void initialize() {
    }


}