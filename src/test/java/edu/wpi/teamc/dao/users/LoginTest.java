package edu.wpi.teamc.dao.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest {
  @Test
  public void testPassword() {
    Login testAccount = new Login("Wong", "password", "Admin");
    Assertions.assertEquals(true, testAccount.checkPassword("password"));
  }
}
