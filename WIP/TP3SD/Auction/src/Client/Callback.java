package Client;

import Interfaces.CallbackInterface;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.rmi.RemoteException;

public class Callback implements CallbackInterface {
	private ControllerMediator mediator;
	
	public Callback (ControllerMediator med) {
		this.mediator = med;
	}
	
	void setMediator(ControllerMediator mediator) {
		this.mediator = mediator;
	}
	
	@Override
	public void update() throws RemoteException {
		mediator.updateOffers();
	}
}
