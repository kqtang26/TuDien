package com.example.millionaire.tudien.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.millionaire.tudien.R;

import org.w3c.dom.Text;

public class HienThiTuVung extends AppCompatActivity {

    TextView txtViewTuKhoa, txtViewPhatAm, txtViewNghia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_tu_vung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        txtViewTuKhoa = (TextView) findViewById(R.id.txtViewTuKhoa);
        txtViewPhatAm = (TextView) findViewById(R.id.txtViewPhatAm);
        txtViewNghia = (TextView) findViewById(R.id.txtViewNghia);

        String tuKhoa, phatAm, nghia;

        Intent intent = getIntent();

        tuKhoa = intent.getStringExtra("TU_KHOA");
        phatAm = intent.getStringExtra("PHAT_AM");
        nghia = intent.getStringExtra("NGHIA");

        txtViewTuKhoa.setText(tuKhoa);
        phatAm = phatAm.replace("\r\n","");
        txtViewPhatAm.setText(phatAm);
        nghia = nghia.replace("*","\r\n*");
        txtViewNghia.setText(nghia);
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
