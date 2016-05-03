package supergao.www.uploadpicturedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import java.io.File;

import supergao.www.uploadpicturedemo.api.DefaultApiCallbackListener;
import supergao.www.uploadpicturedemo.bean.Constant;
import supergao.www.uploadpicturedemo.bean.LogisticsInfo;
import supergao.www.uploadpicturedemo.core.HttpAction;
import supergao.www.uploadpicturedemo.engine.Api;
import supergao.www.uploadpicturedemo.engine.HttpBen;
import supergao.www.uploadpicturedemo.listener.DefaultActionCallbackListener;
import supergao.www.uploadpicturedemo.utils.BitmapUtils;
import supergao.www.uploadpicturedemo.utils.CameraPop;
import supergao.www.uploadpicturedemo.utils.LogUtils;
import supergao.www.uploadpicturedemo.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_pic1,iv_pic2,iv_pic3,iv_pic4,iv_pic5,iv_pic6;
    private Button btn_submit;
    private HttpAction httpAction;
    /**
     * 点击图片的id
     */
    private int selectPicId = 0;
    private Bitmap pic1Bitmap;
    private Bitmap pic2Bitmap;
    private Bitmap pic3Bitmap;
    private Bitmap pic4Bitmap;
    private Bitmap pic5Bitmap;
    private Bitmap pic6Bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        httpAction=new HttpBen();
    }

    private void initView(){
        iv_pic1=(ImageView)findViewById(R.id.iv_pic1);
        iv_pic2=(ImageView)findViewById(R.id.iv_pic2);
        iv_pic3=(ImageView)findViewById(R.id.iv_pic3);
        iv_pic4=(ImageView)findViewById(R.id.iv_pic4);
        iv_pic5=(ImageView)findViewById(R.id.iv_pic5);
        iv_pic6=(ImageView)findViewById(R.id.iv_pic6);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        iv_pic1.setOnClickListener(this);
        iv_pic2.setOnClickListener(this);
        iv_pic3.setOnClickListener(this);
        iv_pic4.setOnClickListener(this);
        iv_pic5.setOnClickListener(this);
        iv_pic6.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        CameraPop cameraPop;
        switch (view.getId()){
            case R.id.iv_pic1:
                selectPicId = R.id.iv_pic1 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.iv_pic2:
                selectPicId = R.id.iv_pic2 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.iv_pic3:
                selectPicId = R.id.iv_pic3 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.iv_pic4:
                selectPicId = R.id.iv_pic4 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.iv_pic5:
                selectPicId = R.id.iv_pic5 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.iv_pic6:
                selectPicId = R.id.iv_pic6 ;
                cameraPop=new CameraPop(MainActivity.this,view);
                break;
            case R.id.btn_submit:
                //使用BitmapUtils.getInputStream(bitmap)获取图片的输入流然后上传到服务器
                httpAction.uploadPic(pic1Bitmap,pic2Bitmap,pic3Bitmap,pic4Bitmap,pic5Bitmap,pic6Bitmap,new DefaultActionCallbackListener<LogisticsInfo>(this) {
                    @Override
                    public void onSuccess(LogisticsInfo data) {     //上传成功
                        LogUtils.d(JSON.toJSONString(data));

                    }

                    @Override
                    public void onAfterFailure() {
                    }
                });
                ToastUtil.showShort(this,"上传图片");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: // 直接从相册获取
                if (data != null) {
                    startPhotoZoomActivityForResult(data.getData()) ;
                }
                break;
            case 2:  // 调用相机拍照时
                File temp = new File(Constant.PATH, Constant.avatarFileName);
//                File file = new File("1212.jpg");
                if (temp.exists()) {
                    startPhotoZoomActivityForResult(Uri.fromFile(temp)) ;
                }
                break;

            case 3: // 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case 5:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用裁剪图片的activity
     * @param uri 要裁剪图片的uri描述对象
     */
    protected void startPhotoZoomActivityForResult(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photoBitmap = extras.getParcelable("data");

            ImageView imgSelect = null ;
            switch (selectPicId) {
                case R.id.iv_pic1: {
                    imgSelect = iv_pic1 ;
                    pic1Bitmap = photoBitmap ;
                    break;
                }
                case R.id.iv_pic2: {
                    imgSelect = iv_pic2 ;
                    pic2Bitmap = photoBitmap ;
                    break;
                }
                case R.id.iv_pic3: {
                    imgSelect = iv_pic3 ;
                    pic3Bitmap = photoBitmap ;
                    break;
                }
                case R.id.iv_pic4: {
                    imgSelect = iv_pic4 ;
                    pic4Bitmap = photoBitmap ;
                    break;
                }
                case R.id.iv_pic5: {
                    imgSelect = iv_pic5 ;
                    pic5Bitmap = photoBitmap ;
                    break;
                }
                case R.id.iv_pic6: {
                    imgSelect = iv_pic6 ;
                    pic6Bitmap = photoBitmap ;
                    break;
                }
            }

            BitmapDrawable drawable = new BitmapDrawable(photoBitmap);
            imgSelect.setImageDrawable(drawable);
        }
    }
}
