package com.example.millionaire.tudien;

import android.widget.Filter;

import com.example.millionaire.tudien.Adapter.TimKiemAdapter;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;

/**
 * Created by BoBo on 3/23/2017.
 */

//Filter dành riêng cho mục tìm kiếm.
public class CustomFilter extends Filter {

    TimKiemAdapter adapter;
    ArrayList<TuVungAnhViet> filterList;
    public int count;

    public CustomFilter(ArrayList<TuVungAnhViet> filterList, TimKiemAdapter adapter){
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<TuVungAnhViet> filteredList = new ArrayList<>();
            count = 0;
            for(int i = 0;i<filterList.size();i++){
                if(filterList.get(i).getTuKhoa().toUpperCase().startsWith(constraint.toString())){
                    filteredList.add(filterList.get(i));
                    count++;
                }
            }

            results.count = filteredList.size();
            results.values = filteredList;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.arrayList = (ArrayList<TuVungAnhViet>) results.values;
        adapter.notifyDataSetChanged();
    }

    public int getCount() {
        return count;
    }
}

