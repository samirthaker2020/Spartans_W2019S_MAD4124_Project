package com.example.recyclerdemo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerdemo.R;

public class NotesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        TextView cid= (TextView) findViewById(R.id.cid);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

//Extract the data…
        int stuff = bundle.getInt("categoryid");

        cid.setText(Integer.toString(stuff));
    }
}
