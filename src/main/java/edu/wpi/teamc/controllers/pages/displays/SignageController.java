package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.dao.displays.Kiosk;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SignageController {

  @FXML Text first;

  public void initialize() {
    first.setText(Kiosk.getCurrentDeviceMacAddress());
  }
}
