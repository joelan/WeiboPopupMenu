package com.example.administrator.weibopopupmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import lrq.com.addpopmenu.PopMenu;
import lrq.com.addpopmenu.PopMenuItem;
import lrq.com.addpopmenu.PopMenuItemListener;

public class MainActivity extends AppCompatActivity {

    private Button button2;
    private RelativeLayout activitymain;
    private PopMenu mPopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialize();
    }

    private void initialize() {



    mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("广场", getResources().getDrawable(R.drawable.tabbar_compose_idea)))
                .addMenuItem(new PopMenuItem("私密", getResources().getDrawable(R.drawable.tabbar_compose_photo)))
                .addMenuItem(new PopMenuItem("家庭圈", getResources().getDrawable(R.drawable.tabbar_compose_headlines)))

                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {


                        Toast.makeText(MainActivity.this, "你点击了第" + position + "个位置", Toast.LENGTH_SHORT).show();


                    }
                })
                .build();

        button2 = (Button) findViewById(R.id.button2);
        activitymain = (RelativeLayout) findViewById(R.id.activity_main);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mPopMenu!=null)
                    mPopMenu.show();


            }
        });
    }
}
