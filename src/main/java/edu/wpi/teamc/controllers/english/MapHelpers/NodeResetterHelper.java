package edu.wpi.teamc.controllers.english.MapHelpers;

import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeResetterHelper {

  Circle circle = new Circle();

  public NodeResetterHelper() {
    this.circle = new Circle();
  }

  public NodeResetterHelper(Circle circle) {
    this.circle = circle;
  }
}
