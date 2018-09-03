package cn.sinobest.ggjs.util;

import java.util.*;
import java.io.*;

public class FileUtil
{
    private InputStream a;
    public static int b;
    
    public FileUtil(final String s) {
        this.a = Thread.currentThread().getContextClassLoader().getResourceAsStream(s);
    }
    
    public FileUtil(final InputStream a) {
        this.a = a;
    }
    
    
    public void read(final List list, final Properties properties) throws IOException {
        if (this.a == null) {
            throw new IOException(b("\u0003\u0012\u000b?\fw\u0013\u001dm\u00078\u000e\u0006$\u00070Z\u001a\"I%\u001f\u000f)I1\b\u0001 IyT@"));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(this.a));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().indexOf('#') != 0) {
                if (line.trim().indexOf('!') == 0) {
                    continue;
                }
                int index = line.indexOf('=');
                String s;
                String trim;
                if (index > 0) {
                    s = line.substring(0, index).trim();
                    trim = line.substring(++index).trim();
                }
                else {
                    s = line.trim();
                    trim = "";
                }
                list.add(s);
                properties.setProperty(s, trim);
            }
        }
    }

    
	public static String b(final String s){
		final char [] chars = s.toCharArray();
		final char [] fiveChars = new char[]{'W','z','n','M','i'};
		for(int i =0 ; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ fiveChars[i%5]);
		}
        return new String(chars);
	}
}
