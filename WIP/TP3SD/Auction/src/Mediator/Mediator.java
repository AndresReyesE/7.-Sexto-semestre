package Mediator;

import java.util.Hashtable;
import java.util.Map;

public abstract class Mediator {

	protected Hashtable <String, Object> colleagues;
	
	public abstract void addColleague (String name, Object object);
	
	public abstract void deleteColleague (String name);
	
	public abstract Object retrieveColleague (String name);
	
}
