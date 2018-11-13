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

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.RecyclerViewHolder> {
    public ArrayList<TuVungAnhViet> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    SQLiteDatabase database = null;
    Context context;

    public YeuThichAdapter(ArrayList<TuVungAnhViet> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
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
        final TuVungAnhViet tuVung = arrayList.get(position);
        holder.txtView.setText(tuVung.getTuKhoa());

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                tuVung.setYeuThich(0);
                SetYeuThich(0,tuVung.getTuKhoa());
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThi(tuVung);
            }
        });
    }


    //Hiển thị từ vựng được chọn.
    public void hienThi(TuVungAnhViet tuVung){
        Intent intent = new Intent(context,HienThiTuVung.class);

        intent.putExtra("TU_KHOA",tuVung.getTuKhoa());
        intent.putExtra("PHAT_AM",tuVung.getPhatAm());
        intent.putExtra("NGHIA",tuVung.getNghia());

        context.startActivity(intent);
    }

    //Cập nhật trạng thái yêu thích trong cơ sở dữ liệu.
    public void SetYeuThich(int t, String tuKhoa){
        tuKhoa = tuKhoa.replace("'","''");
        String sql = "UPDATE AnhViet SET YeuThich = "+t+" where TuKhoa = '"+tuKhoa+"'";
        database.execSQL(sql);
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

