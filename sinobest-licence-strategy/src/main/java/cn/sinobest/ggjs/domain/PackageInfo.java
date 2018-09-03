package cn.sinobest.ggjs.domain;

public class PackageInfo {

	protected String packageName;
	
	protected String packageVersion;
	
	protected String packagePath;
	
	protected String mainClass; // package-name:class-name
	
	protected String targetPath; // 存放路径，若无使用packagePath
	
	protected final StringBuffer buildLog = new StringBuffer();

	public String getPackgeName() {
		return this.packageName;
	}
	
	public void setPackgeName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getPackgeVersion() {
		return this.packageVersion;
	}
	
	public void setPackgeVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	
	public String getPackagePath() {
		return this.packagePath;
	}
	
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	
	public String getMainClass() {
		return this.mainClass;
	}
	
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}
	
	public String getTargetPath() {
		return this.targetPath;
	}
	
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
	// 记录策略修改日志log
	public String appendBuildLog(String log) {
		buildLog.append(log);
		return buildLog.toString();
	}
	
	public String getBuildLog(){
		return "buildLog{ "+
				"packageName='" + packageName + '\'' +
				", packageVersion='" + packageVersion + '\'' +
				buildLog.toString()
				+ "}";
	}
}
