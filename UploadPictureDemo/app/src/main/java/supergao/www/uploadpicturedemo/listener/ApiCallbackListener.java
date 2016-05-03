package supergao.www.uploadpicturedemo.listener;


/**
 *Api端回调监听接口
 *@author superGao
 *creat at 2016/3/22
 */
public interface ApiCallbackListener<T> {
    /**
     * api访问回调方法
     * @param obj 获取到的对象
     */
    public void onResult(ApiResponse<T> obj) ;
}
