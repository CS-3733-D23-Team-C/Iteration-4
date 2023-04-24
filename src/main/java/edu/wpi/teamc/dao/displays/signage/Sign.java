package edu.wpi.teamc.dao.displays.signage;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
@Getter @Setter
public class Sign {

    String macadd;
    String locationname;
    private HashMap<Date, SignVersion> signVersions;

    public Sign(HashMap<Date, SignVersion> signVersions) {
        this.signVersions = signVersions;
    }
    public Sign() {
        signVersions = new HashMap<>();
    }
    void addSignEntry(SignEntry signEntry) {
        if (signVersions.containsKey(signEntry.getDate())) {
            signVersions.get(signEntry.getDate()).addSignEntry(signEntry);
        } else {
            SignVersion signVersion = new SignVersion();
            signVersion.setDate(signEntry.getDate());
            signVersion.addSignEntry(signEntry);
            signVersions.put(signEntry.getDate(), signVersion);
        }
    }
    public void removeSignEntryVersion(SignEntry signEntry) {
        signVersions.remove(signEntry.getDate());
    }


}
