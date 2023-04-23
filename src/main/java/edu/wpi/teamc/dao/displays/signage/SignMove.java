package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.IOrm;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SignMove implements IOrm {

  int id;
  Date date;
  List<SignDirection> directions;

  public SignMove(int id, Date date, List<SignDirection> directions) {
    this.id = id;
    this.date = date;
    this.directions = directions;
  }

  public SignMove(int id, Date date, String directionstr) {
    this.date = date;
    this.id = id;
    directionstr = directionstr.replace("[", "");
    directionstr = directionstr.replace("]", "");
    String[] directions = directionstr.split(",");
    ArrayList<SignDirection> signDirections = new ArrayList<>();
    for (String direction : directions) {
      signDirections.add(SignDirection.valueOf(direction));
    }
    this.directions = signDirections;
  }

  public SignMove() {}
}
