package com.example.recyclerdemo.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.recyclerdemo.Database.DatabaseHelper;
import com.example.recyclerdemo.R;

public class Addnotes extends AppCompatActivity {
int cid=0;
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
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
