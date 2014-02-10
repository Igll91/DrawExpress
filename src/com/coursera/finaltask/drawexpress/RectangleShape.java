package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Rectangle shape is used to store informations about rectangle that will be drawn.
 * 
 * @author Silvio
 *
 */
public class RectangleShape extends DrawingShape implements Parcelable{

	/**
	 * Rectangle X-axis left position.
	 */
	private float left;
	
	/**
	 * Rectangle Y-axis top position.
	 */
	private float top;
	
	/**
	 * Rectangle X-axis right position.
	 */
	private float right;
	
	/**
	 * Rectangle Y-axis bottom position.
	 */
	private float bottom;

	public RectangleShape(Paint paint) {
		super(paint);
	}

	/**
	 * Creates object with given positions and paint.
	 * 
	 * @param paint Paint used with this object.
	 * @param left X-axis position of the rectangle left side line.
	 * @param top Y-axis position of the rectangle top side line.
	 * @param right X-axis position of the rectangle right side line.
	 * @param bottom Y-axis position of the rectangle bottom side line.
	 */
	public RectangleShape(Paint paint, float left, float top, float right, float bottom)
	{
		super(paint);
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	/**
	 * Returns Object position values.
	 * 
	 * Returns Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #left} </li>
	 * <li> float[1] {@link #top} </li>
	 * <li> float[2] {@link #right} </li>
	 * <li> float[3] {@link #bottom} </li>
	 * </ul>
	 * 
	 * @return Values in order described above.
	 */
	@Override
	public float[] getValues() {
		return new float[]{ left, top, right, bottom };
	}

	/**
	 * Returns Object position certain value.
	 * 
	 * Returns Object position certain value, selected by parameter explained beneath:
	 * 
	 * <ul>
	 * <li> position == 0, return {@link #left} </li>
	 * <li> position == 1, return {@link #top} </li>
	 * <li> position == 2, return {@link #right} </li>
	 * <li> position == 3, return {@link #bottom} </li>
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
			return this.left;
		case 1:
			return this.top;
		case 2:
			return this.right;
		case 3:
			return this.bottom;
		default:
			return -1;
		}
	}

	/**
	 * Sets Object position values.
	 * 
	 * Sets Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #left} </li>
	 * <li> float[1] {@link #top} </li>
	 * <li> float[2] {@link #right} </li>
	 * <li> float[3] {@link #bottom} </li>
	 * </ul>
	 * 
	 * @param values order described above.
	 */
	@Override
	public void setValues(float[] values) 
	{
		this.left 	= values[0];
		this.top  	= values[1];
		this.right	= values[2];
		this.bottom = values[3];
	}

	public RectangleShape(Parcel in)
	{
		super(new Paint(Paint.ANTI_ALIAS_FLAG));
		
		float[] values = new float[4];
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

	public static final Parcelable.Creator<RectangleShape> CREATOR = new Parcelable.Creator<RectangleShape>() {

		@Override
		public RectangleShape createFromParcel(Parcel source) {
			return new RectangleShape(source);
		}

		@Override
		public RectangleShape[] newArray(int size) {
			return new RectangleShape[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}
	
}
