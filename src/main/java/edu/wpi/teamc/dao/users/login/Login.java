package edu.wpi.teamc.dao.users.login;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.users.PERMISSIONS;
import lombok.Getter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
@Getter
public class Login implements IOrm {
  private String username;
  private String salt;
  private String hashedPassword;
  private String otp;
  private PERMISSIONS permissions;


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

  private String generateOTP() {
    String secret = OTP.randomBase32(32);
    return secret;
  }

  private String removeOTP() {
    return null;
  }

  private boolean isOTPEnabled() {
    if (this.otp == null) {
      return false;
    }
    return true;
  }

  private boolean checkOTP(String otp) {
    try {
        String hexTime = OTP.timeInHex(System.currentTimeMillis(), 30);
        String code = OTP.create(otp, hexTime, 6, Type.TOTP);
        if (code.equals(otp)) {
          return true;
        }else {
            return false;
        }
        } catch (Exception e) {
        return false;
    }
  }
}
