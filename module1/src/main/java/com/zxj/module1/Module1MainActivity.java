package com.zxj.module1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zxj.arouter_annotation.Route;

@Route(path = "/module1/module1main")
public class Module1MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_module1);
    }
}