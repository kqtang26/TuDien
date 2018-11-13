package com.example.millionaire.tudien.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.millionaire.tudien.Class.TuVungTheoChuDe;
import com.example.millionaire.tudien.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.os.Handler;

public class Game extends AppCompatActivity {

    SQLiteDatabase database = null;
    private List<TuVungTheoChuDe> lst = new ArrayList<>();
    private List<TuVungTheoChuDe> lstAnswer = new ArrayList<>();
    private List<TuVungTheoChuDe> lstDefault = new ArrayList<>();
    ImageView imageView;
    Button btn1, btn2, btn3, btn4;
    int dapAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        final String DATABASE_NAME = intent.getStringExtra("DATABASE_NAME");
        final String CHU_DE = intent.getStringExtra("CHU_DE");
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        setTitle(CHU_DE);

        imageView = (ImageView) findViewById(R.id.imgView);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        //Lấy danh sách từ vựng theo chủ đề.
        Cursor cursor = database.rawQuery("select * from TuVungTheoChuDe where ChuDe ='"+CHU_DE+"'",null);
        while(cursor.moveToNext()){
            byte[] bb = cursor.getBlob(cursor.getColumnIndex("HinhAnh"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bb, 0, bb.length);
            TuVungTheoChuDe temp = new TuVungTheoChuDe(cursor.getString(0),cursor.getString(1),cursor.getString(2), bitmap);
            lst.add(temp);
            lstAnswer.add(temp);
            lstDefault.add(temp);
        }
        cursor.close();

        display();

        //Xử lý khi chọn đáp án.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
                if(dapAn == 1)
                    btn1.setBackgroundResource(R.drawable.button_right);
                else{
                    btn1.setBackgroundResource(R.drawable.button_wrong);
                    displayRight();
                }
                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, 2000);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
                if(dapAn == 2)
                    btn2.setBackgroundResource(R.drawable.button_right);
                else{
                    btn2.setBackgroundResource(R.drawable.button_wrong);
                    displayRight();
                }
                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, 2000);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
                if(dapAn == 3)
                    btn3.setBackgroundResource(R.drawable.button_right);
                else{
                    btn3.setBackgroundResource(R.drawable.button_wrong);
                    displayRight();
                }
                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, 2000);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
                if(dapAn == 4)
                    btn4.setBackgroundResource(R.drawable.button_right);
                else{
                    btn4.setBackgroundResource(R.drawable.button_wrong);
                    displayRight();
                }
                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, 2000);
            }
        });

    }

    //Hiển thị câu tiếp theo.
    public void next(){
        display();
        btn1.setBackgroundResource(R.drawable.button_normal);
        btn2.setBackgroundResource(R.drawable.button_normal);
        btn3.setBackgroundResource(R.drawable.button_normal);
        btn4.setBackgroundResource(R.drawable.button_normal);
    }

    //Hiển thị đáp án đúng.
    public void displayRight(){
        switch (dapAn) {
            case 1:
                btn1.setBackgroundResource(R.drawable.button_right);
                break;
            case 2:
                btn2.setBackgroundResource(R.drawable.button_right);
                break;
            case 3:
                btn3.setBackgroundResource(R.drawable.button_right);
                break;
            case 4:
                btn4.setBackgroundResource(R.drawable.button_right);
                break;
        }
    }

    //Hiển thị câu hỏi mới.
    public void display(){
        //Khởi tạo lại danh sách answer với đầy đủ câu hỏi.
        lstAnswer.clear();
        lstAnswer = new ArrayList<>(lstDefault);
        if(lst.size() == 0){
            lst = lstDefault;
        }

        unlock();

        Random r = new Random();
        int temp = r.nextInt(lst.size());

        TuVungTheoChuDe tuVung = lst.get(temp);
        lst.remove(temp);
        imageView.setImageBitmap(tuVung.getHinhAnh());
        dapAn = r.nextInt(4)+1;

        switch (dapAn){
            case 1:
                btn1.setText(tuVung.getTuKhoa());
                break;
            case 2:
                btn2.setText(tuVung.getTuKhoa());
                break;
            case 3:
                btn3.setText(tuVung.getTuKhoa());
                break;
            case 4:
                btn4.setText(tuVung.getTuKhoa());
                break;
        }

        int now;

        for(int i = 1;i<=4;i++){
            //Tìm đáp án khác với câu trả lời.
            if(i == dapAn)
                continue;
            do{
                now = r.nextInt(lstAnswer.size());
            }while(now == temp);

            TuVungTheoChuDe tempTuVung = lstAnswer.get(now);
            lstAnswer.remove(now);

            switch (i) {
                case 1:
                    btn1.setText(tempTuVung.getTuKhoa());
                    break;
                case 2:
                    btn2.setText(tempTuVung.getTuKhoa());
                    break;
                case 3:
                    btn3.setText(tempTuVung.getTuKhoa());
                    break;
                case 4:
                    btn4.setText(tempTuVung.getTuKhoa());
                    break;
            }
        }
    }


    public void lock(){
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
    }

    public void unlock(){
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
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

    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            next();
        }
    };
}
