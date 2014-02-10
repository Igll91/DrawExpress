package com.coursera.finaltask.drawexpress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcelable;

/**
 * Abstract class used to define object that will represent Shapes drawn on {@link Canvas}.
 * 
 * @author Silvio
 *
 */
public abstract class DrawingShape implements Parcelable{

	protected Paint mPaint;

	/**
	 * Set {@link Paint} object to this {@link DrawingShape} that is used in derived classes.
	 * 
	 * @param paint paint to be set for the object 
	 */
	public DrawingShape(Paint paint)
	{
		mPaint = paint;
	}
	
	/**
	 * Returns derived objects values.
	 * 
	 * @return values of certain shapes in order described in overridden methods.
	 */
	public abstract float[] getValues();
	
	/**
	 * Returns value from the derived object that is placed at passed parameter int value.
	 * 
	 * @param position Index position of the needed value.
	 * @return Specific value from derived object or -1 if no such value on that index.
	 */
	public abstract float getSingleValue(final int position);
	
	/**
	 * Sets values into derived object.
	 * 
	 * @param values array of values to be set.
	 */
	public abstract void setValues(final float[] values);

	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}
}
