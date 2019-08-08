package com.example.recyclerdemo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.R;

import java.util.ArrayList;
import java.util.List;

public  class Rc_Adapter extends RecyclerView.Adapter<Rc_Adapter.Myviewholder>   {

    private List<Note> categoryList;
    private List<Note> filterlist;
    private Context mcontext;

    public Rc_Adapter(List<Note> categoryList, Context mcontext) {
        this.categoryList = categoryList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rc_layout, viewGroup, false);
        mcontext = viewGroup.getContext();
        return new Myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        final Note cat = categoryList.get(i);
        myviewholder.cname.setText(cat.getCategory());

        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i=new Intent(mcontext, ProductDetails.class);
                i.putExtra("pdetails",pro);
                mcontext.startActivity(i); */

                CharSequence text = cat.getCategory();
                int duration = Toast.LENGTH_LONG;

              //  Toast toast = Toast.makeText(mcontext, text, duration);
                //toast.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder
    {
        public TextView cname;

        public Myviewholder(  View itemView) {
            super(itemView);

            cname=(TextView) itemView.findViewById(R.id.txtcategory);


        }
    }


}
