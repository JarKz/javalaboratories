package jarkz.lab10.types;

public enum Output {
	DECOMPILED ("/decompiled"),
	REFORMATTED ("/reformatted");

	private String parentDir = "/output";
	private String nestedDir;

	private Output(String nestedDir){
		this.nestedDir = nestedDir;
	}

	public String getPath(){
		return parentDir + nestedDir;
	}
}
