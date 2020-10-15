package by.gto.erip.face.arm.listeners;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

class SessionCounterListener implements HttpSessionListener {
	private static int totalActiveSession = 0;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		totalActiveSession++;
		HttpSession session = se.getSession();
		HttpSessionContext sessionContext = session.getSessionContext();
		long creationTime = session.getCreationTime();
		ServletContext servletContext = session.getServletContext();
		String id = session.getId();
		int maxInactiveInterval = session.getMaxInactiveInterval();
		Enumeration<String> attributeNames = session.getAttributeNames();
		System.out.println("SessionCounterListener.sessionCreated - add one session into counter");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		totalActiveSession--;
		HttpSession session = se.getSession();
		String blaaa = bla.get();
		try {
			ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletContext sc = session.getServletContext();

		String contextPath = sc.getContextPath();
		int majorVersion = sc.getMajorVersion();
		int effectiveMajorVersion = sc.getEffectiveMajorVersion();
		Set<String> resourcePaths = sc.getResourcePaths("/"); // список файлов в корне контекста
		Enumeration<Servlet> servlets = sc.getServlets();
		Enumeration<String> servletNames = sc.getServletNames();
		String realPath = sc.getRealPath("/bw_"); // путь к приложению в файловой системе
		String serverInfo = sc.getServerInfo(); // Имя сервера приложений
		Enumeration<String> initParameterNames = sc.getInitParameterNames(); // Имена параметров (можно задавать в
																				// web.xml)
		Enumeration<String> attributeNames = session.getAttributeNames(); // attributes
		while (attributeNames.hasMoreElements()) {
			String attName = attributeNames.nextElement();
			System.out.printf("sessionDestroyed attribute %s -> %s%n", attName, session.getAttribute(attName));
		}
		String servletContextName = sc.getServletContextName(); // web.xml <display-name>ARM BTO</display-name>
		Map<String, ? extends ServletRegistration> servletRegistrations = sc.getServletRegistrations(); // загруженные
																										// сервлеты
		Map<String, ? extends FilterRegistration> filterRegistrations = sc.getFilterRegistrations(); // загруженные
																										// фильтры
		SessionCookieConfig sessionCookieConfig = sc.getSessionCookieConfig(); // имена cookies
		Set<SessionTrackingMode> defaultSessionTrackingModes = sc.getDefaultSessionTrackingModes(); // режим
																									// отслеживания
																									// сессий:
		// <session-config><tracking-mode>COOKIE</tracking-mode>
		Set<SessionTrackingMode> effectiveSessionTrackingModes = sc.getEffectiveSessionTrackingModes();
		JspConfigDescriptor jspConfigDescriptor = sc.getJspConfigDescriptor();
		ClassLoader classLoader = sc.getClassLoader();
		String virtualServerName = sc.getVirtualServerName(); // имя виртуального сервера, для wildfly:
																// <subsystem
																// xmlns="urn:jboss:domain:undertow:3.1"><server
																// name="default-server">....
		HttpSessionContext sessionContext = session.getSessionContext();
		if (sessionContext != null) {
			Enumeration<String> ids = sessionContext.getIds();
		}
		Enumeration<String> attributeNames2 = session.getAttributeNames();
		System.out.println("SessionCounterListener.sessionDestroyed - deduct one session from counter");
	}

	@Inject
	@Named("bla")
	private Supplier<String> bla;

	@Resource(mappedName = "java:/env/jdbc/eripDS")
	private DataSource ds;
}