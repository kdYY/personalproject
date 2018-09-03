package cn.sinobest.ggjs.domain;

import java.util.Map;

public class StrategyInfo {
	
	protected String stragegyName;
	
	protected Long certifiedDays; // 时长
	
	protected String publicKey; // 公钥
	
	protected String privateKey; // 密钥
	
	protected Map<String, String> variables; // 变量
	
	public String getStragegyName() {
		return this.stragegyName;
	}
	
	public void setStragegyName(String stragegyName) {
		this.stragegyName = stragegyName;
	}

	public Long getCertifiedDays() {
		return certifiedDays;
	}
	
	public void setCertifiedDays(Long certifiedDays) {
		this.certifiedDays = certifiedDays;
	}
	
    public Map<String, String> getVariables() {
        return variables;
    }
    
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	
	public String getPrivateKey() {
		return this.privateKey;
	}
	
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

}
