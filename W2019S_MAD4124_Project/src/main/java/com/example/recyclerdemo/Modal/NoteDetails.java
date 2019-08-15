package com.example.recyclerdemo.Modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

public class NoteDetails implements Serializable {
    public static final String TABLE_NAME = "notesdetails";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_NOTETITLE = "notetitle";
    public static final String COLUMN_NOTEDATE = "notedate";
    public static final String COLUMN_NOTEDETAILS = "notedetails";
    public static final String COLUMN_NOTEIMAGE = "noteimage";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_FULLADDRESS = "fulladdress";

    private int id;
    private String category;
    private String notetitle;
    private String notedetails;
    private String notedate;
    private String noteimage;
    private double latitude;
    private double longitude;
    private String fulldaaress;

    public NoteDetails(int id, String category, String notetitle, String notedetails, String notedate, String noteimage) {
        this.id = id;
        this.category = category;
        this.notetitle = notetitle;
        this.notedetails = notedetails;
        this.notedate = notedate;
        this.noteimage = noteimage;
    }

    public NoteDetails(int id, String category, String notetitle, String notedetails, String notedate, String noteimage, double latitude, double longitude, String fulldaaress) {
        this.id = id;
        this.category = category;
        this.notetitle = notetitle;
        this.notedetails = notedetails;
        this.notedate = notedate;
        this.noteimage = noteimage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fulldaaress = fulldaaress;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFulldaaress() {
        return fulldaaress;
    }

    public void setFulldaaress(String fulldaaress) {
        this.fulldaaress = fulldaaress;
    }

    public NoteDetails(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedetails() {
        return notedetails;
    }

    public void setNotedetails(String notedetails) {
        this.notedetails = notedetails;
    }

    public String getNotedate() {
        return notedate;
    }

    public void setNotedate(String notedate) {
        this.notedate = notedate;
    }

    public String getNoteimage() {
        return noteimage;
    }

    public void setNoteimage(String noteimage) {
        this.noteimage = noteimage;
    }

    public static String getCreateTable() {
        return CREATE_TABLE;
    }

    @Override
    public String toString() {
        return "NoteDetails{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", notetitle='" + notetitle + '\'' +
                ", notedetails='" + notedetails + '\'' +
                ", notedate='" + notedate + '\'' +
                ", noteimage='" + noteimage + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", fulldaaress='" + fulldaaress + '\'' +
                '}';
    }

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CATEGORY + " TEXT NOT NULL,"
                    + COLUMN_NOTETITLE + " TEXT NOT NULL,"
                    + COLUMN_NOTEDATE + " DATETIME  DEFAULT (datetime('now','localtime')) NOT NULL,"
                    + COLUMN_NOTEDETAILS + " TEXT,"
                    + COLUMN_NOTEIMAGE + " BLOB NULL,"
                    + COLUMN_LATITUDE + " TEXT NULL,"
                    + COLUMN_LONGITUDE + " TEXT NULL,"
                    + COLUMN_FULLADDRESS + " TEXT NULL"
                    + ")";




}
