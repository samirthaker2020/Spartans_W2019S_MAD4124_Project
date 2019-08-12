package com.example.recyclerdemo.Controller;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Adapter.RcNotes_Adpater;
import com.example.recyclerdemo.Adapter.Rc_Adapter;
import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.Modal.RcModal;
import com.example.recyclerdemo.R;
import com.example.recyclerdemo.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Note> notesList = new ArrayList<>();
    private List<Note> cat = new ArrayList<>();
    private ArrayList<RcModal> cname;
    private RecyclerView lstcategoryData;
    private Rc_Adapter mAdapter;
    public ActionBar ac;
    private DatabaseHelper db;
    public TextView noNotesView;
    private SearchView searchcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyNotes");
        getSupportActionBar().setSubtitle("Categories");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.activity_main);
        searchcat = (SearchView)   findViewById(R.id.search_category);
        searchcat.setFocusable(true);
        searchcat.requestFocus();
        searchcat.requestFocusFromTouch();
        lstcategoryData = findViewById(R.id.rc1);
       searchcat.setQueryHint("Enter Category");
//BottomNavigationView bv= (BottomNavigationView) findViewById(R.id.bnav1);

//bv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

             // data();
        db = new DatabaseHelper(this);

        notesList.addAll(db.getAllNotes());
        mAdapter = new Rc_Adapter(notesList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
       // lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // lstcategoryData.setLayoutManager(new GridLayoutManager(this, 2));
        //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        lstcategoryData.setHasFixedSize(true);
        lstcategoryData.setLayoutManager(mLayoutManager);
        lstcategoryData.setItemAnimator(new DefaultItemAnimator());
        lstcategoryData.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnadd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog();
            }
        });


        lstcategoryData.addOnItemTouchListener(new RecyclerTouchListener(this,
                lstcategoryData, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent i = new Intent(MainActivity.this, NotesDetails.class);
                Note n1;
                if(cat.isEmpty())
                {
                    n1=notesList.get(position);
                    System.out.println(n1.getId());
                }
                 else
                {
                    n1=cat.get(position);
                    System.out.println(n1.getId());
                }




                int getrec= n1.getId();
                Log.d("cid",Integer.toString(getrec));
//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putInt("categoryid", getrec);

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        searchcat.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchContact(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s))
                {
                    cat.clear();
                    getdata();
                    // Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                }
                else {
                    searchContact(s);
                }
                return false;
            }
        });
    }



    private void searchContact(String keyword) {

         cat = db.searchcategory(keyword);
//        System.out.println(contacts.get(2));

        if (cat != null) {

            mAdapter = new Rc_Adapter(cat,this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        //    lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            // lstcategoryData.setLayoutManager(new GridLayoutManager(this, 2));
            //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
            lstcategoryData.setHasFixedSize(true);
            lstcategoryData.setLayoutManager(mLayoutManager);
            lstcategoryData.setItemAnimator(new DefaultItemAnimator());
            lstcategoryData.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
        }else
        {
            // contacts.clear();
            //mAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"No category Found",Toast.LENGTH_SHORT).show();
            // mAdapter = new RcNotes_Adpater(notesdetailsList,this);
//
            // mAdapter.notifyDataSetChanged();
        }
    }




    private void showEditNoteDialog(final int position)
    {
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.note_dialog);
        dialog.setTitle("Update Category");


        // set the custom dialog components - text, image and button
        final TextView upatecategory = (TextView) dialog.findViewById(R.id.category);
        Button btnadd= (Button) dialog.findViewById(R.id.btnadd);
        Button btncancel= (Button) dialog.findViewById(R.id.btncancel);
        TextView title= (TextView) dialog.findViewById(R.id.dialog_title);
        title.setText("Update category");
             Note n= notesList.get(position);
            upatecategory.setText(n.getCategory());
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(upatecategory.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter Category First!", Toast.LENGTH_LONG).show();
                }else{

                    updateNote(upatecategory.getText().toString(),position);
                    dialog.dismiss();
                }


            }
        });
        //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialog.getWindow().setLayout(width, height);

        dialog.show();


    }
      private void showNoteDialog()
      {
          int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
          int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);


          final Dialog dialog = new Dialog(this);
          dialog.setContentView(R.layout.note_dialog);
          dialog.setTitle("New Category");


          // set the custom dialog components - text, image and button
          final TextView newcategory = (TextView) dialog.findViewById(R.id.category);
            Button btnadd= (Button) dialog.findViewById(R.id.btnadd);
          Button btncancel= (Button) dialog.findViewById(R.id.btncancel);


          btncancel.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dialog.dismiss();
              }
          });
          btnadd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (TextUtils.isEmpty(newcategory.getText().toString())) {
                      Toast.makeText(MainActivity.this, "Enter Category First!", Toast.LENGTH_LONG).show();
                  }else {
                      createNote(newcategory.getText().toString());
                        dialog.dismiss();
              }}
          });
        //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
          // if button is clicked, close the custom dialog
          dialog.getWindow().setLayout(width, height);

          dialog.show();


      }
    private void createNote(String note) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertNote(note);

        // get the newly inserted note from db
        Note n = db.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

          //  toggleEmptyNotes();
        }
    }
    private void deleteNote(int position) {
        // deleting the note from db
        db.deleteNote(notesList.get(position));

        // removing the note from the list
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        //toggleEmptyNotes();
    }

    private void updateNote(String note, int position) {
        Note n = notesList.get(position);
        // updating note text
        n.setCategory(note);

        // updating note in db
        db.updateNote(n);

        // refreshing the list
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        //toggleEmptyNotes();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                  //  showNoteDialog(true, notesList.get(position), position);
                    showEditNoteDialog(position);
                } else {
                  //  deleteNote(position);
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }
   /* public void data() {
        cname = new ArrayList<>();
        cname.add(new RcModal("Food"));
        cname.add(new RcModal("Friends"));
        cname.add(new RcModal("Others1"));
        cname.add(new RcModal("Others2"));
        cname.add(new RcModal("Others3"));
        cname.add(new RcModal("Food"));
        cname.add(new RcModal("Friends"));
        cname.add(new RcModal("Others1"));
        cname.add(new RcModal("Others2"));
        cname.add(new RcModal("Others3"));
    } */

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cat_edit:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "EDIT",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return true;

                case R.id.cat_delete:
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "DELETE",
                            Toast.LENGTH_SHORT);

                    toast1.show();
                    return true;
            }

            return false;
        }


        };

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }


    private void getdata()
    {
        mAdapter = new Rc_Adapter(notesList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
      //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // lstcategoryData.setLayoutManager(new GridLayoutManager(this, 2));
        //  lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        lstcategoryData.setHasFixedSize(true);
        lstcategoryData.setLayoutManager(mLayoutManager);
        lstcategoryData.setItemAnimator(new DefaultItemAnimator());
        lstcategoryData.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }
}
