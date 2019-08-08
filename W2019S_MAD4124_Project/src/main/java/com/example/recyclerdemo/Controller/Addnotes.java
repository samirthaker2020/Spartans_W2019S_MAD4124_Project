package com.example.recyclerdemo.Controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Addnotes extends AppCompatActivity {
int cid=0;
private EditText title;
private EditText ndetails;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);
        getSupportActionBar().setTitle("MyNotes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Add Notes");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Bundle bundle = getIntent().getExtras();
        cid=bundle.getInt("cid");
        System.out.println(cid);
        db = new DatabaseHelper(this);
         title= (EditText) findViewById(R.id.txttitle);
         ndetails= (EditText) findViewById(R.id.txtnodedetails);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnotemenu1, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;

            case R.id.addnotes1:
                if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(ndetails.getText().toString()) ) {
                    Toast.makeText(Addnotes.this, "Enter All Field First", Toast.LENGTH_LONG).show();
                }else {
                    db.insertNotedetails(Integer.toString(cid), title.getText().toString(), ndetails.getText().toString());



                     title.setText("");
                     ndetails.setText("");
                    AlertDialog alertDialog = new AlertDialog.Builder(Addnotes.this).create();
                    alertDialog.setTitle("SUCESS");
                    alertDialog.setMessage("Saved Sucessfully");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dialog.dismiss();
                                    Intent it = new Intent(Addnotes.this, NotesDetails.class);
                                    Bundle bundle = new Bundle();

//Add your data to bundle
                                    bundle.putInt("categoryid", cid);

//Add the bundle to the intent
                                    it.putExtras(bundle);
                                    startActivity(it);
                                }
                            });
                    alertDialog.show();

                    return true;
                }
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
