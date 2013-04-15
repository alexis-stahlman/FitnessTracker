package com.example.fitnesstrackerproto;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class TrackRunActivity extends MapActivity implements LocationListener {

	private LocationManager locationManager;
	private String provider;
	private Location location;
	private ArrayList <Location> locs;
	private MapView mMapView;
	private MapViewItemizedOverlay itemizedOverlay;
	//	private TextView text2;
	private List<Overlay> mapOverlays;
	ArrayList<Point> projs;
	Point curr;
	Point prev;
	Projection p;
	Canvas canvas;
	Paint paint;
	private GeoPoint current;
	private GeoPoint previous;
	private int totDist;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_run);
        
        
        locs = new ArrayList<Location> ();
		projs = new ArrayList<Point>();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		mapOverlays = mMapView.getOverlays();
		p=mMapView.getProjection();
		mapOverlays.add(new MyOverlay());
		new MyLineOverlay();
		totDist = 0;
		
		Drawable drawable = TrackRunActivity.this.getResources().getDrawable(R.drawable.point);
		itemizedOverlay = new MapViewItemizedOverlay(drawable,this);

		current = new GeoPoint(0, 0);
		previous = new GeoPoint(0, 0);
		curr=new Point();
		prev=new Point();


		paint = new Paint();
		paint.setColor(00255);

		p=mMapView.getProjection();

		canvas = new Canvas();

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
		}

        Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent mainMenu = new Intent();
                setResult(RESULT_OK, mainMenu);
                finish();
            }
        });

        Button endRun = (Button) findViewById(R.id.endRunButton);
        endRun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent statsIntent = new Intent(view.getContext(),
                        RunStatsActivity.class);
                startActivityForResult(statsIntent, 0);
                RunStatsActivity.timeRan = (String) ((Chronometer) findViewById(R.id.chronometer1)).getText();
                double totDistMiles = totDist*0.000621371;
                RunStatsActivity.miles = totDistMiles;
				
            }
        });
    }
    

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

    // Chronometer start, pause, stop methods
    public void startChronometer(View view)
    {
        ((Chronometer) findViewById(R.id.chronometer1)).start();
    }

    public void stopChronometer(View view)
    {
        ((Chronometer) findViewById(R.id.chronometer1)).stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_track_run, menu);
        return true;
    }

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude()*1e6);
		int lng = (int) (location.getLongitude()*1e6);
		locs.add(location);
		p.toPixels(new GeoPoint(lat, lng), curr);
		projs.add(curr);
		pinpoint(locs);
	}
	
	public void pinpoint (ArrayList<Location> locs) {

		totDist = 0;
		for(int i=0; i<locs.size(); i++) {
			int lat = (int) (locs.get(i).getLatitude()*1e6);
			int lon	= (int) (locs.get(i).getLongitude()*1e6);
			current = new GeoPoint(lat,lon);
			OverlayItem overlayitem1 = new OverlayItem(current, "Info", ""+i );
			itemizedOverlay.addOverlay(overlayitem1);
			mapOverlays.add(itemizedOverlay);

			if(i<1){
				previous = new GeoPoint(lat, lon);
			}
			if (i>=1) {
				int prevLat = (int) (locs.get(i-1).getLatitude()*1e6);
				int prevLon	= (int) (locs.get(i-1).getLongitude()*1e6);
				previous = new GeoPoint(prevLat, prevLon);
				totDist += locs.get(i).distanceTo(locs.get(i-1));
			}

			int x=curr.x;
			int y = curr.y;
			prev.x=x;
			prev.y=y;
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class MyOverlay extends Overlay{

		public MyOverlay(){

		}   

		public void draw(Canvas canvas, MapView mapv, boolean shadow){
			super.draw(canvas, mapv, shadow);
			
			Paint   mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(2);

			for(int i=0; i<locs.size();i++){
				Point p1 = new Point();
				Point p2 = new Point();
				Path path = new Path();

				current = new GeoPoint((int) (locs.get(i).getLatitude()*1e6), (int) (locs.get(i).getLongitude()*1e6));

				if (i>=1){
					previous = new GeoPoint((int) (locs.get(i-1).getLatitude()*1e6), (int) (locs.get(i-1).getLongitude()*1e6));

					p.toPixels(previous, p1);
					p.toPixels(current, p2);

					path.moveTo(p2.x, p2.y);
					path.lineTo(p1.x,p1.y);

					canvas.drawPath(path, mPaint);
				}
			}
		}
	}

}
