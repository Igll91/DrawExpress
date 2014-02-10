package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Freestyle shape is used to store informations about dots that will be drawn.
 * 
 * @author Silvio
 * 
 */
public class FreestyleShape extends DrawingShape {
	
	/**
	 * X position of the dot.
	 */
	private float x;
	
	/**
	 * Y position of the dot.
	 */
	private float y;
	
	public FreestyleShape(Paint paint) {
		super(paint);
	}
	
	/**
	 * Creates object with given positions and paint.
	 * 
	 * @param paint Paint used with this object.
	 * @param x X position of the dot.
	 * @param y Y position of the dot.
	 */
	public FreestyleShape(Paint paint, float x, float y)
	{
		super(paint);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns Object position values.
	 * 
	 * Returns Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #x} </li>
	 * <li> float[1] {@link #y} </li>
	 * </ul>
	 * 
	 * @return Values in order described above.
	 */
	@Override
	public float[] getValues() {
		return new float[]{ x, y };
	}

	/**
	 * Returns Object position certain value.
	 * 
	 * Returns Object position certain value, selected by parameter explained beneath:
	 * 
	 * <ul>
	 * <li> position == 0, return {@link #x} </li>
	 * <li> position == 1, return {@link #y} </li>
	 * </ul>
	 * 
	 * @param position Index position representing value defined above in description.
	 * 
	 * @return Value described above or -1 if no value defined by position parameter.
	 */
	@Override
	public float getSingleValue(final int position) {
		switch(position)
		{
		case 0:
			return x;
		case 1:
			return y;
		default:
			return -1;
		}
	}

	/**
	 * Sets Object position values.
	 * 
	 * Sets Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #x} </li>
	 * <li> float[1] {@link #y} </li>
	 * </ul>
	 * 
	 * @param values order described above.
	 */
	@Override
	public void setValues(float[] values) {
		this.x = values[0];
		this.y = values[1];
	}

	public FreestyleShape(Parcel in)
	{
		super(new Paint(Paint.ANTI_ALIAS_FLAG));
		
		float[] values = new float[2];
		in.readFloatArray(values);
		setValues(values);
		
		int color = in.readInt();;
		Style style = in.readInt() == 0? Style.FILL: Style.STROKE;
		float stroke = in.readFloat();
		
		mPaint.setColor(color);
		mPaint.setStyle(style);
		mPaint.setStrokeWidth(stroke);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloatArray(getValues());
		dest.writeInt(mPaint.getColor());
		dest.writeInt(mPaint.getStyle() == Style.FILL?0:1);
		dest.writeFloat(mPaint.getStrokeWidth());
		// paint doesn't implement parcelable...
		// so added values manually
	}

	public static final Parcelable.Creator<FreestyleShape> CREATOR = new Parcelable.Creator<FreestyleShape>() {

		@Override
		public FreestyleShape createFromParcel(Parcel source) {
			return new FreestyleShape(source);
		}

		@Override
		public FreestyleShape[] newArray(int size) {
			return new FreestyleShape[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}
	
}
