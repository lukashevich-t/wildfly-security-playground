package by.gto.erip.face.arm.events;

import java.io.Serializable;
import java.security.Principal;
import java.util.EventObject;

public class LoggedOutEvent extends EventObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object source;

	private Principal principal;

	public LoggedOutEvent(Object source) {
		super(source);
		this.source = source;
	}

	public LoggedOutEvent(Object source, Principal principal) {
		super(source);
		this.source = source;
		this.principal = principal;
	}
}