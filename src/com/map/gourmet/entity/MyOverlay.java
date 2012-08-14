package com.map.gourmet.entity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyOverlay  extends ItemizedOverlay<OverlayItem>{
//	  private final Bitmap bmp;
//	  private final GeoPoint gpoint;
	  private ArrayList<OverlayItem> overlay_items = new ArrayList<OverlayItem>();
	  private Context context;
	  private Drawable drawable;

	 
	  public MyOverlay(Drawable defaultMarker, Context context) {
	      super(boundCenterBottom(defaultMarker));
	      this.context = context;
	      this.drawable = defaultMarker;

	      // コンストラクタ内でpopulate() しないと，NullPointerExceptionになるので。
	      // http://developmentality.wordpress.com/2009/10/19/android-itemizedoverlay-arrayindexoutofboundsexception-nullpointerexception-workarounds/
	      // populate()がない場合，ユーザ画面からホーム画面に戻って最初の数秒の
	      // まだGPS情報が取得されるよりも前の時点でタップすると落ちた。
	      populate();
	  }

	  @Override
	  protected OverlayItem createItem(int i) {
	    return overlay_items.get(i);
	  }

	  @Override
	  public int size() {
	    return overlay_items.size();
	  }


//	  public MyOverlay(Bitmap bmp, GeoPoint gp) {
//	    this.bmp = bmp;
//	    this.gpoint = gp;
//	  }
	 
//	  public void draw(Canvas canvas, MapView mapView, boolean shadow) {
//	    Projection pro = mapView.getProjection();//Mapと画面の位置を計算するオブジェクト
//	    Point p = pro.toPixels(gpoint, null);    //ロケーションから、表示する位置を計算する
//	    canvas.drawBitmap(bmp, p.x, p.y, null);  //表示する場所へ画像を配置する。
//	  }
	  // アイテムの追加
	  public void addOverlayItem(OverlayItem overlayItem) {
		overlayItem.setMarker(drawable);
	    overlay_items.add(overlayItem);
	    populate();
	  }


	  // ダミーアイコンを描画
	  public void draw_dummy_icons( GeoPoint g )
	  {
	    int lat = g.getLatitudeE6();
	    int lng = g.getLongitudeE6();

	    addOverlayItem( new OverlayItem(
	      new GeoPoint(lat + 1000, lng + 1000),
	      "ダミー",
	      "ダミーですよ！"
	    ) );

	    addOverlayItem( new OverlayItem(
	      new GeoPoint(lat - 3000, lng - 3000),
	      "ダミー2",
	      "ダミー2ですよ！"
	    ) );

	  }
	  public synchronized boolean onTap(GeoPoint p, MapView mapView) {
			Projection pj = mapView.getProjection();
			int hitIndex = hitTest(pj, p);
			
			if(hitIndex != -1){
				OverlayItem item = overlay_items.get(hitIndex);
				Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
			}
			return super.onTap(p, mapView);
	  }
	  private static final int NO_HIT = -1;

		private int hitTest(Projection pj, GeoPoint gp){
			int hitIndex = NO_HIT;
			Point hit = new Point();
			pj.toPixels(gp, hit);

			for(int i = 0; i < overlay_items.size(); i++){
				OverlayItem item = overlay_items.get(i);
				Point point = new Point();
				pj.toPixels(item.getPoint(), point);
				
				int halfWidth = item.getMarker(i).getIntrinsicWidth() / 2;
				
				int left = point.x - halfWidth;
				int right = point.x + halfWidth;
				int top = point.y - item.getMarker(i).getIntrinsicHeight();
				int bottom = point.y;
				
				
				if(left <= hit.x && hit.x <= right){
					if(top <= hit.y && hit.y <= bottom){
						hitIndex = i;
						return i;
					}
				}
			}	
			
			return hitIndex;
		}

}
