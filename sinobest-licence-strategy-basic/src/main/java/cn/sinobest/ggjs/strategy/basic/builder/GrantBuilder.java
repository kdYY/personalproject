package cn.sinobest.ggjs.strategy.basic.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.sinobest.ggjs.strategy.basic.exception.LicenseNotFoundException;
import cn.sinobest.ggjs.strategy.basic.util.ByteHex;
import cn.sinobest.ggjs.strategy.basic.util.DateParser;
import cn.sinobest.ggjs.strategy.basic.util.FileUtil;
import cn.sinobest.ggjs.strategy.basic.util.SignatureUtil;
import cn.sinobest.ggjs.strategy.basic.util.StringUtils;
import cn.sinobest.ggjs.strategy.builder.AbstractBuilder;
import cn.sinobest.ggjs.strategy.domain.PackageInfo;
import cn.sinobest.ggjs.strategy.domain.StrategyInfo;


public class GrantBuilder extends AbstractBuilder {

    private List<String> list = new ArrayList<String>();
    private Properties properties = new Properties();
    private String output;
    
	public GrantBuilder(PackageInfo packageInfo, StrategyInfo strategyInfo) {
		this.packageInfo = packageInfo;
		this.strategyInfo = strategyInfo;
	}
	
	@Override
	public PackageInfo build() throws Exception {

		String packagePath = packageInfo.getPackagePath();
		String licFilePath = packagePath + File.pathSeparator + "license.lic";
		String privateKey = strategyInfo.getPrivateKey();
		long certifiedDays = strategyInfo.getCertifiedDays();
		Map<String, String> variablesMap = strategyInfo.getVariables();
		
		File f = new File(licFilePath);
        try {
        	if (f.exists()) {
        		new FileUtil(new FileInputStream(f)).read(this.list, this.properties);
        	} else {
        		throw new LicenseNotFoundException();
        	}
        }
        catch (Exception ex) {
            try {
				throw new LicenseNotFoundException();
			} catch (LicenseNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // set expiration
        Date date = new Date();
        final DateParser dateParser = new DateParser(new Date(date.getTime() + certifiedDays*1000*60*60*24L));
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(dateParser.getYear()).append('-').append(dateParser.getMonth()).append('-').append(dateParser.getDayOfMonth());
		setExpiration(stringBuilder.toString());

		// set feature
		for (Map.Entry<String, String> entry : variablesMap.entrySet()) {
			setFeature(entry.getKey(), entry.getValue());
		}
		
		// set signature 
        byte [] pk = ByteHex.convert(privateKey);
        
    	String signature = null;
		try {
			signature = SignatureUtil.sign(format(), pk);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	setSignature(signature);
    	
    	try {
			create(licFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return packageInfo;
	}
	
	private void setSignature(final String s) {
        this.setFeature("Signature", s);
    }
	
    private String getSignature() {
        return this.properties.getProperty("Signature");
    }
    
    private void setExpiration(final String s) {
        this.setFeature("Expiration", s);
    }
    
    private void setFeature(final String s, final String s2) {
    	if (s.isEmpty()) return;
        if (!this.list.contains(s)) {
            this.list.add(s);
        }
        this.properties.setProperty(s, s2);
    }
    
    private String format() {
        final StringBuffer stringBuffer = new StringBuffer();
        
        for (String s : this.list) {
        	if (!StringUtils.isNullOrEmpty(s) && !s.equals("Signature")) {
        		stringBuffer.append(s).append('=').append(this.properties.getProperty(s)).append('\n');
        	}
        }
        this.output = stringBuffer.toString();
        return stringBuffer.toString();
    }
    
    private void create(String filePath) throws Exception {
        if (this.output == null) { // 
        	return;
        }
        
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(filePath);
        
        fileWriter.write(this.output);
        fileWriter.write(10);
        
        String signature = this.getSignature();
	    if (signature != null) {
	        fileWriter.write("Signature");
	        fileWriter.write(61);
	        fileWriter.write(signature);
        }
        fileWriter.flush();
        fileWriter.close();
    }

}
