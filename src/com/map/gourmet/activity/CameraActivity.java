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
		//�t���X�N���[���̎w��
//		getWindow().clearFlags(
//				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //���C�A�E�g�̍쐬
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
	    		//�߂�l�̎w��
	    		Intent intent = new Intent();
	    		intent.putExtra("test", CameraView.path);
	    		setResult(Activity.RESULT_OK, intent);
	    		//�A�N�e�B�r�e�B�I��
	    		finish();
	      }
	  });
        
        CameraView cameraView = new CameraView(this);
        cameraView.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        cameraView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                		//�߂�l�̎w��
                		Intent intent = new Intent();
                		intent.putExtra("text", CameraView.path);
                		setResult(Activity.RESULT_OK, intent);
                		//�A�N�e�B�r�e�B�I��
                		finish();
                    }
                });
        
        layout.addView(cameraView);        
//        setContentView(new CameraView(this));

        
        
	}
    public void onItemClick(View view, int pos, long id) {
    }

}
