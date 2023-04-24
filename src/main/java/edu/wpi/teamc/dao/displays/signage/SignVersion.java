package edu.wpi.teamc.dao.displays.signage;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Getter @Setter
public class SignVersion {
   @Getter @Setter Date date;
@Getter List<SignEntry> signEntries;

public SignVersion(List<SignEntry> signEntries) {
    this.signEntries = signEntries;
}
public SignVersion() {
    signEntries = new ArrayList<>();
}
    void addSignEntry(SignEntry signEntry) {
        signEntries.add(signEntry);
    }

    void removeSignEntry(SignEntry signEntry) {
        signEntries.remove(signEntry);
    }

}
