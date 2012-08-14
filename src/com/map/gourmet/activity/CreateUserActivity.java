package com.map.gourmet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public  class CreateUserActivity  extends BaseActivity implements View.OnClickListener{
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	private final static int REQUEST_TEXT = 0;
	private Button okBtn;
	private Button cancelBtn;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //戻り値の指定
        setResult(Activity.RESULT_CANCELED);
        
        //インテントからパラメータ取得
        String text = "";
        Bundle extras = getIntent().getExtras();
        if ( extras != null) {
        	text = extras.getString("text");
        }
        //レイアウトの作成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255, 255, 255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        
        
        okBtn = new Button(this);
        okBtn.setText("OK");
        okBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        okBtn.setOnClickListener(this);
        okBtn.setTag("ok");
        layout.addView(okBtn);
        
        cancelBtn = new Button(this);
        cancelBtn.setText("CANCEL");
        cancelBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        cancelBtn.setOnClickListener(this);
        cancelBtn.setTag("cancel");
        layout.addView(cancelBtn);

    }
	
	public void onClick(View v) {
    	String tag = (String)v.getTag();
    	if ( tag.equals("ok") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.ShopEditActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	}
		//戻り値の指定
		Intent intent = new Intent();
		intent.putExtra("text", "param2");
		setResult(Activity.RESULT_OK, intent);
		//アクティビティ終了
		finish();
	}
   protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if ( requestCode == REQUEST_TEXT && resultCode == RESULT_OK) {
    		String text = "";
    		Bundle extras = intent.getExtras();
    		if ( extras != null) text = extras.getString("test");
    	}
    }

}
