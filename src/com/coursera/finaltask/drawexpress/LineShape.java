package com.coursera.finaltask.drawexpress;

import android.graphics.Paint;

/**
 * Line shape is used to store informations about line that will be drawn.
 * 
 * @author Silvio
 * 
 */
public class LineShape extends DrawingShape {

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
	
	
}
