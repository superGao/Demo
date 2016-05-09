package com.supergao.blur;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.supergao.blur.utils.Fglass;

/**
 *
 *@author superGao
 *creat at 2016/5/9
 */
public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private TextView text;
    private TextView statusText;
    private CheckBox cb_fastBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }

    private void initData() {

    }

    private void initView() {
        image = (ImageView) findViewById(R.id.picture);
        text = (TextView) findViewById(R.id.text);
        cb_fastBlur = (CheckBox) findViewById(R.id.main_cb_fastblur);
        statusText = addStatusText((ViewGroup) findViewById(R.id.controls));
        cb_fastBlur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long startMs = System.currentTimeMillis();
                if (isChecked) {
                    //设置高斯模糊
                    Fglass.blur(image, text, 2, 8);
                } else {
                    text.setBackgroundColor(Color.parseColor("#00ffffff"));
                }
                statusText.setText(System.currentTimeMillis() - startMs + "ms");
            }
        });
    }

    private TextView addStatusText(ViewGroup container) {
        TextView result = new TextView(this);
        result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        result.setTextColor(0xFFFFFFFF);
        container.addView(result);
        return result;
    }

}
