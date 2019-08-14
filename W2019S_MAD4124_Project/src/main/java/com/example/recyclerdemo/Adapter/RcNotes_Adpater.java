package com.example.recyclerdemo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class RcNotes_Adpater extends  RecyclerView.Adapter<RcNotes_Adpater.myviewholder> {

    private List<NoteDetails> lstnote;

    private Context mcontext;

    public RcNotes_Adpater(List<NoteDetails> lstnote, Context mcontext) {
        this.lstnote = lstnote;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RcNotes_Adpater.myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notesdetails, viewGroup, false);
        mcontext = viewGroup.getContext();
        return new RcNotes_Adpater.myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RcNotes_Adpater.myviewholder myviewholder, int i) {
        final NoteDetails nd = lstnote.get(i);
        System.out.println(nd.getNotedate());
        System.out.println(nd.getCategory());
        System.out.println(nd.getNotetitle());
        System.out.println(nd.getNotedetails());
        myviewholder.ntitle.setText(nd.getNotetitle());
        myviewholder.ndatetime.setText("Last Modified:"+" "+nd.getNotedate()+"\n"+nd.getFulldaaress());
        if(nd.getNoteimage()==null)
        {
                myviewholder.nimage.setImageDrawable(null);
        }else {

            myviewholder.nimage.setImageBitmap(StringToBitMap(nd.getNoteimage()));
        }




    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return lstnote.size();
    }
    public class myviewholder extends RecyclerView.ViewHolder
    {
        public TextView ntitle;
        public TextView ndatetime;
        public ImageView nimage;

        public myviewholder(View itemView) {
            super(itemView);

            ntitle=(TextView) itemView.findViewById(R.id.ntitle);
            ndatetime=(TextView) itemView.findViewById(R.id.ndatetime);
            nimage=(ImageView) itemView.findViewById(R.id.imageView2);


        }
    }
}
