package com.example.millionaire.tudien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.millionaire.tudien.Activity.GioiTu;
import com.example.millionaire.tudien.Activity.HienThiCumTu;
import com.example.millionaire.tudien.Activity.HienThiDongTuBatQuyTac;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.Activity.MenuChuDe;
import com.example.millionaire.tudien.Activity.CacThiTiengAnh;
import com.example.millionaire.tudien.Activity.Thu;
import com.example.millionaire.tudien.R;

import java.util.List;

/**
 * Created by BoBo on 4/1/2017.
 */

public class TuHocAdapter extends RecyclerView.Adapter<TuHocAdapter.ViewHolder> {

    private Context context;
    private List<String> lst;
    String DATABASE_NAME;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }

    public TuHocAdapter(Context context, List<String> lst){
        this.context = context;
        this.lst = lst;
        DATABASE_NAME = ((Main) context).DATABASE_NAME;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tu_hoc_card_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String temp = lst.get(position);

        //Kết nối Activity tự học đã chọn.
        if(temp == "TuVungTheoChuDe") {
            holder.imgView.setImageResource(R.drawable.tu_vung_theo_chu_de);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MenuChuDe.class);
                    intent.putExtra("DATABASE_NAME",DATABASE_NAME);
                    context.startActivity(intent);
                }
            });
        }
        else if(temp == "12ThiTiengAnh") {
            holder.imgView.setImageResource(R.drawable.cac_thi_tieng_anh);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CacThiTiengAnh.class);
                    context.startActivity(intent);
                }
            });
        }
        else if(temp == "GioiTu") {
            holder.imgView.setImageResource(R.drawable.gioi_tu);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GioiTu.class);
                    context.startActivity(intent);
                }
            });
        }
        else if(temp == "MauThu") {
            holder.imgView.setImageResource(R.drawable.mau_thu);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Thu.class);
                    context.startActivity(intent);
                }
            });
        }
        else if(temp == "CumTuThongDung") {
            holder.imgView.setImageResource(R.drawable.cum_tu_thong_dung);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HienThiCumTu.class);
                    intent.putExtra("DATABASE_NAME",DATABASE_NAME);
                    context.startActivity(intent);
                }
            });
        }
        else if(temp == "DongTuBatQuyTac") {
            holder.imgView.setImageResource(R.drawable.dong_tu_bat_quy_tac);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HienThiDongTuBatQuyTac.class);
                    intent.putExtra("DATABASE_NAME",DATABASE_NAME);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
