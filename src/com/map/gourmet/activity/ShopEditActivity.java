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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout.LayoutParams;

public class ShopEditActivity extends BaseActivity implements
		View.OnClickListener {
	private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;

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

		// 戻り値の指定
		setResult(Activity.RESULT_CANCELED);

		// インテントからパラメータ取得
		String text = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			text = extras.getString("text");
		}

		TableLayout tableLayout = new TableLayout(this);
		tableLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		setContentView(tableLayout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		TableRow tableRow1 = new TableRow(this);
		shopName = new EditText(this);
		shopName.setText("", EditText.BufferType.NORMAL);
		tableRow1.addView(shopName);

		TableRow tableRow2 = new TableRow(this);
		RadioButton rd0 = new RadioButton(this);
		rd0.setId(0);
		rd0.setText("ランク１");
		rd0.setTextColor(Color.rgb(0, 0, 0));

		RadioButton rd1 = new RadioButton(this);
		rd1.setId(1);
		rd1.setText("ランク2");
		rd1.setTextColor(Color.rgb(0, 0, 0));

		categoryRadio = new RadioGroup(this);
		categoryRadio.addView(rd0);
		categoryRadio.addView(rd1);
		categoryRadio.check(0);
		tableRow2.addView(categoryRadio);

		// 投稿ボタンの生成
		TableRow tableRow3 = new TableRow(this);
		cntrbtBtn = new Button(this);
		cntrbtBtn.setText("投稿");
		cntrbtBtn.setOnClickListener(this);
		cntrbtBtn.setTag("cntrbt");
		tableRow3.addView(cntrbtBtn);

		// 検索
		TableRow tableRow4 = new TableRow(this);
		searchText = new EditText(this);
		searchText.setText("検索文字", EditText.BufferType.NORMAL);
		tableRow4.addView(searchText);

		searchBtn = new Button(this);
		searchBtn.setText("検索");
		searchBtn.setOnClickListener(this);
		searchBtn.setTag("search");
		tableRow4.addView(searchBtn);

		// 地図
		TableRow tableRow5 = new TableRow(this);
		mapBtn = new Button(this);
		mapBtn.setText("地図");
		mapBtn.setOnClickListener(this);
		mapBtn.setTag("map");
		tableRow5.addView(mapBtn);

		// ファイル
		TableRow tableRow6 = new TableRow(this);
		fileBtn = new Button(this);
		fileBtn.setText("画像");
		fileBtn.setOnClickListener(this);
		fileBtn.setTag("file");
		tableRow6.addView(fileBtn);

		// 画像ファイル
		imgFileText = new EditText(this);
		imgFileText.setText("", EditText.BufferType.NORMAL);
		tableRow6.addView(imgFileText);

		// カメラ
		TableRow tableRow7 = new TableRow(this);
		cameraBtn = new Button(this);
		cameraBtn.setText("カメラ");
		cameraBtn.setOnClickListener(this);
		cameraBtn.setTag("camera");
		tableRow7.addView(cameraBtn);

        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        tableLayout.addView(tableRow3);
        tableLayout.addView(tableRow4);
        tableLayout.addView(tableRow5);
        tableLayout.addView(tableRow6);
        tableLayout.addView(tableRow7);
		
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == REQUEST_TEXT && resultCode == RESULT_OK) {
			String text = "";
			Bundle extras = intent.getExtras();
			if (extras != null)
				text = extras.getString("test");
			showDialog(this, "title", text);
			imgFileText.setText(text);

		}
	}

	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals("cntrbt")) {
			Intent intent = new Intent(this,
					com.map.gourmet.activity.LoginActivity.class);
			intent.putExtra("text", "param1");
			startActivityForResult(intent, REQUEST_TEXT);
		} else if (tag.equals("search")) {
			Intent intent = new Intent(this,
					com.map.gourmet.activity.ShopListActivity.class);
			intent.putExtra("text", "param1");
			startActivityForResult(intent, REQUEST_TEXT);
		} else if (tag.equals("file")) {
			Intent intent = new Intent(this,
					com.map.gourmet.activity.FileSelectActivity.class);
			intent.putExtra("text", "param1");
			startActivityForResult(intent, REQUEST_TEXT);
		} else if (tag.equals("camera")) {
			Intent intent = new Intent(this,
					com.map.gourmet.activity.CameraActivity.class);
			intent.putExtra("text", "param1");
			startActivityForResult(intent, REQUEST_TEXT);
		} else if (tag.equals("map")) {
			Intent intent = new Intent(this,
					com.map.gourmet.activity.GoogleMapActivity.class);
			intent.putExtra("text", "param1");
			startActivityForResult(intent, REQUEST_TEXT);
		} else {
			// 戻り値の指定
			Intent intent = new Intent();
			intent.putExtra("text", "param2");
			setResult(Activity.RESULT_OK, intent);
			// アクティビティ終了
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
