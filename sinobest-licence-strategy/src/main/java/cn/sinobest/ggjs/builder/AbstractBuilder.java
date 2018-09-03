package cn.sinobest.ggjs.builder;

import cn.sinobest.ggjs.domain.PackageInfo;
import cn.sinobest.ggjs.domain.StrategyInfo;

public abstract class AbstractBuilder {
	
	protected PackageInfo packageInfo;
	
	protected StrategyInfo strategyInfo;
	
	public AbstractBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractBuilder(PackageInfo packageInfo, StrategyInfo strategyInfo) {
		this.packageInfo = packageInfo;
		this.strategyInfo = strategyInfo;
	}
	
    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }
    
    public PackageInfo getPackageInfo(){
        return  packageInfo;
    }

    public void setStrategyInfo(StrategyInfo strategyInfo) {
        this.strategyInfo = strategyInfo;
    }
    
    public StrategyInfo getStrategyInfo(){
        return  strategyInfo;
    }
	
    public abstract PackageInfo build() throws Exception; 
}
