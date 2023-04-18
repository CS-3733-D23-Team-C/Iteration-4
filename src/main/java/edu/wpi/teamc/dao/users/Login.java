package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.Getter;

@Getter
public class Login implements IOrm {
  private String username;
  private String permissions;
  private String salt;
  private String hashedPassword;

  public Login(String username, String password, String permissions) {
    this.username = username;
    this.permissions = permissions;
    this.salt = saltPassword();
    this.hashedPassword = hashPassword(password + this.salt);
  }

  // only database should use this constructor
  protected Login(String username, String password, String permissions, String salt) {
    this.username = username;
    this.permissions = permissions;
    this.salt = salt;
    this.hashedPassword = password;
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
}
