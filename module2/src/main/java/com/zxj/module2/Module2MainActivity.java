package com.zxj.module2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zxj.arouter_annotation.Route;

@Route(path = "/module2/module2main")
public class Module2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_module2);
    }
}