package cn.sinobest.ggjs.strategy.domain;

import java.util.Map;

public class StrategyInfo {
	
	protected String strategyName;
	
	protected Long certifiedDays; // 时长
	
	protected String publicKey; // 公钥
	
	protected String privateKey; // 密钥
	
	protected Map<String, String> variables; // 变量
	
	public String getStrategyName() {
		return this.strategyName;
	}
	
	public void setStrategyName(String stragegyName) {
		this.strategyName = strategyName;
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
