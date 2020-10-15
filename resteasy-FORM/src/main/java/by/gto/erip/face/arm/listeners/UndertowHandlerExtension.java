package by.gto.erip.face.arm.listeners;

import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;
import java.io.PrintStream;
import javax.servlet.ServletContext;

public class UndertowHandlerExtension implements ServletExtension {
	public void handleDeployment(final DeploymentInfo deploymentInfo, final ServletContext servletContext) {
		PrintStream stream = System.out;
		stream.println("UndertowHandlerExtension.handleDeployment deploymentInfo: " + deploymentInfo);
		stream.println("UndertowHandlerExtension.handleDeployment servletContext: " + servletContext);
		DeploymentInfo info = deploymentInfo.addInnerHandlerChainWrapper(handler -> new AuthEventHandler(handler));
		stream.println("UndertowHandlerExtension.handleDeployment DeploymentInfo: " + info);
	}

	public UndertowHandlerExtension() {
	}
}