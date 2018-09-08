package cn.sinobest.ggjs.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.*;

public class StrategyVerify extends AbstractStrategy {

	private static StrategyVerify instance = new StrategyVerify();
	


    public static StrategyVerify getInstance() {
		return instance;
	}
	
	public StrategyVerify() {

	}

	@Override
	public boolean isValid() throws Exception {
		return false;
	}

	@Override
	public Long daysLeft() throws Exception {
		return 1000L;
	}

	@Override
	public String getVariables(String key) throws Exception {
		return "default";
	}

	@Override
	public Map<String, String> createKey() {
		return new HashMap<>();
	}


}
