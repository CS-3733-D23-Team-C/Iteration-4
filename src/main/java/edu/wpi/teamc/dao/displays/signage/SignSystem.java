package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.HospitalSystem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                sign.setLocationname(signEntry.getLocationname());
                sign.addSignEntry(signEntry);
                signs.put(signEntry.getMacadd(), sign);
            }
        }
    }

    public void addSignEntries(List<SignEntry> se) {
        for (SignEntry signEntry : se) {
            HospitalSystem.addRow(signEntry);
            if (signs.containsKey(signEntry.getMacadd())) {
                signs.get(signEntry.getMacadd()).addSignEntry(signEntry);
            } else {
                Sign sign = new Sign();
                sign.addSignEntry(signEntry);
                signs.put(signEntry.getMacadd(), sign);
            }
        }
    }

}
