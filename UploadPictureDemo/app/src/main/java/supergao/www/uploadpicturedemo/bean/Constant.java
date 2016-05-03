package supergao.www.uploadpicturedemo.bean;

import android.os.Environment;

/**
 *定义常量
 *@author superGao
 *creat at 2016/3/22
 */
public class Constant {

	public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static final String APPS = "/com.d3rich.trafficbiz/";

	public static final String PATH = ROOT + APPS;
	
	/** 拍照头像的文件名 */
	public static String avatarFileName;
}
