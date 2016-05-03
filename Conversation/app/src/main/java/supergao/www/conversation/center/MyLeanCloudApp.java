package supergao.www.conversation.center;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

/**
 * Created by Administrator on 2016/3/7.
 */
public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "m7baukzusy3l5coew0b3em5uf4df5i2krky0ypbmee358yon",
                "2e46velw0mqrq3hl2a047yjtpxn32frm0m253k258xo63ft9");

        //注册默认的消息处理逻辑
        //AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());

        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
    }

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.d("Tom & Jerry", ((AVIMTextMessage) message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }
    public void jerryReceiveMsgFromTom() {
        //Jerry登录
        AVIMClient jerry = AVIMClient.getInstance("Jerry");
        jerry.open(new AVIMClientCallback() {

            @Override
            public void done(AVIMClient client, AVIMException e) {
                if(e==null){
                    //登录成功后的逻辑
                }
            }
        });
    }
}
