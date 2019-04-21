package Observer;

import java.util.ArrayList;

public abstract class Subject {
	
	private ArrayList <Object> observers;
	
	private Object state;
	
	public abstract void attach (Object observer);
	
	public abstract void detach (Object observer);
	
	public abstract void notifyObservers ();
}
