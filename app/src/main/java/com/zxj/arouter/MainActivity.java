package com.zxj.arouter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zxj.arouter_core.ARouter;
import com.zxj.arouter_core.PostCard;
import com.zxj.arouter_core.callback.NavigationCallback;
import com.zxj.base.Module1Providers;

public class MainActivity extends AppCompatActivity {

    private Module1Providers module1Providers;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        module1Providers = (Module1Providers)ARouter.getInstance().build("/module1/providers").navigation();
        textView = findViewById(R.id.tv_service);
    }

    public void startModule1MainActivity(View view) {
        ARouter.getInstance().build("/module1/module1main").withString("msg","从MainActivity").navigation();
    }

    public void startModule2MainActivity(View view) {
        ARouter.getInstance().build("/module2/module2main").navigation(this,
            new NavigationCallback() {
                @Override
                public void onFound(PostCard postCard) {
                    Log.e("==========onFound",postCard.toString());

                }

                @Override
                public void onLost(PostCard postCard) {
                    Log.e("==========onLost",postCard.toString());
                }

                @Override
                public void onArrival(PostCard postCard) {
                    Log.e("==========onArrival",postCard.toString());
                }

                @Override
                public void onInterrupt(Throwable throwable) {
                    Log.e("==========onInterrupt",throwable.toString());
                }
            });

    }

    public void add(View view) {
        int num = module1Providers.add(4 ,5);
        textView.setText(String.valueOf(num));
    }
}