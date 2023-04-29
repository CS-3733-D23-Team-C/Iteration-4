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
  @Getter String email;
  @Getter String salt;
  @Getter String hashedPassword;
  private String otp;
  boolean OTPEnabled;
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
  public Login(String username, String password, PERMISSIONS permissions, String salt, String otp) {
    this.username = username.toLowerCase();
    this.permissions = permissions;
    this.salt = salt;
    this.hashedPassword = password;
    if (otp == null || otp.equalsIgnoreCase("null")) {
      this.otp = null;
      OTPEnabled = false;
    } else {
      this.otp = otp;
      OTPEnabled = true;
    }
  }

  public Login(
      String username,
      String email,
      String password,
      PERMISSIONS permissions,
      String salt,
      String otp) {
    this.username = username.toLowerCase();
    this.email = email;
    this.permissions = permissions;
    this.salt = salt;
    this.hashedPassword = password;
    if (otp == null || otp.equalsIgnoreCase("null")) {
      this.otp = null;
      OTPEnabled = false;
    } else {
      this.otp = otp;
      OTPEnabled = true;
    }
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
    OTPEnabled = true;
    HospitalSystem.updateRow(this);
    return otp;
  }

  public String removeOTP() {
    this.otp = null;
    OTPEnabled = false;
    HospitalSystem.updateRow(this);
    return null;
  }

  public boolean isOTPEnabled() {
    if (this.otp == null || this.otp.equalsIgnoreCase("null")) {
      this.OTPEnabled = false;
      return this.OTPEnabled;
    } else {
      this.OTPEnabled = true;
      return this.OTPEnabled;
    }
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

  String getOtp() {
    return otp;
  }
}
