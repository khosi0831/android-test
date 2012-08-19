package com.map.gourmet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	private EditText userId;
	private EditText passwd;
	private Button okBtn;
	private Button cancelBtn;
	private Button newBtn;
	
	private final static int REQUEST_TEXT = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.test = "bbb";
        
        //���C�A�E�g�̍쐬
        LinearLayout layout = new LinearLayout(this);
//        layout.setBackgroundColor(Color.rgb(255, 255, 255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        userId=new EditText(this);
        userId.setText("userId", EditText.BufferType.NORMAL);
        userId.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
        layout.addView(userId);
        
        passwd=new EditText(this);
        passwd.setText("passwd", EditText.BufferType.NORMAL);
        passwd.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
        layout.addView(passwd);
        
        //�nK�{�^���̐���
        okBtn = new Button(this);
        okBtn.setText("OK");
        okBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        okBtn.setOnClickListener(this);
        okBtn.setTag("ok");
        layout.addView(okBtn);
        
        //CANCEL�{�^���̐���
        cancelBtn = new Button(this);
        cancelBtn.setText("CANCEL");
        cancelBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC+100));
        cancelBtn.setOnClickListener(this);
        cancelBtn.setTag("cancel");
        layout.addView(cancelBtn);
        
        //���[�U�쐬�{�^���̐���
        newBtn = new Button(this);
        newBtn.setText("�V�K�쐬");
        newBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC+100));
        newBtn.setOnClickListener(this);
        newBtn.setTag("new");
        layout.addView(newBtn);

    }
    
    public void onClick(View v) {
    	try {
	    	String tag = (String)v.getTag();
	    	if ( tag.equals("new") ) {
	    		Intent intent = new Intent(this, com.map.gourmet.activity.CreateUserActivity.class);
	    		intent.putExtra("text", "param1");
	    		startActivityForResult(intent, REQUEST_TEXT);
	    	}
	    	if ( tag.equals("ok") ) {
	    		if ( httpTransfar(userId.getText().toString(), passwd.getText().toString())) {
		    		Intent intent = new Intent(this, com.map.gourmet.activity.ShopEditActivity.class);
		    		intent.putExtra("text", "param1");
		    		startActivityForResult(intent, REQUEST_TEXT);
	    		}
	    	}
    	} catch (Exception e) {
    		
    	}
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if ( requestCode == REQUEST_TEXT && resultCode == RESULT_OK) {
    		String text = "";
    		Bundle extras = intent.getExtras();
    		if ( extras != null) text = extras.getString("test");
    		showDialog(this, "title", text);
    	}
    }
    
    public static void showDialog(Context context, String titile, String message) {
    	AlertDialog.Builder ad = new AlertDialog.Builder(context);
    	ad.setTitle(titile);
    	ad.setMessage(message);
    	ad.setPositiveButton("OK", null);
    	ad.show();
    	
    }
}