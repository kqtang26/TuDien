package com.example.millionaire.tudien.Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.Adapter.TuHocAdapter;
import com.example.millionaire.tudien.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BoBo on 4/1/2017.
 */

public class TabTuHoc extends Fragment {
    Context context;
    View v;
    String DATABASE_NAME;
    private RecyclerView recyclerView;
    private TuHocAdapter adapter;
    private List<String> list;

    public TabTuHoc(){

    }

    @SuppressLint("ValidFragment")
    public TabTuHoc(Context context){
        this.context = context;
        DATABASE_NAME = ((Main) context).DATABASE_NAME;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v == null) {
            v = inflater.inflate(R.layout.tu_hoc, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            list = new ArrayList<>();
            list.add("TuVungTheoChuDe");
            list.add("12ThiTiengAnh");
            list.add("CumTuThongDung");
            list.add("DongTuBatQuyTac");
            list.add("GioiTu");
            list.add("MauThu");
            adapter = new TuHocAdapter(context, list);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
