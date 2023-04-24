package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.dao.displays.signage.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class SignageController {

  @FXML private Text AdminHome_Title;

  @FXML private Text location1;

  @FXML private Text location2;

  @FXML private Text location3;

  @FXML private Text location4;

  @FXML private Text location5;

  @FXML private Text location6;

  public void initialize() {
    location1.setText("");
    location2.setText("");
    location3.setText("");
    location4.setText("");
    location5.setText("");
    location6.setText("");
    SignSystem signSystem = new SignSystem();
    SignVersion currentSignVersion = new SignVersion();
    currentSignVersion.setDate(new Date(0));
    currentSignVersion.addSignEntry(
        new SignEntry(
            "", "", new Date(0), "This registered device has no current signs.", DIRECTION.HERE));
    Sign sign = signSystem.getSigns().get(SignEntry.getCurrentDeviceMacAddress());
    try {
      Collection signVersionsCollection = sign.getSignVersions().values();
      ArrayList<SignVersion> signVersions = new ArrayList<>(signVersionsCollection);

      Date currdate = new Date(System.currentTimeMillis());
      for (SignVersion signVersion : signVersions) {
        if (signVersion.getDate().before(currdate)
            && signVersion.getDate().after(currentSignVersion.getDate())) {
          currentSignVersion = signVersion;
        }
      }

    } catch (NullPointerException e) {
      System.out.println("No signs to display");
      location1.setText("This device is not registered.");
      location2.setText("Please contact your admin to register this");
      location3.setText("device through the admin panel.");
      return;
    }
    try {
      location1.setText(currentSignVersion.getSignEntries().get(0).getLocationname());
      location2.setText(currentSignVersion.getSignEntries().get(1).getLocationname());
      location3.setText(currentSignVersion.getSignEntries().get(2).getLocationname());
      location4.setText(currentSignVersion.getSignEntries().get(3).getLocationname());
      location5.setText(currentSignVersion.getSignEntries().get(4).getLocationname());
      location6.setText(currentSignVersion.getSignEntries().get(5).getLocationname());
    } catch (Exception e) {
    }
  }
}
