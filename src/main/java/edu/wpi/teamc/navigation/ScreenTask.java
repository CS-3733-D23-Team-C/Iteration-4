package edu.wpi.teamc.navigation;

import java.util.TimerTask;

public class ScreenTask extends TimerTask {
  @Override
  public void run() {
    System.out.println("ScreenTask");
    Navigation.navigate(Screen.SCREENSAVER);
  }
}
