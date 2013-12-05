package nl.madebymark.maxmsp;

import nl.madebymark.Processing.HelloWorld;

import com.cycling74.max.*;

/**
 * This MXJ object demonstrates how to pass data from
 * MAX/MSP to a Processing object and back!
 *
 */
public class Example extends MaxObject
{
	private Processing p;
	private HelloWorld sketch;
	
	private static final String[] INLET_ASSIST = new String[]{
		"Insert number here!"
	};
	
	private static final String[] OUTLET_ASSIST = new String[]{
		"ENTER message is send when an enter is detected in Processing!"
	};
	
	public Example(Atom[] args)
	{
		declareInlets(new int[]{DataTypes.ALL});
		declareOutlets(new int[]{DataTypes.MESSAGE});
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		p = new Processing("nl.madebymark.Processing.HelloWorld");
		sketch = (HelloWorld) p.getSketch();
		sketch.setMaxMSP(this);
	}
    
	public void bang()
	{	p.show();
	}
    
	public void inlet(int i)
	{	sketch.setMessage("Max gave me a "+String.valueOf(i));
	}
  
}







