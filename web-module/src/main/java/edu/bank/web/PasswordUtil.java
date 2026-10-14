package edu.bank.web;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

final class PasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private PasswordUtil() {}
    static String hash(char[] password) throws Exception {
        byte[] salt = new byte[16]; RANDOM.nextBytes(salt);
        byte[] key = derive(password, salt, 120000);
        return "pbkdf2$120000$" + Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(key);
    }
    static boolean verify(char[] password, String stored) throws Exception {
        String[] p = stored.split("\\$");
        if (p.length != 4 || !"pbkdf2".equals(p[0])) return false;
        byte[] salt = Base64.getDecoder().decode(p[2]);
        byte[] expected = Base64.getDecoder().decode(p[3]);
        byte[] actual = derive(password, salt, Integer.parseInt(p[1]));
        if (actual.length != expected.length) return false;
        int diff = 0;
        for (int i = 0; i < actual.length; i++) diff |= actual[i] ^ expected[i];
        return diff == 0;
    }
    private static byte[] derive(char[] password, byte[] salt, int iterations) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 256);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        finally { spec.clearPassword(); }
    }
}
