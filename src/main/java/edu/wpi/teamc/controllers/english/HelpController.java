package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

public class HelpController {

  @FXML private HBox flowerHelp;
  @FXML private HBox furnitureHelp;
  @FXML private HBox mealHelp;
  @FXML private HBox stationaryHelp;

  @FXML private ImageView flowerImage;
  @FXML private HBox roomReservationHelp;
  @FXML private HBox directionsHelp;

  @FXML private HBox editMapHelp;

  @FXML private HBox historyHelp;

  @FXML private HBox exitHelp;

  @FXML private HBox logoutHelp;

  @FXML private MFXButton flowerHelpButton;

  @FXML private MFXButton furnitureHelpButton;

  @FXML private MFXButton mealHelpButton;

  @FXML private MFXButton stationaryHelpButton;

  @FXML private MFXButton roomReservationHelpButton;

  @FXML private MFXButton directionsHelpButton;

  @FXML private MFXButton editMapHelpButton;

  @FXML private MFXButton historyHelpButton;

  @FXML private MFXButton exitHelpButton;

  @FXML private MFXButton logoutHelpButton;

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {

    AtomicBoolean isHovering = new AtomicBoolean(false);

    flowerHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                flowerHelp.toFront();

              } else {
                flowerHelp.toBack();
              }
            });

    furnitureHelp.toBack();
    mealHelp.toBack();
    stationaryHelp.toBack();
    roomReservationHelp.toBack();
    directionsHelp.toBack();
    editMapHelp.toBack();
    historyHelp.toBack();
    exitHelp.toBack();
    logoutHelp.toBack();
    flowerHelp.toBack();
    mealHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                mealHelp.toFront();
              } else {
                mealHelp.toBack();
              }
            });
    furnitureHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                furnitureHelp.toFront();
              } else {
                furnitureHelp.toBack();
              }
            });
    stationaryHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                stationaryHelp.toFront();
              } else {
                stationaryHelp.toBack();
              }
            });
    roomReservationHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                roomReservationHelp.toFront();
              } else {
                roomReservationHelp.toBack();
              }
            });
    directionsHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                directionsHelp.toFront();
              } else {
                directionsHelp.toBack();
              }
            });
    editMapHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                editMapHelp.toFront();
              } else {
                editMapHelp.toBack();
              }
            });
    historyHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                historyHelp.toFront();
              } else {
                historyHelp.toBack();
              }
            });
    exitHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                exitHelp.toFront();
              } else {
                exitHelp.toBack();
              }
            });
    logoutHelpButton
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue || isHovering.get()) {
                logoutHelp.toFront();
              } else {
                logoutHelp.toBack();
              }
            });
  }

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
