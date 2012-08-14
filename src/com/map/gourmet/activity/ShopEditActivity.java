package com.map.gourmet.activity;

import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ShopEditActivity  extends BaseActivity implements View.OnClickListener{
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;

	private Button fileBtn;
	private Button cntrbtBtn;
	private Button mapBtn;
	private Button searchBtn;
	private Button cameraBtn;
	private EditText shopName;
	private EditText searchText;
	private RadioGroup categoryRadio;
	private final static int REQUEST_TEXT = 0;
	private EditText imgFileText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //�߂�l�̎w��
        setResult(Activity.RESULT_CANCELED);
        
        //�C���e���g����p�����[�^�擾
        String text = "";
        Bundle extras = getIntent().getExtras();
        if ( extras != null) {
        	text = extras.getString("text");
        }
        //���C�A�E�g�̍쐬
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255, 255, 255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        LinearLayout searchLinearLayout = new LinearLayout(this);
        searchLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        searchLinearLayout.setBackgroundColor(Color.BLACK);
        
        //�X���e�L�X�g
        shopName = new EditText(this);
        shopName.setText("", EditText.BufferType.NORMAL);
        shopName.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
        layout.addView(shopName);

      RadioButton rd0 = new RadioButton(this);
      rd0.setId(0);
      rd0.setText("�����N�P");
      rd0.setTextColor(Color.rgb(0, 0, 0));

      RadioButton rd1 = new RadioButton(this);
      rd1.setId(1);
      rd1.setText("�����N2");
      rd1.setTextColor(Color.rgb(0, 0, 0));

      categoryRadio = new RadioGroup(this);
      categoryRadio.addView(rd0);
      categoryRadio.addView(rd1);
      categoryRadio.check(0);
      categoryRadio.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
      layout.addView(categoryRadio);

        //���e�{�^���̐���
        cntrbtBtn = new Button(this);
        cntrbtBtn.setText("���e");
        cntrbtBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        cntrbtBtn.setOnClickListener(this);
        cntrbtBtn.setTag("cntrbt");
        layout.addView(cntrbtBtn);
        
        //���������e�L�X�g
        searchText = new EditText(this);
        searchText.setText("��������", EditText.BufferType.NORMAL);
        searchText.setLayoutParams(new LinearLayout.LayoutParams(MP, WC+100));
        layout.addView(searchText);
//        searchLinearLayout.addView(searchText);

        //����
        searchBtn = new Button(this);
        searchBtn.setText("����");
        searchBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        searchBtn.setOnClickListener(this);
        searchBtn.setTag("search");
        layout.addView(searchBtn);
//        searchLinearLayout.addView(searchText);
//        layout.addView(searchLinearLayout);
        
        //�n�}
        mapBtn = new Button(this);
        mapBtn.setText("�n�}");
        mapBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        mapBtn.setOnClickListener(this);
        mapBtn.setTag("map");
        layout.addView(mapBtn);
        
        //�t�@�C��
        fileBtn = new Button(this);
        fileBtn.setText("�摜");
        fileBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        fileBtn.setOnClickListener(this);
        fileBtn.setTag("file");
        layout.addView(fileBtn);
        
        //�摜�t�@�C��
        imgFileText = new EditText(this);
        imgFileText.setText("", EditText.BufferType.NORMAL);
        imgFileText.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
        layout.addView(imgFileText);

        //�J����
        cameraBtn = new Button(this);
        cameraBtn.setText("�J����");
        cameraBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        cameraBtn.setOnClickListener(this);
        cameraBtn.setTag("camera");
        layout.addView(cameraBtn);

        
	}
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if ( requestCode == REQUEST_TEXT && resultCode == RESULT_OK) {
    		String text = "";
    		Bundle extras = intent.getExtras();
    		if ( extras != null) text = extras.getString("test");
    		showDialog(this, "title", text);
    		imgFileText.setText(text);
    	
    	}
    }
	public void onClick(View v) {
    	String tag = (String)v.getTag();
    	if ( tag.equals("cntrbt") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.LoginActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	} else if ( tag.equals("search") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.ShopListActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	} else if ( tag.equals("file") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.FileSelectActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	} else if ( tag.equals("camera") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.CameraActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	} else if ( tag.equals("map") ) {
    		Intent intent = new Intent(this, com.map.gourmet.activity.GoogleMapActivity.class);
    		intent.putExtra("text", "param1");
    		startActivityForResult(intent, REQUEST_TEXT);    		
    	} else {
    		//�߂�l�̎w��
    		Intent intent = new Intent();
    		intent.putExtra("text", "param2");
    		setResult(Activity.RESULT_OK, intent);
    		//�A�N�e�B�r�e�B�I��
    		finish();
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
