package edu.wpi.teamc.dao.users.login;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Login implements IOrm {
  @Getter private String username;
  String salt;
  String hashedPassword;
  String otp;
  @Getter private PERMISSIONS permissions;

  public Login() {}

  public Login(String username, String password, PERMISSIONS permissions) {
    this.username = username.toLowerCase();
    this.permissions = permissions;
    this.salt = saltPassword();
    this.hashedPassword = hashPassword(password + this.salt);
    this.otp = null;
  }

  // only database should use this constructor
  Login(String username, String password, PERMISSIONS permissions, String salt, String otp) {
    this.username = username.toLowerCase();
    this.permissions = permissions;
    this.salt = salt;
    this.hashedPassword = password;
    this.otp = otp;
  }

  public String saltPassword() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);

    // Convert the salt to a hexadecimal string
    StringBuilder saltString = new StringBuilder();
    for (byte b : salt) {
      saltString.append(String.format("%02X", b));
    }
    return saltString.toString();
  }

  private String hashPassword(String password) {
    String hashedPassword = null;
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

      // Convert the password with salt to bytes and hash with SHA-256
      byte[] hash = messageDigest.digest(password.getBytes());

      // Convert the hash to a hexadecimal string
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        hexString.append(String.format("%02X", b));
      }
      hashedPassword = hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashedPassword;
  }

  public boolean checkPassword(String password) {
    // check hashed password against database
    String givenSaltedPassword = password + this.salt;
    String givenHashedPassword = hashPassword(givenSaltedPassword);
    if (givenHashedPassword.equals(this.hashedPassword)) {
      return true;
    }
    return false;
  }

  public String generateOTP() {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    GoogleAuthenticatorKey secret = gAuth.createCredentials();
    this.otp = secret.getKey();
    HospitalSystem.updateRow(this);
    return otp;
  }

  public String removeOTP() {
    this.otp = null;
    HospitalSystem.updateRow(this);
    return null;
  }

  public boolean isOTPEnabled() {
    if (this.otp == null) {
      return false;
    }
    return true;
  }

  public boolean checkOTP(String otp) {
    boolean isCodeValid = false;
    try {
      GoogleAuthenticator gAuth = new GoogleAuthenticator();
      isCodeValid = gAuth.authorize(this.otp, Integer.parseInt(otp));
    } catch (NumberFormatException e) {
      isCodeValid = false;
    }
    return isCodeValid;
  }
}
