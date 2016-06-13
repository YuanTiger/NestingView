package com.my.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.my.R;


/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/6/13/上午9:56
 * DESC:
 */
public class EnterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_ry_ry;
    private Button bt_sc_lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        bt_ry_ry = (Button) findViewById(R.id.bt_ry_ry);
        bt_sc_lv = (Button) findViewById(R.id.bt_sc_lv);

        bt_ry_ry.setOnClickListener(this);
        bt_sc_lv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.bt_ry_ry:
                intent = new Intent(this,RyRyActivity.class);
                break;
            case R.id.bt_sc_lv:
                intent = new Intent(this,ScLvActivity.class);
                break;
        }
        startActivity(intent);
    }
}
