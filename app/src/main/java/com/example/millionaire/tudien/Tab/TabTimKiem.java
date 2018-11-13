package com.example.millionaire.tudien.Tab;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.millionaire.tudien.Adapter.TimKiemAdapter;
import com.example.millionaire.tudien.LinearLayoutManagerWithSmoothScroller;
import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungAnhViet;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by BoBo on 3/29/2017.
 */
public class TabTimKiem extends Fragment {
    SQLiteDatabase database = null;
    RecyclerView recyclerView;
    TextView noResult;
    public TimKiemAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TuVungAnhViet> arrayList = new ArrayList<TuVungAnhViet>();
    SearchView searchView;
    Context context;
    final int REQ_CODE_SPEECH_INPUT = 100;
    View v;

    public TabTimKiem(){

    }

    @SuppressLint("ValidFragment")
    public TabTimKiem(Context context, ArrayList<TuVungAnhViet> arrayList){
        this.context = context;
        this.database = ((Main) context).database;
        this.arrayList = arrayList;
    }

    public void check(){
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v == null) {
            v = inflater.inflate(R.layout.tim_kiem, container, false);

            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            adapter = new TimKiemAdapter(arrayList, context);
            recyclerView.setHasFixedSize(false);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getContext()));
            recyclerView.setAdapter(adapter);
            searchView = (SearchView) v.findViewById(R.id.searchView);
            noResult = (TextView) v.findViewById(R.id.noResult);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.equals("")) {
                        //Ẩn danh sách nếu ô tìm kiếm rỗng.
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        adapter.getFilter().filter(newText);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });

            Button test = (Button) v.findViewById(R.id.test);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptSpeechInput();
                }
            });
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /*Hien thi dialog input google*/
    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this.getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*Nhan input giong noi*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String str = result.get(0);
                    searchView.setQuery(str, false);
                    searchView.clearFocus();
                }
                break;
            }

        }
    }
}
