package com.coursera.finaltask.drawexpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnItemSelectedListener, OnSeekBarChangeListener, OnCheckedChangeListener {

	/**
	 * View used to show and store {@link ShapeDrawable} that user draws.
	 */
	private DrawingView drawingView;
	
	/**
	 * Shows currently mixed color. Color is mixed with {@link #seekBarR} {@link #seekBarG} & {@link #seekBarB} in {@link #onProgressChanged(SeekBar, int, boolean)} method. 
	 */
	private ImageView pickedColorIV;
	
	/*
	 *  Seek bars responsible for mixing color.
	 */
	private SeekBar seekBarR;
	private SeekBar seekBarG;
	private SeekBar seekBarB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawingView = (DrawingView) findViewById(R.id.drawingView);
		
		Spinner tools_spinner = (Spinner) findViewById(R.id.spinner_drawing_option_selector);
		tools_spinner.setAdapter(new MySpinnerAdapter(this, R.layout.custom_spinner));
		tools_spinner.setOnItemSelectedListener(this);
		
		pickedColorIV = (ImageView) findViewById(R.id.imageView_pickedColor);
		
		seekBarR = (SeekBar) findViewById(R.id.seekBar_R);
		seekBarG = (SeekBar) findViewById(R.id.seekBar_G);
		seekBarB = (SeekBar) findViewById(R.id.seekBar_B);
		
		seekBarR.setOnSeekBarChangeListener(this);
		seekBarG.setOnSeekBarChangeListener(this);
		seekBarB.setOnSeekBarChangeListener(this);
		
		ToggleButton fillToggle = (ToggleButton) findViewById(R.id.toggleButton_filled);
		fillToggle.setOnCheckedChangeListener(this);
		
		SeekBar thickness = (SeekBar) findViewById(R.id.seekbar_thickness);
		thickness.setOnSeekBarChangeListener(this);
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
			getNewDrawingDialog().show();
			break;
		case R.id.action_undo:
			drawingView.undoMove();
			break;
		case R.id.action_Save:
			getSaveImageDialog().show();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(Storage.APP_TAG, "onPause");
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
		// NO NEED BUT MUST IMPLEMENT!
	}

	/**
	 * Creates new Dialog that asks user to confirm action.
	 * 
	 * Creates dialog with yes and no options. On yes, drawingView is cleaned. 
	 * 
	 * @return Dialog described above.
	 */
	private Dialog getNewDrawingDialog()
	{
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle(R.string.string_new_drawing_dialog_title);
		b.setMessage(R.string.string_new_drawing_dialog_message);
		b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				drawingView.newDrawing();
			}
		});
	
		b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		b.setCancelable(true);
		
		return b.create();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		switch(seekBar.getId())
		{
		case R.id.seekBar_R:
		case R.id.seekBar_G:
		case R.id.seekBar_B:
			int r = seekBarR.getProgress();
			int g = seekBarG.getProgress();
			int b = seekBarB.getProgress();
			
			pickedColorIV.setBackgroundColor(Color.argb(255, r, g, b));
			drawingView.getPaint().setARGB(255, r, g, b);
			break;
			
		case R.id.seekbar_thickness:
			drawingView.getPaint().setStrokeWidth(progress);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// NOT NEEDED BUT MUST IMPLEMENT !! 
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// NOT NEEDED BUT MUST IMPLEMENT !! 
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		drawingView.getPaint().setStyle(isChecked?Paint.Style.FILL:Paint.Style.STROKE);
	}
	
	/**
	 * Restores application state before resuming.
	 * 
	 * <p>
	 * Restores list of drawings from Bundle. Sets those drawings to {@link DrawingView} so it 
	 * can reproduce what you have drawn before.
	 * </p>
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		ArrayList<DrawingShape> arrayList = savedInstanceState.getParcelableArrayList(Storage.DRAWINGS_SAVE_INSTANCE);
		List<DrawingShape> list = new CopyOnWriteArrayList<DrawingShape>();
		
		for(DrawingShape s: arrayList)
			list.add(s);
		
		drawingView.setListOfShapesToBeDrawn(list);
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	/**
	 * Saves application state before pausing.
	 * 
	 * <p>
	 * Saves all drawing moves from {@link DrawingView} to Bundle so it can be restored when
	 * application is called again.
	 * </p>
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		List<DrawingShape> list = drawingView.getListOfShapesToBeDrawn();
		ArrayList<DrawingShape> arrayList = new ArrayList<DrawingShape>();
		
		for(DrawingShape s: list)
			arrayList.add(s);
		
		outState.putParcelableArrayList(Storage.DRAWINGS_SAVE_INSTANCE, arrayList);
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * Saves image in public pictures directory.
	 * 
	 * <p>
	 * 	Saves image if possible with given filename. Saved picture format is JPEG.
	 *  If saving fails user will be notified with {@link Toast} message.
	 * </p>
	 * 
	 * @param filename Name of the file that will be saved.
	 */
	private void saveImage(String filename)
	{
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path.mkdirs();
		
		File file = new File(path, filename +".jpg");
		if(file.exists())
		{
			Toast.makeText(this, R.string.file_exists, Toast.LENGTH_LONG).show();
			return;
		}
		FileOutputStream stream = null;
		try 
		{
			stream = new FileOutputStream(file);
			Bitmap bitmap = drawingView.getmBitmap();
			bitmap.compress(CompressFormat.JPEG, 100, stream);
			
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri contentUri = Uri.fromFile(file);
			mediaScanIntent.setData(contentUri);
			getApplicationContext().sendBroadcast(mediaScanIntent);
			
			Toast.makeText(this, "Picture saved as: " +file.getName(), Toast.LENGTH_LONG).show();
			
		} catch (FileNotFoundException e) {
			Log.e(Storage.APP_TAG, "saveImage: " +e.getMessage());
			Toast.makeText(this, R.string.error_saving_image, Toast.LENGTH_LONG).show();
		}
		finally{
			try {
				if(stream != null)
					stream.close();
			} catch (IOException e) {
				Log.e(Storage.APP_TAG, "saveImage: " +e.getMessage());
				Toast.makeText(this, "Error while saving image!", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	/**
	 * Creates Dialog with {@link EditText} widget in which user enters filename.
	 * 
	 * Max one line and 10 characters allowed.
	 * 
	 * @return Dialog described above.
	 */
	private Dialog getSaveImageDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(R.string.saveImage_dialog_title);
		alert.setMessage(R.string.saveImage_dialog_text);

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setMaxLines(1);
		input.setHint(R.string.saveImage_dialog_hint);
		input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
		alert.setView(input);

		alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// check input text... 
				String filename = input.getText().toString();
				if(filename.length() < 1)
					return;
				// if everything is ok ... go save image
				saveImage(filename);
			}
		});

		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		
		return alert.create();
	}
}
