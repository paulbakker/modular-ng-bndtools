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

public class MainModuleServlet extends HttpServlet {
	private final Map<String, List<String>> m_modules = new ConcurrentHashMap<>();
	private volatile TemplateEngine m_templateEngine;
	private volatile BundleContext m_bundleContext;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		URL templateFile = m_bundleContext.getBundle().getEntry("/app.vm");
		System.out.println(templateFile);
		try {
			TemplateProcessor processor = m_templateEngine.createProcessor(templateFile);
			TemplateContext context = m_templateEngine.createContext();
			List<String> moduleNames = new ArrayList<>();
			for (String bsn : m_modules.keySet()) {
				if(!bsn.equals(m_bundleContext.getBundle().getSymbolicName())) {
					moduleNames.addAll(m_modules.get(bsn));
				}
			}

			context.put("modules", moduleNames);
			String output = processor.generateString(context);
			
			resp.getWriter().write(output);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	public void bundleAdded(Bundle bundle) {
		String webResourceHeader = (String)bundle.getHeaders().get("X-Web-Resource");
		String[] entries = webResourceHeader.split(",");
		
		ArrayList<String> resourceEntries = new ArrayList<String>();
		m_modules.put(bundle.getSymbolicName(), resourceEntries);
		
		for (String entry : entries) {
			String[] split = entry.split(";");
			String path = split[0];
			if(path.startsWith("/")) {
				resourceEntries.add(path.substring(1));
			} else {
				resourceEntries.add(path);
			}
			
		}
	}
	
	public void bundleRemoved(Bundle bundle) {
		m_modules.remove(bundle.getSymbolicName());
	}
}
