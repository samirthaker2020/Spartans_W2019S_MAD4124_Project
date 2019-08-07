package com.example.recyclerdemo.Modal;



public class Note {
    public static final String TABLE_NAME = "category";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORY = "category";


    private int id;
    private String category;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CATEGORY + " TEXT"
                    + ")";

    public Note() {
    }

    public Note(int id, String category ) {
        this.id = id;
       this.category=category;

    }

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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
