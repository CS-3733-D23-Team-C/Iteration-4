package edu.wpi.teamc.controllers.pages.displays;

import edu.wpi.teamc.dao.displays.signage.*;
import javafx.fxml.FXML;
import javafx.scene.shape.SVGPath;
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

  @FXML private SVGPath icon1;
  @FXML private SVGPath icon2;
  @FXML private SVGPath icon3;
  @FXML private SVGPath icon4;
  @FXML private SVGPath icon5;
  @FXML private SVGPath icon6;

  public void initialize() {
    location1.setText("");
    location2.setText("");
    location3.setText("");
    location4.setText("");
    location5.setText("");
    location6.setText("");
    icon1.setContent("");
    icon2.setContent("");
    icon3.setContent("");
    icon4.setContent("");
    icon5.setContent("");
    icon6.setContent("");
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

    try {
      icon1.setContent(returnSVG(currentSignVersion.getSignEntries().get(0).getDirection()));
      icon2.setContent(returnSVG(currentSignVersion.getSignEntries().get(1).getDirection()));
      icon3.setContent(returnSVG(currentSignVersion.getSignEntries().get(2).getDirection()));
      icon4.setContent(returnSVG(currentSignVersion.getSignEntries().get(3).getDirection()));
      icon5.setContent(returnSVG(currentSignVersion.getSignEntries().get(4).getDirection()));
      icon6.setContent(returnSVG(currentSignVersion.getSignEntries().get(5).getDirection()));
    } catch (Exception e) {

    }
  }

  public String returnSVG(DIRECTION d) {
    if (d.equals(DIRECTION.UP)) {
      return "M23.89 17.75 12.49 1.09 1.09 17.75 7.83 17.75 7.83 38.23 17.15 38.23 17.15 17.75 23.89 17.75";
    } else if (d.equals(DIRECTION.DOWN)) {
      return "M1.09 21.56 12.49 38.23 23.89 21.56 17.15 21.56 17.15 1.09 7.83 1.09 7.83 21.56 1.09 21.56";
    } else if (d.equals(DIRECTION.RIGHT)) {
      return "M38.23 12.49 21.56 23.89 21.56 17.15 10.37 17.15 10.37 37.51 1.09 37.51 1.09 17.15 1.09 7.89 1.09 7.83 21.56 7.83 21.56 1.09 38.23 12.49";
    } else if (d.equals(DIRECTION.LEFT)) {
      return "M17.75 7.83 17.75 1.09 1.09 12.49 17.75 23.89 17.75 17.15 28.94 17.15 28.94 37.51 38.23 37.51 38.23 17.15 38.23 7.89 38.23 7.83 17.75 7.83";
    } else if (d.equals(DIRECTION.HERE)) {
      return "M26.64,13.86A12.78,12.78,0,1,0,3,20.52H3L13.86,38.74l10.9-18.22h0A12.68,12.68,0,0,0,26.64,13.86ZM13.86,20.51a6.67,6.67,0,1,1,6.67-6.67A6.67,6.67,0,0,1,13.86,20.51Z";
    } else {
      return "";
    }
  }
}
