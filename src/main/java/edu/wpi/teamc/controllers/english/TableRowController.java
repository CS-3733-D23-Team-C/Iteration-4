package edu.wpi.teamc.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRow {
    private String s1, s2, s3, s4, s5, s6, s7, s8, s9;
    int i;

    public TableRow(String s1, String s2, String s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    //  public TableRow(String nodeID, String longName, String date, int index) {
    //    this.NodeID = nodeID;
    //    this.LongName = longName;
    //    this.Date = date;
    //    this.index = index;
    //  }

    public TableRow(String s1, String s2, String s3, String s4, String s5, String s6) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
    }
    //  public TableRow(String nodeID, String longName, String date, int index) {
    //    this.NodeID = nodeID;
    //    this.LongName = longName;
    //    this.Date = date;
    //    this.index = index;
    //  }

    public TableRow(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
    }

    public TableRow(
            String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
        this.s8 = s8;
    }

    public TableRow(
            String s1, String s2, String s3, String s4, String s5, String s6, String s7, int i) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
        this.i = i;
    }

    public TableRow(
            String s1,
            String s2,
            String s3,
            String s4,
            String s5,
            String s6,
            String s7,
            String s8,
            String s9) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
        this.s8 = s8;
        this.s9 = s9;
    }

    public TableRow(String s1, String s2, int i) {
        this.s1 = s1;
        this.s2 = s2;
        this.i = i;
    }
}
