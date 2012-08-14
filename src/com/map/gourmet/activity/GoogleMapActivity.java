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
	

	private  MapView mapView;					//�}�b�v�r���[
	private LocationManager locationManager;	//���P�[�V�����}�l�[�W��

	private static final int M_ZOOMIN = Menu.FIRST + 1;		//�Y�[���C��
	private static final int M_ZOOMOUT = Menu.FIRST + 2;	//�Y�[���A�E�g
	private static final int M_SATELLITE = Menu.FIRST + 3;	//�q��ʐ^���[�h�ؑ�
	private static final int M_NOW_LOCATION = Menu.FIRST + 4;	//���ݒn�̕\��
	private static final int M_KOKKAI_GIJIDOU = Menu.FIRST + 5;	//����c�����̕\��
	private static final int M_HIMEJI = Menu.FIRST + 6;			//�P�H��̕\��
	private GeoPoint nowlocation;	//���ݒn
	
	/**
	 * �N�����Ɏ��s
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//�A�v���P�[�V�����̃^�C�g���o�[���\���ɐݒ�
		requestWindowFeature(Window.FEATURE_NO_TITLE );

		
        //���C�A�E�g�̍쐬
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

		
		
		//Map�r���[�̐���
		//�J��
		//mapView=new MapView(this,"Develop APi Key");
		//�����[�X
//		mapView=new MapView(this,"0r42KkVJhJa3CKhjla7539L09YYASh1CWTqMrhA");
		mapView=new MapView(this,"0mRSoSPou2MwOmvM5_xc-Rn595le171YQJbrrjw");
		//GoogleMap��ł̃h���b�O�A�h���b�v��������
		mapView.setClickable(true);
		//�g��k���{�^���̕\��
		mapView.setBuiltInZoomControls(true);
//		setContentView(mapView);
		
		// �摜��n�}��ɔz�u����I�[�o�[���C
		mapView.setEnabled(true);
		mapView.setClickable(true);
//		  setContentView(mapView);
		
	    layout.addView(mapView, new LinearLayout.LayoutParams(WC, WC));        
		
		//LocationManager���擾
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		
		//���݈ʒu���擾���X�V
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,	   //�擾�ł��Ȃ��ꍇ������
				//LocationManager.NETWORK_PROVIDER, //�擾�ł��Ȃ��ꍇ������
				0 , 0, this);
		
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
	/**
	 * ���j���[�{�^���̒ǉ�
	 * @param	menu	���j���[�I�u�W�F�N�g
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, M_ZOOMIN,Menu.NONE, "�Y�[���C��");
		menu.add(Menu.NONE, M_ZOOMOUT,Menu.NONE, "�Y�[���A�E�g");
		menu.add(Menu.NONE, M_SATELLITE,Menu.NONE, "�q��ʐ^���[�h�ؑ�");
		menu.add(Menu.NONE, M_NOW_LOCATION, Menu.NONE,"���ݒn��\��");
		menu.add(Menu.NONE, M_KOKKAI_GIJIDOU,Menu.NONE, "����c����");
		menu.add(Menu.NONE, M_HIMEJI,Menu.NONE, "�P�H��");

		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * ���j���[�̑I�����̃C�x���g����
	 * @param	item	�I�����ꂽ���j���[�{�^��
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MapController mc = mapView.getController();

		int ido = 0;
		int keido = 0;
		switch (item.getItemId()) {
		//�Y�[���C��
		case M_ZOOMIN:
			mc.setZoom(mapView.getZoomLevel() + 1);
			return true;
		//�Y�[���A�E�g	
		case M_ZOOMOUT:
			mc.setZoom(mapView.getZoomLevel() - 1);
			return true;
		//�q��ʐ^���[�h�ւ̐ؑ�
		case M_SATELLITE:
			mapView.setSatellite(!mapView.isSatellite());
			return true;
		//���݈ʒu���擾���ړ�	  
		case M_NOW_LOCATION:
			//�����̋���ʒu�i�ܓx�A�o�x�j���擾����
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
				Toast.makeText(this, "���݂̈ʒu��񂪎擾�ł��܂���ł����BGps�@�\��ON�ɂȂ��Ă��邩�A���΂炭���Ă�����s���Ă��������B", Toast.LENGTH_LONG).show();
			}
			return true;
		//����c������\��
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

			  // �_�~�[���[�U�̃A�C�R����\��
			  overlay.draw_dummy_icons(new GeoPoint(ido, keido));


			  List<Overlay> list = mapView.getOverlays();
			  list.add(overlay);
			  System.out.println("aaaa");
			  
			  return true;
			
		//�P�H���\��
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
	 * �ʒu��񂪍X�V���̃C�x���g
	 * @param	location	���P�[�V�����I�u�W�F�N�g
	 */
	@Override
	public void onLocationChanged(Location location) {
		nowlocation = 
			new GeoPoint((int)(location.getLatitude()*1E6),
				         (int)(location.getLongitude()*1E6));

	}
	/**
	 * �v���o�C�_���g�p�\�ȏ��
	 */
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	/**
	 * �v���o�C�_���g�p�s�̏��
	 */
	@Override
	public void onProviderEnabled(String provider) {
	}
	/**
	 * �v���o�C�_�̏�Ԃ��ω������ꍇ
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch (status) {
		case LocationProvider.AVAILABLE:
			//�v���o�C�_���s�\���̏������L�q
			break;
		case LocationProvider.OUT_OF_SERVICE:
			//�v���o�C�_���s�s���̏������L�q
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			//�e���|�������擾�ł��Ȃ�
			break;
		}

	}

}
