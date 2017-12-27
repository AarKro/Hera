package d4jbots.enums;

public enum Version {
	VERSION("0.3.2");
	
	private String version;
	
	// constructor
	private Version(String version) {
		this.version = version;
	}

	// getters & setters
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
