package edu.wpi.teamc.dao.displays.signage;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;

@Getter
@Setter
public class SignEntry {
  String macadd;
  String devicename;
  Date date;
  String locationname;
  DIRECTION direction;


  public SignEntry(String macadd, String devicename, Date date, String locaiton, DIRECTION direction) {
   this.macadd = macadd;
   this.devicename = devicename;
    this.date = date;
    this.location = locaiton;
    this.direction = direction;
  }

  public SignEntry() {}

  public static String getCurrentDeviceMacAddress() {
    InetAddress localHost = null;
    String macAddress = null;
    try {
      localHost = InetAddress.getLocalHost();
      NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
      byte[] hardwareAddress = ni.getHardwareAddress();
      String[] hexadecimal = new String[hardwareAddress.length];
      for (int i = 0; i < hardwareAddress.length; i++) {
        hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
      }
       macAddress = String.join("-", hexadecimal);
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    return macAddress;

  }
}
