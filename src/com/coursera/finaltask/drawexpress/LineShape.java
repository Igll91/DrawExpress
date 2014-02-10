package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Line shape is used to store informations about line that will be drawn.
 * 
 * @author Silvio
 * 
 */
public class LineShape extends DrawingShape implements Parcelable{

	/**
	 * Starting X-axis position.
	 */
	private float startX;
	
	/**
	 * Starting Y-axis position.
	 */
	private float startY;
	
	/**
	 * Ending X-axis position.
	 */
	private float endX;
	
	/**
	 * Ending Y-axis position.
	 */
	private float endY;
	
	
	public LineShape(Paint paint) {
		super(paint);
	}

	/**
	 * Creates object with given positions and paint.
	 * 
	 * @param paint Paint used with this object.
	 * @param startX Starting x position of the line.
	 * @param startY Starting y position of the line.
	 * @param endX Ending x position of the line. 
	 * @param endY Ending y position of the line.
	 */
	public LineShape(Paint paint, float startX, float startY, float endX, float endY) {
		super(paint);
		this.startX = startX;
		this.startY = startY;
		this.endX 	= endX;
		this.endY 	= endY;
	}
	
	/**
	 * Returns Object position values.
	 * 
	 * Returns Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #startX} </li>
	 * <li> float[1] {@link #startY} </li>
	 * <li> float[2] {@link #endX} </li>
	 * <li> float[3] {@link #endY} </li>
	 * </ul>
	 * 
	 * @return Values in order described above.
	 */
	@Override
	public float[] getValues() {
		return new float[]{ startX, startY, endX, endY };
	}

	/**
	 * Sets Object position values.
	 * 
	 * Sets Object position values in order:
	 * <ul>
	 * <li> float[0] {@link #startX} </li>
	 * <li> float[1] {@link #startY} </li>
	 * <li> float[2] {@link #endX} </li>
	 * <li> float[3] {@link #endY} </li>
	 * </ul>
	 * 
	 * @param values order described above.
	 */
	@Override
	public void setValues(float[] values) {
		this.startX = values[0];
		this.startY = values[1];
		this.endX	= values[2];
		this.endY	= values[3];
	}

	/**
	 * Returns Object position certain value.
	 * 
	 * Returns Object position certain value, selected by parameter explained beneath:
	 * 
	 * <ul>
	 * <li> position == 0, return {@link #startX} </li>
	 * <li> position == 1, return {@link #startY} </li>
	 * <li> position == 2, return {@link #endX} </li>
	 * <li> position == 3, return {@link #endY} </li>
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
			return startX;
		case 1:
			return startY;
		case 2:
			return endX;
		case 3:
			return endY;
		default:
			return -1;
		}
	}
	
	public LineShape(Parcel in)
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

	public static final Parcelable.Creator<LineShape> CREATOR = new Parcelable.Creator<LineShape>() {

		@Override
		public LineShape createFromParcel(Parcel source) {
			return new LineShape(source);
		}

		@Override
		public LineShape[] newArray(int size) {
			return new LineShape[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}
}
