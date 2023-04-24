package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineResetterHelper {

  Line line = new Line();

  public LineResetterHelper() {
    this.line = new Line();
  }

  public LineResetterHelper(Line line) {
    this.line = line;
  }
}
