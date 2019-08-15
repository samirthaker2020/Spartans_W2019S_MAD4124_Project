package com.example.recyclerdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
        db.execSQL(NoteDetails.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NoteDetails.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_CATEGORY, note);

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_CATEGORY},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_CATEGORY)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setCategory(cursor.getString(cursor.getColumnIndex(Note.COLUMN_CATEGORY)));


                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_CATEGORY, note.getCategory());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    //---------------------------------note-deatails-----------------------------------------

    public List<NoteDetails> getAllNotesDetails() {
        List<NoteDetails> notesdetails = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NoteDetails.TABLE_NAME + " ORDER BY " +
                NoteDetails.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteDetails nd = new NoteDetails();
                nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));
                nd.setNotetitle(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)));
                nd.setNotedetails(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)));
                nd.setNotedate(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)));
               // nd.setNoteimage(cursor.getBlob(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)));
                notesdetails.add(nd);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notesdetails;
    }

    public long insertNotedetails(String cid,String title,String ndetails, String img_str,double lati,double longi,String fulladd) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        LocalDateTime myDateObj = LocalDateTime.now();
        // System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
       if(img_str==null )
       {
           Log.d("image",img_str);
           values.put(NoteDetails.COLUMN_CATEGORY, cid);
           values.put(NoteDetails.COLUMN_NOTETITLE,title);
           values.put(NoteDetails.COLUMN_NOTEDETAILS,ndetails);
          values.put(NoteDetails.COLUMN_NOTEIMAGE,img_str);
           values.put(NoteDetails.COLUMN_NOTEDATE,formattedDate);
           values.put(NoteDetails.COLUMN_LATITUDE,lati);
           values.put(NoteDetails.COLUMN_LONGITUDE,longi);
           values.put(NoteDetails.COLUMN_FULLADDRESS,fulladd);
       }else
       {
           values.put(NoteDetails.COLUMN_CATEGORY, cid);
           values.put(NoteDetails.COLUMN_NOTETITLE,title);
           values.put(NoteDetails.COLUMN_NOTEDETAILS,ndetails);
           values.put(NoteDetails.COLUMN_NOTEIMAGE,img_str);
           values.put(NoteDetails.COLUMN_NOTEDATE,formattedDate);
           values.put(NoteDetails.COLUMN_LATITUDE,lati);
           values.put(NoteDetails.COLUMN_LONGITUDE,longi);
           values.put(NoteDetails.COLUMN_FULLADDRESS,fulladd);
     System.out.println(lati);
           System.out.println(longi);
           System.out.println(fulladd);
       }

        // insert row
        long id = db.insert(NoteDetails.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<NoteDetails> getNoteDetails(long id) {
        List<NoteDetails> notesdetails1 = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NoteDetails.TABLE_NAME,
                new String[]{NoteDetails.COLUMN_ID, NoteDetails.COLUMN_CATEGORY,NoteDetails.COLUMN_NOTETITLE,NoteDetails.COLUMN_NOTEDETAILS,NoteDetails.COLUMN_NOTEDATE,NoteDetails.COLUMN_NOTEIMAGE,NoteDetails.COLUMN_FULLADDRESS,NoteDetails.COLUMN_LATITUDE,NoteDetails.COLUMN_LONGITUDE},
                NoteDetails.COLUMN_CATEGORY + "=?",
                new String[]{String.valueOf(id)}, null, null, NoteDetails.COLUMN_NOTETITLE+" " +"ASC", null);

        if (cursor.moveToFirst()) {
            do {
                NoteDetails nd = new NoteDetails();
                nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));
                nd.setNotetitle(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)));
                nd.setNotedetails(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)));
                nd.setNotedate(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)));
                 nd.setNoteimage(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)));
                nd.setFulldaaress(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_FULLADDRESS)));
                nd.setLatitude(cursor.getDouble(cursor.getColumnIndex(NoteDetails.COLUMN_LATITUDE)));
                nd.setLongitude(cursor.getDouble(cursor.getColumnIndex(NoteDetails.COLUMN_LONGITUDE)));
                notesdetails1.add(nd);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return notesdetails1;
    }
    public NoteDetails getNotedetails(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NoteDetails.TABLE_NAME,
                new String[]{NoteDetails.COLUMN_ID, NoteDetails.COLUMN_CATEGORY,NoteDetails.COLUMN_NOTETITLE,NoteDetails.COLUMN_NOTEDETAILS,NoteDetails.COLUMN_NOTEDATE,NoteDetails.COLUMN_NOTEIMAGE,NoteDetails.COLUMN_FULLADDRESS,NoteDetails.COLUMN_LONGITUDE,NoteDetails.COLUMN_LATITUDE},
                NoteDetails.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        NoteDetails note = new NoteDetails(
                cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)),
                cursor.getDouble(cursor.getColumnIndex(NoteDetails.COLUMN_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndex(NoteDetails.COLUMN_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_FULLADDRESS))
        );
        // close the db connection
        cursor.close();
        Log.d("fcat",note.getCategory());
        return note;
    }
    public void deleteNotedetails(String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NoteDetails.TABLE_NAME, NoteDetails.COLUMN_ID + " = ?",
                new String[]{a});
        db.close();
    }

    public int updateNotedetails(NoteDetails note) {
        SQLiteDatabase db = this.getWritableDatabase();
        LocalDateTime myDateObj = LocalDateTime.now();
       // System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        ContentValues values = new ContentValues();
        values.put(NoteDetails.COLUMN_NOTETITLE, note.getNotetitle());
        values.put(NoteDetails.COLUMN_NOTEDETAILS, note.getNotedetails());
        values.put(NoteDetails.COLUMN_NOTEIMAGE, note.getNoteimage());
        values.put(NoteDetails.COLUMN_NOTEDATE,formattedDate);
        values.put(NoteDetails.COLUMN_LATITUDE,note.getLatitude());
        values.put(NoteDetails.COLUMN_LONGITUDE,note.getLongitude());
        values.put(NoteDetails.COLUMN_FULLADDRESS,note.getFulldaaress());

        // updating row
        return db.update(NoteDetails.TABLE_NAME, values, NoteDetails.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }


    public List<NoteDetails> search(String keyword) {
        List<NoteDetails> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + NoteDetails.TABLE_NAME + " where " + NoteDetails.COLUMN_NOTETITLE + " like ?", new String[] { "%" + keyword + "%" });

                contacts = new ArrayList<NoteDetails>();
            if (cursor.moveToFirst()) {
                do {
                    NoteDetails nd = new NoteDetails();
                    nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                    nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));
                    nd.setNotetitle(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)));
                    nd.setNotedetails(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)));
                    nd.setNotedate(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)));
                     nd.setNoteimage(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)));
                             nd.setFulldaaress(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_FULLADDRESS)));

                    contacts.add(nd);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }


    public List<Note> searchcategory(String keyword) {
        List<Note> cat = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Note.TABLE_NAME + " where " + Note.COLUMN_CATEGORY + " like ?", new String[] { "%" + keyword + "%" });

            cat = new ArrayList<Note>();
            if (cursor.moveToFirst()) {
                do {
                    Note nd = new Note();
                    nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                    nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));

                    cat.add(nd);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            cat = null;
        }
        return cat;
    }

    public List<NoteDetails> getsortbytitle(long id,String a) {
        List<NoteDetails> notesdetails1 = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NoteDetails.TABLE_NAME,
                new String[]{NoteDetails.COLUMN_ID, NoteDetails.COLUMN_CATEGORY,NoteDetails.COLUMN_NOTETITLE,NoteDetails.COLUMN_NOTEDETAILS,NoteDetails.COLUMN_NOTEDATE,NoteDetails.COLUMN_NOTEIMAGE,NoteDetails.COLUMN_FULLADDRESS},
                NoteDetails.COLUMN_CATEGORY + "=?",
                new String[]{String.valueOf(id)}, null, null, NoteDetails.COLUMN_NOTETITLE+" " +a, null);

        if (cursor.moveToFirst()) {
            do {
                NoteDetails nd = new NoteDetails();
                nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));
                nd.setNotetitle(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)));
                nd.setNotedetails(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)));
                nd.setNotedate(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)));
                nd.setNoteimage(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)));
                nd.setFulldaaress(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_FULLADDRESS)));
                notesdetails1.add(nd);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return notesdetails1;
    }


    public List<NoteDetails> getsortbydate(long id,String a) {
        List<NoteDetails> notesdetails1 = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NoteDetails.TABLE_NAME,
                new String[]{NoteDetails.COLUMN_ID, NoteDetails.COLUMN_CATEGORY,NoteDetails.COLUMN_NOTETITLE,NoteDetails.COLUMN_NOTEDETAILS,NoteDetails.COLUMN_NOTEDATE,NoteDetails.COLUMN_NOTEIMAGE,NoteDetails.COLUMN_FULLADDRESS},
                NoteDetails.COLUMN_CATEGORY + "=?",
                new String[]{String.valueOf(id)}, null, null, NoteDetails.COLUMN_NOTEDATE+" " +a, null);

        if (cursor.moveToFirst()) {
            do {
                NoteDetails nd = new NoteDetails();
                nd.setId(cursor.getInt(cursor.getColumnIndex(NoteDetails.COLUMN_ID)));
                nd.setCategory(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_CATEGORY)));
                nd.setNotetitle(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTETITLE)));
                nd.setNotedetails(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDETAILS)));
                nd.setNotedate(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEDATE)));
                nd.setNoteimage(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_NOTEIMAGE)));
                nd.setFulldaaress(cursor.getString(cursor.getColumnIndex(NoteDetails.COLUMN_FULLADDRESS)));
                notesdetails1.add(nd);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return notesdetails1;
    }



}
