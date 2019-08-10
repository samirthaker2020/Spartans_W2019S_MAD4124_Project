package com.example.recyclerdemo.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Database.DatabaseHelper;
import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewNotes extends AppCompatActivity {
private int colid=0;
private TextView pcolid;
private EditText txtviewtitle;
private EditText txtviewndetails;
public MenuItem saveitem;
    private DatabaseHelper db;
    private NoteDetails ndd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Bundle bundle = getIntent().getExtras();
        colid = bundle.getInt("colid");
      //  pcolid.setText(Integer.toString(colid));

        getSupportActionBar().setTitle("MyNotes");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        db = new DatabaseHelper(this);
          ndd=db.getNotedetails(colid);
      getSupportActionBar().setSubtitle(ndd.getNotetitle());
      txtviewtitle= (EditText) findViewById(R.id.txtviewtitle);
      txtviewndetails=(EditText) findViewById(R.id.txtviewndetails);
      txtviewndetails.setText(ndd.getNotedetails());
      txtviewtitle.setText(ndd.getNotetitle());
        entext(false);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewnotes_menu, menu);
        saveitem= (MenuItem) findViewById(R.id.viewnotes_save);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
            case R.id.viewnotes_edit:


                    entext(true);
                return true;
            case R.id.viewnotes_delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Write your message here.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteNote();
                                Intent it = new Intent(ViewNotes.this, NotesDetails.class);
                                Bundle bundle = new Bundle();

//Add your data to bundle
                                bundle.putInt("categoryid", Integer.valueOf( ndd.getCategory()));

//Add the bundle to the intent
                                it.putExtras(bundle);
                                startActivity(it);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
        }
        return super.onOptionsItemSelected(item);
    }

public void entext(boolean a)
{
    if(a==true)
    {
        txtviewtitle.setEnabled(true);
        txtviewndetails.setEnabled(true);
    }else
    {
        txtviewtitle.setEnabled(false);
        txtviewndetails.setEnabled(false);
    }

}

    private void deleteNote() {
        // deleting the note from db
        db.deleteNotedetails(String.valueOf( ndd.getId()));



        //toggleEmptyNotes();
    }
}