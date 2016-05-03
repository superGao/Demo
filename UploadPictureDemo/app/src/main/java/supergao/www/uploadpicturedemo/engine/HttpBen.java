package supergao.www.uploadpicturedemo.engine;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import supergao.www.uploadpicturedemo.api.DefaultApiCallbackListener;
import supergao.www.uploadpicturedemo.bean.LogisticsInfo;
import supergao.www.uploadpicturedemo.core.HttpAction;
import supergao.www.uploadpicturedemo.listener.ActionCallbackListener;
import supergao.www.uploadpicturedemo.listener.ApiResponse;
import supergao.www.uploadpicturedemo.utils.BitmapUtils;

/**
 * 网络接口实现类
 * author：superGao on 2016/3/22.
 * note：借用请注明来源，侵权必究！
 */
public class HttpBen implements HttpAction{

    @Override
    public void uploadPic(Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, ActionCallbackListener<LogisticsInfo> callbackListener) {
        Map<String, Object> params = new HashMap<String ,Object>() ;
        if (null != bitmap1) {
            params.put("bitmap1", BitmapUtils.getInputStream(bitmap1)) ;
        }

        if (null != bitmap2) {
            params.put("bitmap2", BitmapUtils.getInputStream(bitmap2)) ;
        }
        if (null != bitmap3) {
            params.put("bitmap3", BitmapUtils.getInputStream(bitmap3)) ;
        }

        if (null != bitmap4) {
            params.put("bitmap4", BitmapUtils.getInputStream(bitmap4)) ;
        }
        if (null != bitmap5) {
            params.put("bitmap5", BitmapUtils.getInputStream(bitmap5)) ;
        }
        if (null != bitmap6) {
            params.put("bitmap6", BitmapUtils.getInputStream(bitmap6)) ;
        }

        HttpEngine.post(Api.Logistics.PERFECTINFO, params, new DefaultApiCallbackListener<LogisticsInfo>(callbackListener), new TypeToken<ApiResponse<LogisticsInfo>>() {
        }.getType());
    }
}
