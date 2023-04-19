package edu.wpi.teamc.controllers.english.CredentialsHelpers;

import edu.wpi.teamc.dao.users.Login;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class permissionsHelper {
  Login login;

  public permissionsHelper(Login login) {
    this.login = login;
  }

  public permissionsHelper() {}
}
