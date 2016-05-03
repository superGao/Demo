package supergao.www.uploadpicturedemo.api;


import supergao.www.uploadpicturedemo.listener.AbstractApiCallbackListener;
import supergao.www.uploadpicturedemo.listener.ActionCallbackListener;
import supergao.www.uploadpicturedemo.listener.ApiResponse;

/**
 *ApiCallbackListener 默认实现类
 *@author superGao
 *creat at 2016/3/22
 */
public class DefaultApiCallbackListener<T> extends AbstractApiCallbackListener<T> {
    private ActionCallbackListener<T> callbackListener ;

    public DefaultApiCallbackListener(ActionCallbackListener<T> callbackListener) {
        this.callbackListener = callbackListener ;
    }



    @Override
    public void onResult(ApiResponse<T> obj) {
        if (obj.isSuccess()) {
            callbackListener.onSuccess(obj.getData());
        } else {
            callbackListener.onFailure(obj.getCode(), obj.getMsg());
        }
    }
}