package com.zwh.photoframe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class ClassActivity extends Activity {

	public static String AVATAR_FILE_NAME = "class.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
				"image/*");
		startActivityForResult(intent, 1);
		super.onCreate(savedInstanceState);
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		switch (requestCode) {  
		case 1:  
			// 如果是直接从相册获取  
			if( data != null ){  
				startPhotoZoom(data.getData());
			}  
			break;  

		case 3:  
			// 取得裁剪后的图片  
			if(data != null){
				setPicToView(data);  
			}  
			break;  
		default:  
			break;  

		}  
		super.onActivityResult(requestCode, resultCode, data);  
	}
	/**  
	 * 裁剪图片方法实现  
	 * @param uri  
	 */
	public void startPhotoZoom(Uri uri) {  
		Intent intent = new Intent("com.android.camera.action.CROP");  
		intent.setDataAndType(uri, "image/*");  
		//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪  
		intent.putExtra("crop", "true");  
		// aspectX aspectY 是宽高的比例  
		intent.putExtra("aspectX", 2);  
		intent.putExtra("aspectY", 2);  
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
		Bitmap avatarBitmap = null;
		if (extras != null) {  
			avatarBitmap = extras.getParcelable("data");
			FileUtils fileUtils = new FileUtils();
			//fileUtils.savePhotoBitmap(avatarBitmap, AVATAR_FILE_NAME);	
			fileUtils.saveFile(avatarBitmap, AVATAR_FILE_NAME);	
			Intent intent = new Intent(ClassAppWidgetProvider.ACTION_APPWIDGET_ONCLICK);
			sendBroadcast(intent);
		}
		this.finish();
	}
}
