package com.map.gourmet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	private EditText userId;
	private EditText passwd;
	private Button okBtn;
	private Button cancelBtn;
	private Button newBtn;
	
	private final static int REQUEST_TEXT = 0;
    private InputFilter[] filters = { new MyFilter() };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.test = "bbb";
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        setContentView(tableLayout, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        TableRow tableRow1 = new TableRow(this);
        userId=new EditText(this);
        userId.setText("userId", EditText.BufferType.NORMAL);
//        userId.setFilters(filters);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams();
        rowLayout.span = 2;
        tableRow1.addView(userId,rowLayout);
        
        TableRow tableRow2 = new TableRow(this);
        passwd=new EditText(this);
        passwd.setText("passwd", EditText.BufferType.NORMAL);
        passwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tableRow2.addView(passwd, rowLayout);
        
        TableRow tableRow3 = new TableRow(this);
        //ＯKボタンの生成
        okBtn = new Button(this);
        okBtn.setText("OK");
        okBtn.setOnClickListener(this);
        okBtn.setTag("ok");
        tableRow3.addView(okBtn);
        
        //CANCELボタンの生成
        cancelBtn = new Button(this);
        cancelBtn.setText("CANCEL");
        cancelBtn.setOnClickListener(this);
        cancelBtn.setTag("cancel");
        tableRow3.addView(cancelBtn);
        
        //ユーザ作成ボタンの生成
        newBtn = new Button(this);
        newBtn.setText("新規作成");
        newBtn.setOnClickListener(this);
        newBtn.setTag("new");
        tableRow3.addView(newBtn);

        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        tableLayout.addView(tableRow3);
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
	    		if ( ! isAlpha(userId.getText().toString())) {
	    			showDialog(this, "ユーザ名", "半角英数字で入力してください");
	    			return;
	    		}
	    		if ( httpTransfar(userId.getText().toString(), passwd.getText().toString())) {
		    		Intent intent = new Intent(this, com.map.gourmet.activity.ShopEditActivity.class);
		    		intent.putExtra("text", "param1");
		    		startActivityForResult(intent, REQUEST_TEXT);
	    		} else {
	    			showDialog(this, "認証", "認証に失敗しました。。。");
	    		}
	    	}
    	} catch (Exception e) {
    		
    	}
    }
    class MyFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                        Spanned dest, int dstart, int dend) {
                if( source.toString().matches("^[a-zA-Z0-9]+$") ){
                	Toast toast = Toast.makeText(LoginActivity.this, "ユーザ名は半角英数字で入力してください。", Toast.LENGTH_LONG);
                	toast.show();
                	return source;
                }else{
                	return "";
                }
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