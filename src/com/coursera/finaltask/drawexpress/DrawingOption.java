package com.coursera.finaltask.drawexpress;

public class DrawingOption {

	private String name;
	private int imageResource;
	
	public DrawingOption() {
		// TODO Auto-generated constructor stub
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
