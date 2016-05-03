package com.finddreams.runningman;

import com.example.runningman.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
/**
 *
 *@author superGao
 *creat at 2016/4/29
 */
public class RuningManActivity extends Activity {

	CustomProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_runing_man);
	}
	/**
	 * 显示美团进度对话框
	 * @param v
	 */
	public void showmeidialog(View v){
		new CustomProgressDialog(this, "正在加载中",R.drawable.frame_meituan).show();
	}
	/**
	 * 显示顺丰快递员进度对话框
	 * @param v
	 */
	public void showsfdialog(View v){
		new CustomProgressDialog(this, "正在加载中",R.drawable.frame_shunfeng).show();

	}

	public void showBone(View v){
		new CustomProgressDialog(this,"别急",R.drawable.frame_bone).show();
	}

	public void showhaha(View v){
		new CustomProgressDialog(this,"我正在玩儿命加载。。。",R.drawable.frame_haha).show();
	}
}
