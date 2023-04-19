package edu.wpi.teamc.dao.users;

import static edu.wpi.teamc.dao.users.PERMISSIONS.ADMIN;

import edu.wpi.teamc.dao.users.login.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest {
  @Test
  public void testPassword() {
    Login testAccount = new Login("Wong", "password", ADMIN);
    Assertions.assertEquals(true, testAccount.checkPassword("password"));
  }

  @Test
  public void testPassword2() {
    Login testAccount = new Login("Wong", "password1", ADMIN);
    Assertions.assertEquals(true, testAccount.checkPassword("password1"));
  }

  @Test
  public void testPassword3() {
    Login testAccount = new Login("Wong", "SoftEng", ADMIN);
    Assertions.assertEquals(false, testAccount.checkPassword("pass123"));
  }
}
