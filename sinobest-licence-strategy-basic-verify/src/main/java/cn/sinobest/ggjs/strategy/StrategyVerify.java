package cn.sinobest.ggjs.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.sinobest.ggjs.exception.LicenseNotFoundException;
import cn.sinobest.ggjs.util.ByteHex;
import cn.sinobest.ggjs.util.DateParser;
import cn.sinobest.ggjs.util.FileUtil;
import cn.sinobest.ggjs.util.MACAddressUtil;
import cn.sinobest.ggjs.util.SignatureUtil;
import cn.sinobest.ggjs.util.StringUtils;

public class StrategyVerify extends AbstractStrategy {

	private static StrategyVerify instance = new StrategyVerify();
	
	private boolean b;
	private static final int d = 86400000;
    private byte[] e;
    private List<String> f = new ArrayList<String>();
    private Properties g = new Properties();

    public static StrategyVerify getInstance() {
		return instance;
	}
	
	public StrategyVerify() {
		String userDir = getClass().getResource("/").toString();
		File f = new File(userDir+a("LDDPSH	YC"));
        try {
        	if (f.exists()) {
        		new FileUtil(new FileInputStream(f)).read(this.f, this.g);
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
		
		initE();
	}
	
	private void initE() {

	}
	
	@Override
	public boolean isValid() throws Exception {
		String str = getFeature(a("sD@[TXUP"));
		if ((str == null) || (str.trim().length() == 0))
			return false;
		boolean bool = SignatureUtil.verify(format(), ByteHex.convert(str), this.e);
		if (!(bool))
			return false;
		return (daysLeft() > 0 && isValidSystemId());
	}

	private boolean isValidSystemId(){
		if (StringUtils.isNullOrEmpty(getFeature(a("sTTAMdC"))))
			return false;
		String check = getFeature(a("sTTAMdC"));
		if (!getFeature(a("sTTAMdC")).equals(getSystemId(true, check)))
			return false;
		return true;
	}
	
	private String format() {
        final StringBuffer stringBuffer = new StringBuffer();
        
        for (String s : this.f) {
        	if (!StringUtils.isNullOrEmpty(s) && !s.equals(a("sD@[TXUP"))) {
        		stringBuffer.append(s).append('=').append(this.g.getProperty(s)).append('\n');
        	}
        }
        return stringBuffer.toString();
    }
	
	@Override
	public Long daysLeft() throws Exception {
		final String expiration;
        final String s = expiration = getFeature("Expiration");
        if (!b) {
            if (expiration == null) {
                return -1L;
            }
            s.trim();
        }
        final int length = expiration.length();
        if (b || length == 0) {
            return (long) length;
        }
        final String s2 = s;
        if (!b && s2.indexOf(a("NHQP\u0000")) != -1) {
            return 0L;
        }
        return (long) (1 + (int)((DateParser.toUtilDate(s2).getTime() - System.currentTimeMillis()) / d));
	}

	@Override
	public String getVariables(String key) throws Exception {
		return getFeature(key);
	}

	private String getFeature(final String s) {
        return this.g.getProperty(s);
    }
	
	@Override
	public Map<String, String> createKey() {
		return null;
	}

	private static String C() {
		String str1 = null;
		try {
			InetAddress localInetAddress = InetAddress.getLocalHost();
			str1 = localInetAddress.getHostName();
		} catch (Exception localException) {
		}
		int i = (StringUtils.isNullOrEmpty(str1)) ? 0 : Math.abs(str1.hashCode());
		String str2 = String.format("%02X", new Object[] { new Integer(i % 256) });
		return str2;
	}
    
    private static String getSystemId(boolean isCheck, String check){
		return C() + new MACAddressUtil().getMACAddress(isCheck, check);
	}
    
	private static String a(final String s){
		final char [] chars = s.toCharArray();
		final char [] fiveChars = new char[]{' ','-','\'','5','r'};
		for(int i =0 ; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ fiveChars[i%5]);
		}
        return new String(chars);
	}
}
