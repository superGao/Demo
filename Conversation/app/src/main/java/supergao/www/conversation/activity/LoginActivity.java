package supergao.www.conversation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;


import butterknife.Bind;
import butterknife.OnClick;
import supergao.www.conversation.R;
import supergao.www.conversation.center.AVBaseActivity;
import supergao.www.conversation.center.AVImClientManager;
import supergao.www.conversation.center.Constants;

/**
 * Created by wli on 15/8/13.
 * 登陆页面，暂时未做自动登陆，每次重新进入都要再登陆一次
 */
public class LoginActivity extends AVBaseActivity {

    /**
     * 此处 xml 里限制了长度为 30，汉字算一个
     */
    @Bind(R.id.edt_name)
    protected EditText userNameView;

    @Bind(R.id.btn_login)
    protected Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick(View view) {
        openClient(userNameView.getText().toString().trim());
    }

    private void openClient(String selfId) {
        if (TextUtils.isEmpty(selfId)) {
            showToast(R.string.login_null_name_tip);
            return;
        }

        loginButton.setEnabled(false);
        userNameView.setEnabled(false);
        AVImClientManager.getInstance().open(selfId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                Log.d("login","登录成功");
                loginButton.setEnabled(true);
                userNameView.setEnabled(true);
                if (filterException(e)) {
                    Intent intent = new Intent(LoginActivity.this, AVSquareActivity.class);
                    intent.putExtra(Constants.CONVERSATION_ID, Constants.SQUARE_CONVERSATION_ID);
                    intent.putExtra(Constants.ACTIVITY_TITLE, getString(R.string.square_name));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

