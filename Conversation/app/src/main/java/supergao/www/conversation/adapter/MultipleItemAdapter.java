package supergao.www.conversation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import supergao.www.conversation.center.AVImClientManager;
import supergao.www.conversation.event.MemberLetterEvent;
import supergao.www.conversation.viewholder.AVCommonViewHolder;
import supergao.www.conversation.viewholder.LeftTextHolder;
import supergao.www.conversation.viewholder.RightTextHolder;

/**
 * Created by wli on 15/8/13.
 * 聊天的 Adapter，此处还有可优化的地方，稍后考虑一下提取出公共的 adapter
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final int ITEM_LEFT_TEXT = 0;
  private final int ITEM_RIGHT_TEXT = 1;

  // 时间间隔最小为十分钟
  private final long TIME_INTERVAL = 10 * 60 * 1000;

  private List<AVIMMessage> messageList = new ArrayList<AVIMMessage>();

  public MultipleItemAdapter() {
  }

  public void setMessageList(List<AVIMMessage> messages) {
    messageList.clear();
    if (null != messages) {
      messageList.addAll(messages);
    }
  }

  public void addMessageList(List<AVIMMessage> messages) {
    messageList.addAll(0, messages);
  }

  public void addMessage(AVIMMessage message) {
    messageList.addAll(Arrays.asList(message));
  }

  public AVIMMessage getFirstMessage() {
    if (null != messageList && messageList.size() > 0) {
      return messageList.get(0);
    } else {
      return null;
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ITEM_LEFT_TEXT) {
      return new LeftTextHolder(parent.getContext(), parent);
    } else if (viewType == ITEM_RIGHT_TEXT) {
      return new RightTextHolder(parent.getContext(), parent);
    } else {
      //TODO
      return null;
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((AVCommonViewHolder)holder).bindData(messageList.get(position));
    if (holder instanceof LeftTextHolder) {
      ((LeftTextHolder)holder).showTimeView(shouldShowTime(position));
    } else if (holder instanceof RightTextHolder) {
      ((RightTextHolder)holder).showTimeView(shouldShowTime(position));
    }
  }

  @Override
  public int getItemViewType(int position) {
    AVIMMessage message = messageList.get(position);
    if (message.getFrom().equals(AVImClientManager.getInstance().getClientId())) {
      return ITEM_RIGHT_TEXT;
    } else {
      return ITEM_LEFT_TEXT;
    }
  }

  @Override
  public int getItemCount() {
    return messageList.size();
  }

  private boolean shouldShowTime(int position) {
    if (position == 0) {
      return true;
    }
    long lastTime = messageList.get(position - 1).getTimestamp();
    long curTime = messageList.get(position).getTimestamp();
    return curTime - lastTime > TIME_INTERVAL;
  }

  /**
   * Created by wli on 15/8/24.
   * 联系人列表，快速滑动字母导航 View
   * 此处仅在滑动或点击时发送 MemberLetterEvent，接收放自己处理相关逻辑
   * 注意：因为长按事件等触发，有可能重复发送
   */
  public static class LetterView extends LinearLayout {

    public LetterView(Context context) {
      super(context);
      setOrientation(VERTICAL);
      updateLetters();
    }

    public LetterView(Context context, AttributeSet attrs) {
      super(context, attrs);
      setOrientation(VERTICAL);
      updateLetters();
    }

    private void updateLetters() {
      setLetters(getSortLetters());
    }

    /**
     * 设置快速滑动的字母集合
     */
    public void setLetters(List<Character> letters) {
      removeAllViews();
      for(Character content : letters) {
        TextView view = new TextView(getContext());
        view.setText(content.toString());
        addView(view);
      }

      setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          int x = Math.round(event.getX());
          int y = Math.round(event.getY());
          for (int i = 0; i < getChildCount(); i++) {
            TextView child = (TextView) getChildAt(i);
            if (y > child.getTop() && y < child.getBottom()) {
              MemberLetterEvent letterEvent = new MemberLetterEvent();
              letterEvent.letter = child.getText().toString().charAt(0);
              EventBus.getDefault().post(letterEvent);
            }
          }
          return true;
        }
      });
    }

    /**
     * 默认的只包含 A-Z 的字母
     */
    private List<Character> getSortLetters() {
      List<Character> letterList = new ArrayList<Character>();
      for (char c = 'A'; c <= 'Z'; c++) {
        letterList.add(c);
      }
      return letterList;
    }
  }
}