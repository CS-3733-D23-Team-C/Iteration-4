package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
abstract class AbsUser implements IOrm, IUser {
  @Getter private int id;
  @Setter @Getter private String name;
  @Setter @Getter private String username;

  AbsUser(int id, String name, String username) {
    this.id = id;
    this.name = name;
    this.username = username;
  }

  AbsUser(String name, String username) {
    this.name = name;
    this.username = username;
  }

  AbsUser(String name) {
    this.name = name;
  }

  public AbsUser() {}
}
