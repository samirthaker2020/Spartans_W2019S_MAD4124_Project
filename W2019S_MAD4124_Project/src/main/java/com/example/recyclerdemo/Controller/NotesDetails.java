package com.example.recyclerdemo.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Adapter.RcNotes_Adpater;
import com.example.recyclerdemo.Adapter.Rc_Adapter;
import com.example.recyclerdemo.Database.DatabaseHelper;
import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.Modal.RcModal;
import com.example.recyclerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class NotesDetails extends AppCompatActivity {
    private List<NoteDetails> notesdetailsList = new ArrayList<>();
    private List<NoteDetails> cname = new ArrayList<>();
    private RecyclerView lstallnoteData;
    private RcNotes_Adpater mAdapter;
private int stuff=0;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyNotes");
        getSupportActionBar().setSubtitle("All Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.activity_notes_details);
        lstallnoteData=findViewById(R.id.notes_rc1);
        //  TextView cid= (TextView) findViewById(R.id.cid);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        SearchView sc = (SearchView) findViewById(R.id.notes_search);
//Extract the data…
         stuff = bundle.getInt("categoryid");
        db = new DatabaseHelper(this);

        notesdetailsList.addAll(db.getNoteDetails(stuff));
        //  cid.setText(Integer.toString(stuff));
       // System.out.println(notesdetailsList.get(2));
//data();
        mAdapter = new RcNotes_Adpater(notesdetailsList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        lstallnoteData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // lstcategoryData.setLayoutManager(new GridLayoutManager(this, 2));
        //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        lstallnoteData.setHasFixedSize(true);
        lstallnoteData.setLayoutManager(mLayoutManager);
        lstallnoteData.setItemAnimator(new DefaultItemAnimator());
        lstallnoteData.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


    }

     public void data() {
        cname = new ArrayList<>();
        cname.add(new NoteDetails(1,"1","1","1","1","1"));
         cname.add(new NoteDetails(1,"1","1","1","1","1"));

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
                Intent i=new Intent(this, Addnotes.class);

                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putInt("cid", stuff);

//Add the bundle to the intent
                i.putExtras(bundle);
                startActivity(i);
                return true;
            case android.R.id.home:

                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    }

