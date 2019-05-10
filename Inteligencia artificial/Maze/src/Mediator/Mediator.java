package Mediator;

import java.util.Hashtable;

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Abstract structure of a Mediator.
 * Software design pattern that defines an object that controls how a set of other objects, denominated colleagues, interact with each other.
 *
 * The objective of this pattern is to loose a little the coupling among objects that may require a several amount of connections
 * by substituting 'n to n' connections to '1 to n' connections, which are way easier to control and vary independently.
 * The mediator ends working as a intermediary that holds references to all the implicated objects (colleagues) and these hold just a reference
 * to this shared mediator; when the colleagues need to communicate, request or inform something to another object implicated in this set of
 * connections it just has to invoke the method in the mediator and the latter will perform the required redirection for parameters and return values
 * to the implicated colleagues.
 */
public abstract class Mediator {

	protected Hashtable <String, Object> colleagues;
	
	public abstract void addColleague (String name, Object object);
	
	public abstract void deleteColleague (String name);
	
	public abstract Object retrieveColleague (String name);
	
}
