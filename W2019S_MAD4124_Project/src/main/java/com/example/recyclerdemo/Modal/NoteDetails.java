package com.example.recyclerdemo.Modal;

import java.util.Arrays;

public class NoteDetails {
    public static final String TABLE_NAME = "notesdetails";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_NOTETITLE = "notetitle";
    public static final String COLUMN_NOTEDATE = "notedate";
    public static final String COLUMN_NOTEDETAILS = "notedetails";
    public static final String COLUMN_NOTEIMAGE = "noteimage";

    private int id;
    private String category;
    private String notetitle;
    private String notedetails;
    private String notedate;
    private String noteimage;

    public NoteDetails(int id, String category, String notetitle, String notedetails, String notedate, String noteimage) {
        this.id = id;
        this.category = category;
        this.notetitle = notetitle;
        this.notedetails = notedetails;
        this.notedate = notedate;
        this.noteimage = noteimage;
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
                ", noteimage=" + noteimage +
                '}';
    }

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CATEGORY + " TEXT,"
                    + COLUMN_NOTETITLE + " TEXT,"
                    + COLUMN_NOTEDATE + " TEXT,"
                    + COLUMN_NOTEDETAILS + " TEXT,"
                    + COLUMN_NOTEIMAGE + " BLOB NULL"
                    + ")";

}
