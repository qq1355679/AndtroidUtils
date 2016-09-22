package com.wzb.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wzb.support.Main;

import java.io.IOException;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main.setMsg("myJar");
        Main.showMsg();
        Main.setMsg("myJar2");
        Main.showMsg();
    }
}
