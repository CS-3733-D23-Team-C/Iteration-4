package edu.wpi.teamc.dao.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest {
  @Test
  public void testPassword() {
    Login testAccount = new Login("Wong", "password", "Admin");
    Assertions.assertEquals(true, testAccount.checkPassword("password"));
  }

  @Test
  public void testPassword2() {
    Login testAccount = new Login("Wong", "password1", "Admin");
    Assertions.assertEquals(true, testAccount.checkPassword("password1"));
  }

  @Test
  public void testPassword3() {
    Login testAccount = new Login("Wong", "SoftEng", "Admin");
    Assertions.assertEquals(false, testAccount.checkPassword("pass123"));
  }
}
