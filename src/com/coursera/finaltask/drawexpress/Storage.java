package com.coursera.finaltask.drawexpress;

import java.util.ArrayList;

/**
 * Used as a storage class for public static fields used in whole application.
 * 
 * @author Silvio
 *
 */
public final class Storage {

	private Storage(){}

	/**
	 * TAG used for logging, main application tag.
	 */
	public static final String APP_TAG = "APP_DRAWING";
	
	/**
	 * Represents key for storing and reading {@link ArrayList} of {@link DrawingShape} when pausing/resuming application.
	 */
	public static final String DRAWINGS_SAVE_INSTANCE = "drawings";
}
