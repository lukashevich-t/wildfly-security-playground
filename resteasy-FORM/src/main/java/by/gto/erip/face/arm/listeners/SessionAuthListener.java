package by.gto.erip.face.arm.listeners;

import by.gto.erip.face.arm.events.AuthenticatedEvent;
import by.gto.erip.face.arm.events.LoggedOutEvent;
import java.io.PrintStream;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

@SessionScoped
public class SessionAuthListener implements Serializable {
	private static final long serialVersionUID = 1L;

	public void onAuthenticated(@Observes AuthenticatedEvent event) {
		String username = event.getPrincipal().getName();
		System.out.println("SessionAuthListener.onAuthenticated username=" + username);
		// Do something with name, e.g. audit,
		// load User instance into session, etc
	}

	public void onLoggedOut(@Observes LoggedOutEvent event) {
		PrintStream stream = System.out;
		stream.println("SessionAuthListener.onLoggedOut event=" + event);
	    stream.println("SessionAuthListener.onLoggedOut	event class" + event.getClass().getCanonicalName());

	    Object source = event.getSource();
	    stream.println("SessionAuthListener.onLoggedOut	source class" + source.getClass().getCanonicalName());
	    
		// take some action, e.g. audit, null out User, etc
	}

}