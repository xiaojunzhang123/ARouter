package com.zxj.module1;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zxj.arouter_annotation.Extra;
import com.zxj.arouter_annotation.Route;
import com.zxj.arouter_core.ARouter;

@Route(path = "/module1/module1main")
public class Module1MainActivity extends AppCompatActivity {

    @Extra(name = "msg")
    String msg;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_module1);
        ARouter.getInstance().inject(this);
        textView =   findViewById(R.id.tv_msg);
        textView.setText(msg);
    }
}