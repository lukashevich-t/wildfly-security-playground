package by.gto.erip.face.arm.listeners;

import by.gto.erip.face.arm.events.AuthenticatedEvent;
import by.gto.erip.face.arm.events.LoggedOutEvent;
import io.undertow.security.api.NotificationReceiver;
import io.undertow.security.api.SecurityContext;
import io.undertow.security.api.SecurityNotification;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import javax.enterprise.inject.spi.CDI;

/**
 *
 * http://arjan-tijms.omnifaces.org/2012/12/bridging-undertows-authentication.html
 */
public class AuthEventHandler implements HttpHandler {

	private final AuthEventHandler.SecurityNotificationReceiver NOTIFICATION_RECEIVER = new AuthEventHandler.SecurityNotificationReceiver();
	private HttpHandler next = null;

	public AuthEventHandler() {
	}

	public AuthEventHandler(HttpHandler next) {
		this.next = next;
	}

	public void handleRequest(HttpServerExchange exchange) throws Exception {
		SecurityContext securityContext = exchange.getSecurityContext();
		securityContext.registerNotificationReceiver(NOTIFICATION_RECEIVER);
		System.out.println("AuthEventHandler: securityContext: " + securityContext);
		next.handleRequest(exchange);
	}

	private static class SecurityNotificationReceiver implements NotificationReceiver {
		public void handleNotification(SecurityNotification notification) {
			switch (notification.getEventType()) {
			case AUTHENTICATED:
				CDI.current().getBeanManager()
						.fireEvent(new AuthenticatedEvent(notification, notification.getAccount().getPrincipal()));
				break;
			case LOGGED_OUT:
				CDI.current().getBeanManager()
						.fireEvent(new LoggedOutEvent(notification, notification.getAccount().getPrincipal()));
				break;
			default:
				break;
			}
		}
	}
}