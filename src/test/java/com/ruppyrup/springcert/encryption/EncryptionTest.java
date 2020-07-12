package com.ruppyrup.springcert.encryption;

import com.ruppyrup.encryption.GenericEncryptionService;
import com.ruppyrup.encryption.IEncryptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptionTest {

    IEncryptionService<String> encryptionService = new GenericEncryptionService<>("secrete", "Blowfish");

    @Test
    void encryptionAndThenDecryptionShouldReturnSameString() throws IllegalAccessException, NoSuchFieldException {
        String data = "this is the secrete";

        String encryptedData = encryptionService.encrypt(data);
        String decryptedData = encryptionService.decrypt(encryptedData);

        Assertions.assertThat(decryptedData).isEqualTo(data);
    }
}