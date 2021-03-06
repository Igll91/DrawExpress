package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Circle shape is used to store informations about circle that will be drawn.
 * 
 * @author Silvio
 * 
 */
public class CircleShape extends DrawingShape {

	/**
	 * Circle center position X-axis.
	 */
	private float centerX;
	
	/**
	 * Circle center position Y-axis.
	 */
	private float centerY;
	
	/**
	 * Circles radius size.
	 */
	private float radius;
	
	public CircleShape(Paint paint) {
		super(paint);
	}

	/**
	 * Creates object with given positions, radius and paint.
	 * 
	 * @param paint Paint object will use.
	 * @param centerX Center X-axis position of circle.
	 * @param centerY Center Y-axis position of circle.
	 * @param radius Radius of the circle.
	 */
	public CircleShape(Paint paint, float centerX, float centerY, float radius) {
		super(paint);
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius	 = radius;
	}
	
	/**
	 * Returns Object position values.
	 * 
	 * Returns Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #centerX} </li>
	 * <li> float[1] {@link #centerY} </li>
	 * <li> float[2] {@link #radius} </li>
	 * </ul>
	 * 
	 * @return Values in order described above.
	 */
	@Override
	public float[] getValues() {
		return new float[]{ centerX, centerY, radius };
	}

	/**
	 * Sets Object position values.
	 * 
	 * Sets Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #centerX} </li>
	 * <li> float[1] {@link #centerY} </li>
	 * <li> float[2] {@link #radius} </li>
	 * </ul>
	 * 
	 * @param values order described above.
	 */
	@Override
	public void setValues(final float[] values) {
		this.centerX = values[0];
		this.centerY = values[1];
		this.radius	 = values[2];
	}
	
	/**
	 * Returns Object position certain value.
	 * 
	 * Returns Object position certain value, selected by parameter explained beneath:
	 * 
	 * <ul>
	 * <li> position == 0, return {@link #centerX} </li>
	 * <li> position == 1, return {@link #centerY} </li>
	 * <li> position == 2, return {@link #radius} </li>
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
				return centerX;
			case 1:
				return centerY;
			case 2:
				return radius;
			default:
				return -1;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public CircleShape(Parcel in)
	{
		super(new Paint(Paint.ANTI_ALIAS_FLAG));
		
		float[] values = new float[3];
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

	public static final Parcelable.Creator<CircleShape> CREATOR = new Parcelable.Creator<CircleShape>() {

		@Override
		public CircleShape createFromParcel(Parcel source) {
			return new CircleShape(source);
		}

		@Override
		public CircleShape[] newArray(int size) {
			return new CircleShape[size];
		}
	};
	
}
