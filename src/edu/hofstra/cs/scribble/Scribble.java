package edu.hofstra.cs.scribble;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Scribble extends Activity {

	// These are menu options
	final static int THICK = 1;
	final static int THIN = 2;
	final static int CLEAR = 3;
	final static int BLACK = 4;
	final static int RED = 5;
	final static int BLUE = 6;

	ScribbleView sv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sv = new ScribbleView(this);
		setContentView(sv);
	}

	/*
	  Add menu.add() to add items to the menu.
	  Include the .setShowAsAction() method call to show in Action Bar.
	*/
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, BLACK, Menu.NONE, "Black").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, RED, Menu.NONE, "Red").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, BLUE, Menu.NONE, "Blue").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, THICK, Menu.NONE, "Thick").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, THIN, Menu.NONE, "Thin").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, CLEAR, Menu.NONE, "Clear").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	/*
	  This method is invoked whent the user selects an item from the menu.
	*/
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case THICK:
			sv.thickLines();  //Look for the thickLines() method in the ScribbleView class
			break;
		case THIN:
			sv.thinLines();
			break;
		case CLEAR:
			sv.clear();
			break;
		case BLUE:
			sv.blue();
			break;
		case RED:
			sv.red();
			break;
		case BLACK:
			sv.black();
			break;
		}
		return true;
	}
}

class ScribbleView extends View implements OnTouchListener {

	private float x = -1, y = -1, prev_x, prev_y;
	private Bitmap buffer;  //This is the buffer into which all drawing happens; then in the
	                        //onDraw() method the buffer is dumped on screen.
	private Paint p;

	public ScribbleView(Context context) {
		super(context);
		setOnTouchListener(this);
		p = new Paint();
	}

	public void thickLines(){
		p.setStrokeWidth(5);  //Draw 5 pixel wide lines
	}
	
	public void thinLines(){
		p.setStrokeWidth(1); //Draw 1 pixel wide lines
	}
	
	public void clear(){
		createBitmap();
		Canvas c = new Canvas(buffer);
		c.drawARGB(255, 255, 255, 255);
		invalidate(); // Ask to redraw the View
	}
	public void blue(){
		p.setARGB(255, 0, 0, 255);
		
	}
	public void red(){
		p.setARGB(255, 255, 0, 0);
		
	}
	public void black(){
		p.setARGB(255, 0, 0, 0);
		
	}
	
	
	public void onDraw(Canvas c) {
		if (buffer != null) {
			c.drawBitmap(buffer, 0, 0, p); //Dump the bitmap buffer on screen
		}
	}

	public boolean onTouch(View v, MotionEvent event) {

	    // Note the x and y coordinates of where the finger first touched the screen.
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			x = event.getX();
			y = event.getY();
			return true;
		}

		prev_x = x; //Save the previous point of contact
		prev_y = y;

		x = event.getX(); //Get the current point of contact
		y = event.getY();

		createBitmap();

		Canvas c = new Canvas(buffer);
		c.drawLine(prev_x, prev_y, x, y, p);  //Draw a line from the previous point to current point
		invalidate();  //Ask for the screen to be redrawn.
		return true;
	}

	/*
	  Create the buffer bitmap, if necessary.
	*/
	void createBitmap() {
		if (buffer == null) { //buffer has not yet been created?
			buffer = Bitmap.createBitmap(getWidth(), getHeight(),Bitmap.Config.ARGB_8888);
		}
	}
}