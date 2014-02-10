package com.coursera.finaltask.drawexpress;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

/**
 * View responsible for drawing shapes one screen.
 * 
 * @author Silvio
 *
 */
public class DrawingView extends SurfaceView implements Runnable, OnTouchListener{

	Thread thread = null;
	private SurfaceHolder surfaceHolder;
	volatile boolean running = false;

	/**
	 * Shape that is currently being redrawn.
	 */
	private DrawingShape currentShape; 

	/*
	 * Compare with createMap putting sequence.
	 * These values are used as keys in MAP_DRAWING_TOOLS
	 */
	public static final int OPTION_DRAW_LINE   		= 0;
	public static final int OPTION_DRAW_CIRCLE 		= 1;
	public static final int OPTION_DRAW_RECTANGLE	= 2;
	public static final int OPTION_DRAW_FREESTYLE	= 3;

	/**
	 * Holds map of {@link DrawingOption} objects. 
	 */
	public static final Map<Integer, DrawingOption> MAP_DRAWING_TOOLS = createMap();

	/**
	 * Holds all drawing options in one place.
	 * 
	 * <p>
	 * 	Inserts keys and values into {@link #MAP_DRAWING_TOOLS}.
	 * </p>
	 * @return
	 */
    @SuppressLint("UseSparseArrays") // need custom key values.
	private static Map<Integer, DrawingOption> createMap() 
    {
        Map<Integer, DrawingOption> result = new HashMap<Integer, DrawingOption>();
        result.put(OPTION_DRAW_LINE, new DrawingOption("Line", R.drawable.line));
        result.put(OPTION_DRAW_CIRCLE, new DrawingOption("Circle", R.drawable.circle));
        result.put(OPTION_DRAW_RECTANGLE, new DrawingOption("Rectangle", R.drawable.rectangle));
        result.put(OPTION_DRAW_FREESTYLE, new DrawingOption("Freestyle", R.drawable.freestyle));
        return Collections.unmodifiableMap(result);
    }
	
    /**
     * Currently selected drawing option.
     */
	private int selected_drawing_option = 0; 
	
	/**
	 * List containing {@link DrawingShape} objects that will be drawn with canvas.
	 * 
	 * Notice the use of {@link CopyOnWriteArrayList} because of the ConcurrentModificationException in multi-threaded environment.
	 */
	private List<DrawingShape> listOfShapesToBeDrawn;

	/**
	 * Currently selected paint properties.
	 * Saves stuff such as: color, thickness and style.
	 */
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public DrawingView(Context context) 
	{
		super(context);
		surfaceHolder = getHolder();
		listOfShapesToBeDrawn = new CopyOnWriteArrayList<DrawingShape>();
		setOnTouchListener(this);
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		surfaceHolder = getHolder();
		listOfShapesToBeDrawn = new CopyOnWriteArrayList<DrawingShape>();
		setOnTouchListener(this);
	}
	
	public void onResumeMySurfaceView()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void onPauseMySurfaceView()
	{
		boolean retry = true;
		running = false;
		while(retry)
		{
			try 
			{
				thread.join();
				retry = false;
			} 
			catch (InterruptedException e) 
			{
				Log.e(Storage.APP_TAG, "onPauseMySurfaceView: ", e);
			}
		}
	}
	
	@Override
	public void run() {
		while(running)
		{
			if(surfaceHolder.getSurface().isValid())
			{
				Canvas canvas = surfaceHolder.lockCanvas();
				
				//... actual drawing on canvas
				canvas.drawColor(Color.LTGRAY);

				Iterator<DrawingShape> iterator = listOfShapesToBeDrawn.iterator();
				while(iterator.hasNext())
				{
					DrawingShape shape = iterator.next();
					float[] values = shape.getValues();

					if (shape instanceof LineShape) 
						canvas.drawLines(values, shape.getmPaint());
					else if(shape instanceof CircleShape)
						canvas.drawCircle(values[0], values[1], values[2], shape.getmPaint());
					else if(shape instanceof RectangleShape)
						canvas.drawRect(values[0], values[1], values[2], values[3], shape.getmPaint());
					else if(shape instanceof FreestyleShape)
						canvas.drawPoint(values[0], values[1], shape.getmPaint());
					else
					{
						Log.e(Storage.APP_TAG, "DrawingView: run() ERR#1 - listOfShapesToBeDrawn contains unknown element!");
						Toast.makeText(getContext(), "Contact developer, error with id: ERR#1 occurred!", Toast.LENGTH_LONG).show();
					}
				}

				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		
		// ACTION 0 PRESS, 1 = RELEASE 2 = MOVE

		float x = event.getX();
		float y = event.getY();
		
		switch(action)
		{
		case MotionEvent.ACTION_DOWN:
			switch(selected_drawing_option)
			{
				case OPTION_DRAW_LINE:
					currentShape = new LineShape(new Paint(paint), x, y, x + 10, y);
					listOfShapesToBeDrawn.add(currentShape);
					break;
				case OPTION_DRAW_CIRCLE:
					currentShape = new CircleShape(new Paint(paint), x, y, 5);
					listOfShapesToBeDrawn.add(currentShape);
					break;
				case OPTION_DRAW_RECTANGLE:
					currentShape = new RectangleShape(new Paint(paint), x, y, x + 5, y + 5);
					listOfShapesToBeDrawn.add(currentShape);
					break;
				case OPTION_DRAW_FREESTYLE:
					currentShape = new FreestyleShape(new Paint(paint), x, y);
					listOfShapesToBeDrawn.add(currentShape);
					break;
				default:
					return false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(currentShape instanceof LineShape)
				currentShape.setValues(new float[]{ currentShape.getSingleValue(0), currentShape.getSingleValue(1), x, y });
			else if (currentShape instanceof CircleShape)
				currentShape.setValues(new float[]{ currentShape.getSingleValue(0), currentShape.getSingleValue(1), Math.abs(currentShape.getSingleValue(0) - x) < 5? 5: Math.abs(currentShape.getSingleValue(0) - x) });
			else if(currentShape instanceof RectangleShape)
				currentShape.setValues(new float[]{ currentShape.getSingleValue(0), currentShape.getSingleValue(1), x, y });
			else if(currentShape instanceof FreestyleShape)
			{
				currentShape = new FreestyleShape(new Paint(paint), x, y);
				listOfShapesToBeDrawn.add(currentShape);
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}
		return true;
	}

	public int getSelected_drawing_option() {
		return selected_drawing_option;
	}

	public void setSelected_drawing_option(int selected_drawing_option) {
		this.selected_drawing_option = selected_drawing_option;
	}

	/**
	 * Removes last shape drawn from list.
	 */
	public void undoMove()
	{
		int size = listOfShapesToBeDrawn.size();
		if(size > 0)
			listOfShapesToBeDrawn.remove(size - 1);
	}
	
	/**
	 * Clears all shapes drawn before.
	 */
	public void newDrawing()
	{
		listOfShapesToBeDrawn.clear();
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public List<DrawingShape> getListOfShapesToBeDrawn() {
		return listOfShapesToBeDrawn;
	}

	public void setListOfShapesToBeDrawn(List<DrawingShape> listOfShapesToBeDrawn) {
		this.listOfShapesToBeDrawn = listOfShapesToBeDrawn;
	}

	/**
	 * Returns bitmap with all drawing done up till call of this function.
	 * 
	 * <p>
	 *  Not sure if this is done correctly! 
	 * </p>
	 * 
	 * <p>
	 * 	Creates Bitmap with size equal to the size of your mobile phone screen.
	 *  Fills bitmap with all drawings.
	 * </p>
	 * 
	 * @return Bitmap with all drawings done up till now. 
	 */
	public Bitmap getmBitmap() {
		Bitmap mBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);

		canvas.drawColor(Color.LTGRAY);

		Iterator<DrawingShape> iterator = listOfShapesToBeDrawn.iterator();
		while(iterator.hasNext())
		{
			DrawingShape shape = iterator.next();
			float[] values = shape.getValues();

			if (shape instanceof LineShape) 
				canvas.drawLines(values, shape.getmPaint());
			else if(shape instanceof CircleShape)
				canvas.drawCircle(values[0], values[1], values[2], shape.getmPaint());
			else if(shape instanceof RectangleShape)
				canvas.drawRect(values[0], values[1], values[2], values[3], shape.getmPaint());
			else if(shape instanceof FreestyleShape)
				canvas.drawPoint(values[0], values[1], shape.getmPaint());
		}
        
		return mBitmap;
	}

}
