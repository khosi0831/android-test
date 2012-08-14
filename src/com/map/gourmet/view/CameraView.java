package com.map.gourmet.view;

import java.io.FileOutputStream;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {
	private SurfaceHolder holder; //ホルダー
	private Camera camera; //カメラ
	public  static String path = "";
	public Context _context;
	public CameraView(Context context) {
		super(context);
		
		//サーフェイスフォルダの作成
		holder = getHolder();
		holder.addCallback(this);
		
		//プッシュバッファの指定
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	}
	//サーフェイス生成イベントの初期化
	public void surfaceCreated(SurfaceHolder holder) {
		//カメラの初期化
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
		} catch(Exception e) {
		}
	}
	
	//サーフェイス変更イベントの処理
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		//カメラプレビューの開始
		camera.startPreview();
	}
	
	//サーフェイス解放イベントの処理
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//カメラプレビューの停止
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	
	//タッチ時によばれる
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//カメラのスクリーンショットの取得
			camera.takePicture(null, null, this);
		}
		return false;
	}
	//写真撮影完了時に呼ばれる
	public void onPictureTaken(byte[] data, Camera camera) {
		//SDカードへのデータ保存
		try {
			path = Environment.getExternalStorageDirectory() + "/test.jpeg";
			data2file(data, path);
			
		}catch(Exception e) {
			
		}
	}
	
	//バイトデータ→ファイル
	private void data2file(byte[] w, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(w);
			out.close();
		} catch(Exception e) {
			if(out!=null) {
				out.close();
			}
			throw e;
		}
	}
}
