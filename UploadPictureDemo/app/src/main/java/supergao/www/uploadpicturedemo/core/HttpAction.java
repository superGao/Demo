package supergao.www.uploadpicturedemo.core;

import android.graphics.Bitmap;

import supergao.www.uploadpicturedemo.bean.LogisticsInfo;
import supergao.www.uploadpicturedemo.listener.ActionCallbackListener;

/**
 * 定义网络接口
 * author：superGao on 2016/3/22.
 * note：借用请注明来源，侵权必究！
 */
public interface HttpAction {

    /**
     * 批量上传图片
     * @param bitmap1
     * @param bitmap2
     * @param bitmap3
     * @param bitmap4
     * @param bitmap5
     * @param bitmap6
     * @param callbackListener
     */
    void uploadPic(Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, ActionCallbackListener<LogisticsInfo> callbackListener) ;
}
