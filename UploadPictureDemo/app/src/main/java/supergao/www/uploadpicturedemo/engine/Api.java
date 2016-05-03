package supergao.www.uploadpicturedemo.engine;

/**
 *远程服务器api接口
 *@author superGao
 *creat at 2016/3/22
 */
public interface Api {
    String APP_KEY = "ANDROID_KCOUPON";
    String TIME_OUT_CODE = "-1";
    String TIME_OUT_CODE_MSG = "连接服务器失败";
    /**
     * JSON 字符串解析错误
     */
    String PARSE_RESPONSE_DATA_ERROR_CODE = "-11" ;
    String PARSE_RESPONSE_DATA_ERROR_CODE_MSG = "解析服务器响应数据失败" ;

    /**
     * 物流公司接口
     */
    interface Logistics {
        // 上传图片
        String PERFECTINFO = "/logistics/addlogistics" ;
    }

}
