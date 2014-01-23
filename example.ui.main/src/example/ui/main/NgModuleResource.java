package example.ui.main;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import example.ui.main.api.NgModule;
import example.ui.main.api.NgModuleRegistry;

@Path("ngmodules")
public class NgModuleResource {
	private volatile NgModuleRegistry m_registry;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NgModule> listModules() {
		return m_registry.listModules();
	}
}
