package com.example.millionaire.tudien.Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.millionaire.tudien.Adapter.LichSuAdapter;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.DividerItemDecoration;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;

/**
 * Created by BoBo on 3/29/2017.
 */
public class TabLichSu extends Fragment{
        SQLiteDatabase database = null;
        RecyclerView recyclerViewLichSu;
        LichSuAdapter lichSuAdapter;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<String> arrayListLichSu = new ArrayList<String>();
        ArrayList<TuVungAnhViet> arrayList = new ArrayList<TuVungAnhViet>();
        Context context;
        View v;

    public TabLichSu(){

    }

    @SuppressLint("ValidFragment")
    public TabLichSu (Context context, ArrayList<TuVungAnhViet> arrayList){
        this.context = context;
        this.database = ((Main) context).database;
        this.arrayList = arrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        check();
        if (v == null) {
            v = inflater.inflate(R.layout.lich_su, container, false);

            recyclerViewLichSu = (RecyclerView) v.findViewById(R.id.recyclerViewLichSu);
            lichSuAdapter = new LichSuAdapter(arrayListLichSu, arrayList, getContext());
            recyclerViewLichSu.setHasFixedSize(false);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerViewLichSu.setLayoutManager(layoutManager);
            recyclerViewLichSu.setAdapter(lichSuAdapter);
            recyclerViewLichSu.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recycler_view_divider));
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        v = null;
    }

    //Refresh danh sách lịch sử.
    public void check(){
        arrayListLichSu.clear();
        Cursor cursor = database.rawQuery("select * from LichSu order by rowid DESC",null);
        while(cursor.moveToNext()){
            arrayListLichSu.add(cursor.getString(0));
        }
        cursor.close();
    }


}
