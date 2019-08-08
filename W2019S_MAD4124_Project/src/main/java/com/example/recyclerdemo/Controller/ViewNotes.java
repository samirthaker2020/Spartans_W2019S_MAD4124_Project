package com.example.recyclerdemo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerdemo.R;

public class ViewNotes extends AppCompatActivity {
private int colid=0;
private TextView pcolid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Bundle bundle = getIntent().getExtras();
        colid = bundle.getInt("colid");
        pcolid= (TextView) findViewById(R.id.textView2);
        pcolid.setText(Integer.toString(colid));


    }
}
