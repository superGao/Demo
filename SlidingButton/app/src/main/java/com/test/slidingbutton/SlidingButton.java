package com.test.slidingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SlidingButton extends Button {
	public SlidingButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SlidingButton(Context context) {
		super(context);
	}

	private boolean isMe = false;

	public boolean isMe() {
		return isMe;
	}

	public void setMe(boolean isMe) {
		this.isMe = isMe;
	}

	public SlidingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.e("SlidingButton--dispatchTouchEvent", "收到事件：" + event.getAction());
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			isMe = true;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("SlidingButton--onTouchEvent", "收到事件：" + event.getAction());
		handleActivityEvent(event);
		//return super.onTouchEvent(event);//不消费事件，这个事件会从当前 View 向上传递，并且都是由上层 View 的 onTouchEvent 来接收，
		// 如果传递到上面的 onTouchEvent 也返回 false，这个事件就会“消失”，而且接收不到下一次事件。
		return true;
	}

	public boolean handleActivityEvent(MotionEvent activityEvent) {
		boolean result = false;
		Log.e("handleActivityEvent", "判断操作，isMe:  "+isMe());
		if (isMe()) {
			if (activityEvent.getAction() == MotionEvent.ACTION_UP) {
//				Log.v("yupeng", "frame left" + ((FrameLayout)this.getParent().getParent()).getLeft());
//				Log.v("yupeng", "my left" + this.getLeft());
//				Log.v("yupeng", "touch " + this.getLeft());


				if(this.getLeft() + this.getWidth()/2 > ((FrameLayout)this.getParent().getParent()).getWidth() - this.getWidth()){
					//拖动完成
					Log.e("handleActivityEvent", "拖动完成");
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)this.getLayoutParams();
					lp.leftMargin = 0;
					this.setLayoutParams(lp);
					this.setMe(false);
					result = true;
				}else{
					Log.e("handleActivityEvent", "恢复原始状态");
					TranslateAnimation trans =
				              new TranslateAnimation(
				            		  Animation.ABSOLUTE, 0, 
									  Animation.ABSOLUTE,-this.getLeft(), Animation.RELATIVE_TO_SELF, 0,
									  Animation.RELATIVE_TO_SELF, 0);
					
//					trans.setStartOffset(0);
					trans.setDuration(600);
//					trans.setFillAfter(true);
					trans.setInterpolator(new AccelerateInterpolator());
					trans.setInterpolator(new Interpolator() {

						@Override
						public float getInterpolation(float input) {
							// TODO Auto-generated method stub
							return 0;
						}
					});
					trans.setAnimationListener(new SlidingAnimationListener(this));
					startAnimation(trans);
				}
			} else if(activityEvent.getAction() == MotionEvent.ACTION_MOVE){
				//还在拖动
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
				Log.e("handleActivityEvent","原始左边距 "+lp.leftMargin);
				lp.leftMargin = (int) (activityEvent.getX() - ((FrameLayout)this.getParent().getParent()).getLeft()) - this.getWidth()/2;
				Log.e("handleActivityEvent","当前左边距 "+lp.leftMargin);
				if (lp.leftMargin > 0 && lp.leftMargin < ((FrameLayout)this.getParent().getParent()).getWidth() - this.getWidth()) {
					setLayoutParams(lp);
					Log.e("handleActivityEvent", "设置左边距");
				}
			}
		}
		return result;
	}
	
	private static class SlidingAnimationListener implements AnimationListener {

		private SlidingButton but;

		public SlidingAnimationListener(SlidingButton button) {
			this.but = button;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			rePosition();
			but.setMe(false);
			but.clearAnimation();
		}

		private void rePosition() {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) but
					.getLayoutParams();
			lp.leftMargin = 0;
			but.setLayoutParams(lp);
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

	}
	
}
