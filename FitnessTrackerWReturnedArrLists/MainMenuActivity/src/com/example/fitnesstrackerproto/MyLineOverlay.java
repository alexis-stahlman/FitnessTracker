package com.example.fitnesstrackerproto;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.Overlay;

public class MyLineOverlay extends Overlay{
	ArrayList<Point> points;
	Paint paint;
	Canvas canvas;
	
	public void setPoints(ArrayList<Point> point){
		this.points=point;
		canvas = new Canvas();
	}
	
	public void drawLine(Point startpoint, Point endpoint){
		paint = new Paint();
		paint.setARGB(80, 0, 0, 255);
		paint.setStrokeWidth(4);
		canvas.drawLine(startpoint.x, 
				startpoint.y, 
				endpoint.x, 
				endpoint.y, 
				paint);
		
	}

}
