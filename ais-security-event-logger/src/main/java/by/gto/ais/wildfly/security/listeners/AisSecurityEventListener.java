package by.gto.ais.wildfly.security.listeners;

import java.io.PrintStream;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.wildfly.security.auth.server.event.SecurityAuthenticationFailedEvent;
import org.wildfly.security.auth.server.event.SecurityAuthenticationSuccessfulEvent;
import org.wildfly.security.auth.server.event.SecurityEvent;

/**
 * Example of custom-security-event-listener
 *
 * @author <a href="mailto:jkalina@redhat.com">Jan Kalina</a>
 */
public class AisSecurityEventListener implements Consumer<SecurityEvent> {

	private String myAttribute;
	private String dsJndiName;
	private DataSource dataSource = null;

	private DataSource getDataSource() throws NamingException {
		if (this.dataSource == null) {
			System.out.println("Retrieving datasource:");
			InitialContext iniCtx = new InitialContext();
			this.dataSource = (DataSource) iniCtx.lookup(dsJndiName);
			System.out.println("datasource is " + this.dataSource);
		}
		return this.dataSource;
	}

	// receiving configuration from subsystem
	public void initialize(Map<String, String> configuration) throws NamingException {
		myAttribute = configuration.get("myAttribute");
		dsJndiName = configuration.getOrDefault("dsJndiName", "java:jboss/datasources/ExampleDS");
		System.out.printf("MySecurityEventListener initialized with myAttribute = %s, dsJndiName = %s", myAttribute,
				dsJndiName);
		System.out.println("all attributes:\n");
		for (Map.Entry<String, String> entry : configuration.entrySet()) {
			System.out.printf("%s -> %s%n", entry.getKey(), entry.getValue());
		}
	}

	public void accept(SecurityEvent securityEvent) {
		PrintStream stream = System.err;
		int eventId = 0;
		String login = "";
		stream.println("MySecurityEventListener.accept class: " + securityEvent.getClass().getCanonicalName());
		stream.println("securityEvent is " + securityEvent.getClass().getCanonicalName());
		stream.println("securityEvent " + securityEvent.toString());
		if (securityEvent instanceof SecurityAuthenticationSuccessfulEvent) {
			eventId = 1;
			login = securityEvent.getSecurityIdentity().getPrincipal().getName();
			stream.printf("Authenticated user \"%s\"\n", login);
		} else if (securityEvent instanceof SecurityAuthenticationFailedEvent) {
			eventId = 2;
			login = ((SecurityAuthenticationFailedEvent) securityEvent).getPrincipal().getName();
			stream.printf("Failed authentication as user \"%s\"\n", login);
		} else {
			return;
		}
		try (Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO logs.security_audit (event_id, login) VALUES (?, ?)")) {

			stmt.setInt(1, eventId);
			stmt.setString(2, login);
			stmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			stream.println(e);
		}
	}
}