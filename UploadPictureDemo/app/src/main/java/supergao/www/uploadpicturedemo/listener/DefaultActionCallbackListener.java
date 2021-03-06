package supergao.www.uploadpicturedemo.listener;

import android.content.Context;

import supergao.www.uploadpicturedemo.utils.LogUtils;
import supergao.www.uploadpicturedemo.utils.ToastUtil;

/**
 * AppAction调用回调接口
 * @param <T>
 */
public abstract class DefaultActionCallbackListener<T> implements ActionCallbackListener<T> {

    protected Context mContext ;

    public DefaultActionCallbackListener(Context context) {
        this.mContext = context ;
    }




    /**
     * 失败时调用
     * @param code 错误码
     * @param message 错误信息
     */
    public void onFailure(String code, String message) {
        LogUtils.w("FAILURE: code: " + code + ", msg: " + message);
        if (null != mContext) {
            ToastUtil.showLong(mContext, message);
        }
        onAfterFailure();
    }

    /**
     * onFailure处理完毕后的事件
     */
    public void onAfterFailure() {

    }
}