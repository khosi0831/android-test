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

        //レイアウトの作成
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
	    		//戻り値の指定
	    		Intent intent = new Intent();
	    		intent.putExtra("test", CameraView.path);
	    		setResult(Activity.RESULT_OK, intent);
	    		//アクティビティ終了
	    		finish();
	      }
	  });
        btnLinearLayout.addView(okBtn);        
        layout.addView(btnLinearLayout, new LinearLayout.LayoutParams(FP, WC));
        
        
        //戻り値の指定
        setResult(Activity.RESULT_CANCELED);
        
        items = new ArrayList<AdapterItem>();
        for(int i = 0; i < 30; i++) {
        	AdapterItem item = new AdapterItem();
        	item.id = i;
        	item.text = "項目" + i;
        	items.add(item);
        }
        
        //リストビューの作成
        ListView listView = new ListView(this);
        listView.setScrollingCacheEnabled(false);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(new ListArrayAdapter());
        
        // リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                AdapterItem item = (AdapterItem) listView.getItemAtPosition(position);
//                Toast.makeText(ShopListActivity.this, item.text, Toast.LENGTH_LONG).show();
        		//戻り値の指定
        		Intent intent = new Intent();
        		intent.putExtra("text", item.text);
        		setResult(Activity.RESULT_OK, intent);
        		//アクティビティ終了
        		finish();
            }
        });
        // リストビューのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // 選択されたアイテムを取得します
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
			//レイアウトの生成
			if ( convertView == null ) {
				LinearLayout layout = new LinearLayout(context);
				layout.setPadding(10, 10, 10, 10);
				convertView = layout;
				
				//テキストの指定
				TextView textView = new TextView(context);
				textView.setTag("text");
				textView.setTextColor(Color.rgb(0, 0, 0));
				textView.setPadding(10, 20, 10, 20);
				layout.addView(textView);
			}
			
			//値の指定
			TextView  textView = (TextView)convertView.findViewWithTag("text");
			textView.setText(item.text);
			return convertView;
		}
		
	}

}
