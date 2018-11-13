package com.example.millionaire.tudien.Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.millionaire.tudien.Adapter.YeuThichAdapter;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.DividerItemDecoration;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;

/**
 * Created by BoBo on 3/29/2017.
 */
public class TabYeuThich extends Fragment {
    SQLiteDatabase database = null;
    RecyclerView recyclerViewYeuThich;
    YeuThichAdapter yeuThichAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TuVungAnhViet> arrayListYeuThich = new ArrayList<TuVungAnhViet>();
    ArrayList<TuVungAnhViet> arrayList = new ArrayList<TuVungAnhViet>();
    Context context;
    View v;

    public TabYeuThich(){

    }

    @SuppressLint("ValidFragment")
    public TabYeuThich(Context context, ArrayList<TuVungAnhViet> arrayList){
        this.context = context;
        this.database = ((Main) context).database;
        this.arrayList = arrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v == null) {
            v = inflater.inflate(R.layout.yeu_thich, container, false);

            recyclerViewYeuThich = (RecyclerView) v.findViewById(R.id.recyclerViewYeuThich);
            yeuThichAdapter = new YeuThichAdapter(arrayListYeuThich, getContext());
            recyclerViewYeuThich.setHasFixedSize(false);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerViewYeuThich.setLayoutManager(layoutManager);
            recyclerViewYeuThich.setAdapter(yeuThichAdapter);
            recyclerViewYeuThich.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recycler_view_divider));
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        v = null;
    }

    //Refresh danh sách yêu thích.
    public void check(){
        arrayListYeuThich.clear();
        for(int i = 0;i<arrayList.size();i++){
            TuVungAnhViet temp = arrayList.get(i);
            if(temp.getYeuThich() == 1){
                arrayListYeuThich.add(temp);
            }
        }
        yeuThichAdapter.notifyDataSetChanged();
    }
}
