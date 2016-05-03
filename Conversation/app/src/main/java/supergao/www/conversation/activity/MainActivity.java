package supergao.www.conversation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import supergao.www.conversation.R;
import supergao.www.conversation.center.AVBaseActivity;
import supergao.www.conversation.center.AVImClientManager;
import supergao.www.conversation.center.Constants;
import supergao.www.conversation.event.LeftChatItemClickEvent;
import supergao.www.conversation.fragment.ChatFragment;

public class MainActivity extends AVBaseActivity {
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    protected ChatFragment chatFragment;

    /**
     * 上一次点击 back 键的时间
     * 用于双击退出的判断
     */
    private static long lastBackTime = 0;

    /**
     * 当双击 back 键在此间隔内是直接触发 onBackPressed
     */
    private final int BACK_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatFragment = (ChatFragment)getFragmentManager().findFragmentById(R.id.fragment_chat);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String memberId = getIntent().getStringExtra(Constants.MEMBER_ID);
        setTitle(memberId);
        getConversation(memberId);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (null != extras && extras.containsKey(Constants.MEMBER_ID)) {
            String memberId = extras.getString(Constants.MEMBER_ID);
            setTitle(memberId);
            getConversation(memberId);
        }
    }

    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        final AVIMClient client = AVImClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(memberId), true);
        conversationQuery.whereEqualTo("customConversationType", 1);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        chatFragment.setConversation(list.get(0));
                    } else {
                        HashMap<String,Object> attributes = new HashMap<String, Object>();
                        attributes.put("customConversationType", 1);
                        client.createConversation(Arrays.asList(memberId), null, attributes, false, new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                chatFragment.setConversation(avimConversation);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime < BACK_INTERVAL) {
            super.onBackPressed();
        } else {
            showToast("双击 back 退出");
        }
        lastBackTime = currentTime;
    }

    /**
     * 处理聊天 item 点击事件，点击后跳转到相应1对1的对话
     */
    public void onEvent(LeftChatItemClickEvent event) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.MEMBER_ID, event.userId);
        startActivity(intent);
    }

}
