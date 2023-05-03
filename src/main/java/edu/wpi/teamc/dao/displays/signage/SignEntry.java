package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Date;
import java.util.Enumeration;

@Getter
@Setter
public class SignEntry implements IOrm {
  String macadd;
  String devicename;
  Date date;
  String locationname;
  DIRECTION direction;

  public SignEntry(
      String macadd, String devicename, Date date, String locationname, DIRECTION direction) {
    this.macadd = macadd;
    this.devicename = devicename;
    this.date = date;
    this.locationname = locationname;
    this.direction = direction;
  }

  public SignEntry() {}

  public static String getCurrentDeviceMacAddress() {
    String mac = "";
    Enumeration<NetworkInterface> networkInterfaces = null;
    try {
      networkInterfaces = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface ni = networkInterfaces.nextElement();
      byte[] hardwareAddress = new byte[0];
      try {
        hardwareAddress = ni.getHardwareAddress();
      } catch (SocketException e) {
        throw new RuntimeException(e);
      }
      if (hardwareAddress != null) {
        String[] hexadecimalFormat = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) {
          hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
        }
        mac = String.join(":", hexadecimalFormat);
      }
    }
    return mac;
  }
}
