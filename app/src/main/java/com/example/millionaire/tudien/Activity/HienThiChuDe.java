package com.example.millionaire.tudien.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.millionaire.tudien.Adapter.TuVungTheoChuDeAdapter;
import com.example.millionaire.tudien.Class.TuVungTheoChuDe;
import com.example.millionaire.tudien.R;

import java.util.ArrayList;
import java.util.List;

public class HienThiChuDe extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TuVungTheoChuDeAdapter adapter;
    private List<TuVungTheoChuDe> lst;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_chu_de);

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String DATABASE_NAME = intent.getStringExtra("DATABASE_NAME");
        final String CHU_DE = intent.getStringExtra("CHU_DE");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        setTitle(CHU_DE);

        Button btnGame = (Button) findViewById(R.id.btnGame);

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HienThiChuDe.this,Game.class);
                intent.putExtra("DATABASE_NAME",DATABASE_NAME);
                intent.putExtra("CHU_DE",CHU_DE);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        lst = new ArrayList<>();
        adapter = new TuVungTheoChuDeAdapter(this, lst);

        //Lấy danh sách từ tựng theo chủ đề.
        Cursor cursor = database.rawQuery("select * from TuVungTheoChuDe where ChuDe ='"+CHU_DE+"'",null);
        while(cursor.moveToNext()){
            byte[] bb = cursor.getBlob(cursor.getColumnIndex("HinhAnh"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bb, 0, bb.length);
            TuVungTheoChuDe temp = new TuVungTheoChuDe(cursor.getString(0),cursor.getString(1),cursor.getString(2), bitmap);
            lst.add(temp);
        }
        cursor.close();

        //Hiển thị danh sách từ vựng đã lấy.
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
