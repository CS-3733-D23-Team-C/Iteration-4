package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;


public abstract class AbsUser implements IOrm {
    @Getter
    private int id;
  @Setter   @Getter
    private  String name;

    public AbsUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public AbsUser(String name) {
        this.name = name;
    }
}
