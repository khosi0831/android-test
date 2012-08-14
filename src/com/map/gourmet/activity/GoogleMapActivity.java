package com.map.gourmet.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.map.gourmet.entity.MyOverlay;
import com.map.gourmet.view.CameraView;

public class GoogleMapActivity extends MapActivity implements LocationListener{
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;
	private final static int FP=LinearLayout.LayoutParams.FILL_PARENT;
	

	private  MapView mapView;					//マップビュー
	private LocationManager locationManager;	//ロケーションマネージャ

	private static final int M_ZOOMIN = Menu.FIRST + 1;		//ズームイン
	private static final int M_ZOOMOUT = Menu.FIRST + 2;	//ズームアウト
	private static final int M_SATELLITE = Menu.FIRST + 3;	//航空写真モード切替
	private static final int M_NOW_LOCATION = Menu.FIRST + 4;	//現在地の表示
	private static final int M_KOKKAI_GIJIDOU = Menu.FIRST + 5;	//国会議事堂の表示
	private static final int M_HIMEJI = Menu.FIRST + 6;			//姫路城の表示
	private GeoPoint nowlocation;	//現在地
	
	/**
	 * 起動時に実行
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//アプリケーションのタイトルバーを非表示に設定
		requestWindowFeature(Window.FEATURE_NO_TITLE );

		
        //レイアウトの作成
        LinearLayout layout = new LinearLayout(this);
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

		
		
		//Mapビューの生成
		//開発
		//mapView=new MapView(this,"Develop APi Key");
		//リリース
//		mapView=new MapView(this,"0r42KkVJhJa3CKhjla7539L09YYASh1CWTqMrhA");
		mapView=new MapView(this,"0mRSoSPou2MwOmvM5_xc-Rn595le171YQJbrrjw");
		//GoogleMap上でのドラッグ、ドロップを許可する
		mapView.setClickable(true);
		//拡大縮小ボタンの表示
		mapView.setBuiltInZoomControls(true);
//		setContentView(mapView);
		
		// 画像を地図上に配置するオーバーレイ
		mapView.setEnabled(true);
		mapView.setClickable(true);
//		  setContentView(mapView);
		
	    layout.addView(mapView, new LinearLayout.LayoutParams(WC, WC));        
		
		//LocationManagerを取得
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		
		//現在位置を取得し更新
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,	   //取得できない場合がある
				//LocationManager.NETWORK_PROVIDER, //取得できない場合がある
				0 , 0, this);
		
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
	/**
	 * メニューボタンの追加
	 * @param	menu	メニューオブジェクト
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, M_ZOOMIN,Menu.NONE, "ズームイン");
		menu.add(Menu.NONE, M_ZOOMOUT,Menu.NONE, "ズームアウト");
		menu.add(Menu.NONE, M_SATELLITE,Menu.NONE, "航空写真モード切替");
		menu.add(Menu.NONE, M_NOW_LOCATION, Menu.NONE,"現在地を表示");
		menu.add(Menu.NONE, M_KOKKAI_GIJIDOU,Menu.NONE, "国会議事堂");
		menu.add(Menu.NONE, M_HIMEJI,Menu.NONE, "姫路城");

		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * メニューの選択時のイベント処理
	 * @param	item	選択されたメニューボタン
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MapController mc = mapView.getController();

		int ido = 0;
		int keido = 0;
		switch (item.getItemId()) {
		//ズームイン
		case M_ZOOMIN:
			mc.setZoom(mapView.getZoomLevel() + 1);
			return true;
		//ズームアウト	
		case M_ZOOMOUT:
			mc.setZoom(mapView.getZoomLevel() - 1);
			return true;
		//航空写真モードへの切替
		case M_SATELLITE:
			mapView.setSatellite(!mapView.isSatellite());
			return true;
		//現在位置を取得し移動	  
		case M_NOW_LOCATION:
			//自分の居る位置（緯度、経度）を取得する
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location == null){
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			if(location != null){
				ido = (int)(location.getLatitude()*1E6) ;
				keido = (int)(location.getLongitude()*1E6) ;
				mc.animateTo(new GeoPoint(ido,keido));
			}else if(nowlocation != null){
				mc.animateTo(nowlocation);
			}else{
				Toast.makeText(this, "現在の位置情報が取得できませんでした。Gps機能がONになっているか、しばらくしてから実行してください。", Toast.LENGTH_LONG).show();
			}
			return true;
		//国会議事堂を表示
		case M_KOKKAI_GIJIDOU:
			ido = (int)(35.675911 * 1E6);
			keido = (int)(139.745042 * 1E6);
			mc.animateTo(new GeoPoint(ido,keido));
			mc.setZoom(15);
			if(!mapView.isSatellite()) {
				mapView.setSatellite(!mapView.isSatellite());
			}
			  Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//			  MyOverlay overlay = new MyOverlay(bmp, new GeoPoint(ido, keido));
			  Drawable drawable_icon = new BitmapDrawable(bmp);
			  MyOverlay overlay = new MyOverlay(drawable_icon, this);

			  // ダミーユーザのアイコンを表示
			  overlay.draw_dummy_icons(new GeoPoint(ido, keido));


			  List<Overlay> list = mapView.getOverlays();
			  list.add(overlay);
			  System.out.println("aaaa");
			  
			  return true;
			
		//姫路城を表示
		case M_HIMEJI:
			ido =  (int)(34.838113 * 1E6);
			keido =  (int)(134.692755 * 1E6);
			mc.animateTo(new GeoPoint(ido,keido));
			mc.setZoom(15);
			if(!mapView.isSatellite()) {
				mapView.setSatellite(!mapView.isSatellite());
			}
			return true;
		}
	  return true;
	}
	/**
	 * 位置情報が更新時のイベント
	 * @param	location	ロケーションオブジェクト
	 */
	@Override
	public void onLocationChanged(Location location) {
		nowlocation = 
			new GeoPoint((int)(location.getLatitude()*1E6),
				         (int)(location.getLongitude()*1E6));

	}
	/**
	 * プロバイダが使用可能な状態
	 */
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	/**
	 * プロバイダが使用不可の状態
	 */
	@Override
	public void onProviderEnabled(String provider) {
	}
	/**
	 * プロバイダの状態が変化した場合
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch (status) {
		case LocationProvider.AVAILABLE:
			//プロバイダ実行可能時の処理を記述
			break;
		case LocationProvider.OUT_OF_SERVICE:
			//プロバイダ実行不可時の処理を記述
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			//テンポラリが取得できない
			break;
		}

	}

}
