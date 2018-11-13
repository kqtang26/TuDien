package com.example.millionaire.tudien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.millionaire.tudien.CustomFilter;
import com.example.millionaire.tudien.Activity.HienThiTuVung;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;

/**
 * Created by BoBo on 3/22/2017.
 */

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.RecyclerViewHolder> implements Filterable{
    public ArrayList<TuVungAnhViet> arrayList = new ArrayList<>();
    public ArrayList<TuVungAnhViet> filterList = new ArrayList<>();
    LayoutInflater inflater;
    SQLiteDatabase database = null;
    Context context;
    public CustomFilter filter;

    public TimKiemAdapter(ArrayList<TuVungAnhViet> arrayList, Context context){
        this.arrayList = arrayList;
        this.filterList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        database = ((Main) context).database;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view_tim_kiem,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final TuVungAnhViet tuVung = arrayList.get(position);
        holder.txtView.setText(tuVung.getTuKhoa());
        if(tuVung.getYeuThich() == 0)
            holder.imgView.setImageResource(R.drawable.black);
        else
            holder.imgView.setImageResource(R.drawable.gold);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThi(tuVung);
            }
        });

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuVung.getYeuThich() == 1){
                    tuVung.setYeuThich(0);
                    holder.imgView.setImageResource(R.drawable.black);
                    SetYeuThich(0,tuVung.getTuKhoa());
                }
                else{
                    tuVung.setYeuThich(1);
                    holder.imgView.setImageResource(R.drawable.gold);
                    SetYeuThich(1,tuVung.getTuKhoa());
                }
                notifyDataSetChanged();
            }
        });

    }

    //Cập nhật từ được yêu thích trong cơ sỡ dữ liệu.
    public void SetYeuThich(int t, String tuKhoa){
        tuKhoa = tuKhoa.replace("'","''");
        String sql = "UPDATE AnhViet SET YeuThich = "+t+" where TuKhoa = '"+tuKhoa+"'";
        database.execSQL(sql);
    }

    //Hiển thị từ đã được chọn.
    public void hienThi(TuVungAnhViet tuVung){
        Intent intent = new Intent(context,HienThiTuVung.class);

        intent.putExtra("TU_KHOA",tuVung.getTuKhoa());
        intent.putExtra("PHAT_AM",tuVung.getPhatAm());
        intent.putExtra("NGHIA",tuVung.getNghia());
        String temp = tuVung.getTuKhoa();
        if(tuVung.getTuKhoa().contains("'"))
            temp = tuVung.getTuKhoa().replaceAll("[']","''");

        //Cập nhật từ đã được chọn trong lịch sử.
        database.execSQL("delete from LichSu where TuKhoa = '"+temp+"'");
        database.execSQL("insert into LichSu values('"+temp+"')");

        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter(arrayList,this);
        }
        return filter;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtView;
        ImageView imgView;

        public RecyclerViewHolder(View v){
            super(v);
            txtView = (TextView) v.findViewById(R.id.txtView);
            imgView = (ImageView) v.findViewById(R.id.imgView);
        }

    }
}
