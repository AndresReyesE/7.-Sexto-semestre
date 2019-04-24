package Mediator;

import java.util.Hashtable;

public abstract class Mediator {

	private Hashtable <String, Object> colleagues;
	
	public Hashtable<String, Object> getColleagues() {
		return colleagues;
	}
	
	public abstract void addColleague (String name, Object object);
	
	public abstract void deleteColleague (String name);
	
	public abstract Object retrieveColleague (String name);
	
}
