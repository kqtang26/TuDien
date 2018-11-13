package com.example.millionaire.tudien.Adapter;

/**
 * Created by BoBo on 3/25/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.millionaire.tudien.Activity.HienThiTuVung;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.RecyclerViewHolder> {
    public ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<TuVungAnhViet> arrayListTuVung = new ArrayList<>();
    LayoutInflater inflater;
    SQLiteDatabase database = null;
    Context context;

    public LichSuAdapter(ArrayList<String> arrayList, ArrayList<TuVungAnhViet> arrayListTuVung, Context context){
        this.arrayList = arrayList;
        this.context = context;
        this.arrayListTuVung = arrayListTuVung;
        inflater = LayoutInflater.from(context);
        database = ((Main) context).database;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view_yeu_thich,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final String tuVung = arrayList.get(position).replace("'","''");
        holder.txtView.setText(tuVung);

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.execSQL("delete from LichSu where TuKhoa = '"+tuVung+"'");
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThi(holder.txtView.getText().toString());
            }
        });
    }

    //Hiển thị từ vựng được chọn.
    public void hienThi(String temp){
        TuVungAnhViet tuVung = null;
        for(int i = 0;i<arrayListTuVung.size();i++){
            if(arrayListTuVung.get(i).getTuKhoa().equals(temp)){
                tuVung = arrayListTuVung.get(i);
                break;
            }
        }
        Intent intent = new Intent(context,HienThiTuVung.class);

        intent.putExtra("TU_KHOA",tuVung.getTuKhoa());
        intent.putExtra("PHAT_AM",tuVung.getPhatAm());
        intent.putExtra("NGHIA",tuVung.getNghia());

        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtView;
        ImageView imgView;

        public RecyclerViewHolder(View v){
            super(v);
            txtView = (TextView) v.findViewById(R.id.txtViewYeuThich);
            imgView = (ImageView) v.findViewById(R.id.btnDelete);
        }

    }
}

