package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;

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

}
