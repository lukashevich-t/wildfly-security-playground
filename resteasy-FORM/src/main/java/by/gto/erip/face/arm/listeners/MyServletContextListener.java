package by.gto.erip.face.arm.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent sce) {
		if (sce != null && sce.getServletContext() != null) {
			System.out.println("MyServletContextListener.contextInitialized: VirtualServerName "
					+ sce.getServletContext().getVirtualServerName());
		}
		System.out.println(sce);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(sce);
	}
}
