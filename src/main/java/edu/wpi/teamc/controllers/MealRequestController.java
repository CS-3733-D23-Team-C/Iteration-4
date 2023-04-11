package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MealRequestController {
    @FXML
    public void getLogout(ActionEvent actionEvent) {
        Navigation.navigate(Screen.HOME);
    }

    @FXML
    public void getExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
