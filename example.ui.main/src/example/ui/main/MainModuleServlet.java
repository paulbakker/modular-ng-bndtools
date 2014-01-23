package example.ui.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amdatu.template.processor.TemplateContext;
import org.amdatu.template.processor.TemplateEngine;
import org.amdatu.template.processor.TemplateException;
import org.amdatu.template.processor.TemplateProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import example.ui.main.api.NgModule;
import example.ui.main.api.NgModuleRegistry;

public class MainModuleServlet extends HttpServlet implements NgModuleRegistry {
	private final Map<String, List<NgModule>> m_modules = new ConcurrentHashMap<>();
	private volatile TemplateEngine m_templateEngine;
	private volatile BundleContext m_bundleContext;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		URL templateFile = m_bundleContext.getBundle().getEntry("/app.vm");
		System.out.println(templateFile);
		try {
			TemplateProcessor processor = m_templateEngine.createProcessor(templateFile);
			TemplateContext context = m_templateEngine.createContext();
			List<NgModule> moduleNames = createListOfModules();

			context.put("modules", moduleNames);
			String output = processor.generateString(context);
			
			resp.getWriter().write(output);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	private List<NgModule> createListOfModules() {
		List<NgModule> moduleNames = new ArrayList<>();
		for (String bsn : m_modules.keySet()) {
			if(!bsn.equals(m_bundleContext.getBundle().getSymbolicName())) {
				moduleNames.addAll(m_modules.get(bsn));
			}
		}
		return moduleNames;
	}
	
	public void bundleAdded(Bundle bundle) {
		String webResourceHeader = (String)bundle.getHeaders().get("X-NgApp");
		String[] entries = webResourceHeader.split(",");
		
		ArrayList<NgModule> resourceEntries = new ArrayList<>();
		m_modules.put(bundle.getSymbolicName(), resourceEntries);
		
		for (String entry : entries) {
			String[] split = entry.split(";");
			if(split.length != 3) {
				return;
			}
			
			String path = split[0];
			String moduleFileName = split[1];
			String moduleName = split[2];
			
			if(path.startsWith("/")) {
				path = path.substring(1);
			} 
			
			resourceEntries.add(new NgModule(path, moduleFileName, moduleName, (String)bundle.getHeaders().get("X-NgLink")));
			
		}
	}
	
	public void bundleRemoved(Bundle bundle) {
		m_modules.remove(bundle.getSymbolicName());
	}

	@Override
	public List<NgModule> listModules() {
		return createListOfModules();
	}
}
