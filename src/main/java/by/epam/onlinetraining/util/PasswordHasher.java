package by.epam.onlinetraining.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    private static final Logger LOGGER = LogManager.getLogger(PasswordHasher.class);

    private PasswordHasher(){
    }


    public static String shaHashing(String password) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "Can't hash the password.", e);
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String hashedPassword = bigInt.toString(16);

        while( hashedPassword.length() < 32 ){
            hashedPassword = "0" + hashedPassword;
        }
        return hashedPassword;
    }
}
