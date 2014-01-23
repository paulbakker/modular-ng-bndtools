package example.ui.main.api;

public class NgModule {
	private final String path;
	private final String moduleFileName;
	private final String moduleName;
	private final String linkName;

	public NgModule(String path, String fileName, String moduleName, String linkName) {
		this.path = path;
		this.moduleFileName = fileName;
		this.moduleName = moduleName;
		this.linkName = linkName;
	}

	public String getPath() {
		return path;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getLinkName() {
		return linkName;
	}

	public String getModuleFileName() {
		return moduleFileName;
	}
}
