package nl.madebymark.maxmsp;
// MAX/MSP version
//
//You might want to remove this if you just dump the
// .java and .class file directly in MAX/MSP's java directory
// ("..\Max 5.0\Cycling '74\java")

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

import processing.core.PApplet;

/**
 * The Processing class encapsulates a sketch (PApplet) into a window (Frame)
 * 
 * Usage:
 * 
 * You can run a sketch instantly using the static method:
 * 		Processing.run('nl.madebymark.Processing.HelloWorld') //runs a non-persistent hello-sketch
 *
 * Or you could create a instance if you want more control.
 * This sketch is non-persistent, which means it is disposed when the
 * window is closed:
 * 		Processing p = new Processing('nl.madebymark.Processing.HelloWorld,false);
 *
 * Creating a persistent sketch (default):
 * 		Processing p = new Processing('nl.madebymark.Processing.HelloWorld)
 * 
 * Persistent sketches run continue to run in the background 
 * after the first p.show() is called. You need to call p.dispose()
 *  to free resources taken by the window (Frame) and sketch (PApplet).
 * 
 * Non-persistent sketches are disposed when the windows is closed.
 * They are created again if necessary when p.show() is called.
 * 
 * Showing a sketch:
 * 		p.show(); 
 * 
 * Using the sketch (PApplet) to do stuff:
 * 		p.getSketch(); 
 * 
 * Other methods:
 * 		p.setResizable(boolean)
 * 		p.setLocation(x,y)
 * 		p.setAlwaysOnTop(boolean)
 * 
 * MAX/MSP: 
 * ------------------------------------------------------------
 * You can use this class in MAX/MSP. This is straight forward:
 *  	[mxj Processing nl.madebymark.Processing.HelloWorld]
 *  
 *  Then use "show","dispose",etc to control the processing sketch.
 *  
 *  You need to write your own MXJ object if you want to pass data
 *  from MAX/MSP to Processing and back. 
 *  
 *  For example from MAX to Processing, add in the MAX class:
 *  	p = new Processing("nl.madebymark.Processing.HelloWorld");
 *  	p.getSketch().setMessage("Max says: Hello Processing");
 *  
 *  Be sure to add the processing "core.jar" to MAXMSP classpath
 *  Copy it from "processing-1.5.1/lib/" to "../max 5.0/Cycling '74/java/lib".
 *  
 *  From Processing back to Max is a little more difficult,
 *  but can be done by giving Processing a reference to the
 *  MAX/MSP instance; i.e in MXJ:
 *  	p.setMaxMsp(this); //sets a MaxObject called maxmsp
 *  
 *  And in Processing:
 *  	maxmsp.outlet(0,"Hello Max, this is Processing!");
 *  
 *  These processing sketches can not run stand-alone anymore,
 *  unless you include the "max.jar". 
 *  "Sketch -> Add file -> browse to ../Max 5.0/Cycling '74/java/lib/max.jar"
 *  Or copy it into a "code" subfolder of your sketch.
 *  
 *  MATLAB
 *  ---------------------------------------------------------
 *  Run install.m to add "core.jar" and "max.jar" to your classpath..
 *  or do it manually:
 *  
 *  Add the path to "core.jar" located in "../processing-1.5.1/lib" to
 *  Matlab's classpath, located in "[matlabroot]/toolbox/local/classpath.txt"
 *  
 *  Add the path of "max.jar" located in "../Max 5.0/Cycling '74/java/lib/max.jar"
 *  to the classpath. (or comment out the MaxMSP code in the constructors!)
 *  
 *  Now you can use the Processing class like this:
 *  
 *  javaclasspath('classes'); %should point to the class files!
 *  import nl.madebymark.Processing.*
 *  Processing.run('nl.madebymark.Processing.HelloWorld');
 *  p = Processing('nl.madebymark.Processing.HelloWorld',true);
 *  p.show;
 *  p.dispose;
 *  methodsview Processing
 *  methods Processing
 *  methods Processing -full
 *  clear java
 *  clear classes
 */
public class Processing extends MaxObject {
	public static void run(String sketchname)
	{	Processing p = new Processing(sketchname,false);
		p.show();
	}
		
	private Frame frame;
	private PApplet sketch;	
	private boolean firstshow;
	private boolean persistent;
	private String sketchname;
	
	public Processing()
	{	//max msp support. Comment out if you don't want to include
		// the "max.jar" in Matlab and lose the MAX/MSP support
		bail("You must provide a sketchname, e.g. [mxj nl.madebymark.Processing.Processing nl.madebymark.Processing.HelloWorld]");		
	}
	
	/**
	 * Creates a persistent sketch
	 * @param _sketchname the full classname of the sketch
	 */
	public Processing(String _sketchname)
	{	this(_sketchname,true);
	}
	/**
	 * Creates a sketch
	 * @param _sketchname the full classname of the sketch
	 * @param _persistent true=dispose manually, run in background. false=automatisch disposal when window is closed
	 */
	public Processing(String _sketchname, boolean _persistent)
	{	//max msp support. Comment out if you don't want MAX/MSP support 
		//and include the "max.jar" in Matlab
		declareInlets(new int[]{DataTypes.MESSAGE});
		declareOutlets(NO_OUTLETS);
		createInfoOutlet(false);
		setInletAssist(0, "Messages: show, setAlwaysOnTop(boolean), setLocation(x,y), setResizable(boolean)");
		//end max msp support
		
		sketchname = _sketchname;
		persistent = _persistent;
		reset();
	}
	/**
	 *  Get the PApplet sketch 
	 */
	public PApplet getSketch()
	{	return sketch;		
	}
	
	/**
	 *  Show the Sketch - create / initialize if necessary 
	 */
	public void show()
	{  	//initialize applet, start everything...
		if(frame == null) reset();
	   	if(firstshow) 
	   	{  	sketch.init();
			sketch.start();
			firstshow=  false;
   	    } 
	   	frame.setVisible(true);	       
   		sketch.redraw();	   
	}
	
	public void setAlwaysOnTop(boolean value)
	{	if(frame != null) frame.setAlwaysOnTop(value);
	}
	
	/** 
	 * Set window location on the screen. (0,0) is top-left
	 * @param _x
	 * @param _y
	 */
	public void setLocation(int _x, int _y)
	{	if(frame != null) frame.setLocation(_x, _y);
	}
	
	/**
	 * Sets whether the windows is resizable or not
	 * (not tested torougly - this may not work as expected
	 * for all sketches!)
	 * @param value
	 */
	public void setResizable(boolean value)
	{	if (frame != null) frame.setResizable(value);
	}
	
	/**
	 * Private method that disposes the current frame and sketch,
	 * and creates a new one from scratch
	 */
	private void reset()
	{	if (frame != null) dispose();
		try {
			sketch = (PApplet) Class.forName(sketchname).newInstance();
		} catch (Exception e) {
			System.err.println("Could not create Processing sketch: "+sketchname);
		}
		firstshow = true;
		frame = new Frame();
		
		//layout
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(0);
        fl.setVgap(0);
        frame.setLayout(fl);
        frame.add(sketch);
       
        //set frame properties
        int mid = sketchname.lastIndexOf('.') + 1;
        frame.setTitle(sketchname.substring(mid));	       
        frame.setLocation(100, 100);
        frame.setAlwaysOnTop(false);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
    	      public void windowClosing(WindowEvent we){
    	    	  frame.setVisible(false);
    	    	  if (!persistent) dispose();	    	    	  
    	      }	    		    	      
    	      public void windowActivated(WindowEvent we) {
    	    	   frame.pack();
    	      }	   
    	});
        frame.addComponentListener(new ComponentListener(){
   			@Override 
			public void componentResized(ComponentEvent arg0) {
				sketch.resize(frame.getWidth(),frame.getHeight());	
				sketch.redraw();
			}				
			@Override public void componentHidden(ComponentEvent arg0) {}
			@Override public void componentMoved(ComponentEvent arg0) {}
			@Override public void componentShown(ComponentEvent arg0) {		}
       });
	}
	
	/**
	 * Free resources used by the window (Frame) and sketch (PApplet)
	 * This is called the window is closed if persistent is false.
	 * If persistent is true, you will manually have to call this
	 * function!!!
	 */
	public void dispose()
	{ 	//frees the resources (at least that *should* happen!)
		if (frame != null)
		{	frame.dispose();    	
    		frame = null;	
		}
		if (sketch != null)
		{	sketch.dispose();
    		sketch = null;
		}

	}
	
	/**
	 * Making sure that resources really get freed!
	 */
	public void finalize()
	{   //make sure Frame and Sketch are disposed of!
		dispose();
		try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
