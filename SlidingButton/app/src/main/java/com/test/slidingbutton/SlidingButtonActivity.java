package com.test.slidingbutton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SlidingButtonActivity extends Activity {
    
	private SlidingButton mSlidingButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSlidingButton = (SlidingButton)this.findViewById(R.id.mainview_answer_1_button);
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("SlidingButtonActivity", "收到事件："+event.getAction());
    	if (mSlidingButton.handleActivityEvent(event)) {
			Toast.makeText(SlidingButtonActivity.this, "touch", Toast.LENGTH_SHORT).show();
		}
    	
    	return super.onTouchEvent(event);
    }
    
}