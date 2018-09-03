package cn.sinobest.ggjs.util;

import java.security.spec.*;
import java.security.*;

public class KeyUtil
{
    private static KeyPairGenerator a;
    private static KeyFactory b;
    public static boolean c;
    
    public static PublicKey getPublic(final byte[] array) throws InvalidKeySpecException {
        return KeyUtil.b.generatePublic(new X509EncodedKeySpec(array));
    }
    
    public static PrivateKey getPrivate(final byte[] array) throws InvalidKeySpecException {
        return KeyUtil.b.generatePrivate(new PKCS8EncodedKeySpec(array));
    }
    
    public static KeyPair getKeyPair() {
        return KeyUtil.a.genKeyPair();
    }
    
    static {
        try {
            (KeyUtil.a = KeyPairGenerator.getInstance(a("\u0017&\u001d"))).initialize(1024, new SecureRandom(new SecureRandom().generateSeed(8)));
            KeyUtil.b = KeyFactory.getInstance(a("\u0017&\u001d"));
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
	public static String a(final String s){
		final char [] chars = s.toCharArray();
		final char [] fiveChars = new char[]{'S','u','\\','C','O'};
		for(int i =0 ; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ fiveChars[i%5]);
		}
        return new String(chars);
	}
}
