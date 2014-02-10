package com.coursera.finaltask.drawexpress;

/**
 * Represents drawing option. (POJO)
 * 
 * <p>
 * 	Saves drawing option name and image resource (for example r.drawable.image_xyz).
 * </p>
 * 
 * @author Silvio
 *
 */
public class DrawingOption {

	private String name;
	private int imageResource;
	
	public DrawingOption() {
	}
	
	public DrawingOption(String name, int imageResource)
	{
		this.name = name;
		this.imageResource = imageResource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageResource() {
		return imageResource;
	}

	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}
	
	
}
