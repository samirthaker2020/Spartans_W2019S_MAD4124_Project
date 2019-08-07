package com.example.recyclerdemo.Controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.recyclerdemo.Adapter.Rc_Adapter;
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
    }

    public void data() {
        cname = new ArrayList<>();
        cname.add(new RcModal("Food"));
        cname.add(new RcModal("Friends"));
        cname.add(new RcModal("Others1"));
        cname.add(new RcModal("Others2"));
        cname.add(new RcModal("Others3"));
    }
}
