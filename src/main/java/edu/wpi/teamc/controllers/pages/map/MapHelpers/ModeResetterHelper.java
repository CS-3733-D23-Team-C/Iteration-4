package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import io.github.palexdev.materialfx.controls.MFXButton;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeResetterHelper {

  MFXButton button = new MFXButton();

  public ModeResetterHelper() {
    this.button = new MFXButton();
  }

  public ModeResetterHelper(MFXButton button) {
    this.button = button;
  }
}
