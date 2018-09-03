package cn.sinobest.ggjs.strategy.basic.util;

import java.security.GeneralSecurityException;
import java.security.Signature;

public class SignatureUtil
{
    private static final String a = "lZ.";

    public static String sign(final String s, final byte[] array) throws GeneralSecurityException {
        final Signature instance = Signature.getInstance(a(a));
        instance.initSign(KeyUtil.getPrivate(array));
        instance.update(s.getBytes());
        final String convert = ByteHex.convert(instance.sign());
        return convert;
    }
    
    public static boolean verify(final String s, final byte[] array, final byte[] array2) throws GeneralSecurityException {
        final Signature instance = Signature.getInstance(a(a));
        instance.initVerify(KeyUtil.getPublic(array2));
        instance.update(s.getBytes());
        return instance.verify(array);
    }
    
	private static String a(final String s){
		final char [] chars = s.toCharArray();
		final char [] fiveChars = new char[]{'(','\t','o','I','J'};
		for(int i =0 ; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ fiveChars[i%5]);
		}
        return new String(chars);
	}
}
