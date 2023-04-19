package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import io.github.palexdev.materialfx.controls.MFXButton;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorResetterHelper {

  MFXButton button = new MFXButton();

  public FloorResetterHelper() {
    this.button = new MFXButton();
  }

  public FloorResetterHelper(MFXButton button) {
    this.button = button;
  }
}
