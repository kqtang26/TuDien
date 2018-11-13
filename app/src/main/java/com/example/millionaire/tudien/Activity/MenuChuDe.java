package com.example.millionaire.tudien.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.millionaire.tudien.R;

import java.util.ArrayList;

public class MenuChuDe extends AppCompatActivity {

    SQLiteDatabase database = null;
    ArrayList<String> arrayList = new ArrayList<>();
    String CHU_DE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chu_de);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Từ vựng theo chủ đề");

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        final String DATABASE_NAME = intent.getStringExtra("DATABASE_NAME");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        //Lấy danh sách chủ đề.
        Cursor cursor = database.rawQuery("select count(rowid),ChuDe from TuVungTheoChuDe group by ChuDe",null);
        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(1)+" ("+String.valueOf(cursor.getInt(0))+")");
        }
        cursor.close();

        ListView listView = (ListView) findViewById(R.id.lstViewChuDe);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), HienThiChuDe.class);
                intent.putExtra("DATABASE_NAME",DATABASE_NAME);
                CHU_DE = arrayList.get(position).substring(0,arrayList.get(position).indexOf('(')-1);
                intent.putExtra("CHU_DE",CHU_DE);
                startActivity(intent);
            }
        });
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
