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

		// �߂�l�̎w��
		setResult(Activity.RESULT_CANCELED);

		// �C���e���g����p�����[�^�擾
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
		tableRow2.addView(categoryRadio);

		// ���e�{�^���̐���
		TableRow tableRow3 = new TableRow(this);
		cntrbtBtn = new Button(this);
		cntrbtBtn.setText("���e");
		cntrbtBtn.setOnClickListener(this);
		cntrbtBtn.setTag("cntrbt");
		tableRow3.addView(cntrbtBtn);

		// ����
		TableRow tableRow4 = new TableRow(this);
		searchText = new EditText(this);
		searchText.setText("��������", EditText.BufferType.NORMAL);
		tableRow4.addView(searchText);

		searchBtn = new Button(this);
		searchBtn.setText("����");
		searchBtn.setOnClickListener(this);
		searchBtn.setTag("search");
		tableRow4.addView(searchBtn);

		// �n�}
		TableRow tableRow5 = new TableRow(this);
		mapBtn = new Button(this);
		mapBtn.setText("�n�}");
		mapBtn.setOnClickListener(this);
		mapBtn.setTag("map");
		tableRow5.addView(mapBtn);

		// �t�@�C��
		TableRow tableRow6 = new TableRow(this);
		fileBtn = new Button(this);
		fileBtn.setText("�摜");
		fileBtn.setOnClickListener(this);
		fileBtn.setTag("file");
		tableRow6.addView(fileBtn);

		// �摜�t�@�C��
		imgFileText = new EditText(this);
		imgFileText.setText("", EditText.BufferType.NORMAL);
		tableRow6.addView(imgFileText);

		// �J����
		TableRow tableRow7 = new TableRow(this);
		cameraBtn = new Button(this);
		cameraBtn.setText("�J����");
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
			// �߂�l�̎w��
			Intent intent = new Intent();
			intent.putExtra("text", "param2");
			setResult(Activity.RESULT_OK, intent);
			// �A�N�e�B�r�e�B�I��
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
