package by.gto.erip.face.arm.listeners;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MyAttributeListener implements HttpSessionAttributeListener {
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.printf("MyAttributeListener.attributeAdded : %s -> %s%n", event.getName(), event.getValue());
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.printf("MyAttributeListener.attributeRemoved : %s -> %s%n", event.getName(), event.getValue());
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.printf("MyAttributeListener.attributeReplaced : %s -> %s%n", event.getName(), event.getValue());
	}
}
