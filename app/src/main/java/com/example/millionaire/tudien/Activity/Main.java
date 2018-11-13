package com.example.millionaire.tudien.Activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.millionaire.tudien.Class.TuVungAnhViet;
import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Tab.TabDich;
import com.example.millionaire.tudien.Tab.TabLichSu;
import com.example.millionaire.tudien.Tab.TabTimKiem;
import com.example.millionaire.tudien.Tab.TabTuHoc;
import com.example.millionaire.tudien.Tab.TabYeuThich;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Main extends AppCompatActivity {

    public String DATABASE_NAME = "AnhVietDictionary.sqlite";
    String path = "/databases/";
    public SQLiteDatabase database = null;
    ArrayList<TuVungAnhViet> arrayList = new ArrayList<TuVungAnhViet>();
    TabLayout tabLayout;
    ViewPager viewPager;
    TabTimKiem tabTimKiem;
    TabYeuThich tabYeuThich;
    TabLichSu tabLichSu;
    TabTuHoc tabTuHoc;
    TabDich tabDich;
    static String Fragment[] = {"Tìm kiếm","Yêu thích","Dịch","Tự học","Lịch sử"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SaoChepDataBase();

        //Ẩn status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        AddTuVung();
        addControls();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(customAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //Set layout cho tab.
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);
            ImageView icon;
            switch (i){
                case 0:
                    icon = (ImageView) relativeLayout.findViewById(R.id.icon);
                    icon.setBackgroundResource(R.drawable.search);
                    break;
                case 1:
                    icon = (ImageView) relativeLayout.findViewById(R.id.icon);
                    icon.setBackgroundResource(R.drawable.star);
                    break;
                case 2:
                    icon = (ImageView) relativeLayout.findViewById(R.id.icon);
                    icon.setBackgroundResource(R.drawable.translate);
                    break;
                case 3:
                    icon = (ImageView) relativeLayout.findViewById(R.id.icon);
                    icon.setBackgroundResource(R.drawable.study);
                    break;
                case 4:
                    icon = (ImageView) relativeLayout.findViewById(R.id.icon);
                    icon.setBackgroundResource(R.drawable.time);
                    break;
            }

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);

            if(i == 0) {
                tab.getCustomView().findViewById(R.id.view).setVisibility(View.GONE);
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
                tabTimKiem.check();
                tabYeuThich.check();
                tabLichSu.check();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        public Context context;
        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
            context = applicationContext;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return tabTimKiem;
                case 1:
                    return tabYeuThich;
                case 2:
                    return tabDich;
                case 3:
                    return tabTuHoc;
                case 4:
                    return tabLichSu;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return Fragment.length;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return Fragment[position];
        }
    }


    //Thêm danh sách từ vựng vào tab tìm kiếm.
    public void AddTuVung(){
        Cursor cursor = database.rawQuery("select * from AnhViet",null);
        arrayList = new ArrayList<>();
        for(int i = 0;i<cursor.getCount();i++){
            cursor.moveToNext();
            TuVungAnhViet temp = new TuVungAnhViet(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            arrayList.add(temp);
        }
        cursor.close();
    }

    //Kiểm tra và khởi tạo các tab.
    private void addControls() {
        if(tabTimKiem == null)
            tabTimKiem = new TabTimKiem(Main.this,arrayList);
        if(tabYeuThich == null)
            tabYeuThich = new TabYeuThich(Main.this,arrayList);
        if(tabLichSu == null)
            tabLichSu = new TabLichSu(Main.this,arrayList);
        if(tabTuHoc == null)
            tabTuHoc = new TabTuHoc(Main.this);
        if(tabDich == null)
            tabDich = new TabDich(Main.this);
    }

    //Kiểm tra và tạo cơ sỡ dữ liệu.
    public void SaoChepDataBase(){
        File file = getDatabasePath(DATABASE_NAME);
        if(!file.exists()){
            try{
                InputStream input = getAssets().open(DATABASE_NAME);
                String fileName = getPath();

                File temp = new File(getApplicationInfo().dataDir + path);
                if(!temp.exists()){
                    temp.mkdir();
                }

                OutputStream output = new FileOutputStream(fileName);
                byte[] buffer = new byte[1024];
                int length;
                while((length = input.read(buffer)) > 0){
                    output.write(buffer, 0 , length);
                }

                output.flush();
                output.close();
                input.close();

                Toast.makeText(this,"Sao chép thành công",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getPath(){
        return getApplicationInfo().dataDir + path + DATABASE_NAME;
    }

    //Ẩn bàn phím.
    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
