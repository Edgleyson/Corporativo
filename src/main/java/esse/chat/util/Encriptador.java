package esse.chat.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encriptador {

    private final static int ITERATION_NUMBER = 1000;

    public String salgarSenha() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return (Base64.getEncoder().encodeToString(randomBytes));
    }

    public String encriptarSenha(String senhaSalgada) {
        MessageDigest mDigest;
        try {
            //Instanciamos nosso HASH SHA-2
            mDigest = MessageDigest.getInstance("SHA-256");
            
            mDigest.update(senhaSalgada.getBytes(Charset.forName("UTF-8")));
            
            //iterações de hashing para minimizar as chances de ataques de força bruta.
//            byte byteHash[] = mDigest.digest();
//            for (int i = 0; i < ITERATION_NUMBER; i++) {
//                mDigest.reset();
//                byteHash = mDigest.digest(byteHash);
//            }
            
            return(Base64.getEncoder().encodeToString(mDigest.digest()));
            //converter em String com hexadecimais para armazenar no BD
//            StringBuilder hexHash = new StringBuilder();
//            for (byte b : byteHash) {
//                hexHash.append(String.format("%02X", 0xFF & b));
//            }
//
//            return hexHash.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
