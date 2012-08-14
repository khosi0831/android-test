package com.map.gourmet.activity;

import com.map.gourmet.view.CameraView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class CameraActivity extends BaseActivity {
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	CameraView cameraView = null;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		//フルスクリーンの指定
//		getWindow().clearFlags(
//				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //レイアウトの作成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255, 255, 255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        Button okBtn = new Button(this);
        okBtn.setText("OK");
        okBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        okBtn.setTag("ok");
        layout.addView(okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View v) {
	    		//戻り値の指定
	    		Intent intent = new Intent();
	    		intent.putExtra("test", CameraView.path);
	    		setResult(Activity.RESULT_OK, intent);
	    		//アクティビティ終了
	    		finish();
	      }
	  });
        
        CameraView cameraView = new CameraView(this);
        cameraView.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        cameraView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                		//戻り値の指定
                		Intent intent = new Intent();
                		intent.putExtra("text", CameraView.path);
                		setResult(Activity.RESULT_OK, intent);
                		//アクティビティ終了
                		finish();
                    }
                });
        
        layout.addView(cameraView);        
//        setContentView(new CameraView(this));

        
        
	}
    public void onItemClick(View view, int pos, long id) {
    }

}
