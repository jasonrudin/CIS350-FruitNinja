package edu.upenn.cis350.fruitninja;

import java.util.ArrayList;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SlicingView extends View{
	
	public MainActivity m;
	
	public SlicingView(Context c){
		super(c);
		m = (MainActivity) c;
		init();
	}

	public SlicingView(Context c, AttributeSet a){
		super(c, a);
		m = (MainActivity) c;
		init();
	}
	
	public boolean clear;
	public Paint paintBrush;
	public Path p;
	public ArrayList<Path> strokes;
	public ArrayList<Paint> strokesPaint;
	
	//fruit ninja stuff
	int squarex, squarey;
	public ArrayList<ShapeDrawable> objects;
	int c = Color.RED;
	
	protected void init(){
		clear = false;
		strokes = new ArrayList<Path>();
		strokesPaint = new ArrayList<Paint>();
		objects = new ArrayList<ShapeDrawable>();
		squarex = 0;
		squarey = 50;
		ShapeDrawable square = new ShapeDrawable(new RectShape());
		square.setBounds(squarex,squarey,squarex+50,squarey+50);
		objects.add(square);
	}
	
	protected void onDraw(Canvas canvas){
		//move square (SUPER PRIMITIVE)
		squarex+=2;
		for (int i = 0; i < objects.size(); i++){
			objects.get(i).getPaint().setColor(c);
			objects.get(i).setBounds(squarex,squarey,squarex+50,squarey+50);
			objects.get(i).draw(canvas);
		}
		
		
		for (int i = 0; i < strokes.size(); i++){
			canvas.drawPath(strokes.get(i), strokesPaint.get(i));
		}

	    if (clear){
	    	canvas.drawColor(Color.WHITE);
	    	strokesPaint.clear();
	    	strokes.clear();
	    	clear = false;
	    	init();
	    	invalidate();
	    }
	    if (squarex <= 400)	//to stop the square
	    	invalidate();
	}
	
	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)e.getX();
			int y = (int)e.getY();
			p = new Path();
			paintBrush = new Paint();
			paintBrush.setColor(m.getColor());                    // set the color
		    paintBrush.setStrokeWidth(m.getThickness());               // set the size
		    paintBrush.setDither(true);                    // set the dither to true
		    paintBrush.setStyle(Paint.Style.STROKE);       // set to STOKE
		    paintBrush.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
		    paintBrush.setStrokeCap(Paint.Cap.ROUND);      // set the paintBrush cap to round too
		    paintBrush.setAntiAlias(true);                         // set anti alias so it smooths
		    strokesPaint.add(paintBrush);
			strokes.add(p);
			p.moveTo(x, y);
			invalidate();
			return true;
		}else if (e.getAction() == MotionEvent.ACTION_MOVE){
			int x = (int)e.getX();
			int y = (int)e.getY();
			p.lineTo(x,y);
			if (x >= squarex && x <= squarex+50 && y >= squarey && y <= squarey+50 ){
				//intersection test
				objects.clear();	
			}
			invalidate();
			return true;
		}
		return false;
	}
	
}
