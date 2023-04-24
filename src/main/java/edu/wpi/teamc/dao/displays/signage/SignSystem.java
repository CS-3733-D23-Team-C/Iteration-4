package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.HospitalSystem;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Getter;

public class SignSystem {
  @Getter private HashMap<String, Sign> signs;

  public SignSystem(HashMap<String, Sign> signs) {
    this.signs = signs;
  }

  public SignSystem() {
    signs = new HashMap<>();
    ArrayList<SignEntry> s = (ArrayList<SignEntry>) HospitalSystem.fetchAllObjects(new SignEntry());
    for (SignEntry signEntry : s) {
      if (signs.containsKey(signEntry.getMacadd())) {
        signs.get(signEntry.getMacadd()).addSignEntry(signEntry);
      } else {
        Sign sign = new Sign();
        sign.setMacadd(signEntry.getMacadd());
        sign.setDevicename(signEntry.getDevicename());
        sign.addSignEntry(signEntry);
        signs.put(signEntry.getMacadd(), sign);
      }
    }
  }

  public void removeSignVersion(SignVersion selected) {
    Date date = selected.getDate();
    SignEntryDao signEntryDao = new SignEntryDao();
    signEntryDao.deleteVersion(selected.getSignEntries().get(0).macadd, date);
    signs
        .get(selected.getSignEntries().get(0).macadd)
        .removeSignEntryVersion(selected.getSignEntries().get(0));
  }

  public void addSignVersion(SignVersion newVersion) {
    SignEntryDao signEntryDao = new SignEntryDao();
    for (SignEntry signEntry : newVersion.getSignEntries()) {
      signEntryDao.addRow(signEntry);
    }
  }
}
