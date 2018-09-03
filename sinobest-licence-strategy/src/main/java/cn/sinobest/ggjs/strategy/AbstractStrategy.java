package cn.sinobest.ggjs.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sinobest.ggjs.strategy.builder.AbstractBuilder;
import cn.sinobest.ggjs.strategy.domain.PackageInfo;
import cn.sinobest.ggjs.strategy.domain.StrategyInfo;


public abstract class AbstractStrategy {

	protected List<AbstractBuilder> builderList = new ArrayList<>();
	
	protected PackageInfo packageInfo;
	
	protected StrategyInfo strategyInfo;
	
	public AbstractStrategy() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractStrategy(PackageInfo packageInfo, StrategyInfo strategyInfo) {
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
        return strategyInfo;
    }
    
    public void setBuilderList(List<AbstractBuilder> builderList) {
        this.builderList = builderList;
    }

    public void addBuilder(AbstractBuilder builder){
        builderList.add(builder);
    }
    
    public PackageInfo excute() throws Exception {
    	if (builderList.isEmpty()) {
    		throw new RuntimeException("is empty");
    	}
    	for(AbstractBuilder builder : builderList) {
    		builder.setPackageInfo(this.packageInfo);
    		this.packageInfo = builder.build();
    	}
    	return this.packageInfo;
    }
    
    public abstract boolean isValid() throws Exception;
    
    public abstract Long daysLeft() throws Exception;
    
    public abstract String getVariables(String key) throws Exception;
    
    public abstract Map<String,String> createKey();
}
