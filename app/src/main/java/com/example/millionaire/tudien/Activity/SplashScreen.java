package com.example.millionaire.tudien.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;

import com.example.millionaire.tudien.R;


public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }
}
