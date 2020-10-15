package by.gto.erip.face.arm.events;

import java.io.Serializable;
import java.security.Principal;
import java.util.EventObject;

public class AuthenticatedEvent extends EventObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Principal principal;

	public AuthenticatedEvent(Object source) {
		super(source);
	}

	public AuthenticatedEvent(Object source, Principal principal) {
		super(source);
		this.principal = principal;
	}

	public Principal getPrincipal() {
		return principal;
	}

}