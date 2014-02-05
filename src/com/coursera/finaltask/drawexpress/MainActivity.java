package com.coursera.finaltask.drawexpress;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {

	private DrawingView drawingView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawingView = (DrawingView) findViewById(R.id.drawingView);
		
		Spinner tools_spinner = (Spinner) findViewById(R.id.spinner_drawing_option_selector);
		tools_spinner.setAdapter(new MySpinnerAdapter(this, R.layout.custom_spinner));
		tools_spinner.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case  R.id.action_newDrawing:
			// insert dialog here to ask yes or cancel!
			drawingView.newDrawing();
			break;
		case R.id.action_undo:
			drawingView.undoMove();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		drawingView.onPauseMySurfaceView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		drawingView.onResumeMySurfaceView();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		drawingView.setSelected_drawing_option(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
