package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;

@Getter
public class Sign implements IOrm {
int id;
String name;
  public Sign(String name) {
   this.name = name;
  }

  public Sign() {}
}
