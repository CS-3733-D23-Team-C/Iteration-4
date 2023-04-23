package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class Kiosk implements IOrm {

  String id;
  String kioskname;
  int signid;

  public Kiosk(String id, String kioskname, int signid) {
    this.id = id;
    this.signid = signid;
  }

  public Kiosk() {}

  public static String getCurrentDeviceMacAddress() {
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
