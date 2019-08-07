package com.example.recyclerdemo.Controller;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Adapter.Rc_Adapter;
import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.RcModal;
import com.example.recyclerdemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RcModal> cname;
    private RecyclerView lstcategoryData;
    private Rc_Adapter mAdapter;
    public ActionBar ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MyNotes");
        getSupportActionBar().setSubtitle("Categories");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.activity_main);

        lstcategoryData = findViewById(R.id.rc1);
//BottomNavigationView bv= (BottomNavigationView) findViewById(R.id.bnav1);

//bv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

              data();

        mAdapter = new Rc_Adapter(cname);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        lstcategoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

      private void showNoteDialog()
      {
          int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
          int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);


          final Dialog dialog = new Dialog(this);
          dialog.setContentView(R.layout.note_dialog);
          dialog.setTitle("New Category");


          // set the custom dialog components - text, image and button
          TextView newcategory = (TextView) dialog.findViewById(R.id.category);
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

              }
          });
        //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
          // if button is clicked, close the custom dialog
          dialog.getWindow().setLayout(width, height);

          dialog.show();


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
                } else {
                  //  deleteNote(position);
                }
            }
        });
        builder.show();
    }
    public void data() {
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
    }

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
}
