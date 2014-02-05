package com.coursera.finaltask.drawexpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySpinnerAdapter extends ArrayAdapter<String> {

	private LayoutInflater inflater;

	private static String[] getArrayOfNames() 
	{
		String[] ret = new String[DrawingView.MAP_DRAWING_TOOLS.size()];
		int counter = 0;
		
		for (Object x: DrawingView.MAP_DRAWING_TOOLS.values().toArray())
		{
			DrawingOption option;
			if(x instanceof DrawingOption)
			{
				option = (DrawingOption) x;
				ret[counter++] = option.getName();
			}
		}
		return ret;
	}

	public MySpinnerAdapter(Context context, int resource) 
	{
		super(context, resource, getArrayOfNames());
		inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View mySpinner = inflater.inflate(R.layout.cutom_spinner_drop_down, parent, false);

		ImageView icon = (ImageView) mySpinner.findViewById(R.id.imageview_selected_drawing_tool);
		icon.setImageResource(DrawingView.MAP_DRAWING_TOOLS.get(position).getImageResource());

		return mySpinner;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);

		TextView text = (TextView) mySpinner.findViewById(R.id.textview_selected_drawing_tool);
		text.setText(DrawingView.MAP_DRAWING_TOOLS.get(position).getName());

		ImageView icon = (ImageView) mySpinner.findViewById(R.id.imageview_selected_drawing_tool);
		icon.setImageResource(DrawingView.MAP_DRAWING_TOOLS.get(position).getImageResource());

		return mySpinner;
	}

}
