package com.example.millionaire.tudien.Tab;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.millionaire.tudien.Activity.Main;
import com.example.millionaire.tudien.Class.TuVungAnhViet;
import com.example.millionaire.tudien.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by BoBo on 5/1/2017.
 */

public class TabDich extends Fragment{

    EditText textInput;
    Button btnTranslate, btnSwitch;
    static TextView textOutput, textViewSource, textViewTarget;
    static String source = "en",target = "vi";
    Context context;
    View v;

    public TabDich(){

    }

    @SuppressLint("ValidFragment")
    public TabDich (Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v == null) {
            v = inflater.inflate(R.layout.dich, container, false);

            textInput = (EditText) v.findViewById(R.id.InputText);
            btnTranslate = (Button) v.findViewById(R.id.TranslateButton);
            textOutput = (TextView) v.findViewById(R.id.OutputText);
            textViewSource = (TextView) v.findViewById(R.id.textViewSource);
            textViewTarget = (TextView) v.findViewById(R.id.textViewTarget);
            btnSwitch = (Button) v.findViewById(R.id.btnSwitch);

            btnSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temp = source;
                    source = target;
                    target = temp;
                    temp = textViewSource.getText().toString();
                    textViewSource.setText(textViewTarget.getText().toString());
                    textViewTarget.setText(temp);
                    textInput.setText("");
                    textOutput.setText("");
                }
            });

            btnTranslate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = textInput.getText().toString();
                    if (isOnline() == false) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Kết nối mạng");
                        alertDialog.setMessage("Cần kết nối mạng cho chức năng này");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                    if (input.equals(""))
                        return;
                    input = input.replaceAll(" ", "%20");

                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(textInput.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    yourDataTask task = new yourDataTask(input, context);
                    task.execute();
                }
            });
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        v = null;
    }

    //Kiểm tra kết nối mạng.
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class yourDataTask extends AsyncTask<Void, Void, String>
    {
        String input;
        Context context;
        final ProgressDialog dialog;

        public yourDataTask(String input, Context context){
            this.input = input;
            this.context = context;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected String doInBackground(Void... params)
        {
            //API của Yandex.
            String str="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170430T035515Z.f607326c3c2c3d0c.730e101d30e3b7906b94b589c85f5b79e6102582&text="+input+"&lang="+source+"-"+target;
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }
                JSONObject temp = new JSONObject(stringBuffer.toString());
                return temp.getString("text");
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null){
                Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_LONG).show();
                return;
            }
            s = s.substring(2,s.length()-2);
            textOutput.setText(s);
        }
    }
}
