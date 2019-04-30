package Mediator;

import java.util.Hashtable;

/**
 * Abstract structure of a Mediator.
 * Software design pattern
 */
public abstract class Mediator {

	protected Hashtable <String, Object> colleagues;
	
	public abstract void addColleague (String name, Object object);
	
	public abstract void deleteColleague (String name);
	
	public abstract Object retrieveColleague (String name);
	
}
