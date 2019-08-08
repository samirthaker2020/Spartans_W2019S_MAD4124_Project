package com.example.recyclerdemo.Controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.R;

public class NotesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        //  TextView cid= (TextView) findViewById(R.id.cid);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        SearchView sc = (SearchView) findViewById(R.id.notes_search);
//Extract the data…
        int stuff = bundle.getInt("categoryid");

        //  cid.setText(Integer.toString(stuff));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnotesmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addnotes:
                //Write your code
                Toast toast = Toast.makeText(getApplicationContext(),
                        "This is a message displayed in a Toast",
                        Toast.LENGTH_SHORT);

                toast.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }

