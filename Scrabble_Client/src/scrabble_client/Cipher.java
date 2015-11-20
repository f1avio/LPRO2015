package scrabble_client;

/**@author Adam Kopnicky
 * @author Ewa Godlewska
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cipher {
       private MessageDigest md;

    /**
     * Constructor that initiates the md MessageDigest
     */   
    public Cipher() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Converts a password to an Hex file
     * @param password that will be converted
     * @return converted string
     */
    public String convert (String password)
    {
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        
        //convert the byte to hex format method
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
