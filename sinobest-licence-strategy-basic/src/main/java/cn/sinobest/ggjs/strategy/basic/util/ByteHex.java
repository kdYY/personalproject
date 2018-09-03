package cn.sinobest.ggjs.strategy.basic.util;

public final class ByteHex
{
    
    public static String convert(final byte[] array) {
    	return Base64.getEncoder().encodeToString(array);
    }
    
    public static byte[] convert(final String s) {
    	return Base64.getDecoder().decode(s);
    }
}
