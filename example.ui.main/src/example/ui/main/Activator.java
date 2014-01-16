package example.ui.main;

import java.util.Properties;

import javax.servlet.Servlet;

import org.amdatu.template.processor.TemplateEngine;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext bundleContext, DependencyManager dm) throws Exception {
		Properties props = new Properties();
		props.put("alias", "/js/app.js");
		dm.add(createComponent()
				.setInterface(Servlet.class.getName(), props)
				.setImplementation(MainModuleServlet.class)
				.add(createServiceDependency().setService(TemplateEngine.class))
				.add(createBundleDependency().setFilter("(X-Web-Resource-Version=1.1)").setCallbacks("bundleAdded", "bundleRemoved")));
	
	}
	
	@Override
	public void destroy(BundleContext bundleContext, DependencyManager dm) throws Exception {

	}
}