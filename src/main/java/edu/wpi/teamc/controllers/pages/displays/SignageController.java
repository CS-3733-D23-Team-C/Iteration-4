package edu.wpi.teamc.controllers.pages.displays;

import java.net.InetAddress;
import java.net.NetworkInterface;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SignageController {

  @FXML Text first;

  public void initialize() {
    first.setText(getMacAddress());
  }

  public String getMacAddress() {
    String macAddress = "";
    try {
      InetAddress localHost = InetAddress.getLocalHost();
      NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
      byte[] hardwareAddress = ni.getHardwareAddress();
      String[] hexadecimal = new String[hardwareAddress.length];
      for (int i = 0; i < hardwareAddress.length; i++) {
        hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
      }
      macAddress = String.join("-", hexadecimal);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return macAddress;
  }
}
