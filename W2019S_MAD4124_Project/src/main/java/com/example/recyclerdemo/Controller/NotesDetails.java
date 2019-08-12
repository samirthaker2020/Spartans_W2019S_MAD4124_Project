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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private SearchView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyNotes");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.activity_notes_details);
        lstallnoteData=findViewById(R.id.notes_rc1);
        //  TextView cid= (TextView) findViewById(R.id.cid);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
          sc = (SearchView) findViewById(R.id.notes_search);
//Extract the data…

            stuff = bundle.getInt("categoryid");

        db = new DatabaseHelper(this);
        Note n=db.getNote(stuff);
        getSupportActionBar().setSubtitle(n.getCategory());
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


        lstallnoteData.addOnItemTouchListener(new RecyclerTouchListener(this,
                lstallnoteData, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(NotesDetails.this, ViewNotes.class);
                NoteDetails n1=notesdetailsList.get(position);
                int getrec= n1.getId();
                Log.d("colid",Integer.toString(getrec));
//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putInt("colid", getrec);

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                    searchContact(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    //contacts.clear();
                    getdata();
                   // Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                }
                else {
                    searchContact(newText);
                }
                return false;
            }
        });
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

    private void searchContact(String keyword) {

        List<NoteDetails> contacts = db.search(keyword);
//        System.out.println(contacts.get(2));

        if (contacts != null) {

            mAdapter = new RcNotes_Adpater(contacts,this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            lstallnoteData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            // lstcategoryData.setLayoutManager(new GridLayoutManager(this, 2));
            //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
            lstallnoteData.setHasFixedSize(true);
            lstallnoteData.setLayoutManager(mLayoutManager);
            lstallnoteData.setItemAnimator(new DefaultItemAnimator());
            lstallnoteData.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else
        {
           // contacts.clear();
            //mAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
            // mAdapter = new RcNotes_Adpater(notesdetailsList,this);
//
           // mAdapter.notifyDataSetChanged();
        }
    }


    public void getdata()
    {
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
    }

