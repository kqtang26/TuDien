package com.example.millionaire.tudien.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.millionaire.tudien.Class.CumTuThongDung;
import com.example.millionaire.tudien.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by BoBo on 5/3/2017.
 */

public class CumTuAdapter extends ArrayAdapter{
    Context context;
    int resource;
    List<CumTuThongDung> list;

    public CumTuAdapter(Context context, int resource, List list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(resource,parent,false);
        TextView textViewVi = (TextView) v.findViewById(R.id.txtVi);
        TextView textViewEn = (TextView) v.findViewById(R.id.txtEn);

        CumTuThongDung temp = list.get(position);

        textViewEn.setText(temp.getTiengAnh());
        textViewVi.setText(temp.getTiengViet());

        return v;
    }
}
