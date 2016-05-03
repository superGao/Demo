package com.test.slidingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * author：superGao on 2016/4/1.
 * note：借用请注明来源，侵权必究！
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Touch 事件发生时 Activity 的 dispatchTouchEvent(MotionEvent ev) 方法会以隧道方式
     * （从根元素依次往下传递直到最内层子元素或在中间某一元素中由于某一条件停止传递）将事件传递给最外层 View 的
     * dispatchTouchEvent(MotionEvent ev) 方法，并由该 View 的 dispatchTouchEvent(MotionEvent ev) 方法对事件进行分发。
     * dispatchTouchEvent 的事件分发逻辑如下：

     1、如果 return true，事件会分发给当前 View 并由 dispatchTouchEvent 方法进行消费，同时事件会停止向下传递；

     2、如果 return false，事件分发分为两种情况：

     1）如果当前 View 获取的事件直接来自 Activity，则会将事件返回给 Activity 的 onTouchEvent 进行消费；

     2）如果当前 View 获取的事件来自外层父控件，则会将事件返回给父 View 的 onTouchEvent 进行消费。

     3、如果返回系统默认的 super.dispatchTouchEvent(ev)，事件会自动的分发给当前 View 的 onInterceptTouchEvent 方法。
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("MyScrollView--dispatchTouchEvent", "收到事件 ：" + ev.getAction());
        /*if(ev.getAction()==2){//移动
            return false;
        }else{
            return super.dispatchTouchEvent(ev);
        }*/
        return super.dispatchTouchEvent(ev);
    }

    /**
     * ViewGroup特有的
     * 在外层 View 的 dispatchTouchEvent(MotionEvent ev) 方法返回系统默认的 super.dispatchTouchEvent(ev) 情况下，
     * 事件会自动的分发给当前 View 的 onInterceptTouchEvent 方法。onInterceptTouchEvent 的事件拦截逻辑如下：

     1、如果 onInterceptTouchEvent 返回 true，则表示将事件进行拦截，并将拦截到的事件交由当前 View 的 onTouchEvent 进行处理；

     2、如果 onInterceptTouchEvent 返回 false，则表示将事件放行，当前 View 上的事件会被传递到子 View 上，
     再由子 View 的 dispatchTouchEvent 来开始这个事件的分发；

     3、如果 onInterceptTouchEvent 返回 super.onInterceptTouchEvent(ev)，事件默认不会被拦截。
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("MyScrollView--onInterceptTouchEvent", "收到事件 ：" + ev.getAction());
        /*if(ev.getAction()==MotionEvent.ACTION_MOVE){
            return true;
        }else{
            return super.onInterceptTouchEvent(ev);
        }*/
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 在 dispatchTouchEvent 返回 super.dispatchTouchEvent(ev) 并且 onInterceptTouchEvent 返回 true
     * 或返回 super.onInterceptTouchEvent(ev) 的情况下 onTouchEvent 会被调用。onTouchEvent 的事件响应逻辑如下：

     1、如果事件传递到当前 View 的 onTouchEvent 方法，而该方法返回了 false，那么这个事件会从当前 View 向上传递，
     并且都是由上层 View 的 onTouchEvent 来接收，如果传递到上面的 onTouchEvent 也返回 false，这个事件就会“消失”，而且接收不到下一次事件。

     2、如果返回了 true 则会接收并消费该事件。

     3、如果返回 super.onTouchEvent(ev) 默认处理事件的逻辑和返回 false 时相同。
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("MyScrollView--onTouchEvent", "收到事件 ：" + ev.getAction());
        /*if(ev.getAction()==MotionEvent.ACTION_MOVE){
            return false;//不消费事件，将事件向上传递
        }else{
            return super.onTouchEvent(ev);
        }*/
        return super.onTouchEvent(ev);
    }
}
