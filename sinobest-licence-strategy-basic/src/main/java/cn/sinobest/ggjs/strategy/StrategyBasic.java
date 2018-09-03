package cn.sinobest.ggjs.strategy;

import java.io.File;
import java.security.KeyPair;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.sinobest.ggjs.builder.CopyBuilder;
import cn.sinobest.ggjs.builder.GrantBuilder;
import cn.sinobest.ggjs.builder.InjectCheckPointBuilder;
import cn.sinobest.ggjs.builder.LicenseCodeBuilder;
import cn.sinobest.ggjs.domain.PackageInfo;
import cn.sinobest.ggjs.domain.StrategyInfo;
import cn.sinobest.ggjs.util.ByteHex;
import cn.sinobest.ggjs.util.KeyUtil;

import org.apache.log4j.Logger;

public class StrategyBasic extends AbstractStrategy {
    
    private static final Logger LOGGER = Logger.getLogger(StrategyBasic.class);
    
    public StrategyBasic(PackageInfo packageInfo, StrategyInfo strategyInfo) {
    	super(packageInfo, strategyInfo);
    	init();
    }
    
    public StrategyBasic() {
    	
    }
    
	public void init() {
		GrantBuilder grantBuilder = new GrantBuilder(packageInfo, strategyInfo);
		addBuilder(grantBuilder);
		CopyBuilder copyBuilder = new CopyBuilder(packageInfo, strategyInfo);
		addBuilder(copyBuilder);
		LicenseCodeBuilder licenseCodeBuilder = new LicenseCodeBuilder(packageInfo, strategyInfo);
        addBuilder(licenseCodeBuilder);
	}
	
	@Override
	public boolean isValid() throws Exception {
		return false;
	}
	
	@Override
	public Long daysLeft() throws Exception {
		return -1L;
	}

	@Override
	public String getVariables(String key) throws Exception {
		return "";
	}

	@Override
	public Map<String, String> createKey() {
		Map<String, String> keyMap = new HashMap<>();
		
		// add created date
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		keyMap.put("createdOn", dateFormat.format(date));
		
		// add keys
		final KeyPair keyPair = KeyUtil.getKeyPair();
		keyMap.put("privateKey", ByteHex.convert(keyPair.getPrivate().getEncoded()));
		keyMap.put("publicKey", ByteHex.convert(keyPair.getPublic().getEncoded()));

		return keyMap;
	}

}
