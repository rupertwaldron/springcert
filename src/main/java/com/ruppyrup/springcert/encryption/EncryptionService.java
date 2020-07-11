package com.ruppyrup.springcert.encryption;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {

    public String encrypt(String data, String key) {
        try {
            byte[] keyData = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.getEncoder().encode(hasil));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String data, String key) {
        try {
            byte[] keyData = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(hasil);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
