package com.map.gourmet.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.map.gourmet.entity.AdapterItem;
import com.map.gourmet.view.CameraView;

public class ShopListActivity  extends BaseActivity {
	public static ArrayList<AdapterItem> items;
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	private final static int FP=LinearLayout.LayoutParams.FILL_PARENT;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //���C�A�E�g�̍쐬
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255, 255, 255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        LinearLayout btnLinearLayout = new LinearLayout(this);
        btnLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        btnLinearLayout.setBackgroundColor(Color.BLACK);
        
		Button okBtn = new Button(this);
        okBtn.setText("OK");
        okBtn.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        okBtn.setTag("ok");
        
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
        btnLinearLayout.addView(okBtn);        
        layout.addView(btnLinearLayout, new LinearLayout.LayoutParams(FP, WC));
        
        
        //�߂�l�̎w��
        setResult(Activity.RESULT_CANCELED);
        
        items = new ArrayList<AdapterItem>();
        for(int i = 0; i < 30; i++) {
        	AdapterItem item = new AdapterItem();
        	item.id = i;
        	item.text = "����" + i;
        	items.add(item);
        }
        
        //���X�g�r���[�̍쐬
        ListView listView = new ListView(this);
        listView.setScrollingCacheEnabled(false);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(new ListArrayAdapter());
        
        // ���X�g�r���[�̃A�C�e�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // �N���b�N���ꂽ�A�C�e�����擾���܂�
                AdapterItem item = (AdapterItem) listView.getItemAtPosition(position);
//                Toast.makeText(ShopListActivity.this, item.text, Toast.LENGTH_LONG).show();
        		//�߂�l�̎w��
        		Intent intent = new Intent();
        		intent.putExtra("text", item.text);
        		setResult(Activity.RESULT_OK, intent);
        		//�A�N�e�B�r�e�B�I��
        		finish();
            }
        });
        // ���X�g�r���[�̃A�C�e�����I�����ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // �I�����ꂽ�A�C�e�����擾���܂�
                AdapterItem item = (AdapterItem) listView.getSelectedItem();
                Toast.makeText(ShopListActivity.this, item.text, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        setContentView(listView);
	    layout.addView(listView, new LinearLayout.LayoutParams(WC, WC));        
	}
	private class ListArrayAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return items.size();
		}
		@Override
		public AdapterItem getItem(int pos) {
			return items.get(pos);
		}
		@Override
		public long getItemId(int pos) {
			return pos;
		}
		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			Context context = ShopListActivity.this;
			AdapterItem item = items.get(pos);
			//���C�A�E�g�̐���
			if ( convertView == null ) {
				LinearLayout layout = new LinearLayout(context);
				layout.setPadding(10, 10, 10, 10);
				convertView = layout;
				
				//�e�L�X�g�̎w��
				TextView textView = new TextView(context);
				textView.setTag("text");
				textView.setTextColor(Color.rgb(0, 0, 0));
				textView.setPadding(10, 20, 10, 20);
				layout.addView(textView);
			}
			
			//�l�̎w��
			TextView  textView = (TextView)convertView.findViewWithTag("text");
			textView.setText(item.text);
			return convertView;
		}
		
	}

}
