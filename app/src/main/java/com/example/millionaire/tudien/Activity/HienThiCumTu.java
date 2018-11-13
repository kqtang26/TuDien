package com.example.millionaire.tudien.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.millionaire.tudien.Adapter.CumTuAdapter;
import com.example.millionaire.tudien.Class.CumTuThongDung;
import com.example.millionaire.tudien.R;

import java.util.ArrayList;

public class HienThiCumTu extends AppCompatActivity {

    SQLiteDatabase database = null;
    ArrayList<CumTuThongDung> arrayList = new ArrayList<>();
    String CHU_DE;
    ListView listView;
    CumTuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cum_tu_thong_dung);
        getSupportActionBar().setTitle("Cụm từ thông dụng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        final String DATABASE_NAME = intent.getStringExtra("DATABASE_NAME");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        //Lấy danh sách cụm từ thông dụng.
        Cursor cursor = database.rawQuery("select * from CumTuThongDung",null);
        while(cursor.moveToNext()){
            arrayList.add(new CumTuThongDung(cursor.getString(0),cursor.getString(1)));
        }
        cursor.close();

        //Hiển thị danh sách đã lấy lên giao diện.
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CumTuAdapter(HienThiCumTu.this,R.layout.custom_list_view_thong_dung,arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
